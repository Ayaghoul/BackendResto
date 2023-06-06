package tn.pfe.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.pfe.spring.Entity.Ingredient;


@Repository 
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
}
