
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
//import domain.Administrator;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private ActorService			actorService;


	public Brotherhood save(final Brotherhood b) {
		Assert.notNull(b);
		this.findByPrincipal();
		return this.brotherhoodRepository.save(b);

	}
	//RF.2 List the brotherhoods in the system

	public Collection<Brotherhood> findAll() {
		Collection<Brotherhood> result;
		result = this.brotherhoodRepository.findAll();
		return result;
	}

	public Brotherhood findOne(final int id) {
		Assert.isTrue(id != 0);
		this.findByPrincipal();
		Brotherhood result;
		result = this.brotherhoodRepository.findOne(id);
		return result;
	}

	public Brotherhood create() {

		Assert.isTrue(!LoginService.getPrincipal().getAuthorities().containsAll(Authority.listAuthorities()), "You can't register if you are already registered");
		final Brotherhood b = new Brotherhood();
		final UserAccount ua = new UserAccount();
		b.setUserAccount(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		b.getUserAccount().setAuthorities(authorities);
		return b;

	}

	public void delete(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		this.findByPrincipal();
		this.brotherhoodRepository.delete(brotherhood);

	}

	public Collection<Brotherhood> findBrotherhoodByMemberBelong(final int id) {
		Assert.isTrue(id != 0);
		Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		Collection<Brotherhood> result = new ArrayList<Brotherhood>();
		Collection<Brotherhood> brotherhoods;
		brotherhoods = this.brotherhoodRepository.findBrotherhoodByMemberBelong(id);
		for(Brotherhood b : brotherhoods){
			if(b.getMembers().contains(this.actorService.findByPrincipal())){
				result.add(b);
			}
		}
		return result;
	}

	public Collection<Brotherhood> findBrotherhoodByMemberHasBelonged(final int id) {
		Assert.isTrue(id != 0);
		Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		Collection<Brotherhood> result;
		result = this.brotherhoodRepository.findBrotherhoodByMemberHasBelonged(id);
		return result;

	}

	public Brotherhood findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Brotherhood b = this.findByUserId(user.getId());
		Assert.notNull(b);
		this.actorService.auth(b, Authority.BROTHERHOOD);
		return b;
	}

	public Brotherhood findByUserId(final int id) {
		final Brotherhood b = this.brotherhoodRepository.findByUserId(id);
		return b;
	}

	//---Ale----

	public Collection<Brotherhood> findLargest() {
		final Collection<Brotherhood> result = new ArrayList<>();
		final Collection<Brotherhood> largest = this.brotherhoodRepository.findLargest();
		Assert.notNull(largest);
		if (largest.size() > 3)
			for (int i = 0; i < 3; i++) {
				final Brotherhood b = (Brotherhood) largest.toArray()[i];
				result.add(b);
			}
		else
			result.addAll(largest);
		return result;
	}

	public Collection<Brotherhood> findSmallest() {
		final Collection<Brotherhood> result = new ArrayList<>();
		final Collection<Brotherhood> smallest = this.brotherhoodRepository.findSmallest();
		Assert.notNull(smallest);
		if (smallest.size() > 3)
			for (int i = 0; i < 3; i++) {
				final Brotherhood b = (Brotherhood) smallest.toArray()[i];
				result.add(b);
			}
		else
			result.addAll(smallest);
		return result;
	}

	//---Ale----
}
