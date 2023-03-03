package adapters;

import java.util.List;

import data.model.OrderEnt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository extends IRepositoryImpl<OrderEnt> {

    public List<OrderEnt> findOngoingOrders(){
        return filter(order -> !order.isDelivered());
    }

    public List<OrderEnt> findFinishedOrders(){
        return filter(OrderEnt::isDelivered);
    }

}
