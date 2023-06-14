package tn.pfe.spring.Service;

import tn.pfe.spring.Entity.Chef;
import tn.pfe.spring.Entity.MenuItem;

import java.util.List;

public interface ChefService {
    Chef addChef(Chef chef);

    String addMenuItemTochef(Long idChef, MenuItem menuItem);

    List<MenuItem> getMenuItemByChefId(Long idChef);

    Chef updateChef(Long id, Chef chef);

    void deleteChef(Long id);


    List<Chef> getAllChefs();
    List<Chef> getChefs();


    Chef getChefById(Long id);
}
