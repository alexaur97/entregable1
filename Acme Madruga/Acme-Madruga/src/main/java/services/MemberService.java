
package services;

import java.util.ArrayList;
import java.util.Collection;

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

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository		memberRepository;
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;


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
		if (!(this.findByPrincipal() == null)) {
			//PUEDE QUE SEA COMPROBAR EL PROPIO MEMBER
			Assert.isTrue(this.findByPrincipal().getId() == m.getId());
		}
		Assert.notNull(m);
		return this.memberRepository.save(m);
	}
	//RF 8.2

	public Collection<Member> findMembersByBrotherhood(int id) {
		Collection<Member> result;
		result = this.memberRepository.findMembersByBrotherhood(id);
		return result;
	}
	public Collection<Member> findMembersByBrotherhoodPrincipal() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD));
		Collection<Member> result;
		Integer idB = LoginService.getPrincipal().getId();
		Integer id = this.brotherhoodRepository.findByUserId(idB).getId();
		result = this.memberRepository.findMembersByBrotherhood(id);
		return result;
	}

	public Member findMembersById(int id) {
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
		Integer id = LoginService.getPrincipal().getId();
		Member result = this.memberRepository.findMemberByPrincipal(id);
		return result;
	}
}
