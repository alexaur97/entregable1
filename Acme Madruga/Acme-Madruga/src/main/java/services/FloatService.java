
package services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import security.Authority;
import domain.Float;
import forms.FloatForm;

@Service
@Transactional
public class FloatService {

	//Repositorio

	private FloatRepository		floatRepository;

	//Servicios
	private BrotherhoodService	brotherhoodService;


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
	public Collection<Float> findFloatsByBrotherhood(final int id) {
		Assert.notNull(id);
		final Collection<Float> res = this.floatRepository.findFloatsByBrotherhood(id);
		return res;
	}

	public FloatForm toForm(final int floatId) {
		final FloatForm res = new FloatForm();
		final Float float1 = this.findOne(floatId);
		res.setTitle(float1.getTitle());
		res.setDescription(float1.getDescription());
		res.setPictures(float1.getPictures());
		return res;
	}

	public Float reconstruct(final FloatForm floatForm) {
		final Float res;
		if (floatForm.getFloatId() == 0)
			res = this.create();
		else
			res = this.findOne(floatForm.getFloatId());
		res.setTitle(floatForm.getTitle());
		res.setDescription(floatForm.getDescription());
		res.setPictures(floatForm.getPictures());
		return res;
	}
}
