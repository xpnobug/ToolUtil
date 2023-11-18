package com.reai.toolutil.controller;

import com.reai.toolutil.Reais;
import com.reai.toolutil.qqbots.webscoket.MyWebSocketClient;
import com.reai.toolutil.githubtool.service.impl.StartServiceImpl;
import com.reai.toolutil.sendEmail.config.MailConfig;
import com.reai.toolutil.sendEmail.constant.EmailContentTypeEnum;
import com.reai.toolutil.sendEmail.constant.SmtpHostEnum;
import com.reai.toolutil.sendEmail.core.MiniEmail;
import com.reai.toolutil.sendEmail.core.MiniEmailFactory;
import com.reai.toolutil.sendEmail.core.MiniEmailFactoryBuilder;
import com.reai.toolutil.sendEmail.service.FreemarkerView;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 86136
 */
@RestController
@RequestMapping("/tool")
public class TestController {

    /**
     * 使用 ConcurrentHashMap 存储 WebSocketClient 实例
     */
    private final ConcurrentHashMap<String, MyWebSocketClient> webSocketClients = new ConcurrentHashMap<>();
    @Autowired
    public FreemarkerView freemarkerView;

    @Autowired
    private StartServiceImpl startService;

    /**
     * 该邮箱修改为你需要测试的收件邮箱地址
     */
    private static final String TO_EMAIL = "2877406366@qq.com";
    /**
     * 发送邮件给多个收件人
     */
    private static final String[] TO_EMAILS = new String[]{"669307582@qq.com", "1919101871@qq.com"};

    MiniEmailFactory miniEmailFactory;

    @GetMapping("/startGithubBf")
    public JSONObject updateGithub(String githubId, String githubPAT) {
        startService.start();
        return new JSONObject();
    }

    @GetMapping("/startQQBot")
    @ResponseBody
    public String startQQBot() {
        try {
            // 判断是否已存在连接
            if (!webSocketClients.containsKey("qqBot")) {
                MyWebSocketClient client = new MyWebSocketClient(
                    new URI("wss://api.sgroup.qq.com/websocket"));
                client.connect();
                // 将新的 WebSocketClient 放入 Map 中
                webSocketClients.put("qqBot", client);
                sendEmail();
                return "WebSocket 连接已创建";
            } else {
                return "WebSocket 连接正在运行中...";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "连接失败：" + e.getMessage();
        }
    }

    public void sendEmail() throws Exception {
        String html = freemarkerView.createHtml(2);
        // 创建工厂类
        miniEmailFactory = new MiniEmailFactoryBuilder().build(
            MailConfig.config(Reais.userName, Reais.emailPwd)
                .setMailDebug(Boolean.TRUE)
                .setSenderNickname("QQBot")
                .setMailSmtpHost(SmtpHostEnum.SMTP_QQ)
        );
        MiniEmail miniEmail = miniEmailFactory.init();
        miniEmail
            .addBlindCarbonCopy(new String[]{TO_EMAIL})
            .send(TO_EMAILS, "QQBot启动通知", EmailContentTypeEnum.HTML, html);
    }

}
