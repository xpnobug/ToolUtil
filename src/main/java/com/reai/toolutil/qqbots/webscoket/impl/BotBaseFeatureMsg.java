package com.reai.toolutil.qqbots.webscoket.impl;

import com.reai.toolutil.qqbots.base.config.BotEvent;
import org.json.JSONObject;

/**
 * 功能列表
 *
 * @author 86136
 */
public class BotBaseFeatureMsg {

    public static void feature(String sendMsgUrl, String content) {
        if ("<@!2745424269720774961> /功能" .equals(content)) {
            JSONObject heroMsg = new JSONObject();
            heroMsg.put("content", "我有好多功能\n 1.图片  \n 2.王者海报 如：(王者海报:妲己)\n 3.天气 如：(天气:北京)");
            BotEvent.sendMsg(sendMsgUrl, heroMsg);
        }
    }
}
