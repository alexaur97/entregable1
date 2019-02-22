
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationParametersRepository;
import domain.ConfigurationParameters;

@Service
@Transactional
public class ConfigurationParametersService {

	@Autowired
	private ConfigurationParametersRepository	configurationParametersRepository;

	@Autowired
	private AdministratorService				administratorService;


	public ConfigurationParameters create() {
		return new ConfigurationParameters();
	}

	public ConfigurationParameters find() {
		final ConfigurationParameters res = (ConfigurationParameters) this.configurationParametersRepository.findAll().toArray()[0];
		return res;
	}

	public ConfigurationParameters save(final ConfigurationParameters config) {

		this.administratorService.findByPrincipal();

		if (config.getId() == 0) {
			config.setName("Acme Madrugá");
			config.setBanner("https://tinyurl.com/acme-madruga");
			config.setMessage("Welcome to Acme Madrugá, the site to organise your processions.");
			config.setMessageEs("¡Bienvenidos a Acme Madrugá! Tu sitio para organizar procesiones.");
			config.setCountryCode("+34");

			final Collection<String> positions = new ArrayList<>();
			positions.add("President");
			positions.add("Vice president");
			positions.add("Secretary");
			positions.add("Treasurer");
			positions.add("Historian");
			positions.add("Fundraiser");
			positions.add("Officer");

			config.setPositions(positions);

			final Collection<String> positionsEs = new ArrayList<>();
			positions.add("Presidente");
			positions.add("Vicepresidente");
			positions.add("Secretario");
			positions.add("Tesorero");
			positions.add("Historiador");
			positions.add("Promotor");
			positions.add("Vocal");

			config.setPositionsEs(positionsEs);
		}
		final ConfigurationParameters saved = this.configurationParametersRepository.save(config);
		Assert.notNull(saved);
		return saved;
	}
}
