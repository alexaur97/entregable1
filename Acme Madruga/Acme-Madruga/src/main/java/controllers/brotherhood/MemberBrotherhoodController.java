
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import controllers.AbstractController;
import domain.Member;

@Controller
@RequestMapping("member/brotherhood")
public class MemberBrotherhoodController extends AbstractController {

	@Autowired
	MemberService	memberService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Member> members = memberService.findMembersByBrotherhoodPrincipal();
		result = new ModelAndView("member/list");
		result.addObject("members", members);
		result.addObject("requestURI", "member/brotherhood/list.do");
		return result;
	}
}
