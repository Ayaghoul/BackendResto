package tn.pfe.spring.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.pfe.spring.Entity.AppUser;
import tn.pfe.spring.Entity.Order;
import tn.pfe.spring.Entity.Reservation;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findAllByCustomer(AppUser customer);

}
