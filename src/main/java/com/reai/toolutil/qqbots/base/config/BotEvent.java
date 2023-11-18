package com.reai.toolutil.qqbots.base.config;

import static com.reai.toolutil.qqbots.base.config.BaseFinal.CONNECTION_TIMEOUT;
import static com.reai.toolutil.qqbots.base.config.BaseFinal.GATEWAY_BOT_URL;
import static com.reai.toolutil.qqbots.base.config.BaseFinal.REQUEST_TIMEOUT;

import com.reai.toolutil.Reais;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.http.HttpServletResponse;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * api事件
 *
 * @author 86136
 */
public class BotEvent {

    /**
     * 发送消息
     *
     * @param url
     * @param msgJson
     */
    public static void sendMsg(String url, JSONObject msgJson) {
        final HttpResponse res = HttpRequest.post(url)
            .connectionTimeout(CONNECTION_TIMEOUT)
            .timeout(REQUEST_TIMEOUT)
            .header("Content-Type", "application/json")
            .header("Authorization", Reais.authorization_header)
            .bodyText(msgJson.toString(), "application/json")
            .send();

        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("消息发送失败:" + res.bodyText());
        } else {
            System.out.println("消息发送成功:" + msgJson.toString());
        }
    }

    /**
     * 获取带分片 WSS 接入点
     */
    public static JSONObject fetchGatewayBotInfo() {
        try {
            final HttpResponse res = HttpRequest.get(GATEWAY_BOT_URL)
                .connectionTimeout(CONNECTION_TIMEOUT)
                .timeout(REQUEST_TIMEOUT)
                .header("Content-Type", "application/json")
                .header("Authorization", Reais.authorization_header)
                .send();
            if (HttpServletResponse.SC_OK != res.statusCode()) {
                System.out.println("请求失败，状态码：" + res.statusCode());
            } else {
                String responseBody = res.bodyText();
                JSONObject jsonObject = new JSONObject(responseBody);
                int recommendedShards = jsonObject.getInt("shards");
                JSONObject sessionStartLimit = jsonObject.getJSONObject("session_start_limit");
                int maxConcurrency = sessionStartLimit.getInt("max_concurrency");
                BigInteger guildId = new BigInteger("16093159052943820804");
                int shardId = BaseFinal.calculateShardId(guildId, recommendedShards);

                JSONObject properties = new JSONObject();
                properties.put("$os", "linux");
                properties.put("$browser", "my_library");
                properties.put("$device", "my_library");

                JSONObject payload = new JSONObject();
                payload.put("op", 2);
                JSONObject data = new JSONObject();
                data.put("token", Reais.authorization_header);
                data.put("intents", 512);
                data.put("shard", new JSONArray(new int[]{shardId, maxConcurrency}));
                data.put("properties", properties);

                payload.put("d", data);
                return payload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求或解析失败");
        }
        return null;
    }

    /**
     * 随机唱鸭
     *
     * @return
     */
    public static String getcyApi() {
        final HttpResponse res = HttpRequest.get("https://ybapi.cn/API/cy.php")
            .connectionTimeout(CONNECTION_TIMEOUT)
            .timeout(REQUEST_TIMEOUT)
            .header("Content-Type", "application/json")
            .send();

        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("请求失败，状态码：" + res.statusCode());
        } else {
            String responseBody = res.bodyText();
            JSONObject jsonObject = new JSONObject(responseBody);
            String data = jsonObject.get("data").toString();
            JSONObject result = new JSONObject(data);
            return result.get("song_url").toString();
        }
        return null;
    }

    /**
     * Chat-3.5模型,文字语音双重模式,无需sk,可支持连续对话 type	String	是	填 1 是返回文字,填 2 是返回语音
     *
     * @param content
     * @return
     */
    //https://ybapi.cn/API/gpt.php?type=1&msg=你好
    public static String getGpt3(String content) {
        final HttpResponse res = HttpRequest.get(
                "http://ovoa.cc/api/Bing.php?msg=" + content + "?&model=down&type=json")
            .connectionTimeout(CONNECTION_TIMEOUT)
            .timeout(REQUEST_TIMEOUT)
            .header("Content-Type", "application/json")
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("请求失败，状态码：" + res.statusCode());
        } else {
            res.charset("UTF-8");
            String responseBody = res.bodyText();
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.get("content").toString();
        }
        return null;
    }

    /**
     * 全球天气
     *
     * @param content
     * @return
     */
    //http://ovoa.cc/api/tianqi.php?msg=广东&n=1
    public static String getWeather(String content, String n) {
        String url;
        if (n == null) {
            url = "http://ovoa.cc/api/tianqi.php?msg=" + content;
        } else {
            url = "http://ovoa.cc/api/tianqi.php?msg=" + content + "&n=" + n;
        }
        final HttpResponse res = HttpRequest.get(url)
            .connectionTimeout(CONNECTION_TIMEOUT)
            .timeout(REQUEST_TIMEOUT)
            .header("Content-Type", "application/json")
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("请求失败，状态码：" + res.statusCode());
        } else {
            res.charset("UTF-8");
            return res.bodyText();
        }
        return null;
    }

    /**
     * 口吐芬芳
     *
     * @return
     */
    //http://ovoa.cc/api/ktff.php
    public static String getVocal() {
        final HttpResponse res = HttpRequest.get(
                "http://ovoa.cc/api/ktff.php")
            .connectionTimeout(CONNECTION_TIMEOUT)
            .timeout(REQUEST_TIMEOUT)
            .header("Content-Type", "application/json")
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("请求失败，状态码：" + res.statusCode());
        } else {
            res.charset("UTF-8");
            return res.bodyText();
        }
        return null;
    }

    /**
     * 王者海报
     *
     * @param content
     * @return
     */
    //http://kloping.top/api/get/pvp-skin?name=瑶
    public static JSONArray getWzHaiBao(String content) {
        final HttpResponse res = HttpRequest.get(
                "http://kloping.top/api/get/pvp-skin?name=" + content)
            .connectionTimeout(CONNECTION_TIMEOUT)
            .timeout(REQUEST_TIMEOUT)
            .header("Content-Type", "application/json")
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("请求失败，状态码：" + res.statusCode());
        } else {
            res.charset("UTF-8");
            String responseBody = res.bodyText();
            return new JSONArray(responseBody);
        }
        return null;
    }

    //https://v2.api-m.com/api/random4kPic?type=acg
    //https://v.api.aa1.cn/api/api-fj-2/index.php?aa1=yuantu
    public static String getRandom4kPic() {
        String[] type = {"acg"};//, "wallpaper"
        Random random = new Random();
        int randomIndex = random.nextInt(type.length);
        String randomType = type[randomIndex];
        final HttpResponse res = HttpRequest.get(
                "https://api.52vmy.cn/api/img/pc")
            .header("Content-Type", "application/json")
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("请求失败，状态码：" + res.statusCode());
        } else {
            res.charset("UTF-8");
            String responseBody = res.bodyText();
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.get("url").toString();
        }
        return null;
    }

    /**
     * 每日新闻快讯v5版-带天气播报
     *
     * @return
     */
    //https://www.apii.cn/api/60s-v5/?city=%E5%8C%97%E4%BA%AC
    public static String newsv5() {
        final HttpResponse res = HttpRequest.get(
                "https://www.apii.cn/api/60s-v5/")
            .header("Content-Type", "application/json")
            .send();
        if (HttpServletResponse.SC_OK != res.statusCode()) {
            System.out.println("请求失败，状态码：" + res.statusCode());
        } else {
//            res.charset("UTF-8");
            String responseBody = res.bodyText();
//            JSONObject jsonObject = new JSONObject(responseBody);
            return responseBody;
        }
        return null;
    }

    /**
     * 压缩字节数组
     *
     * @param data 要压缩的字节数组
     * @return 压缩后的字节数组
     * @throws Exception 压缩过程中可能抛出的异常
     */
    private static byte[] compress(byte[] data) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }

    /**
     * 解压缩字节数组
     *
     * @param compressedData 要解压缩的字节数组
     * @return 解压缩后的字节数组
     * @throws Exception 解压缩过程中可能抛出的异常
     */
    private static byte[] decompress(byte[] compressedData) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
        GZIPInputStream gis = new GZIPInputStream(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = gis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        gis.close();
        bos.close();
        return bos.toByteArray();
    }

}
