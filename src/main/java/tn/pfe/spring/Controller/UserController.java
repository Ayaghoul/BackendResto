package tn.pfe.spring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.pfe.spring.DTO.UserDTO;
import tn.pfe.spring.Entity.AppUser;
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
}
