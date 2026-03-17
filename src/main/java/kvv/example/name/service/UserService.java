package kvv.example.name.service;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> generateUser(Integer numb);
}
