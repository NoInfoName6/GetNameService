package kvv.example.name.client.service;

import kvv.example.name.client.exception.ApiClientException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public abstract class ApiClient {

    protected final WebClient webClient;
    protected final String basePath;

    protected ApiClient(WebClient webClient, String basePath) {
        this.webClient = webClient;
        this.basePath = basePath;
    }

    protected <T> Mono<T> get(String path, Class<T> responseType) {
        return webClient.get()
                .uri(basePath + path)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(this::mapError);
    }

    protected <T, R> Mono<T> post(String path, R request, Class<T> responseType) {
        return webClient.post()
                .uri(basePath + path)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(this::mapError);
    }

    protected <T, R> Mono<T> put(String path, R request, Class<T> responseType) {
        return webClient.put()
                .uri(basePath + path)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(this::mapError);
    }

    protected Mono<Void> delete(String path) {
        return webClient.delete()
                .uri(basePath + path)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(this::mapError);
    }

    private Throwable mapError(Throwable throwable) {
        if (throwable instanceof ApiClientException) {
            return throwable;
        }
        return new ApiClientException(
                "Network error: " + throwable.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}
