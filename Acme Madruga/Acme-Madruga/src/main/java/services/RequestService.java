
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
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
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;


	public Request findOne(int id) {

		Request result;
		result = this.requestRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Request request) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		Assert.notNull(request);
		Assert.isTrue(request.getMember().getId() == this.memberService.findByPrincipal().getId());
		Assert.isTrue(request.getStatus().equals("PENDING"));
		this.requestRepository.delete(request);
	}

	//FR 10.6
	public Collection<Request> findRequestByStatusAndBrotherhood(final String status) {
		Collection<Request> result;
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

		final Integer id = this.brotherhoodService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndBrotherhood(status, id);
		return result;
	}

	//FR 11.1
	public Collection<Request> findRequestByStatusAndMember(final String status) {
		Collection<Request> result;
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

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
		Actor a = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.authEdit(a, "MEMBER") || this.actorService.authEdit(a, "BROTHERHOOD"));
		final Member m = r.getMember();
		final Brotherhood b = r.getProcession().getBrotherhood();
		if (this.actorService.authEdit(a, "MEMBER")) {
			Assert.isTrue(this.memberService.findByPrincipal().getId() == m.getId());
			Assert.isTrue(r.getStatus() == "PENDING");
		}
		if (this.actorService.authEdit(a, "BROTHERHOOD")) {
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getId() == b.getId());
			final Collection<Request> rs = this.requestRepository.findRequestApprovedByProcession(r.getProcession().getId(), "APPROVED");

		}
		final Brotherhood brotherhood = r.procession.brotherhood;
		Assert.isTrue(brotherhood.getMembers().contains(m));
		Request res = r;
		return this.requestRepository.save(res);

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

	public Request reconstruct(final Request request) {
		final Request res;
		if (request.getId() == 0)
			res = this.create();
		else
			res = this.findOne(request.getId());
		res.setProcession(request.getProcession());
		res.setStatus("PENDING");
		res.setMember(this.memberService.findByPrincipal());
		return res;
	}

	public Request rejectRecostruction(Request request, BindingResult binding) {
		Request res = request;
		Request a = this.requestRepository.findOne(request.getId());
		res.setStatus("REJECTED");
		res.setMember(a.getMember());
		res.setProcession(a.getProcession());
		res.setExplanation(a.getExplanation());
		return res;
	}
	public Request acceptRecostruction(Request request, BindingResult binding) {
		Request result = request;
		int id = request.getId();
		Request res = this.requestRepository.findOne(id);
		result.setStatus("APPROVED");
		result.setMember(res.member);
		result.setProcession(res.procession);
		validator.validate(result, binding);
		return result;
	}
	public Boolean posDisp(int id, int column, int row) {
		Collection<Request> all = this.requestRepository.findRequestApprovedByProcession(id, "APPROVED");
		Boolean res = true;
		for (Request request : all) {
			if (request.getColumn() == column && request.getRow() == row) {
				res = false;
			}
		}
		return res;
	}

	public String findPos(int id) {
		Collection<Request> all = this.requestRepository.findRequestApprovedByProcession(id, "APPROVED");
		int posR = 0;
		int posC = 0;
		for (Request request : all) {
			int column = request.getColumn();
			int row = request.getRow();
			if ((column - 1) > 0 && (row - 1) > 0) {
				if (this.posDisp(id, column - 1, row)) {
					posC = column - 1;
					posR = row;
				} else if (this.posDisp(id, column, row - 1)) {
					posC = column;
					posR = row - 1;
				} else if (this.posDisp(id, column - 1, row - 1)) {
					posC = column - 1;
					posR = row - 1;
				}
			} else {
				if (this.posDisp(id, column + 1, row)) {
					posC = column + 1;
					posR = row;
				} else if (this.posDisp(id, column, row + 1)) {
					posC = column;
					posR = row + 1;
				} else if (this.posDisp(id, column + 1, row + 1)) {
					posC = column + 1;
					posR = row + 1;
				}
			}
		}
		String result = "Columna: " + posC + "Fila: " + posR;
		return result;
	}
}
