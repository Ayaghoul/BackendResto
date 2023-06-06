package tn.pfe.spring.Entity;


import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity 
@Table(name = "customer")
@Data 
public class Customer {
	
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String adress;
    private String phoneNumber;
    //calorie
    private String age;
    private String height;
    private String weight;
    private String gender;
    private boolean sportif;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "selectedCategories",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> selectedCategories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "selectedMenuItems",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private List<MenuItem> selectedMenuItems;

    @JsonIgnore
    @OneToMany
    @JoinTable(name = "customer_favoris",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "menuItem_id"))
    private List<MenuItem> favoris;
    
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
    
    @Override
    public int hashCode() {
        return Objects.hash(id); // Seule la propriété 'id' est incluse dans le hashcode
    }
     }
	
    
	



