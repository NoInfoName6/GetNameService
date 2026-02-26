package kvv.example.name.client.exception;

import lombok.Getter;

@Getter
public class ApiClientException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;

    public ApiClientException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public ApiClientException(String message, int statusCode) {
        this(message, statusCode, null);
    }
}
