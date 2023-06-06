package tn.pfe.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.pfe.spring.Entity.OrderItem;

@Repository 
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
