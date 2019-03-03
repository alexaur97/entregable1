
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;
import domain.Request;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// FR 8.2 - FR 10.3



	@Query("select distinct e.member from Enrolment e where e.brotherhood.id = ?1")
	Collection<Member> findMembersByBrotherhood(int id);

	@Query("select distinct m from Member m where m.userAccount.id = ?1")
	Member findMemberByPrincipal(int id);

	//--------- Ale
	@Query("select avg(b.members.size),min(b.members.size),max(b.members.size), stddev(b.members.size) from Brotherhood b")
	Collection<Double> statsMembersPerBrotherhood();

	@Query("select r from Request r where r.status='APPROVED'")
	Collection<Request> approvedRequests();
	//--------- Ale
}
