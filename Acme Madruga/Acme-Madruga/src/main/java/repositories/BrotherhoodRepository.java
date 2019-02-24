
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends JpaRepository<domain.Brotherhood, Integer> {

	// RF 11.3 (a)
	@Query("select e.brotherhood from Enrolment e where e.member.id = ?1")
	Collection<Brotherhood> findBrotherhoodByMemberBelong(int id);

	// RF 11.3 (b)
	@Query("select d.brotherhood from DropOut d where d.member.id = ?1")
	Collection<Brotherhood> findBrotherhoodByMemberHasBelonged(int id);

	// RF 12.3.1
	@Query("select avg(1.0*(select count(e.member) from Enrolment e where e.brotherhood.id=br.id))," + "min(1.0*(select count(e.member) from Enrolment e where e.brotherhood.id=br.id)), "
		+ "max(1.0*(select count(e.member) from Enrolment e where e.brotherhood.id=br.id))," + "stddev(1.0*(select count(e.member) " + "from Enrolment e where e.brotherhood.id=br.id)) from Brotherhood br")
	Collection<Double> membersPerBrotherhood();

	// RF 12.3.2
	@Query("select b from Brotherhood b where (select count(e.member) from Enrolment e where e.brotherhood.id=b.id)=(select max(1.0*(select count(e.member) from Enrolment e where e.brotherhood.id=bt.id)) from Brotherhood bt)")
	Collection<Brotherhood> largestBrotherhoods();

	// RF 12.3.3
	@Query("select b from Brotherhood b where (select count(e.member) from Enrolment e where e.brotherhood.id=b.id)=(select min(1.0*(select count(e.member) from Enrolment e where e.brotherhood.id=bt.id)) from Brotherhood bt)")
	Collection<Brotherhood> smallestBrotherhoods();

	@Query("select b from Brotherhood b where b.userAccount.id=?1")
	Brotherhood findByUserId(int id);

}
