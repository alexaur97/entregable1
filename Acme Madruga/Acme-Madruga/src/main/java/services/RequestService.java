
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import domain.Brotherhood;
import domain.Member;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MemberService		memberService;


	//FR 10.6
	public Collection<Request> findRequestByStatusAndBrotherhood(final String status) {
		Collection<Request> result;
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD));
		final Integer id = this.brotherhoodService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndBrotherhood(status, id);
		return result;
	}
	//FR 11.1
	public Collection<Request> findRequestByStatusAndMember(final String status) {
		Collection<Request> result;
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.MEMBER));
		final Integer id = this.memberService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndMember(status, id);
		return result;
	}
	public Request create() {
		Request result;
		result = new Request();
		result.setStatus("PENDING");
		return result;
	}
	public Request save(final Request r) {
		Assert.notNull(r);
		Assert.isTrue((LoginService.getPrincipal().getAuthorities().contains(Authority.MEMBER)) || (LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD)));
		final Member m = r.getMember();
		final Brotherhood b = r.getProcession().getBrotherhood();
		if (LoginService.getPrincipal().getAuthorities().contains(Authority.MEMBER)) {
			Assert.isTrue(this.memberService.findByPrincipal().getId() == m.getId());
			Assert.isTrue(r.getStatus() == "PENDING");
		}
		if (LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD)) {
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getId() == b.getId());
			final Collection<Request> rs = this.requestRepository.findRequestApprovedByProcession(r.getProcession().getId(), "APPROVED");
			for (final Request request : rs)
				Assert.isTrue(!((r.getRow() == request.getRow()) && (r.getColumn() == request.getColumn())));
		}

		final Collection<Member> b2 = this.memberService.findMembersByBrotherhood(b.getId());
		Assert.isTrue(b2.contains(b));
		return this.requestRepository.save(r);

	}

	//---Ale---

	public Double approvedRatio() {
		final Double result = this.requestRepository.approvedRatio();
		return result;
	}

	public Double pendingRatio() {
		final Double result = this.requestRepository.pendingRatio();
		return result;
	}

	public Double rejectedRatio() {
		final Double result = this.requestRepository.rejectedRatio();
		return result;
	}

	//---Ale---

}
