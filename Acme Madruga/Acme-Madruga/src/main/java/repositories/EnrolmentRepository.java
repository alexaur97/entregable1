
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Enrolment;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

	//--------- Ale
	@Query("select count(e) from Enrolment e where e.brotherhood.id=?1")
	Integer countEnrolmentByBrotherhood(int id);
	//--------- Ale

	//JAVI
	@Query("select e from Enrolment e where e.member.id=?1")
	Collection<Enrolment> enrolmentByMember(int id);
}
