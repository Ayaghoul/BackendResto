package tn.pfe.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.pfe.spring.Entity.Chef;
import tn.pfe.spring.Entity.MenuItem;

import java.util.List;

@Repository
public interface ChefRepository extends JpaRepository<Chef,Long>{

	void save(MenuItem menuItem);

	@Query("select  c from Chef c ")
	List<Chef> getAll();

}
