package tn.pfe.spring.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.pfe.spring.Entity.Ingredient;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Entity.ResourceNotFoundException;
import tn.pfe.spring.Repository.IngredientRepository;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Service.MenuItemService;

@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;
    
    @Autowired
    private IngredientRepository ingredientRepository;
    
    @Autowired
    private MenuItemRepository menuItemRepository;


    @GetMapping("/getAllMenuItem") //tested successfully
    public List<MenuItem> getAllMenuItem() {
        return menuItemService.getAllMenuItem();
    }
      //msg de retour ken ma fama htta produit
    

    @GetMapping("/MenuItemById/{id}") //tested successfully
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
        //msg de retour ken ma fama htta produit
    }
    

    @PostMapping("/addMenuItem/{categoryId}")//tested successfully
    public ResponseEntity<String> createMenuItem(@RequestBody MenuItem menuItem, @PathVariable Long categoryId) {
        MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem, categoryId);
        return ResponseEntity.ok("MenuItem créé avec succès : " + createdMenuItem.getId());
    }

    @PutMapping("/updateMenuItem/{id}") //tested successfully
    public ResponseEntity<String> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(id, menuItem);
    }

    @DeleteMapping("/deleteMenuItem/{id}") //tested successfully
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    
    }
        
        @PostMapping("/{menuItemId}/ingredients/{ingredientId}") //tested successfully
        public ResponseEntity<String> addIngredientToMenuItem(
                @PathVariable Long menuItemId,
                @PathVariable Long ingredientId) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + menuItemId));
            
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + ingredientId));
            
            menuItem.addIngredient(ingredient);
            
            menuItemRepository.save(menuItem);
            
            return ResponseEntity.ok("Ingredient added successfully to MenuItem.");
        }
        
        @DeleteMapping("/{menuItemId}/ingredients/{ingredientId}") //tested successfully
        public ResponseEntity<String> removeIngredientFromMenuItem(
                @PathVariable Long menuItemId,
                @PathVariable Long ingredientId) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + menuItemId));
            
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + ingredientId));
            
            menuItem.removeIngredient(ingredient);
            
            menuItemRepository.save(menuItem);
            
            return ResponseEntity.ok("Ingredient removed successfully from MenuItem.");
        }
    
        @GetMapping("/{menuItemId}/calories")
        public ResponseEntity<Double> calculateMenuItemCalories(@PathVariable Long menuItemId) {
            MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);

            if (menuItem == null) {
                throw new ResourceNotFoundException("MenuItem not found with id: " + menuItemId);
            }

            menuItem.calculateTotalCalories();
            menuItemRepository.save(menuItem);

            double totalCalories = menuItem.getNbCalorie().getTotalCalories();

            return ResponseEntity.ok(totalCalories);
        }
    @PostMapping("/{menuItemId}/ratings") //tested successfully
    public ResponseEntity<String> addRatingToMenuItem(
            @PathVariable Long menuItemId,
            @RequestParam double rating
    ) {
        menuItemService.addRatingToMenuItem(menuItemId, rating);
        return ResponseEntity.ok("Rating added successfully.");
    }
    
    @GetMapping("/{menuItemId}/averageRating") //tested successfully
    public ResponseEntity<Double> getAverageRatingByMenuItemId(@PathVariable Long menuItemId) {
        double averageRating = menuItemService.getAverageRatingByMenuItemId(menuItemId);
        return ResponseEntity.ok(averageRating);
    }
    
    
    @GetMapping("/menuItems/averageRatings") //tested successfully
    public ResponseEntity<Map<Long, Double>> getMenuItemsAverageRatings() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItem();
        Map<Long, Double> averageRatingsMap = new HashMap<>();
        for (MenuItem menuItem : menuItems) {
            averageRatingsMap.put(menuItem.getId(), menuItem.getAverageRating());
        }
        return ResponseEntity.ok(averageRatingsMap);
    }
    
}
