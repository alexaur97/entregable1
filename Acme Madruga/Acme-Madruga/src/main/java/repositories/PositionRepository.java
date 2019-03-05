
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.id=?1")
	Position findPositionById(int id);

	@Query("select case when count(e)=0 then 'true' else 'false' end as bool from Enrolment e where e.position.id=?1")
	Boolean checkIfNotAssigned(int id);

	@Query("select count(e) from Enrolment e  where e.position.id=?1")
	Integer numberOfPositionsById(Integer id);
}
