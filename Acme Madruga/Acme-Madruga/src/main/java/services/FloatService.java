
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatRepository;
import security.Authority;
import security.LoginService;
import domain.Float;

@Service
@Transactional
public class FloatService {

	//Repositorio
	@Autowired
	private FloatRepository		floatRepository;

	//Servicios
	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private Validator			validator;


	//Metodos CRUD

	public Collection<Float> findAll() {
		final Collection<Float> res = this.floatRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public Float findOne(final int FloatId) {
		final Float res = this.floatRepository.findOne(FloatId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Float floatt) {
		Assert.notNull(floatt);
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		this.floatRepository.delete(floatt.getId());
	}

	public Float save(final Float floatt) {
		final Float result;

		Assert.notNull(floatt);
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		result = this.floatRepository.save(floatt);
		return result;
	}

	public Float create() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		return new Float();
	}
	//FR 8.2 - FR 10.1
	public Collection<Float> findFloatsByBrotherhood(final int id) {
		Assert.notNull(id);
		System.out.println(this.floatRepository);
		final Collection<Float> res = this.floatRepository.findFloatsByBrotherhood(id);
		return res;
	}

	public Float reconstruct(final Float floaat, final BindingResult binding) {
		Float res;

		if (floaat.getId() == 0)
			res = floaat;
		else {
			res = this.floatRepository.findOne(floaat.getId());
			floaat.setBrotherhood(res.getBrotherhood());
			res = floaat;
		}
		this.validator.validate(res, binding);

		return res;
	}
}
