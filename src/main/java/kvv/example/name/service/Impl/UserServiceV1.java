package kvv.example.name.service.Impl;

import kvv.example.name.repo.UserRepository;
import kvv.example.name.service.UserService;
import kvv.example.name.util.UserGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceV1 implements UserService {
    private final UserRepository userRepository;
    private final UserGenerator userGenerator;
    @Override
    public Mono<Void> generateUser(Integer numb) {
        var uL = userGenerator.generate(numb);
        return userRepository.saveAll(uL).then();
    }

}
