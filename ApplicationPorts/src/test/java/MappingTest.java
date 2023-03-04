import com.tks.User.BaseUser;
import com.tks.User.User;
import com.tks.mapper.ModelMapperBean;
import data.model.AddressEnt;
import data.user.BaseUserEnt;
import data.user.CartEnt;
import data.user.UserEnt;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.UUID;

public class MappingTest {

    @Test
    public void mappingTest(){
        ModelMapperBean mapper = new ModelMapperBean();
        UserEnt userEnt = new BaseUserEnt();
        userEnt.setId(UUID.randomUUID());
        userEnt.setLogin("Login");
        userEnt.setAddress(new AddressEnt("raz","dwa","trzy","trzery","piec"));
        userEnt.setPassword("raz");
        userEnt.setCart(new CartEnt());

        User usr = mapper.toDomainModel(userEnt);
        System.out.printf("");
        UserEnt ent = mapper.toEntModel(usr);
        System.out.println();
    }
}
