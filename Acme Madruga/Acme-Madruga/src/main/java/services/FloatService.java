
package services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import security.Authority;
import domain.Float;

@Service
@Transactional
public class FloatService {

	//Repositorio

	private FloatRepository		floatRepository;

	//Servicios
	private BrotherhoodService	brotherhoodService;


	//Metodos CRUD

	public Collection<Float> findAll() {
		Collection<Float> res = this.floatRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public Float findOne(final int FloatId) {
		Float res = this.floatRepository.findOne(FloatId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Float floatt) {
		Assert.notNull(floatt);
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		this.floatRepository.delete(floatt.getId());
	}

	public Float save(final Float floatt) {
		final Float result;
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		Assert.notNull(floatt);
		result = this.floatRepository.save(floatt);
		return result;
	}

	public Float create() {
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		return new Float();
	}
	//FR 8.2 - FR 10.1
	public Collection<Float> findFloatsByBrotherhood(int id) {
		Assert.notNull(id);
		Collection<Float> res = this.floatRepository.findFloatsByBrotherhood(id);
		return res;
	}
}
