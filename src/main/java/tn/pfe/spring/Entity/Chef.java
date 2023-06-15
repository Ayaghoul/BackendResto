package tn.pfe.spring.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String chefName;
    private Long phoneNumber;
    private String email;
    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;

}