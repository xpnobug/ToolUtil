package com.reai.toolutil.qqbots.webscoket.impl;

import com.reai.toolutil.qqbots.base.config.BotEvent;
import org.json.JSONObject;

/**
 * 全球天气
 *
 * @author 86136
 */
public class BotWeatherMsg {

    public static void getWeatherMsg(String content, String msgId, String sendMsgUrl) {
        String tq = content.split(":")[0];
        if ("天气" .equals(tq)) {
            JSONObject msgContent = new JSONObject();
            String weatherName = content.split(":")[1];
            String weather = BotEvent.getWeather(weatherName, "1");
            msgContent.put("content", weather);
            msgContent.put("msg_id", msgId);
            BotEvent.sendMsg(sendMsgUrl, msgContent);
        }
    }
}
