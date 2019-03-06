
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;


	public Actor save(final Actor a) {
		Assert.notNull(a);
		return this.actorRepository.save(a);

	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor result;

		result = this.actorRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public Actor findByPrincipal() {
		UserAccount userAccount;
		Actor result;

		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Actor findOne(final int id) {

		Assert.isTrue(id != 0);

		Actor result;

		result = this.actorRepository.findOne(id);
		Assert.notNull(result);
		return null;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Boolean auth(final Actor a, final String auth) {
		final UserAccount userAccount = a.getUserAccount();
		final Collection<Authority> allAuths = userAccount.getAuthorities();
		final Authority au = new Authority();
		au.setAuthority(auth);
		final Boolean res = allAuths.contains(au);
		Assert.isTrue(res);
		return res;
	}

	public Collection<String> findAllAccounts() {
		final Collection<String> result = this.actorRepository.findAllAccounts();
		return result;
	}

}
