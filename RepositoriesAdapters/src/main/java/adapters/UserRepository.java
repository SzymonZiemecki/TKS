package adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.model.AddressEnt;
import data.model.OrderEnt;
import data.model.UserEnt;
import data.user.AdminEnt;
import data.user.BaseUserEnt;
import data.user.CartEnt;
import data.user.ManagerEnt;
import exception.LoginAlreadyTakenException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class UserRepository extends IRepositoryImpl<UserEnt> {

    @Inject
    OrderRepository orderRepository;

    @Override
    public synchronized UserEnt add(UserEnt entity) {
        if (!findByLogin(entity.getLogin()).isEmpty()) {
            throw new LoginAlreadyTakenException("Login already taken");
        } else {
            return super.add(entity);
        }
    }

    public List<UserEnt> findByLogin(String login) {
        return filter(user -> user.getLogin().contains(login));
    }

    public List<UserEnt> findOneByLogin(String login) {
        return new ArrayList<>(filter(user -> user.getLogin().equals(login)));
    }

    public List<OrderEnt> findUserOrders(UUID login) {
        return orderRepository.filter(order -> order.getCustomer().getId().equals(login));
    }

    @PostConstruct
    public void init() {
        UserEnt baseUser = new BaseUserEnt("Szymon", "Ziemecki", "baseUser", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"),  new CartEnt(), false, 200d);
        UserEnt manager = new ManagerEnt("Szymon", "Ziemecki", "manager", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"), new CartEnt(), false, 200d);
        UserEnt admin = new AdminEnt("Szymon", "Ziemecki", "admin", "testpass", new AddressEnt("Poland", "Lodz", "Pomorska", "20", "90-001"),  new CartEnt(), false, 200d);

        this.add(baseUser);
        this.add(manager);
        this.add(admin);
    }
}
