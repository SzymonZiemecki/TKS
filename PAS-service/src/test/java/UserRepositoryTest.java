import com.pas.model.User;
import com.pas.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    UserRepository userRepository = new UserRepository();

    @Test
    public void testUserRepository(){
        User user = new User();
        user.setId(UUID.randomUUID());
        UserRepository userRepository = new UserRepository();
        userRepository.add(user);
        assertEquals(userRepository.size(), 1);
        userRepository.delete(user.getId().toString());
        assertEquals(userRepository.size(), 0);
    }
}
