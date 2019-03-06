
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import services.PositionService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Member;
import domain.Position;
import domain.Procession;

@Controller
@RequestMapping("/stats/administrator")
public class StatsAdministratorController extends AbstractController {

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private PositionService		positionService;
	@Autowired
	private EnrolmentService	enrolmentService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Collection<Double> membersPerBrotherhood = this.memberService.statsMembersPerBrotherhood();
		final Collection<Brotherhood> largestBrotherhoods = this.brotherhoodService.findLargest();
		final Collection<Brotherhood> smallestBrotherhoods = this.brotherhoodService.findSmallest();
		final Double approvedRatio = this.requestService.approvedRatio();
		final Double pendingRatio = this.requestService.pendingRatio();
		final Double rejectedRatio = this.requestService.rejectedRatio();
		final Collection<Procession> soon = this.processionService.processionsBefore30Days();
		final Collection<Member> members = this.memberService.tenPercentMembers();
		final Collection<Position> positions = this.positionService.findAll();

		String pos = "";
		String posEs = "";

		//		for (final Position p : positions) {
		//			final Integer i = this.positionService.numberOfPositionsById(p.getId());
		//			pos = pos + "{label: '" + p.getName() + "', backgroundColor:'blue', data['" + i + "']},";
		//			posEs = posEs + "{label: '" + p.getNameEs() + "', backgroundColor:'blue', data['" + i + "']},";
		//		}

		int president = this.enrolmentService.enrolmentsByPosition("President");
		int vicePresident = this.enrolmentService.enrolmentsByPosition("Vice President");
		Integer secretary = this.enrolmentService.enrolmentsByPosition("Secretary");
		Integer treasurer = this.enrolmentService.enrolmentsByPosition("Treasure");
		Integer historian = this.enrolmentService.enrolmentsByPosition("Historian");
		Integer fundraiser = this.enrolmentService.enrolmentsByPosition("Fundraiser");
		Integer officer = this.enrolmentService.enrolmentsByPosition("Officer");
		Integer others = this.enrolmentService.enrolmentsByPosition("others");

		int numero = 3;

		result = new ModelAndView("stats/display");
		//		result.addObject("membersPerBrotherhood", membersPerBrotherhood);
		//		result.addObject("largestBrotherhoods", largestBrotherhoods);
		//		result.addObject("smallestBrotherhoods", smallestBrotherhoods);
		//		result.addObject("approvedRatio", approvedRatio);
		//		result.addObject("pendingRatio", pendingRatio);
		//		result.addObject("rejectedRatio", rejectedRatio);
		//		result.addObject("soon", soon);
		//		result.addObject("members", members);
		//		result.addObject("pos", pos);
		//		result.addObject("posEs", posEs);
		result.addObject("president", president);
		result.addObject("vicePresident", vicePresident);
		result.addObject("secretary", secretary);
		result.addObject("treasurer", treasurer);
		result.addObject("historian", historian);
		result.addObject("fundraiser", fundraiser);
		result.addObject("officer", officer);
		result.addObject("others", others);
		result.addObject("numero", numero);

		return result;
	}
}
