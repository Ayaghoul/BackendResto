/*package tn.pfe.spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.pfe.spring.Entity.Ingredient;
import tn.pfe.spring.Repository.IngredientRepository;

@Service
public class IngredientService {
	
	@Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient createIngredient(String name, double calories) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setCalories(calories);
        
        // Enregistrez l'ingrédient dans la base de données
        return ingredientRepository.save(ingredient);
    }

}*/
