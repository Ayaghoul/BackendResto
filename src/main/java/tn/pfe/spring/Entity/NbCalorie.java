package tn.pfe.spring.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "nb_calorie")
@Data
public class NbCalorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalCalories;

    @OneToOne(mappedBy = "nbCalorie")
    private MenuItem menuItem;
}
