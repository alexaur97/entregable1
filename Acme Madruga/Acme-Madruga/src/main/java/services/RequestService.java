
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
import domain.Position;
import domain.Request;
import forms.PositionForm;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MemberService		memberService;
	@Autowired
	private ActorService		actorService;
	
	public Request findOne(final int id) {
		Assert.isTrue(id != 0);
		Request result;
		result = this.requestRepository.findOne(id);
		return result;
	}
	
	public void delete(final Request request) {
		Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		Assert.notNull(request);
		Assert.isTrue(request.getMember().getId() == memberService.findByPrincipal().getId());
		Assert.isTrue(request.getStatus().equals("PENDING"));
		this.requestRepository.delete(request);
	}

	//FR 10.6
	public Collection<Request> findRequestByStatusAndBrotherhood(final String status) {
		Collection<Request> result;
		Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

		final Integer id = this.memberService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndBrotherhood(status, id);
		return result;
	}
	
	//FR 11.1
	public Collection<Request> findRequestByStatusAndMember(final String status) {
		Collection<Request> result;
		Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

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
		Authority auth = new Authority();
		Authority auth2 = new Authority();
		auth.setAuthority(Authority.MEMBER);
		auth2.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Member m = r.getMember();
		final Brotherhood b = r.getProcession().getBrotherhood();
		if (LoginService.getPrincipal().getAuthorities().contains(auth)) {
			Assert.isTrue(this.memberService.findByPrincipal().getId() == m.getId());
			Assert.isTrue(r.getStatus() == "PENDING");
		}
		if (LoginService.getPrincipal().getAuthorities().contains(auth2)) {
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getId() == b.getId());
			final Collection<Request> rs = this.requestRepository.findRequestApprovedByProcession(r.getProcession().getId(), "APPROVED");
			for (final Request request : rs)
				Assert.isTrue(!((r.getRow() == request.getRow()) && (r.getColumn() == request.getColumn())));
		}

		final Brotherhood brotherhood = r.procession.brotherhood;
		Assert.isTrue(brotherhood.getMembers().contains(m));
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
	
	public Request reconstruct(final RequestForm requestForm) {
		final Request res;
		if (requestForm.getRequestId() == 0)
			res = this.create();
		else
			res = this.findOne(requestForm.getRequestId());
		res.setProcession(requestForm.getProcession());
		res.setRow(requestForm.getRow());
		res.setColumn(requestForm.getColumn());
		res.setStatus("PENDING");
		res.setMember(this.memberService.findByPrincipal());
		return res;
	}

}
