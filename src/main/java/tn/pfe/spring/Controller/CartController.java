package tn.pfe.spring.Controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.pfe.spring.Entity.Cart;
import tn.pfe.spring.Service.CartService;
import tn.pfe.spring.Service.impl.CartServiceImpl;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;


    @GetMapping("/getCartById/{id}")
    public Cart getCartById(@PathVariable(value = "id") Long id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/get-all-carts")
    public ResponseEntity<List<Cart>> getCartById() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @PostMapping("/createCart")
    public Cart createCart(@Valid @RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    @PatchMapping("/add-menu-item-cart")
    public ResponseEntity<Cart> addMenuItemToCart(@RequestBody List<Long> menuItemId) {
        cartService.addMenuItemToCart(menuItemId);
        return ResponseEntity.ok(cartService.addMenuItemToCart(menuItemId));
    }

//    @PostMapping("/place")
//    public ResponseEntity<String> placeOrderFromCart(@RequestParam("customerId") Long customerId,
//                                                     @RequestParam("quantity") int quantity) {
//        try {
//            cartService.placeOrderFromCart(customerId, quantity);
//            return ResponseEntity.ok("La commande a été passée avec succès.");
//        } catch (IllegalArgumentException | IllegalStateException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

}


