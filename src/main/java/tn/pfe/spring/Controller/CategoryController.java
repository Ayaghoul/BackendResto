package tn.pfe.spring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Service.CategoryService;

@RestController
public class CategoryController{

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategories") //tested successfully
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
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

    @PostMapping("/addCategory") //tested successfully
    public String createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
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
