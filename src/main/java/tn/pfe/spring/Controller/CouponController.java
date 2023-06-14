package tn.pfe.spring.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.pfe.spring.Entity.Coupon;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Service.CouponService;

@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("createCoupon")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon,
                                               @RequestParam("menuItemId") Long menuItemId) {
        Coupon createdCoupon = couponService.createCoupon(coupon, menuItemId);
        return ResponseEntity.ok(createdCoupon);
    }

    @PostMapping("/{couponId}/apply")
    public ResponseEntity<MenuItem> applyCouponToMenuItem(@PathVariable("couponId") Long couponId,
                                                          @RequestParam("menuItemId") Long menuItemId) {
        MenuItem menuItem = couponService.applyCouponToMenuItem(couponId, menuItemId);
        return ResponseEntity.ok(menuItem);
    }

    @GetMapping("/getCouponById/{couponId}") //tested successfully
    public ResponseEntity<Coupon> getCouponById(@PathVariable("couponId") Long couponId) {
        Coupon coupon = couponService.getCouponById(couponId);
        if (coupon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/allCoupons") //tested successfully
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }
}

