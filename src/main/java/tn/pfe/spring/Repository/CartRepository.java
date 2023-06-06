package tn.pfe.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.pfe.spring.Entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
