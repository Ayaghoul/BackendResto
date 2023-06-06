package tn.pfe.spring.Service;


import java.util.List;


import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.MenuItem;

import tn.pfe.spring.Repository.MenuItemRepository;

@Service
@Transactional
@Lazy
public class MenuItemService {
	

	private final MenuItemRepository menuItemRepository;
    private final CategoryService categoryService;


    public MenuItemService(@Lazy MenuItemRepository menuItemRepository,@Lazy CustomerService customerService, 
    		 @Lazy CategoryService categoryService) {
        this.menuItemRepository = menuItemRepository;
		this.categoryService = categoryService;
	
    }
  
    public List<MenuItem> getAllMenuItem() {
        return menuItemRepository.findAll();
    }
    
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }
    
    
      
        public MenuItem createMenuItem(MenuItem menuItem, Long categoryId) {
        	Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                throw new IllegalArgumentException("La catégorie avec l'ID " + categoryId + " n'existe pas.");
            }

            menuItem.setCategory(category);
            return menuItemRepository.save(menuItem);
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
