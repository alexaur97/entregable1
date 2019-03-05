
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DropOut;

@Repository
public interface DropOutRepository extends JpaRepository<DropOut, Integer> {

	@Query("select d from DropOut d where d.member.id=?1")
	Collection<DropOut> dropOutByMember(int id);

}
