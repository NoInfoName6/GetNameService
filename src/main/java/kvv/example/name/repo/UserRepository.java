package kvv.example.name.repo;

import kvv.example.name.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findByAgeGreaterThan(Integer age);
    Mono<User> findByEmail(String email);
}
