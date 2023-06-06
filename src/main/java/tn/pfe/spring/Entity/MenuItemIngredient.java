package tn.pfe.spring.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import tn.pfe.spring.Repository.IngredientRepository;

@Entity
@Table(name = "menuitem_ingredient")
@Data 
public class MenuItemIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menuitem_id")
    private MenuItem menuItem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    
    public MenuItemIngredient() {
    }

    public MenuItemIngredient(MenuItem menuItem, Ingredient ingredient) {
        this.menuItem = menuItem;
        this.ingredient = ingredient;
    }
    
    }