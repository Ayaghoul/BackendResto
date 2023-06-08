package tn.pfe.spring.Service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.pfe.spring.DTO.MenuItemDTO;
import tn.pfe.spring.DTO.UserDTO;
import tn.pfe.spring.Entity.AppUser;
import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Entity.Role;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Repository.RoleRepository;
import tn.pfe.spring.Repository.UserRepository;
import tn.pfe.spring.Service.CategoryService;
import tn.pfe.spring.Service.MenuItemService;
import tn.pfe.spring.Service.UserService;
import tn.pfe.spring.mapper.MenuItemMapper;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryService categoryService;
    private final MenuItemMapper menuItemMapper;


    public List<MenuItem> getAllMenuItem() {
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }


    public MenuItem createMenuItem(MenuItemDTO menuItem) throws IOException {
        Category category = categoryService.getCategoryById(menuItem.getCategory());
        if (category == null) {
            throw new IllegalArgumentException("La catégorie avec l'ID " + menuItem.getCategory() + " n'existe pas.");
        }
        if (menuItem.getPhoto().isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String fileName = menuItem.getPhoto().getOriginalFilename();

        // Define the directory to save the file
        String uploadDir = "resources";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Create the file path
        String filePath = uploadDir + fileName;

        // Save the file to the specified path
        menuItem.getPhoto().transferTo(new File(filePath));

        menuItem.setCategory(category.getId());
        return menuItemRepository.save(
                menuItemMapper.toEntity(menuItem)
        );
    }


    public ResponseEntity<String> updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem MenuItemToUpdate = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Le produit avec l'ID " + id + " n'a pas été trouvé."));

        MenuItemToUpdate.setName(menuItem.getName());
        MenuItemToUpdate.setDescription(menuItem.getDescription());
        MenuItemToUpdate.setPrice(menuItem.getPrice());
        MenuItemToUpdate.setDiscountedPrice(menuItem.getDiscountedPrice());
        MenuItemToUpdate.setRatingsCount(menuItem.getRatingsCount());
        MenuItemToUpdate.setRatingsValue(menuItem.getRatingsValue());
        MenuItemToUpdate.setCartCount(menuItem.getCartCount());
        MenuItemToUpdate.setImage(menuItem.getImage());
        MenuItemToUpdate.setIsVegetarian(menuItem.getIsVegetarian());
        MenuItemToUpdate.setWeight(menuItem.getWeight());


        menuItemRepository.save(MenuItemToUpdate);
        return ResponseEntity.ok("MenuItem mis à jour avec succès");
    }


    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }


    public void addRatingToMenuItem(Long menuItemId, double rating) {

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Le produit avec l'ID " + menuItemId + " n'a pas été trouvé."));

        // Update the ratings count and value
        menuItem.setRatingsCount(menuItem.getRatingsCount() + 1);
        menuItem.setRatingsValue(menuItem.getRatingsValue() + rating);

        // Calculate the average rating
        menuItem.calculateAverageRating();

        menuItemRepository.save(menuItem);
    }

    public double getAverageRatingByMenuItemId(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            throw new IllegalArgumentException("Le MenuItem avec l'ID " + menuItemId + " n'existe pas.");
        }

        return menuItem.getRatingsValue() / menuItem.getRatingsCount();
    }

    public void incrementCartCount(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Le produit avec l'ID " + menuItemId + " n'a pas été trouvé."));

        menuItem.setCartCount(menuItem.getCartCount() + 1);

        menuItemRepository.save(menuItem);
    }
}

