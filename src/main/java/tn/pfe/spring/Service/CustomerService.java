package tn.pfe.spring.Service;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.pfe.spring.Entity.*;
import tn.pfe.spring.Repository.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final MenuItemRepository menuItemRepository;
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id)
                .orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElse(null);

        customer.setFullName(customerDetails.getFullName());
        customer.setAdress(customerDetails.getAdress());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        customer.setEmail(customerDetails.getEmail());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElse(null);
        customerRepository.delete(customer);
    }

        public String addMenuItemToFavoris(Long customerId, Long menuItemId) {
            Customer customer = customerRepository.findById(customerId)
                    .orElse(null);
            if (customer == null) {
                return "Le client n'existe pas.";
            }

            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElse(null);
            if (menuItem == null) {
                return "Le produit n'existe pas.";
            }

            List<MenuItem> favoris = customer.getFavoris();
            if (favoris == null) {
                favoris = new ArrayList<>();
            }

            favoris.add(menuItem);
            customer.setFavoris(favoris);
            customerRepository.save(customer);

            return "MenuItem ajouté aux favoris du client avec succès.";
        }
        
        
        public List<MenuItem> getFavoris(Long customerId) {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer == null) {
                return Collections.emptyList();
            }

            return customer.getFavoris();
        }
        
        public String removeMenuItemFromFavoris(Long customerId, Long menuItemId) {
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer == null) {
                return "Le client n'existe pas.";
            }

            List<MenuItem> favoris = customer.getFavoris();
            if (favoris == null || favoris.isEmpty()) {
                return "Aucun produit dans les favoris du client.";
            }

            boolean removed = favoris.removeIf(menuItem -> menuItem.getId().equals(menuItemId));
            if (removed) {
            	customerRepository.save(customer);
                return "MenuItem supprimé des favoris du client avec succès.";
            } else {
                return "Le produit n'existe pas dans les favoris du client.";
            }
        }
           

        public double calculateTotalCaloriesConsumed(Long customerId) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

            double totalCalories = 0;

            List<Category> selectedCategories = customer.getSelectedCategories();
            for (Category category : selectedCategories) {
                List<MenuItem> menuItems = category.getMenuItems();
                for (MenuItem menuItem : menuItems) {
                    totalCalories += menuItem.getNbCalorie().getTotalCalories();
                }
            }

            return totalCalories;
        }
            
           /* public DailyCalorieNeeds calculateDailyCalorieNeeds(Customer customer) {
                double BMR;
                
                // Calcul du métabolisme de base (Basal Metabolic Rate - BMR)
                if (customer.getGender().equalsIgnoreCase("male")) {
                    BMR = 10 * Double.parseDouble(customer.getWeight()) +
                          6.25 * Double.parseDouble(customer.getHeight()) -
                          5 * Double.parseDouble(customer.getAge()) + 5;
                } else {
                    BMR = 10 * Double.parseDouble(customer.getWeight()) +
                          6.25 * Double.parseDouble(customer.getHeight()) -
                          5 * Double.parseDouble(customer.getAge()) - 161;
                }
                
                // Facteur d'activité en fonction du niveau d'activité
                double activityFactor;
                // Exemple : Si le niveau d'activité est "sedentary" (sédentaire)
                // L'activité physique est faible ou inexistante
                // On multiplie le BMR par 1.2 pour obtenir les besoins caloriques quotidiens
                activityFactor = 1.2;
                
                // Calcul des besoins caloriques quotidiens
                double dailyCalorieNeeds = BMR * activityFactor;
                
                // Décomposition des besoins caloriques en besoins pour différentes parties de la journée
                double breakfastCalories = dailyCalorieNeeds * 0.25;
                double lunchCalories = dailyCalorieNeeds * 0.35;
                double dinnerCalories = dailyCalorieNeeds * 0.3;
                double snackCalories = dailyCalorieNeeds * 0.1;
                
                // Création et retour d'un objet DailyCalorieNeeds contenant les besoins caloriques décomposés
                DailyCalorieNeeds calorieNeeds = new DailyCalorieNeeds(dailyCalorieNeeds, breakfastCalories, lunchCalories, dinnerCalories, snackCalories);
                return calorieNeeds;
            }*/
        }

        







    
   


