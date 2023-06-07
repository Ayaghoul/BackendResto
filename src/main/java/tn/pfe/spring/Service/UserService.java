package tn.pfe.spring.Service;

import tn.pfe.spring.Entity.*;
import tn.pfe.spring.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AppUser saveUser(UserDTO userDTO);

    void addRoleToUser(String username, String role);

    AppUser loadUserByUsername(String username);

    List<AppUser> getAllUsers();
   AppUser getUser(String username);


    Role saveRole(Role role);
}
