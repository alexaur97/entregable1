
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
	public Collection<Request> findRequestByStatusAndBrotherhood(String status) {
		Collection<Request> result;
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD));
		Integer id = this.brotherhoodService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndBrotherhood(status, id);
		return result;
	}
	//FR 11.1
	public Collection<Request> findRequestByStatusAndMember(String status) {
		Collection<Request> result;
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(Authority.MEMBER));
		Integer id = this.memberService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndMember(status, id);
		return result;
	}
	public Request create() {
		Request result;
		result = new Request();
		result.setStatus("PENDING");
		return result;
	}
	public Request save(Request r) {
		Assert.notNull(r);
		Assert.isTrue((LoginService.getPrincipal().getAuthorities().contains(Authority.MEMBER)) || (LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD)));
		Member m = r.getMember();
		Brotherhood b = r.getProcession().getBrotherhood();
		if (LoginService.getPrincipal().getAuthorities().contains(Authority.MEMBER)) {
			Assert.isTrue(this.memberService.findByPrincipal().getId() == m.getId());
			Assert.isTrue(r.getStatus() == "PENDING");
		}
		if (LoginService.getPrincipal().getAuthorities().contains(Authority.BROTHERHOOD)) {
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getId() == b.getId());
			Collection<Request> rs = this.requestRepository.findRequestApprovedByProcession(r.getProcession().getId(), "APPROVED");
			for (Request request : rs) {
				Assert.isTrue(!((r.getRow() == request.getRow()) && (r.getColumn() == request.getColumn())));

			}
		}

		Collection<Member> b2 = this.memberService.findMembersByBrotherhood(b.getId());
		Assert.isTrue(b2.contains(b));
		return this.requestRepository.save(r);

	}
}
