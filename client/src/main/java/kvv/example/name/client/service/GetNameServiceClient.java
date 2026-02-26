package kvv.example.name.client.service;


import kvv.example.name.client.config.WebClientProperties;
import kvv.example.name.client.dto.UserResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GetNameServiceClient extends ApiClient {

    private final WebClientProperties webClientProperties;

    public GetNameServiceClient(WebClient webClient, WebClientProperties webClientProperties) {
        super(webClient, webClientProperties.getUserPath());
        this.webClientProperties = webClientProperties;
    }

    public Mono<UserResponse> getName(){
        return webClient.get()
                .uri(webClientProperties.getUserPath())
                .retrieve()
                .bodyToMono(UserResponse.class);
    }
}
