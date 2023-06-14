package tn.pfe.spring.Service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.pfe.spring.Entity.Chef;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Repository.ChefRepository;
import tn.pfe.spring.Repository.MenuItemRepository;
import tn.pfe.spring.Service.ChefService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChefServiceImpl implements ChefService {
    private final MenuItemRepository menuItemRepository;
    private final ChefRepository chefrepo;
    public List<Chef> getAllChefs() {
        return chefrepo.findAll();
    }
    public List<Chef> getChefs() {
        return chefrepo.getAll();
    }
    public Chef addChef(Chef chef) {
        List<MenuItem> menuItems = chef.getMenuItems();
        if (menuItems != null && !menuItems.isEmpty()) {
            menuItems.forEach(MenuItem -> MenuItem.setChef(chef));
        }
        return chefrepo.save(chef);
    }

    public String addMenuItemTochef(Long idChef, MenuItem menuItem) {
        Chef chef = chefrepo.findById(idChef).orElse(null);
        if (chef == null) {
            return "Le chef n'existe pas.";
        }

        menuItem.setChef(chef);
        menuItemRepository.save(menuItem);

        return "MenuItem ajouté avec succès à chef : " + chef.getChefName();
    }

    public List<MenuItem> getMenuItemByChefId(Long idChef) {
        Chef chef = chefrepo.findById(idChef).orElse(null);
        if (chef == null) {
            throw new IllegalArgumentException("Le chef avec l'ID " + idChef + " n'existe pas.");
        }
        return chef.getMenuItems();
    }

    public Chef updateChef(Long id, Chef chef) {
        Chef chefToUpdate = chefrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Le chef  avec l'ID " + id + " n'a pas été trouvée."));
        chefToUpdate.setChefName(chef.getChefName());
        chefToUpdate.setDescription(chef.getDescription());
        chefToUpdate.setEmail(chef.getEmail());
        chefToUpdate.setPhoneNumber(chef.getPhoneNumber());
        return chefrepo.save(chefToUpdate);
    }

    public void deleteChef(Long id) {
        Chef chef = chefrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Le chef avec l'ID " + id + " n'a pas été trouvée."));
        List<MenuItem> menuItems = chef.getMenuItems();
        if (menuItems != null && !menuItems.isEmpty()) {
            throw new RuntimeException("Impossible de supprimer le chef car elle contient des produits associés.");
        }
        chefrepo.deleteById(id);
    }




    public Chef getChefById(Long id) {
        return chefrepo.findById(id).orElse(null);
    }


}
