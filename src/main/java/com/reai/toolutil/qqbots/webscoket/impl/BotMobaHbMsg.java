package com.reai.toolutil.qqbots.webscoket.impl;

import com.reai.toolutil.qqbots.base.config.BotEvent;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 王者英雄海报
 * @author 86136
 */
public class BotMobaHbMsg {

    public static void getHaiBaoMsg(String content, String msgId, String sendMsgUrl) {
        String hero = content.split(":")[0];
        if ("王者海报" .equals(hero)) {
            String heroName = content.split(":")[1];
            JSONArray wzHaiBao = BotEvent.getWzHaiBao(heroName);
            for (int i = 0; i < wzHaiBao.length(); i++) {
                JSONObject res = wzHaiBao.optJSONObject(i);
                String pic = res.get("pic").toString();
                JSONObject heroMsg = new JSONObject();
                heroMsg.put("image", pic);
                heroMsg.put("msg_id", msgId);
                BotEvent.sendMsg(sendMsgUrl, heroMsg);
            }
        }
    }
}
