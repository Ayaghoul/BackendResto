package tn.pfe.spring.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.pfe.spring.Entity.Chef;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Service.ChefService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chefs")
public class ChefController {
    private final ChefService chefserv;

    @GetMapping("/get-all-chefs") //tested successfully
    public ResponseEntity<List<Chef>> getAllChefs() {
        return  ResponseEntity.ok(chefserv.getAllChefs());
    }

    @PostMapping("/add-chef") //tested successfully
    public Chef addChef(@RequestBody Chef chef) {
        return chefserv.addChef(chef);
    }

    @PostMapping("/{chefId}/MenuItem") //tested successfully
    public ResponseEntity<String> addMenuItemTochef
            (
                    @PathVariable Long chefId,
                    @RequestBody MenuItem menuItem) {
        String message = chefserv.addMenuItemTochef(chefId, menuItem);
        return ResponseEntity.ok(message);
    }


    @GetMapping("/{chefId}/MenuItem") //tested successfully
    public ResponseEntity<List<MenuItem>> getMenuItemByChefId(@PathVariable Long chefId) {
        List<MenuItem> menuItems = chefserv.getMenuItemByChefId(chefId);
        return ResponseEntity.ok(menuItems);
    }

    @PatchMapping("/update-chef/{id}")//tested successfully
    public Chef updateChef(@PathVariable Long id, @RequestBody Chef chef) {
        return chefserv.updateChef(id, chef);
    }

    @DeleteMapping("/delete-chef/{id}")//tested successfully
    public void deleteChef(@PathVariable Long id) {
        chefserv.deleteChef(id);
    }


    @GetMapping("/getChefById/{id}") //tested successfully
    public Chef getChefById(@PathVariable Long id) {
        return chefserv.getChefById(id);
    }


}
