package com.reai.toolutil.qqbots.webscoket.impl;

import com.reai.toolutil.qqbots.base.config.BotEvent;
import org.json.JSONObject;

/**
 * @author 86136
 */
public class MessageImpl {

    public static void getMusic(String content, String msgId, String sendMsgUrl, String id) {
        if ("music" .equals(content)) {
            JSONObject msgContent = new JSONObject();
            String wyy = BotEvent.getcyApi();
            msgContent.put("content", "<@!" + id + ">" + wyy);
            msgContent.put("msg_id", msgId);
            BotEvent.sendMsg(sendMsgUrl, msgContent);
        }
    }

    public static void getRandomPic(String sendMsgUrl, String content) {
        if ("图片" .equals(content)) {
            String random4kPic = BotEvent.getRandom4kPic();
            JSONObject imgMsg = new JSONObject();
            imgMsg.put("image", random4kPic);
            BotEvent.sendMsg(sendMsgUrl, imgMsg);
        }
    }

}
