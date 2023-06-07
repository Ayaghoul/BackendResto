package tn.pfe.spring.Service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.pfe.spring.Entity.*;
import tn.pfe.spring.Repository.CategoryRepository;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Service.CategoryService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;


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


    public Category createCategory(Category category) {
        List<MenuItem> menuItems = category.getMenuItems();
        if (menuItems != null && !menuItems.isEmpty()) {
            menuItems.forEach(MenuItem -> MenuItem.setCategory(category));
        }
        return categoryRepository.save(category);
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