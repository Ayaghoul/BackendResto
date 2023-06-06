
package tn.pfe.spring.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.pfe.spring.Entity.Cart;
import tn.pfe.spring.Entity.Customer;

import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Repository.CustomerRepository;
import tn.pfe.spring.Service.CartService;
import tn.pfe.spring.Service.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    
    
    @GetMapping("/getAllCustomers") //tested successfully
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    
    @GetMapping("/getCustomerById/{id}") //tested successfully
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/addCustomer") //tested successfully
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/updateCustomer/{id}") //tested successfully
    public Customer updateCustmor(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/deleteCustomer/{id}") //tested successfully
    public void deleteCustomer(@PathVariable Long id) {
    	customerService.deleteCustomer(id);
    }
    
   
   
    
   @GetMapping("/{custmorId}/favoris")  //tested successfully
   public ResponseEntity<List<MenuItem>> getFavoris(@PathVariable Long customerId) {
       List<MenuItem> favoris = customerService.getFavoris(customerId);
       return ResponseEntity.ok(favoris);
   }

   @DeleteMapping("/{customerId}/favoris/{menuItemId}") //tested successfully
   public ResponseEntity<String> removeMenuItemFromFavoris(
           @PathVariable Long customerId,
           @PathVariable Long menuItemId
   ) 
   {
       String message = customerService.removeMenuItemFromFavoris(customerId, menuItemId);
       return ResponseEntity.ok(message);
   }
   
   
   @GetMapping("/{customerId}/caloriesConsumed")
   public ResponseEntity<Double> calculateTotalCaloriesConsumed(@PathVariable Long customerId) {
       double totalCalories = customerService.calculateTotalCaloriesConsumed(customerId);
       return ResponseEntity.ok(totalCalories);
   }
   
   /*@PostMapping("/needs/{customerId}")
   public ResponseEntity<Double> calculateDailyCalorieNeeds(@PathVariable Long customerId) {
       Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
       if (optionalCustomer.isEmpty()) {
           return ResponseEntity.notFound().build();
       }

       Customer customer = optionalCustomer.get();
       double dailyCalorieNeeds = customerService.calculateDailyCalorieNeeds(customer);
       
       // Mettre à jour et enregistrer la valeur des besoins caloriques dans l'entité Customer
       customer.setCalorieNeeds(dailyCalorieNeeds);
       customerRepository.save(customer);
       
       return ResponseEntity.ok(dailyCalorieNeeds);
   }*/
   
   }



