
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	// FR 10.6 (a)
	@Query("select r from Request r where r.status = ?1 and r.procession.brotherhood.id = ?2")
	Collection<Request> findRequestByStatusAndBrotherhood(String status, int id);

	// FR 10.6 (b)
	@Query("select r from Request r where r.procession.brotherhood.id = ?1 group by status")
	Collection<Request> findRequestByBrotherhoodGroupByStatus(int id);

	// FR 11.1 (a)
	@Query("select r from Request r where r.status = ?1 and r.member.id = ?2")
	Collection<Request> findRequestByStatusAndMember(String status, int id);

	// FR 11.1 (b)
	@Query("select r from Request r where r.member.id = ?1 group by status")
	Collection<Request> findRequestByMemberGroupByStatus(int id);

	// RF 12.3.4
	@Query("select 1.0*(select count(r1) from Request r1 where r1.procession.id = ?1 and r1.status = 'PENDING'),1.0*(select count(r2) from Request r2 where r2.procession.id = ?1 and r2.status = 'APPROVED'),1.0*(select count(r3) from Request r3 where r3.procession.id = ?1 and r3.status = 'REJECTED'), count(r) from Request r where r.procession.id = ?1 ")
	Collection<Double> ratioOfRequestByProcessionGroupedByStatus(int id);

	// RF 12.3.6
	@Query("select 1.0*(select count(r1) from Request r1 where r1.status = 'PENDING'),1.0*(select count(r2) from Request r2 where r2.status = 'APPROVED'),1.0*(select count(r3) from Request r3 where r3.status= 'REJECTED'), count(r) from Request r")
	Collection<Double> ratioOfRequestGroupedByStatus(int id);

}
