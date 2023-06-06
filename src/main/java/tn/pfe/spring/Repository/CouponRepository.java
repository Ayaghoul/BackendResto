package tn.pfe.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.pfe.spring.Entity.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{
}
