package tn.pfe.spring.Service;


import tn.pfe.spring.DTO.OrderRequestDTO;
import tn.pfe.spring.Entity.*;
import java.util.*;


public interface CartService {

    Cart createCart(Cart cart);

    Cart getCartById(Long id);
   List<Cart> getAllCarts();


    Cart addMenuItemToCart(List<Long> menuItemIds);

    Order placeOrderFromCart(OrderRequestDTO orderRequestDTO, Long cartId);


}

	
