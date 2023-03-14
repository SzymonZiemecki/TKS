/*
package com.tks.aggregates;

import com.tks.infrastructure.carts.AddToUserCartPort;
import com.tks.infrastructure.carts.RemoveFromUserCartPort;
import com.tks.model.Cart;
import data.product.ProductEnt;
import data.user.CartEnt;
import data.user.CartItemEnt;
import data.user.UserEnt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import com.tks.repository.ProductEntRepository;
import com.tks.repository.UserEntRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tks.mapper.ModelMapper.toDomainModel;
import static data.utils.ErrorInfoEnt.ENTITY_NOT_FOUND_MESSAGE;

@ApplicationScoped
public class CartAdapter implements AddToUserCartPort, RemoveFromUserCartPort {

    @Inject
    private UserEntRepository userRepository;
    @Inject
    private ProductEntRepository productRepository;

    @Override
    public Cart addToCart(UUID userId, UUID productId, Long quantity) {
        Optional<UserEnt> found = userRepository.findById(userId);
        Optional<ProductEnt> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            List<CartItemEnt> itemsInCart = found.get().getCart().getCartItems();
            deleteItemFromCartItems(itemsInCart, product.get());
            found.get().setCart(new CartEnt(itemsInCart));
            return toDomainModel(found.get().getCart());
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    @Override
    public Cart removeFromCart(UUID userId, UUID productId) {
        Optional<UserEnt> found = userRepository.findById(userId);
        Optional<ProductEnt> product = productRepository.findById(productId);
        if (found.isPresent() && product.isPresent()) {
            List<CartItemEnt> itemsInCart = found.get().getCart().getCartItems();
            deleteItemFromCartItems(itemsInCart, product.get());
            found.get().setCart(new CartEnt(itemsInCart));
            return toDomainModel(found.get().getCart());
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    private List<CartItemEnt> deleteItemFromCartItems(List<CartItemEnt> cartItems, ProductEnt product) {
        List<CartItemEnt> itemsToDelete = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct()
                        .equals(product))
                .toList();
        List<CartItemEnt> itemsLeft = cartItems;
        itemsToDelete.forEach(itemsLeft::remove);
        return itemsLeft;
    }
}
*/
