
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

	@Query("select r from Request r where r.procession.id = ?1 and r.status=?2 ")
	Collection<Request> findRequestApprovedByProcession(int id, String status);

	//---Ale--- 
	@Query("select sum(case when r.status='APPROVED' then 1.0 else 0.0 end)/count(r) from Request r")
	Double approvedRatio();

	@Query("select sum(case when r.status='PENDING' then 1.0 else 0.0 end)/count(r) from Request r")
	Double pendingRatio();

	@Query("select sum(case when r.status='REJECTED' then 1.0 else 0.0 end)/count(r) from Request r")
	Double rejectedRatio();
	//---Ale---
}
