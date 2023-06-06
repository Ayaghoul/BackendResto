package tn.pfe.spring.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import tn.pfe.spring.Entity.MenuItem;

@Entity 
@Table(name = "menuItem")
@Data 
public class MenuItem{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String name;
    private String description;
    private double price;
    private String image;
    @Transient
    private double discountedPrice;
    private int ratingsCount;
    private double ratingsValue;
    private double averageRating;
    private int cartCount;
    private Boolean isVegetarian;
    private String weight;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItemIngredient> menuItemIngredients;

    
    public void addIngredient(Ingredient ingredient) {
        if (menuItemIngredients == null) {
            menuItemIngredients = new ArrayList<>();
        }
        
        MenuItemIngredient menuItemIngredient = new MenuItemIngredient(this, ingredient);
        menuItemIngredients.add(menuItemIngredient);
        ingredient.getMenuItemIngredients().add(menuItemIngredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        if (menuItemIngredients == null) {
            return;
        }

        MenuItemIngredient menuItemIngredient = null;
        for (MenuItemIngredient mi : menuItemIngredients) {
            if (mi.getIngredient().equals(ingredient)) {
                menuItemIngredient = mi;
                break;
            }
        }

        if (menuItemIngredient != null) {
            menuItemIngredient.setMenuItem(null);
            menuItemIngredient.setIngredient(null);
            menuItemIngredients.remove(menuItemIngredient);
            ingredient.getMenuItemIngredients().remove(menuItemIngredient);
        }
    }
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nb_calorie_id")
    private NbCalorie nbCalorie;

    public void calculateTotalCalories() {
        double totalCalories = 0;

        if (menuItemIngredients != null) {
            for (MenuItemIngredient menuItemIngredient : menuItemIngredients) {
                totalCalories += menuItemIngredient.getIngredient().getCalories();
            }
        }

        if (nbCalorie == null) {
            nbCalorie = new NbCalorie();
        }

        nbCalorie.setTotalCalories(totalCalories);
        nbCalorie.setMenuItem(this);
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;                        
    
  
    public void calculateAverageRating() {
        if (ratingsCount > 0) {
            averageRating = ratingsValue / ratingsCount;
        } else {
            averageRating = 0;
        }
    }
    
}
