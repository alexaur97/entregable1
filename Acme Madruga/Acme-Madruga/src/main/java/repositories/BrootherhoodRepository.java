
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrootherhoodRepository extends JpaRepository<domain.Brotherhood, Integer> {

	// RF 11.3
	@Query("select e.brotherhood from Enrolment e where e.id = ?1")
	Brotherhood findBrootherhoodByEnrolment(int id);
}
