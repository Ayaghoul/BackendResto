package tn.pfe.spring.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import tn.pfe.spring.Entity.MenuItem;

@Entity 
@Table(name = "menuItem")
@Data 
public class MenuItem{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String name;
    @Column(length = 500)
    private String description;
    private double price;
    private String image;
    private double discountedPrice;
    private Long availableCount;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chef_id")
    private Chef chef;
    @CreatedDate
    @Column(name = "creationDate", nullable = false,updatable = false)
    private LocalDate creationDate;

    @LastModifiedDate
    @Column(name = "lastModifiedDate")
    private LocalDate lastModifiedDate;

    @PrePersist
    void onCreate() {
        this.setCreationDate(LocalDate.now());
        this.setLastModifiedDate(LocalDate.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setLastModifiedDate(LocalDate.now());
    }
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
