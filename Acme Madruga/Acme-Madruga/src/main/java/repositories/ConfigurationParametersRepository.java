
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ConfigurationParameters;

@Repository
public interface ConfigurationParametersRepository extends JpaRepository<ConfigurationParameters, Integer> {

}
