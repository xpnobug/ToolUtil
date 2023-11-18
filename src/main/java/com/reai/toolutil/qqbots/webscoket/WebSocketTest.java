package com.reai.toolutil.qqbots.webscoket;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import java.net.URI;

/**
 * @author 86136
 */
@EnableEncryptableProperties
public class WebSocketTest {
    public static void main(String[] args) {
        try {
            MyWebSocketClient client = new MyWebSocketClient(new URI("wss://api.sgroup.qq.com/websocket"));
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
