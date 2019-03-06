
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Brotherhood;
import domain.Member;
import domain.Request;
import forms.MemberRegisterForm;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository		memberRepository;
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private EnrolmentService		enrolmentService;


	public Member findOne(int memberId) {
		Member result;

		result = memberRepository.findOne(memberId);

		return result;
	}

	public Member create() {
		final Member m = new Member();
		final UserAccount ua = new UserAccount();
		m.setUserAccount(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.MEMBER);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		m.getUserAccount().setAuthorities(authorities);
		return m;

	}
	public Member save(final Member m) {
		if (m.getId() != 0) {
			Authority auth = new Authority();
			auth.setAuthority(Authority.BROTHERHOOD);
			Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		}
		Assert.notNull(m);
		return this.memberRepository.save(m);
	}
	//RF 8.2

	public Collection<Member> findMembersByBrotherhood(final int id) {
		Assert.notNull(id);
		final Collection<Member> result = this.memberRepository.findMembersByBrotherhood(id);
		return result;
	}
	public Collection<Member> findMembersByBrotherhoodPrincipal() {
		Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		Collection<Member> result;
		Integer idB = LoginService.getPrincipal().getId();
		Integer id = this.brotherhoodRepository.findByUserId(idB).getId();
		result = this.memberRepository.findMembersByBrotherhood(id);
		return result;
	}

	public Member findMembersById(final int id) {
		Member result;
		result = this.memberRepository.findOne(id);
		return result;
	}
	public Collection<Member> findAll() {
		Collection<Member> result;
		result = this.memberRepository.findAll();
		return result;
	}
	public Member findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		Member result = null;
		if (u != null)
			result = this.memberRepository.findMemberByPrincipal(u.getId());
		return result;
	}
	//---Ale----

	public Collection<Double> statsMembersPerBrotherhood() {
		final Collection<Double> result = this.memberRepository.statsMembersPerBrotherhood();
		Assert.notNull(result);
		return result;
	}
	public Collection<Member> tenPercentMembers() {
		Collection<Member> res = new ArrayList<>();
		final Map<Member, Integer> m = new HashMap<>();
		final Collection<Request> approved = this.memberRepository.approvedRequests();
		final Double tenPerCent = approved.size() * 0.1;
		for (final Request r : approved)
			if (!m.containsKey(r.getMember()))
				m.put(r.getMember(), 1);
			else
				m.put(r.getMember(), m.get(r.getMember()) + 1);
		res = m.keySet();
		for (final Member member : res)
			if (m.get(member) < tenPerCent)
				res.remove(member);
		return res;

	}

	public Member reconstruct(final MemberRegisterForm r) {
		Assert.isTrue(r.getPassword().equals(r.getConfirmPassword()));
		final Member result = this.create();
		final UserAccount userAccount = result.getUserAccount();

		final Md5PasswordEncoder pe = new Md5PasswordEncoder();
		final String password = pe.encodePassword(r.getPassword(), null);

		userAccount.setUsername(r.getUsername());
		userAccount.setPassword(password);

		result.setUserAccount(userAccount);

		result.setName(r.getName());
		result.setMiddleName(r.getMiddleName());
		result.setSurname(r.getSurName());
		result.setPhoto(r.getPhoto());
		result.setEmail(r.getEmail());
		result.setPhoneNumber(r.getPhone());
		result.setAddress(r.getAddress());

		return result;
	}

	//---Ale----

	//JAVI
	public Collection<Member> findAllNotIn() {
		Collection<Member> result;
		result = this.memberRepository.findAll();
		Brotherhood b = this.brotherhoodService.findByPrincipal();
		Collection<Member> ms = b.getMembers();
		result.removeAll(ms);
		return result;
	}
	public Member reconstructEdit(final Actor actor) {
		final Member res;
		res = this.memberRepository.findOne(actor.getId());
		res.setName(actor.getName());
		res.setMiddleName(actor.getMiddleName());
		res.setSurname(actor.getSurname());
		res.setPhoto(actor.getPhoto());
		res.setEmail(actor.getEmail());
		res.setPhoneNumber(actor.getPhoneNumber());
		res.setAddress(actor.getAddress());
		Assert.notNull(res);
		return res;
	}
}
