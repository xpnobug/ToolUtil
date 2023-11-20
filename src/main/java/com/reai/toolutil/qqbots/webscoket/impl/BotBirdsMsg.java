package com.reai.toolutil.qqbots.webscoket.impl;

import com.reai.toolutil.qqbots.base.config.BotEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/**
 * 《飞鸟集》
 *
 * @author 86136
 */
public class BotBirdsMsg {

    private static ScheduledExecutorService scheduler;

    public static void sendMsg(String content, String msgId, String sendMsgUrl, String id) {
        if (content != null) {
            return;
        }

        // 创建一个新的定时线程池
        scheduler = Executors.newScheduledThreadPool(1);

        // 启动定时任务，每过一分钟执行一次
        scheduler.scheduleAtFixedRate(() -> getBirds(sendMsgUrl), 0, 1, TimeUnit.MINUTES);
    }

    private static void getBirds(String sendMsgUrl) {
        String birds = BotEvent.getBirds();
        JSONObject content = new JSONObject();
        content.put("content", birds);
        BotEvent.sendMsg(sendMsgUrl, content);
    }
}
