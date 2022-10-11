import com.pas.model.User;
import com.pas.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {

    UserRepository userRepository;

    @BeforeEach
    public void init(){
        this.userRepository = new UserRepository();
    }

    @Test
    public void addRemoveTest(){
        User user1 = new User("Test", "User");
        User user2 = new User("Test", "User2");
        userRepository.add(user1);
        userRepository.add(user2);
        assertEquals(userRepository.size(), 2);
        userRepository.delete(user2);
        assertEquals(userRepository.size(), 1);
    }
}
