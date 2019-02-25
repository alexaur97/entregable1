
package services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import security.Authority;

@Service
@Transactional
public class FloatService {

	//Repositorio

	private FloatRepository		floatRepository;

	//Servicios
	private BrotherhoodService	brotherhoodService;


	//Metodos CRUD

	public Collection<domain.Float> findAll() {
		Collection<domain.Float> res = this.floatRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public domain.Float findOne(final int FloatId) {
		domain.Float res = this.floatRepository.findOne(FloatId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final domain.Float floatt) {
		Assert.notNull(floatt);
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		this.floatRepository.delete(floatt.getId());
	}

	public domain.Float save(final domain.Float floatt) {
		final domain.Float result;
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		Assert.notNull(floatt);
		result = this.floatRepository.save(floatt);
		return result;
	}

	public domain.Float create() {
		Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		return new domain.Float();
	}
	//FR 8.2 - FR 10.1
	public Collection<domain.Float> findFloatsByBrotherhood(int id) {
		Collection<domain.Float> res = this.floatRepository.findFloatsByBrotherhood(id);
		Assert.notNull(res);
		return res;
	}
}
