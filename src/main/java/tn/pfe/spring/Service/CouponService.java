package tn.pfe.spring.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import tn.pfe.spring.Entity.Coupon;
import tn.pfe.spring.Entity.MenuItem;
import tn.pfe.spring.Repository.CouponRepository;
import tn.pfe.spring.Repository.MenuItemRepository;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MenuItemRepository menuItemRepository;

    public CouponService(CouponRepository couponRepository, MenuItemRepository menuItemRepository) {
        this.couponRepository = couponRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Coupon createCoupon(Coupon coupon, Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            throw new IllegalArgumentException("Le MenuItem avec l'ID " + menuItemId + " n'existe pas.");
        }

        coupon.setMenuItem(menuItem);
        return couponRepository.save(coupon);
    }

    public Coupon getCouponById(Long couponId) {
        return couponRepository.findById(couponId).orElse(null);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public MenuItem applyCouponToMenuItem(Long couponId, Long menuItemId) {
        Coupon coupon = couponRepository.findById(couponId).orElse(null);
        if (coupon == null) {
            throw new IllegalArgumentException("Le coupon avec l'ID " + couponId + " n'existe pas.");
        }

        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            throw new IllegalArgumentException("Le MenuItem avec l'ID " + menuItemId + " n'existe pas.");
        }

        if (coupon.getMenuItem() != null && !coupon.getMenuItem().equals(menuItem)) {
            throw new IllegalArgumentException("Ce coupon ne peut pas être appliqué à ce MenuItem.");
        }

        double menuItemPrice = menuItem.getPrice();
        double discountAmount = coupon.getDiscountAmount();
        double discountedPrice = menuItemPrice - discountAmount;

        if (discountedPrice < 0) {
            discountedPrice = 0; // Assurez-vous que le prix réduit ne devienne pas négatif
        }

        menuItem.setDiscountedPrice(discountedPrice);
        menuItemRepository.save(menuItem);

        return menuItem;
    }
}
