package tn.pfe.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.pfe.spring.Entity.Customer;

@Repository 
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
