package tn.pfe.spring.Service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.pfe.spring.DTO.FavorisResponse;
import tn.pfe.spring.DTO.UserDTO;
import tn.pfe.spring.Entity.*;
import tn.pfe.spring.Repository.*;
import tn.pfe.spring.Service.*;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MenuItemRepository menuItemRepository;

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

    @Override
    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found"));
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


    public AppUser addMenuItemToFavoris(Long menuItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = userRepository.findByUsername(currentPrincipalName);
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElse(null);
        if (menuItem == null) {
            throw new RuntimeException("Le produit n'existe pas.");
        }
        List<MenuItem> favoris = user.getFavoris();
        if (favoris == null) {
            favoris = new ArrayList<>();
        }
        favoris.add(menuItem);
        user.setFavoris(favoris);
                userRepository.save(user);
        return userRepository.save(user);
    }


    public List<MenuItem> getFavoris() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = userRepository.findByUsername(currentPrincipalName);
        if (user == null) {
            return Collections.emptyList();
        }

        return user.getFavoris();
    }

    public void removeMenuItemFromFavoris(Long menuItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = userRepository.findByUsername(currentPrincipalName);
        if (user == null) {
            throw new RuntimeException("Le client n'existe pas.");
        }

        List<MenuItem> favoris = user.getFavoris();
        if (favoris == null || favoris.isEmpty()) {
            throw new RuntimeException("Aucun produit dans les favoris du client.");
        }

        boolean removed = favoris.removeIf(menuItem -> menuItem.getId().equals(menuItemId));
        if (removed) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("Le produit n'existe pas dans les favoris du client.");
        }
    }
}