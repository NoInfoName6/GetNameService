package kvv.example.name.controller;

import kvv.example.name.dto.User;
import kvv.example.name.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/get-name")
public class GetNameController {
    private final UserService userService;

    @GetMapping
    Mono<@NonNull User> getName(){
        return Mono.just(new User("_id","name","email", 18))
                .log("getName");
    }

    @PostMapping("/{numb}")
    Mono<Void> generateUser(@PathVariable Integer numb){
        return userService.generateUser(numb);
    }
}
