
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import security.Authority;
import security.LoginService;
import domain.Brotherhood;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	//Repositorios

	@Autowired
	private ProcessionRepository	processionRepository;

	//Service
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private AdministratorService	administratorService;


	//Metodos CRUD  FR 10.2

	public Collection<Procession> findAll() {
		final Collection<Procession> res = this.processionRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public Procession findOne(final int ProcessionId) {
		final Procession res = this.processionRepository.findOne(ProcessionId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Procession procession) {
		Assert.notNull(procession);
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		this.processionRepository.delete(procession.getId());
	}

	public Procession save(final Procession procession) {
		final Procession result;
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		Assert.notNull(procession);
		result = this.processionRepository.save(procession);
		return result;

	}

	public Procession create() {
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		return new Procession();
	}

	// FR 8.2 - FR 10.2

	public Collection<Procession> findProcessionsByBrotherhood(final int idBrotherhood) {
		Collection<Procession> res = new ArrayList<>();
		final int id = LoginService.getPrincipal().getId();
		final Brotherhood bh = this.brotherhoodService.findOne(id);

		if (this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD) && (bh.getId() == idBrotherhood))
			res = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		else {

			final Collection<Procession> all = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
			for (final Procession procession : all)
				if (!procession.getMode().equals("DRAFT"))
					res.add(procession);
		}

		Assert.notNull(res);
		return res;
	}

	// FR 12.3.5

	public Collection<Procession> processionsBefore30Days() {
		this.administratorService.findByPrincipal();
		final Collection<Procession> res = this.processionRepository.processionsBefore30Days();
		Assert.notNull(res);
		return res;
	}

}
