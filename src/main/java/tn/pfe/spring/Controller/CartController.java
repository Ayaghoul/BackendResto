package tn.pfe.spring.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.pfe.spring.Entity.Cart;
import tn.pfe.spring.Service.CartService;


@RestController
public class CartController {

    @Autowired
    private CartService cartService;
 

    @GetMapping("/getCartById/{id}") 
    public Cart getCartById(@PathVariable(value = "id") Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping("/createCart") 
    public Cart createCart(@Valid @RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMenuItemToCart(@RequestParam Long customerId, @RequestParam Long menuItemId) {
        try {
            cartService.addMenuItemToCart(customerId, menuItemId);
            return ResponseEntity.ok("Plat ajouté au panier avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/place")
    public ResponseEntity<String> placeOrderFromCart(@RequestParam("customerId") Long customerId,
                                                     @RequestParam("quantity") int quantity) {
        try {
            cartService.placeOrderFromCart(customerId, quantity);
            return ResponseEntity.ok("La commande a été passée avec succès.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    }


