package tn.pfe.spring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tn.pfe.spring.Entity.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {

    private Long Id;
    private String name;
    private String description;
    private double price;
    private MultipartFile photo;
    private double discountedPrice;
    private int ratingsCount;
    private double ratingsValue;
    private double averageRating;
    private int cartCount;
    private Boolean isVegetarian;
    private String weight;
    private Long category;
    private List<MenuItemIngredient> menuItemIngredients;
    private NbCalorie nbCalorie;
    private Order order;


}
