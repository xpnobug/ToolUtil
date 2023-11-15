package com.reai.qqbots.test;

import com.alibaba.fastjson.JSONObject;
import com.reai.toolutil.Reais;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import com.alibaba.fastjson.JSON;

public class qqbats {

    public static void main(String[] args) {
        Map<String, Object> requestBody = new HashMap<>();
        String jsonBody = JSON.toJSONString(requestBody);
        JSONObject bodyJson = JSON.parseObject(jsonBody);
        String appId = bodyJson.get("appId").toString();
        final HttpResponse res = HttpRequest.post("https://bots.qq.com/app/getAppAccessToken")
            .connectionTimeout(20000)
            .timeout(60000)
            .header("Content-Type", "application/json")
            .body(String.valueOf(jsonBody))
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("false");
        }
        res.charset("UTF-8");
        String s = res.bodyText();
        JSONObject jsonObject = JSON.parseObject(s);
        String accessToken = jsonObject.get("access_token").toString();
//        getAuthorization(appId,accessToken);
        getUserme(accessToken);
    }

    public static void getAuthorization(String appid, String accessToken){
        final HttpResponse res = HttpRequest.post("https://api.sgroup.qq.com")
            .connectionTimeout(20000)
            .timeout(60000)
            .header("Content-Type", "application/json")
            .header("Authorization","QQBot "+accessToken)
            .header("X-Union-Appid", appid)
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("false");
        }
        res.charset("UTF-8");
        String s = res.bodyText();
        System.out.println(s);
    }


    public static void getUserme(String accessToken){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("appId", "102072731");
        requestBody.put("Authorization", "QQBot "+accessToken);
        String jsonBody = JSON.toJSONString(requestBody);
        JSONObject bodyJson = JSON.parseObject(jsonBody);
        String token = bodyJson.get("Authorization").toString();
        final HttpResponse res = HttpRequest.get("https://api.sgroup.qq.com/users/@me")
            .connectionTimeout(20000)
            .timeout(60000)
            .header("Content-Type", "application/json")
            .header("Authorization","QQBot "+accessToken)
            .body(String.valueOf(jsonBody))
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("false");
        }
        res.charset("UTF-8");
        String s = res.bodyText();
        System.out.println(s);
    }

}
