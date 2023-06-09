package tn.pfe.spring.Service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.pfe.spring.DTO.MenuItemDTO;
import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.Chef;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Service.CategoryService;
import tn.pfe.spring.Service.ChefService;
import tn.pfe.spring.Service.FilesStorageService;
import tn.pfe.spring.Service.MenuItemService;
import tn.pfe.spring.mapper.MenuItemMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryService categoryService;
    private final ChefService chefService;
    private final MenuItemMapper menuItemMapper;
    private final FilesStorageService filesStorageService;


    public List<MenuItem> getAllMenuItem() {
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }


    public MenuItem createMenuItem(MenuItemDTO menuItem) throws IOException {
        Category category = categoryService.getCategoryById(menuItem.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("La catégorie avec l'ID " + menuItem.getCategoryId() + " n'existe pas.");
        }
        Chef chef = chefService.getChefById(menuItem.getChefId());
        if (chef == null) {
            throw new IllegalArgumentException("La catégorie avec l'ID " + menuItem.getChefId() + " n'existe pas.");
        }
        if (menuItem.getPhoto().isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        MenuItem menuItem1=menuItemMapper.toEntity(menuItem);
        menuItem1.setImage(menuItem.getName().replaceAll("\\s", "") + "-" + (int) (Math.random() * 20) + "." + Objects.requireNonNull(menuItem.getPhoto().getContentType()).split("/")[1]);

        filesStorageService.save(menuItem.getPhoto(), menuItem1.getImage());
        // Define the directory to save the file

        menuItem1.setCategory(category);
        menuItem1.setChef(chef);
        return menuItemRepository.save(
                menuItem1
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

