package com.reai.toolutil;

import org.b3log.latke.util.CollectionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 86136
 */
@Component
public class Reais {

    public static String VERSION = "0.0.1";
    public static String token;
    public static String userId;
    public static String repoName;
    public static String filePath;

    /**
     * email
     */
    public static String userName;
    public static String emailPwd;


    /**
     * token
     */
    @Value("${BotToken}")
    public static String authorization_header;

    /**
     * User-Agent.
     */
    public static final String USER_AGENT = "REAI/" + VERSION + "; +https://github.com/xpnobug";

    /**
     * 将指定的文件名进行清理。
     *
     * @param unsanitized 指定的文件名
     * @return 清理后的文件名
     */
    public static String sanitizeFilename(final String unsanitized) {
        return unsanitized.
            replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5\\.)]", "").
            replaceAll("[\\?\\\\/:|<>\\*\\[\\]\\(\\)\\$%\\{\\}@~]", "").
            replaceAll("\\s", "");
    }

    /**
     * 从指定的源对象克隆JSON对象。
     *
     * @param src 指定的源对象
     * @return 克隆的对象
     */
    public static JSONObject clone(final JSONObject src) {
        return new JSONObject(src, CollectionUtils.jsonArrayToArray(src.names(), String[].class));
    }

    @Value("${gitHubToken}")
    public void setToken(String token) {
        Reais.token = token;
    }

    @Value("${gitHubName}")
    public void setUserId(String userId) {
        Reais.userId = userId;
    }

    @Value("${gitHubRepoId}")
    public void setRepoName(String repoName) {
        Reais.repoName = repoName;
    }

    @Value("${gitHubFilePath}")
    public void setFilePath(String filePath) {
        Reais.filePath = filePath;
    }

    @Value("${emailUserName}")
    public void setUserName(String userName) {
        Reais.userName = userName;
    }
    @Value("${emailPwd}")
    public void setEmailPwd(String emailPwd) {
        Reais.emailPwd = emailPwd;
    }


    @Value("${BotToken}")
    public void setAuthorizationHeader(String authorization_header) {
        Reais.authorization_header = authorization_header;
    }

}
