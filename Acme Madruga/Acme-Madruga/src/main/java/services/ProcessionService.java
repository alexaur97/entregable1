
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import domain.Brotherhood;
import domain.Member;
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
	private MemberService			memberService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	//Metodos CRUD  FR 10.2

	public Collection<Procession> findAll() {
		final Collection<Procession> res = this.processionRepository.findAll();
		//Assert.notNull(res);
		return res;

	}

	public Collection<Procession> findProcessionsAvailableForMember() {
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		final Member member = this.memberService.findByPrincipal();
		final Collection<Procession> res = new ArrayList<Procession>();
		for (Brotherhood b : brotherhoods) {
			if (b.getMembers().contains(member))
				res.addAll(this.processionRepository.findProcessionsByBrotherhood(b.getId()));
		}

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
		//this.brotherhoodService.findByPrincipal();
		this.processionRepository.delete(procession.getId());
	}

	public Procession save(final Procession procession) {
		final Procession result;
		Brotherhood bh = this.brotherhoodService.findByPrincipal();
		Assert.notNull(procession);

		procession.setBrotherhood(bh);

		result = this.processionRepository.save(procession);
		return result;

	}

	public Procession create() {
		//Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		return new Procession();
	}

	public Collection<Procession> findProcessionsByBrotherhoodForList(final int idBrotherhood) {
		//Assert.notNull(idBrotherhood);
		final Collection<Procession> res = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		return res;
	}

	// FR 8.2 - FR 10.2

	public Collection<Procession> findProcessionsByBrotherhood(final int idBrotherhood) {
		//Assert.notNull(idBrotherhood);
		final Collection<Procession> res = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		//		Collection<Procession> res = new ArrayList<>();
		//		final int id = LoginService.getPrincipal().getId();
		//		final Brotherhood bh = this.brotherhoodService.findOne(id);
		//
		//		if (this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD) && (bh.getId() == idBrotherhood)){
		//		res = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		//		}else {
		//
		//		final Collection<Procession> all = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		//		for (final Procession procession : all)
		//		if (!procession.getMode().equals("DRAFT"))
		//		res.add(procession);

		//	}

		return res;
	}
	public Collection<Procession> findFinalProcessionsByBrotherhood(final int idBrotherhood) {
		final Collection<Procession> all = this.processionRepository.findProcessionsByBrotherhood(idBrotherhood);
		Collection<Procession> res = new ArrayList<>();
		for (Procession procession : all) {
			if (procession.getMode().equals("FINAL"))
				res.add(procession);
		}
		return res;
	}

	// FR 12.3.5

	public Collection<Procession> processionsBefore30Days() {
		this.administratorService.findByPrincipal();
		final Collection<Procession> res = this.processionRepository.processionsBefore30Days();
		//Assert.notNull(res);
		return res;
	}

	//Other Methods--------------------



	public Procession reconstruct(Procession procession, BindingResult binding) {
		Procession res = procession;
		if(procession.getMoment()!=null){
		
		String pattern = "YYMMdd";
		DateFormat df = new SimpleDateFormat(pattern);
		Date fecha = procession.getMoment(); 
		String fechaFormateada = df.format(fecha);
	
		
		String cadena = this.creaString();
		String ticker = fechaFormateada + "-" + cadena ;
		res.setTicker(ticker);
		}
		
		if (procession.getId() != 0) {
		
			
			Procession p2 = processionRepository.findOne(res.getId());
			res.setBrotherhood(p2.getBrotherhood());



		}
		this.validator.validate(res, binding);
		
		return res;
	}
	
	public String creaString(){
		char[] elementos ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ'
				,'O','P','Q','R','S','T','U','V','W','X','Y','Z'};

				char[] conjunto = new char[5];
				String cadena;
				for(int i=0;i<5;i++){
					
				int el = (int)(Math.random()*27);
				
				conjunto[i] = (char)elementos[el];
				}
				cadena = new String(conjunto);
				return cadena;
				}

	
	
	


}
