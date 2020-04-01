package com.trendyol.distributed.data.cache.core.transport;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import com.trendyol.distributed.data.cache.core.utils.ByteArrayUtil;

import java.io.IOException;
import java.util.Objects;

public class HttpClientWrapper {

    private CloseableHttpClient closeableHttpClient;
    private HttpClientConfiguration httpClientConfiguration;

    {
        String baseUrl = "http://localhost:8083/config/age";
        httpClientConfiguration = new HttpClientConfiguration(baseUrl);
    }

    public HttpClientWrapper(CloseableHttpClient closeableHttpClient) {
        this.closeableHttpClient = closeableHttpClient;
    }

    public byte[] get(String path) {
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(new HttpGet(httpClientConfiguration.getBaseUrl() + path));
            return ByteArrayUtil.toByteArray(closeableHttpResponse.getEntity().getContent());
        } catch (IOException e) {
            System.out.println(e);

        } finally {
            try {
                if (Objects.nonNull(closeableHttpResponse)) {
                    closeableHttpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    public void post(String path, byte[] payload) {
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            HttpPost post = new HttpPost(httpClientConfiguration.getBaseUrl() + path);
            post.setEntity(new ByteArrayEntity(payload));
            closeableHttpResponse = closeableHttpClient.execute(post);
        } catch (IOException e) {
            System.out.println(e);

        } finally {
            try {
                if (Objects.nonNull(closeableHttpResponse)) {
                    closeableHttpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
