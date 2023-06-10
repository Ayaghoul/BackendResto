package tn.pfe.spring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.pfe.spring.DTO.FavorisResponse;
import tn.pfe.spring.DTO.UserDTO;
import tn.pfe.spring.Entity.AppUser;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AppUser> signUp(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.saveUser(userDTO));
    }

    //
    @GetMapping("/")
    public ResponseEntity<List<AppUser>> getUsersPage() {
        log.debug("Getting users page");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<AppUser> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/add-menu-item-favoris/{menuItem}")
    public ResponseEntity<AppUser> getUser(@PathVariable("menuItem") Long menuItem) {
        return ResponseEntity.ok(userService.addMenuItemToFavoris(menuItem));
    }

    @GetMapping("/favoris")  //tested successfully
    public ResponseEntity<List<MenuItem>> getFavoris() {
        List<MenuItem> favoris = userService.getFavoris();
        return ResponseEntity.ok(favoris);
    }

    @DeleteMapping("/remove-favoris/{menuItemId}") //tested successfully
    public ResponseEntity<Void> removeMenuItemFromFavoris(
            @PathVariable Long menuItemId
    ) {
        userService.removeMenuItemFromFavoris(menuItemId);
        return ResponseEntity.ok().build();
    }
}
