package com.tdns.toks.core.common.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

public class WebClientFactory {
    public static WebClient generate(
            String baseUrl,
            Integer connectionTimeoutMillis,
            Long readTimeoutMillis,
            Long writeTimeoutMillis) {
        var clientHttpConnector =
                createFactory(connectionTimeoutMillis, readTimeoutMillis, writeTimeoutMillis);
        return WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(codecs -> codecs.defaultCodecs().enableLoggingRequestDetails(true))
                .clientConnector(clientHttpConnector)
                .build();
    }

    private static ClientHttpConnector createFactory(
            Integer connectionTimeoutMillis, Long readTimeoutMillis, Long writeTimeoutMillis) {
        var httpClient = httpClient(connectionTimeoutMillis, readTimeoutMillis, writeTimeoutMillis);
        return new ReactorClientHttpConnector(httpClient);
    }

    private static HttpClient httpClient(
            Integer connectionTimeoutMillis, Long readTimeoutMillis, Long writeTimeoutMillis) {
        var httpClient =
                HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMillis)
                        .doOnConnected(
                                connection ->
                                        connection
                                                .addHandlerLast(
                                                        new ReadTimeoutHandler(
                                                                readTimeoutMillis,
                                                                TimeUnit.MILLISECONDS))
                                                .addHandlerLast(
                                                        new WriteTimeoutHandler(
                                                                writeTimeoutMillis,
                                                                TimeUnit.MILLISECONDS)));

        httpClient.warmup().block();
        httpClient.compress(true);

        return httpClient;
    }
}
