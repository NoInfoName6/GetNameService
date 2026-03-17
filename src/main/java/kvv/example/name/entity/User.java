package kvv.example.name.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "app_user")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private Integer age;
}
