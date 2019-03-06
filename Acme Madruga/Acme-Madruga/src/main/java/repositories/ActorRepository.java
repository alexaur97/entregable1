
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	// FR 9.2
	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccount(int id);

	@Query("select a.userAccount.username from Actor a")
	Collection<String> findAllAccounts();

}
