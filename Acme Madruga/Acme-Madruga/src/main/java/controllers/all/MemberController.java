
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import controllers.AbstractController;
import domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private MemberService	memberService;


	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(final int brotherhoodId) {
		ModelAndView result;
		Collection<Member> members;

		try {
			Assert.notNull(brotherhoodId);

			members = this.memberService.findMembersByBrotherhood(brotherhoodId);

			result = new ModelAndView("member/listByBrotherhood");
			result.addObject("requestURI", "member/listByBrotherhood.do?=" + brotherhoodId);
			result.addObject("members", members);
		} catch (Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
