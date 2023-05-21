package com.tks.microservices.orderservice.rest.adapter;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tks.microservices.orderservice.core.model.Order;
import com.tks.microservices.orderservice.port.ui.ClientService;
import com.tks.microservices.orderservice.port.ui.OrderService;
import com.tks.microservices.orderservice.rest.dto.AddressDTO;
import com.tks.microservices.orderservice.rest.dto.CreateClientDto;
import com.tks.microservices.orderservice.rest.dto.OrderDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.ws.rs.QueryParam;

@RestController
public class OrderApiImpl {

    private OrderService orderManager;
    private ClientService clientService;

    public OrderApiImpl(final OrderService orderManager, final ClientService clientService) {
        this.orderManager = orderManager;
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllOrders() {
        return ResponseEntity.ok().body(orderManager.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrderById(@PathVariable("id") UUID id) {
        OrderDTO order = new OrderDTO();
        try {
            orderManager.getOrderById(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/ongoing")
    public ResponseEntity getOngoingOrders() {
        return ResponseEntity.ok().body(orderManager.getOngoingOrders());
    }

    @GetMapping("/finished")
    public ResponseEntity getFinishedOrders() {
        return ResponseEntity.ok().body(orderManager.getFinishedOrders());
    }

    @PostMapping("/create")
    public ResponseEntity createOrder(@RequestParam("userId") UUID clientId, @Valid @RequestBody AddressDTO shippingAddress) {
        Order order = new Order();
        try {
            order = orderManager.createOrder(clientId, AddressDTO.addressDTOToDomainModel(shippingAddress));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok(OrderDTO.orderToDTO(order));
    }

    @PutMapping("/{id}/deliver")
    public ResponseEntity deliverOrder(@PathVariable("id") UUID orderId) {
        try {
            orderManager.deliverOrder(orderId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id") UUID orderId) {
        try {
            orderManager.deleteOrder(orderId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/client/{id}/add")
    ResponseEntity addToCart(@PathVariable("id") String id, @RequestParam("productId") String productId, @RequestParam("quantity") int quantity){
        clientService.addToClientCart(id, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/client/{id}/remove")
    ResponseEntity removeFromCart(@PathVariable("id") UUID userId, @RequestParam("productId") UUID id){
        clientService.removeFromClientCart(userId, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    ResponseEntity create(@RequestBody CreateClientDto dto) {
        return ResponseEntity.ok(clientService.createClient(dto));
    }

    @PostMapping("/client/list")
    ResponseEntity findAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
}
