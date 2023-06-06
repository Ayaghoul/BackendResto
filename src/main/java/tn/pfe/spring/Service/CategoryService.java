package tn.pfe.spring.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Repository.CategoryRepository;
import tn.pfe.spring.Repository.MenuItemRepository;

@Service
@Transactional
public class CategoryService{
	
	@PersistenceContext
    private EntityManager entityManager;
	    @Autowired
	    private CategoryRepository categoryRepository;
	    @Autowired
	    private MenuItemRepository menuItemRepository;

	    
	    public List<Category> getAllCategories() {
	        return categoryRepository.findAll();
	    }

	    
	    public Category getCategoryById(Long id) {
	        return categoryRepository.findById(id).orElse(null);
	    }
	    
	    public String addMenuItemToCategory(Long categoryId, MenuItem menuItem) {
	        Category category = categoryRepository.findById(categoryId).orElse(null);
	        if (category == null) {
	            return "La catégorie n'existe pas.";
	        }

	        menuItem.setCategory(category);
	        menuItemRepository.save(menuItem);

	        return "MenuItem ajouté avec succès à la catégorie : " + category.getName();
	    }
	    
	    public List<MenuItem> getMenuItemByCategoryId(Long categorieId) {
	        Category category = categoryRepository.findById(categorieId).orElse(null);
	        if (category == null) {
	            throw new IllegalArgumentException("La catégorie avec l'ID " + categorieId + " n'existe pas.");
	        }
	        return category.getMenuItems();
	    }

	    
	    public String createCategory(Category category) {
	        List<MenuItem> menuItems = category.getMenuItems();
	        if (menuItems != null && !menuItems.isEmpty()) {
	            menuItems.forEach(MenuItem -> MenuItem.setCategory(category));
	        }
	        Category createdCategory = categoryRepository.save(category);
	        return createdCategory != null ? 
	               ("Category créée avec succès : " + createdCategory.getName()) : 
	               "Erreur lors de la création de la catégorie.";
	    }

	    

	    public Category updateCategory(Long id, Category category) {
	        Category categoryToUpdate = categoryRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("La catégorie avec l'ID " + id + " n'a pas été trouvée."));
	        categoryToUpdate.setName(category.getName());
	        categoryToUpdate.setDescription(category.getDescription());
	        return categoryRepository.save(categoryToUpdate);
	    }


	    
	  
	    public void deleteCategory(Long id) {
	        Category category = categoryRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("La catégorie avec l'ID " + id + " n'a pas été trouvée."));
	        List<MenuItem> menuItems = category.getMenuItems();
	        if (menuItems != null && !menuItems.isEmpty()) {
	            throw new RuntimeException("Impossible de supprimer la catégorie car elle contient des produits associés.");
	        }
	        categoryRepository.deleteById(id);
	    }
	    
	    

}
