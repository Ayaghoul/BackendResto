package tn.pfe.spring.DTO;

import lombok.Data;
import tn.pfe.spring.Entity.Role;

@Data
public class UserDTO {

    private String username;
    private String email;
    private String password;
    private Role roles;


}