package kvv.example.name.util;

import kvv.example.name.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class UserGenerator {
    public List<User> generate(int n){
        return IntStream.range(0, n)
                .mapToObj(i->{
                    int rI = RandomUtils.getRandomInt(0, userNameList.size()-1);
                    var u = new User();
                    u.setName(userNameList.get(rI));
                    u.setAge(RandomUtils.getRandomInt(10, 60));
                    u.setEmail("%s_%d_%d@mail.ru".formatted(u.getName(),u.getAge(),RandomUtils.getRandomInt(1000, 6000)));
                    return u;
                })
                .toList();
    }

    private static final List<String> userNameList = new ArrayList<>();

    static {
        userNameList.addAll(Arrays.asList("Bob", "John", "Eva"));
    }
}
