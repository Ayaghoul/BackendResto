package tn.pfe.spring.Service;


import java.io.IOException;
import java.util.List;


import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import tn.pfe.spring.DTO.MenuItemDTO;
import tn.pfe.spring.Entity.Category;
import tn.pfe.spring.Entity.MenuItem;

import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.mapper.MenuItemMapper;

public interface MenuItemService {


    public List<MenuItem> getAllMenuItem();

    public MenuItem getMenuItemById(Long id);


    public MenuItem createMenuItem(MenuItemDTO menuItem) throws IOException;


    public ResponseEntity<String> updateMenuItem(Long id, MenuItem menuItem);


    public void deleteMenuItem(Long id);

    public void addRatingToMenuItem(Long menuItemId, double rating);

    public double getAverageRatingByMenuItemId(Long menuItemId);

    public void incrementCartCount(Long menuItemId);

    ;
}
