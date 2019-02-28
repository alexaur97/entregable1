
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// FR 8.2 - FR 10.3
	@Query("select e.member from Enrolment e where e.brotherhood.id = ?1 unique")
	Collection<Member> findMembersByBrotherhood(int id);

	@Query("select m from Member m where m.userAccount.id = ?1 unique")
	Member findMemberByPrincipal(int id);
}
