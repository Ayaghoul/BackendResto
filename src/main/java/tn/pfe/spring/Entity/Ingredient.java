package tn.pfe.spring.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import tn.pfe.spring.Repository.IngredientRepository;

@Entity
@Table(name = "ingredient")
@Data 
public class Ingredient {
    
    @Id
    private Long id;
    
    private String name;
    
    private double calories;
    
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<MenuItemIngredient> menuItemIngredients;
}