package kvv.example.name.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api.get-name-client")
public class WebClientProperties {
    private String baseUrl = "https://localhost:";
    private String userPath = "/api/get-name";
    private int connectTimeout = 5000;
    private int readTimeout = 10000;
    private int writeTimeout = 5000;
    private int maxInMemorySize = 1024 * 1024; // 1MB
    private boolean enableLogging = true;
}
