import com.tks.services.OrderService;
import com.tks.services.UserService;
import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InjectionTest {

    @InjectMocks
    OrderService orderService;

    @Test
    public void testInjection(){
        Assertions.assertNotNull(orderService);
    }
}
