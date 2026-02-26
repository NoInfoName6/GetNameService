package kvv.example.name.client.config;

import kvv.example.name.client.exception.ApiClientException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import kvv.example.name.client.service.GetNameServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@AutoConfiguration
@EnableConfigurationProperties(WebClientProperties.class)
@ConditionalOnProperty(prefix = "api.get-name-client", name = "baseUrl")
public class WebClientConfig {
    @ConditionalOnMissingBean
    @Bean(name = "GetNameServiceClient")
    public GetNameServiceClient getNameServiceClient(@Qualifier("WebHttpClient") WebClient webClient,
                                                     WebClientProperties webClientProperties){
        return new GetNameServiceClient(webClient, webClientProperties);
    }

    @ConditionalOnMissingBean
    @Bean(name = "WebHttpClient")
    public WebClient getNameApiWebClient(WebClientProperties properties) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectTimeout())
                .responseTimeout(Duration.ofMillis(properties.getReadTimeout()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(properties.getReadTimeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(properties.getWriteTimeout(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("User-Agent", "Demo-App/1.0")
                .filter(logRequest())
                .filter(logResponse())
                .filter(errorHandler())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (true) {
                System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
                clientRequest.headers().forEach((name, values) ->
                        values.forEach(value -> System.out.println(name + ": " + value)));
            }
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (true) {
                System.out.println("Response status: " + clientResponse.statusCode());
            }
            return Mono.just(clientResponse);
        });
    }

    private ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new ApiClientException(
                                "API Error: " + clientResponse.statusCode(),
                                clientResponse.statusCode().value(),
                                errorBody
                        )));
            }
            return Mono.just(clientResponse);
        });
    }
}
