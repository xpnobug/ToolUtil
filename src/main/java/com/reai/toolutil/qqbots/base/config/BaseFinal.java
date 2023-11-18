package com.reai.toolutil.qqbots.base.config;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author 86136
 */
public class BaseFinal {

    /**
     * 统一地址 https://api.sgroup.qq.com
     */
    public static final String BASE_URL = "https://api.sgroup.qq.com";

    /**
     * 获取带分片 WSS 接入点api
     */
    public static final String GATEWAY_BOT_URL = BASE_URL + "/gateway/bot";

    /**
     * 发送消息api
     */
    public static final String SEND_MSG = BASE_URL + "/channels/%s/messages";

    public static final int CONNECTION_TIMEOUT = 20000;
    public static final int REQUEST_TIMEOUT = 60000;

    /**
     * 发送消息url
     * @param channelId
     * @param messageId
     * @return
     */
    public static String getSendMsgUrl(String channelId, String messageId) {
        return String.format(SEND_MSG, channelId);
    }

    /**
     * 按照频道id进行哈希的，同一个频道的信息会固定从同一个链接推送。具体哈希计算规则如下： shard_id = (guild_id >> 22) % num_shards
     *
     * @param guildId
     * @param numShards
     * @return
     */
    public static int calculateShardId(BigInteger guildId, int numShards) {
        return guildId.shiftRight(22).mod(BigInteger.valueOf(numShards)).intValue();
    }
}
