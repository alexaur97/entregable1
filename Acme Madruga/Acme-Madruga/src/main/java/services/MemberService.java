
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;
import domain.Position;
import domain.Request;

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

	
	public Member findOne(int memberId){
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
		if (!(this.findByPrincipal() == null))
			//PUEDE QUE SEA COMPROBAR EL PROPIO MEMBER
			Assert.isTrue(this.findByPrincipal().getId() == m.getId());
		Assert.notNull(m);
		return this.memberRepository.save(m);
	}
	//RF 8.2

	public Collection<Member> findMembersByBrotherhood(final int id) {
		Assert.notNull(id);
		Collection<Member> result = this.memberRepository.findMembersByBrotherhood(id);
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
		final Integer id = LoginService.getPrincipal().getId();
		final Member result = this.memberRepository.findMemberByPrincipal(id);
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

	//---Ale----

}
