package org.NasaTelegramBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

// отправка запросов в Nasa и получение ответов(возвращает ссылку на нашу картинку)
public class Utils {
   private static CloseableHttpClient httpClient = HttpClientBuilder.create() // Настраиваем наш http клиент. этот объект делает запросы
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(30000)
                    .setRedirectsEnabled(false)
                    .build())
            .build();

    public static final ObjectMapper mapper = new ObjectMapper(); // преобразовывает сторку JSON в NasaObject

    public static String getUrl(String uri) throws IOException {
        CloseableHttpResponse response = httpClient.execute(new HttpGet(uri));
        NasaObject nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
        return nasaObject.getUrl();
    }

}
