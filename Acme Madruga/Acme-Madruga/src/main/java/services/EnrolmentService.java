
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Validator;

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

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private Validator			validator;


	// Servicios ajenos

	// Métodos CRUD

	public Collection<Enrolment> findAll() {
		Collection<Enrolment> result;
		result = this.enrolmentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Enrolment findOne(final int enrolmentId) {
		Assert.isTrue(enrolmentId != 0);
		Enrolment result;
		result = this.enrolmentRepository.findOne(enrolmentId);
		Assert.notNull(result);
		return result;
	}

	public void save(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		Brotherhood b = enrolment.getBrotherhood();
		Member m = enrolment.getMember();
		Collection<Member> ms = b.getMembers();
		ms.add(m);
		b.setMembers(ms);
		this.brotherhoodService.save(b);
		this.enrolmentRepository.save(enrolment);
	}

	public void delete(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		this.enrolmentRepository.delete(enrolment);
	}

	// RF 10.3
	public Enrolment create(final Brotherhood brotherhood, final Member member, final Date moment, final Position position) {
		Assert.notNull(member);
		Assert.notNull(moment);
		Assert.notNull(position);
		Assert.notNull(brotherhood);
		Assert.isTrue(LoginService.getPrincipal().getId() == brotherhood.getUserAccount().getId());
		final Enrolment result = new Enrolment();
		result.setBrotherhood(brotherhood);
		result.setMember(member);
		result.setPosition(position);
		result.setMoment(moment);
		return result;
	}

	//----Ale------
	public Integer countEnrolmentsByBrotherhood(final int id) {
		return this.enrolmentRepository.countEnrolmentByBrotherhood(id);
	}
	//JAVI
	public Enrolment reconstruct(final Enrolment enrolment) {
		final Enrolment res;
		Assert.notNull(enrolment.getMember());
		Assert.notNull(enrolment.getPosition());
		Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Member member = enrolment.getMember();
		Date moment = new Date();
		Position position = enrolment.getPosition();
		res = this.create(brotherhood, member, moment, position);
		Assert.notNull(res);
		return res;
	}
	public Collection<Enrolment> enrolmentByMember(int id) {
		Collection<Enrolment> res;
		res = this.enrolmentRepository.enrolmentByMember(id);
		return res;
	}
	public Integer enrolmentsByPosition(String pos) {
		Collection<Enrolment> all = this.enrolmentRepository.findAll();
		Integer res = 0;
		Collection<String> standar = new ArrayList<>();
		standar.add("President");
		standar.add("Vice President");
		standar.add("Secretary");
		standar.add("Treasurer");
		standar.add("Historian");
		standar.add("Fundraiser");
		standar.add("Officer");
		for (Enrolment enrolment : all) {
			String a = enrolment.getPosition().getName();
			if (a.compareTo(pos) == 0) {
				res = res + 1;
			}
			if (pos == "others" && !(standar.contains(pos))) {
				res = res + 1;

			}
		}
		return res;
	}
}
