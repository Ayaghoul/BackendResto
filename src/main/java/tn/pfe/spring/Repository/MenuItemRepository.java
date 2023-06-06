package tn.pfe.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.pfe.spring.Entity.MenuItem;

@Repository 
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
