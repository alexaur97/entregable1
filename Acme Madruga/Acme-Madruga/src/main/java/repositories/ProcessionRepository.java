
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<domain.Procession, Integer> {

	// FR 8.2 - FR 10.2
	@Query("select p from Procession p where p.brotherhood.id = ?1")
	Collection<Procession> findProcessionsByBrotherhood(int id);

	// FR 12.3.5
	@Query("select p from Procession p where DATEDIFF(p.moment,CURRENT_DATE)<=30 and p.moment>CURRENT_DATE")
	Collection<Procession> processionsBefore30Days();
}
