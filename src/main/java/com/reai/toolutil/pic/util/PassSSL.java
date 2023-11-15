package com.reai.toolutil.pic.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * @author 86136
 */
public class PassSSL {

    /**
     * 设置可访问HTTPS
     *
     * @return null
     */
    public CloseableHttpClient createSSLClientDefault() {
        try {
            //信任所有
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                (chain, authType) -> true).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            return HttpClients
                .custom()
                .setSSLSocketFactory(sslsf)
                .build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
}
