package tn.pfe.spring.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Repository.CategoryRepository;
import tn.pfe.spring.Repository.MenuItemRepository;

public interface CategoryService {


    List<Category> getAllCategories();


    Category getCategoryById(Long id);

    String addMenuItemToCategory(Long categoryId, MenuItem menuItem);

    List<MenuItem> getMenuItemByCategoryId(Long categorieId);


    Category createCategory(Category category);


    Category updateCategory(Long id, Category category);


    void deleteCategory(Long id);


}
