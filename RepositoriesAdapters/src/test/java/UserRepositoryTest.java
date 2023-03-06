import data.model.OrderEnt;
import data.user.UserEnt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest extends BaseTest {

    @Test
    public void addUserTest() {
        assertEquals(userEntRepository.findAll().size(), 0);
        UserEnt baseUser = prepareBaseUser();
        UserEnt manager = prepareManager();
        UserEnt admin = prepareAdmin();
        userEntRepository.add(admin);
        assertEquals(userEntRepository.findAll().size(), 1);
        userEntRepository.add(baseUser);
        assertEquals(userEntRepository.findAll().size(), 2);
        userEntRepository.add(manager);
        assertEquals(userEntRepository.findAll().size(), 3);
    }

    @Test
    public void updateUserTest() {
        UserEnt admin = prepareAdmin();
        String oldFirstName = admin.getFirstName();
        UserEnt user = userEntRepository.add(admin);
        assertEquals(userEntRepository.findAll().size(), 1);
        String newFirstName = "changed";
        user.setFirstName(newFirstName);
        userEntRepository.update(user.getId(), user);
        assertEquals(userEntRepository.findById(user.getId()).orElse(null).getFirstName(), newFirstName);
    }

    @Test
    public void findUserTest() {
        UserEnt admin = prepareAdmin();
        UserEnt user = userEntRepository.add(admin);
        admin.setId(user.getId());
        assertEquals(userEntRepository.findAll().size(), 1);
        assertEquals(admin, userEntRepository.findById(user.getId()).orElse(null));
    }
}
