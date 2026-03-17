package kvv.example.name.util;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MongoAuthDebugger {

    @Autowired
    private ReactiveMongoTemplate template;

    @EventListener(ApplicationReadyEvent.class)
    public void debugAuth() {
        // Проверяем статус подключения
        template.executeCommand("{ connectionStatus: 1 }")
                .doOnSuccess(result -> {
                    log.info("=== MongoDB Connection Status ===");
                    log.info("Result: {}", result.toJson());

                    // Проверяем, аутентифицирован ли пользователь
                    Document authInfo = result.get("authInfo", Document.class);
                    List<Document> authenticatedUsers = authInfo.getList("authenticatedUsers", Document.class);

                    if (authenticatedUsers.isEmpty()) {
                        log.error("❌ НЕТ аутентифицированных пользователей!");
                    } else {
                        log.info("✅ Аутентифицирован как: {}", authenticatedUsers);
                    }

                    // Проверяем права на базу test
                    template.executeCommand("{ usersInfo: 'mongo_adm' }")
                            .doOnNext(usersInfo -> log.info("User info: {}", usersInfo.toJson()))
                            .subscribe();
                })
                .doOnError(error -> log.error("Failed to get connection status", error))
                .subscribe();
    }
}
