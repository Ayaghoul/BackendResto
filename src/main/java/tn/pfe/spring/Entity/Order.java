package tn.pfe.spring.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private LocalDateTime date;
	    private int quantity;
	    private double amount;
	    //paypal
	    private double price;
		private String currency;
		private String method;
		private String intent;
		private String description;

	    @Enumerated(EnumType.STRING)
	    private OrderStatus status;

	    @ManyToOne
	    @JoinColumn(name = "customer_id")
	    private AppUser customer;
	    
	    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    private Set<OrderItem> orderItems = new HashSet<>();
	    
	    @Override
		public int hashCode() {
			return Objects.hash(id); // Seule la propriété 'id' est incluse dans le hashcode
		}

	   
	    }
	    

	   
 


