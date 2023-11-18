package com.reai.toolutil.qqbots.webscoket.impl;

import com.reai.toolutil.qqbots.base.config.BotEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;

/**
 * AI语言模型
 *
 * @author 86136
 */
public class BotChatGptMsg {

    /**
     * 定义输出使用的功能列表
     */
    private static final Set<String> MSG_CONTENT = new HashSet<>(Arrays.asList(
        "图片",
        "<@!2745424269720774961> /功能",
        "启用定时新闻",
        "关闭定时新闻"
    ));

    private static final Set<String> MSG_MH_CONTENT = new HashSet<>(Arrays.asList(
        "天气",
        "王者海报"
    ));

    public static void getChatGptMsg(String content, String msgId, String sendMsgUrl, String id) {
        String msgMh = content.split(":")[0];
        if (!MSG_CONTENT.contains(content) && !MSG_MH_CONTENT.contains(msgMh)) {
            JSONObject msgContent = new JSONObject();
            String gptMsg = BotEvent.getGpt3(content);
            msgContent.put("content", "<@!" + id + ">" + gptMsg);
            msgContent.put("msg_id", msgId);
            BotEvent.sendMsg(sendMsgUrl, msgContent);
        }
    }
}
