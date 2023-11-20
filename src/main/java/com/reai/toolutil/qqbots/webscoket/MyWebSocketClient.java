package com.reai.toolutil.qqbots.webscoket;

import com.reai.toolutil.Reais;
import com.reai.toolutil.qqbots.base.config.BaseFinal;
import com.reai.toolutil.qqbots.base.config.BotEvent;
import com.reai.toolutil.qqbots.webscoket.impl.BotBaseFeatureMsg;
import com.reai.toolutil.qqbots.webscoket.impl.BotChatGptMsg;
import com.reai.toolutil.qqbots.webscoket.impl.BotMobaHbMsg;
import com.reai.toolutil.qqbots.webscoket.impl.BotNewsMsg;
import com.reai.toolutil.qqbots.webscoket.impl.BotWeatherMsg;
import com.reai.toolutil.qqbots.webscoket.impl.MessageImpl;
import com.reai.toolutil.qqbots.webscoket.impl.ScheduledTaskMsg;
import java.util.Random;
import org.json.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 86136
 */
public class MyWebSocketClient extends WebSocketClient {

    private static final Random random = new Random();

    /**
     * 使用 ScheduledExecutorService 代替 Timer，用于定时发送心跳
     */
    private ScheduledExecutorService heartbeatScheduler;
    private int reconnectAttempts = 0;

    /**
     * 保存会话信息和消息序列号
     */
    private String sessionId = null;
    private Long lastSequenceNumber = null;

    /**
     * 构造方法，传入 WebSocket 服务器的 URI
     *
     * @param serverUri
     */
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("WebSocket 已连接");
        // 检查 WebSocket 是否已连接
        if (!isOpen()) {
            System.out.println("WebSocket 连接已关闭");
            return;
        }
        // 每30秒发送一次心跳
        long heartbeatInterval = 30000;
        startHeartbeat(heartbeatInterval);

        // 获取并发送网关信息
        JSONObject getWayBotInfo = BotEvent.fetchGatewayBotInfo();
        if (!ObjectUtils.isEmpty(getWayBotInfo)) {
            send(getWayBotInfo.toString());
        }

        // 重置重连尝试次数
        reconnectAttempts = 0;
    }

    @Override
    public void onMessage(String message) {
//        System.out.println("收到消息: " + message);
        try {
            // 解析收到的消息
            JSONObject jsonMessage = new JSONObject(message);
            if (jsonMessage.has("s")) {
                lastSequenceNumber = jsonMessage.getLong("s");
                String d = jsonMessage.get("d").toString();
                JSONObject jsonObject = new JSONObject(d);

                // 更新会话 ID
                if (jsonObject.has("session_id") && !jsonObject.isNull("session_id")) {
                    sessionId = jsonObject.getString("session_id");
                }
                // 处理消息内容
                if (jsonObject.has("content") && !jsonObject.isNull("content")) {
                    String content = jsonObject.get("content").toString();
                    String channelId = jsonObject.get("channel_id").toString();
                    String msgId = jsonObject.get("id").toString();
                    String user = jsonObject.get("author").toString();
                    JSONObject userId = new JSONObject(user);
                    String id = userId.get("id").toString();
                    String username = userId.get("username").toString();
                    System.out.println("收到消息: " + username + "---" + content);

                    String sendMsgUrl = BaseFinal.getSendMsgUrl(channelId, null);
                    // 发送不同的消息
                    sendMsgsMethod(content, msgId, sendMsgUrl, id);

                }
            }
        } catch (Exception e) {
            System.err.println("解析消息时发生错误: " + e.getMessage());
        }
    }

    private void sendMsgsMethod(String content, String msgId, String sendMsgUrl, String id) {
        BotBaseFeatureMsg.feature(sendMsgUrl, content);
        //AI语言模型
        BotChatGptMsg.getChatGptMsg(content, msgId, sendMsgUrl, id);
        //音乐
        MessageImpl.getMusic(content, msgId, sendMsgUrl, id);
        //全球天气
        BotWeatherMsg.startScheduledTask(content, msgId, sendMsgUrl);
        //王者海报
        BotMobaHbMsg.getHaiBaoMsg(content, msgId, sendMsgUrl);
        //定时新闻
        BotNewsMsg.newsv5(sendMsgUrl, content);
        //随机图片
        MessageImpl.getRandomPic(sendMsgUrl, content);

        ScheduledTaskMsg.sendMsg(content, msgId, sendMsgUrl);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket 已关闭: " + reason);
        // 关闭心跳任务
        stopHeartbeat();

        // 最大重连尝试次数
        int maxReconnectAttempts = 10;
        if (reconnectAttempts < maxReconnectAttempts) {
            // 启动新线程进行重新连接
            Executors.newSingleThreadExecutor().execute(this::reconnect);
            System.out.println("WebSocket 重新连接成功！");
            reconnectAttempts++;
        } else {
            System.out.println("超过最大重连尝试次数，不再重连");
        }
    }

    /**
     * 启动心跳任务
     *
     * @param interval
     */
    private void startHeartbeat(long interval) {
        if (heartbeatScheduler != null) {
            heartbeatScheduler.shutdown();
        }
        heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();
        heartbeatScheduler.scheduleAtFixedRate(this::sendHeartbeat, 0, interval,
            TimeUnit.MILLISECONDS);
    }

    /**
     * 停止心跳任务
     */
    private void stopHeartbeat() {
        if (heartbeatScheduler != null) {
            heartbeatScheduler.shutdown();
            heartbeatScheduler = null;
        }
    }

    // 发送心跳
    private void sendHeartbeat() {
        JSONObject payload = new JSONObject();
        // OpCode 1 用于心跳
        payload.put("op", 1);
        // 使用最新的消息序列号，如果是第一次连接，使用 null
        payload.put("d", lastSequenceNumber);
        send(payload.toString());
    }

    /**
     * 发送 Resume 消息，恢复连接
     */
    private void sendResume() {
        try {
            System.out.println("尝试重新连接...");

            JSONObject payload = new JSONObject();
            // OpCode 6 用于 Resume 消息
            payload.put("op", 6);
            JSONObject data = new JSONObject();
            // 鉴权令牌
            data.put("token", Reais.authorization_header);
            // 先前存储的会话 ID
            data.put("session_id", sessionId);
            // 最后一个消息的序列号
            data.put("seq", lastSequenceNumber);
            payload.put("d", data);
            send(payload.toString());

            System.out.println("重新连接成功！");
        } catch (Exception e) {
            System.err.println("重新连接发生错误: " + e.getMessage());
        }
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("发生错误: " + ex.getMessage());
    }

}
