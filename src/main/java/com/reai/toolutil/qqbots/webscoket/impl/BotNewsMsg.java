package com.reai.toolutil.qqbots.webscoket.impl;

import com.reai.toolutil.qqbots.base.config.BotEvent;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/**
 * 新闻消息
 *
 * @author 86136
 */
public class BotNewsMsg {

    private static ScheduledExecutorService scheduler;

    public static void newsv5(String sendMsgUrl, String content) {
        if ("启用定时新闻" .equals(content)) {
            startScheduledTask(sendMsgUrl);
        } else if ("关闭定时新闻" .equals(content)) {
            stopScheduledTask(sendMsgUrl);
        }
    }

    private static void startScheduledTask(String sendMsgUrl) {
        if (scheduler != null && !scheduler.isShutdown()) {
            System.out.println("定时新闻已启用，无需重复启动。");
            JSONObject newMsg = new JSONObject();
            newMsg.put("content", "定时新闻已启用，无需重复启动。");
            BotEvent.sendMsg(sendMsgUrl, newMsg);
            return;
        }

        LocalTime now = LocalTime.now();
        LocalTime midnight = LocalTime.of(0, 0);
        long initialDelay = Duration.between(now, midnight).toMinutes();

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> sendScheduledMessage(sendMsgUrl), initialDelay,
            TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    private static void stopScheduledTask(String sendMsgUrl) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("定时新闻已关闭。");
            JSONObject newMsg = new JSONObject();
            newMsg.put("content", "定时新闻已关闭。");
            BotEvent.sendMsg(sendMsgUrl, newMsg);
        } else {
            System.out.println("定时新闻未启用，无需关闭。");
            JSONObject newMsg = new JSONObject();
            newMsg.put("content", "定时新闻未启用，无需关闭。");
            BotEvent.sendMsg(sendMsgUrl, newMsg);
        }
    }

    private static void sendScheduledMessage(String sendMsgUrl) {
        JSONObject newMsg = new JSONObject();
        newMsg.put("content", "定时新闻已启用，我会每天凌晨12点准时发送，需要关闭请发送：关闭定时新闻");
        newMsg.put("image", "https://zj.v.api.aa1.cn/api/60s-v2/?cc=%E5%B0%8F%E4%B8%83");
        BotEvent.sendMsg(sendMsgUrl, newMsg);
    }
}
