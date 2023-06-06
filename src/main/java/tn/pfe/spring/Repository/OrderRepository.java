package tn.pfe.spring.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.pfe.spring.Entity.Order;



@Repository 
public interface OrderRepository extends JpaRepository<Order, Long>{

}
