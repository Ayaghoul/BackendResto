package tn.pfe.spring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.pfe.spring.DTO.UserDTO;
import tn.pfe.spring.Entity.AppUser;
import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController{

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/") //tested successfully
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/getCategoryById/{id}") //tested successfully
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
    
    @GetMapping("/{categoryId}/MenuItem") //tested successfully
    public ResponseEntity<List<MenuItem>> getMenuItemByCategoryId(@PathVariable Long categoryId) {
        List<MenuItem> menuItems = categoryService.getMenuItemByCategoryId(categoryId);
        return ResponseEntity.ok(menuItems);
    }

    @PostMapping("/{categoryId}/MenuItem") //tested successfully
    public ResponseEntity<String> addMenuItemToCategory
    (
            @PathVariable Long categoryId,
            @RequestBody MenuItem menuItem){
        String message = categoryService.addMenuItemToCategory(categoryId, menuItem);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/") //tested successfully
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.createCategory(category));
    }

    @PutMapping("/updateCategory/{id}")//tested successfully
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/deleteCategory/{id}")//tested successfully
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
    
    // removeMenuItemFromCategory
}
