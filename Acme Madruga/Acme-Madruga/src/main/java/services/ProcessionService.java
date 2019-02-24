
package services;

import java.util.Collection;
import java.util.Date;

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

	private ProcessionRepository	processionRepository;

	//Service
	private BrotherhoodService		brotherhoodService;


	//Metodos CRUD  FR 10.2

	public Collection<Procession> findAll() {
		Collection<Procession> res = this.processionRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public Procession findOne(final int ProcessionId) {
		Procession res = this.processionRepository.findOne(ProcessionId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Procession procession) {
		Assert.notNull(procession);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD));
		this.processionRepository.delete(procession.getId());
	}

	public Procession save(final Procession procession) {
		final Procession result;
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD));
		Assert.notNull(procession);
		result = this.processionRepository.save(procession);
		return result;

	}

	public Procession create() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD));
		return new Procession();
	}

	// FR 8.2 - FR 10.2

	public Collection<Procession> findProcessionsByBrotherhood(int idBrotherhood) {
		Collection<Procession> res;
		int id = LoginService.getPrincipal().getId();
		Brotherhood bh = this.brotherhoodService.findOne(id);

		if (LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD) && (bh.getId() == idBrotherhood)) {
			res = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);

		} else {

			Collection<Procession> all = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
			for (Procession procession : all) {
				if (!procession.getMode().equals("DRAFT")) {
					res.add(procession);
				}

			}
		}

		Assert.notNull(res);
		return res;
	}

	// FR 12.3.5

	public Collection<Procession> processionsBefore30Days(Date date) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.ADMINISTRATOR));
		Collection<Procession> res = this.processionRepository.processionsBefore30Days(date);
		Assert.notNull(res);
		return res;
	}

}
