
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Position;

@Service
@Transactional
public class EnrolmentService {

	// Repositorios propios
	@Autowired
	private EnrolmentRepository	enrolmentRepository;


	// Servicios ajenos

	// M�todos CRUD

	public Collection<Enrolment> findAll() {
		Collection<Enrolment> result;
		result = this.enrolmentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Enrolment findOne(int enrolmentId) {
		Assert.isTrue(enrolmentId != 0);
		Enrolment result;
		result = this.enrolmentRepository.findOne(enrolmentId);
		Assert.notNull(result);
		return result;
	}

	public void save(Enrolment enrolment) {
		Assert.notNull(enrolment);
		this.enrolmentRepository.save(enrolment);
	}

	public void delete(Enrolment enrolment) {
		Assert.notNull(enrolment);
		this.enrolmentRepository.delete(enrolment);
	}

	// RF 10.3
	public Enrolment create(Brotherhood brotherhood, Member member, Date moment, Position position) {
		Assert.notNull(member);
		Assert.notNull(moment);
		Assert.notNull(position);
		Assert.notNull(brotherhood);
		Assert.isTrue(LoginService.getPrincipal().getId() == brotherhood.getUserAccount().getId());
		Enrolment result = new Enrolment();
		result.setBrotherhood(brotherhood);
		result.setMember(member);
		result.setPosition(position);
		result.setMoment(moment);
		return result;
	}

}
