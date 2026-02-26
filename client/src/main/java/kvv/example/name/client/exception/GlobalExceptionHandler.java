package kvv.example.name.client.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiClientException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleApiClientException(ApiClientException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatusCode())
                .error("API Client Error")
                .message(ex.getMessage())
                .build();

        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(error));
    }
}
