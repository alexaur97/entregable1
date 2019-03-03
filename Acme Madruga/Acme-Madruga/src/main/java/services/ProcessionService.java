
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import security.Authority;
import domain.Procession;
import forms.ProcessionForm;

@Service
@Transactional
public class ProcessionService {

	//Repositorios

	private ProcessionRepository	processionRepository;

	//Service
	private BrotherhoodService		brotherhoodService;

	private AdministratorService	administratorService;


	//Metodos CRUD  FR 10.2

	public Collection<Procession> findAll() {
		final Collection<Procession> res = this.processionRepository.findAll();
		//Assert.notNull(res);
		return res;

	}

	public Procession findOne(final int ProcessionId) {
		final Procession res = this.processionRepository.findOne(ProcessionId);
		//Assert.notNull(res);
		return res;
	}

	public void delete(final Procession procession) {
		Assert.notNull(procession);
		//Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		this.processionRepository.delete(procession.getId());
	}

	public Procession save(final Procession procession) {
		final Procession result;
		//Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		//Assert.notNull(procession);
		result = this.processionRepository.save(procession);
		return result;

	}

	public Procession create() {
		//Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		return new Procession();
	}

	public Collection<Procession> findProcessionsByBrotherhoodForList(final int idBrotherhood) {
		Assert.notNull(idBrotherhood);
		final Collection<Procession> res = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		return res;
	}

	// FR 8.2 - FR 10.2

	public Collection<Procession> findProcessionsByBrotherhood(final int idBrotherhood) {
		Assert.notNull(idBrotherhood);
		final Collection<Procession> res = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		//final int id = LoginService.getPrincipal().getId();
		//final Brotherhood bh = this.brotherhoodService.findOne(id);

		//if (this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD) && (bh.getId() == idBrotherhood))
		//res = 
		//else {

		//final Collection<Procession> all = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		//for (final Procession procession : all)
		//if (!procession.getMode().equals("DRAFT"))
		//res.add(procession);
		//}

		return res;
	}

	// FR 12.3.5

	public Collection<Procession> processionsBefore30Days(final Date date) {
		Assert.isTrue(this.administratorService.findByPrincipal().equals(Authority.ADMINISTRATOR));
		final Collection<Procession> res = this.processionRepository.processionsBefore30Days(date);
		Assert.notNull(res);
		return res;
	}

	//Other Methods--------------------

	public ProcessionForm toForm(final int processionId) {
		final ProcessionForm res = new ProcessionForm();
		final Procession procession = this.findOne(processionId);
		res.setDescription(procession.getDescription());
		res.setBrotherhood(procession.getBrotherhood());
		res.setFloats(procession.getFloats());
		res.setMode(procession.getMode());
		res.setMoment(procession.getMoment());
		res.setTitle(procession.getTitle());
		res.setProcessionId(processionId);

		return res;
	}

	public Procession reconstruct(final ProcessionForm processionForm) {
		final Procession res;
		if (processionForm.getProcessionId() == 0)
			res = this.create();
		else
			res = this.findOne(processionForm.getProcessionId());
		res.setDescription(processionForm.getDescription());
		res.setBrotherhood(processionForm.getBrotherhood());
		res.setFloats(processionForm.getFloats());
		res.setMode(processionForm.getMode());
		res.setMoment(processionForm.getMoment());
		res.setTitle(processionForm.getTitle());
		return res;
	}

}
