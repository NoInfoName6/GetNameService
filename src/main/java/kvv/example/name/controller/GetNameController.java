package kvv.example.name.controller;

import kvv.example.name.dto.User;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/get-name")
public class GetNameController {

    @GetMapping
    Mono<@NonNull User> getName(){
        return Mono.just(new User("Bob"))
                .log("getName");
    }
}
