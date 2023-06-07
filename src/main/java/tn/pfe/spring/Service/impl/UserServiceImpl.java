package tn.pfe.spring.Service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.pfe.spring.DTO.UserDTO;
import tn.pfe.spring.Entity.*;
import tn.pfe.spring.Repository.*;
import tn.pfe.spring.Service.*;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public List<AppUser> getAllUsers() {
        log.debug("Getting all users");
        return userRepository.findAll(Sort.by("email"));
    }

    @Override
    public AppUser getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public AppUser saveUser(UserDTO userDTO) {
        String username = userDTO.getUsername();
        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            throw new RuntimeException("This is already exists, Try with an other username");
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(userDTO.getUsername());
        appUser.setEmail(userDTO.getEmail());
        appUser.setActived(true);
        appUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepository.save(appUser);
        addRoleToUser(appUser.getEmail(), "USER");
        return appUser;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepository.findByEmail(username);
        Role role = roleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);

    }
}