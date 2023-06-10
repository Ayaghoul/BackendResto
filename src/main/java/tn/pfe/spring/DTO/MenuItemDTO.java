package tn.pfe.spring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tn.pfe.spring.Entity.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {

    private String name;
    private String description;
    private double price;
    private MultipartFile photo;
    private double discountedPrice;
    private String weight;
    private Long categoryId;
    private Long availableCount;


}
