
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.DropOutService;
import services.EnrolmentService;
import services.MemberService;
import controllers.AbstractController;
import domain.DropOut;
import domain.Enrolment;
import domain.Member;

@Controller
@RequestMapping("member/brotherhood")
public class MemberBrotherhoodController extends AbstractController {

	@Autowired
	MemberService		memberService;
	@Autowired
	BrotherhoodService	brotherhoodService;
	@Autowired
	EnrolmentService	enrolmentService;
	@Autowired
	DropOutService		dropOutService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Member> members = this.brotherhoodService.findByPrincipal().getMembers();
		result = new ModelAndView("member/list");
		result.addObject("members", members);
		result.addObject("requestURI", "member/brotherhood/list.do");
		return result;
	}
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(final int memberId) {
		ModelAndView result;
		Member member;

		try {
			Assert.notNull(memberId);

			member = this.memberService.findMembersById(memberId);
			Collection<Enrolment> enrolments = this.enrolmentService.enrolmentByMember(memberId);
			Collection<DropOut> dropOuts = this.dropOutService.dropOutByMember(memberId);
			result = new ModelAndView("member/profile");
			result.addObject("requestURI", "member/profile.do?=" + memberId);
			result.addObject("member", member);
			result.addObject("dropOuts", dropOuts);
			result.addObject("enrolments", enrolments);
		} catch (Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
