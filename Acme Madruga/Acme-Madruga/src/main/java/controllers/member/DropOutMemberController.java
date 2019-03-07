
package controllers.member;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.DropOutService;
import services.MemberService;
import domain.Brotherhood;
import domain.DropOut;
import domain.Member;

@Controller
@RequestMapping("dropout/member")
public class DropOutMemberController {

	@Autowired
	DropOutService		dropOutService;

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	MemberService		memberService;

	@Autowired
	ActorService		actorService;


	public DropOutMemberController() {

		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int brotherhoodId) {
		ModelAndView result;
		try {
			final Member member = this.memberService.findByPrincipal();
			final Integer id = member.getId();
			final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);

			final Date date = new Date();
			final DropOut dropOut = this.dropOutService.create(member, brotherhood, date);
			this.dropOutService.save(dropOut);

			final Collection<Brotherhood> brotherhoodsPast = this.brotherhoodService.findBrotherhoodByMemberHasBelonged(id);
			final Collection<Brotherhood> brotherhoodsCurrent = this.brotherhoodService.findBrotherhoodByMemberBelong(id);

			result = new ModelAndView("brotherhood/myList");
			result.addObject("requestURI", "brotherhood/list.do");
			result.addObject("brotherhoodsPast", brotherhoodsPast);
			result.addObject("brotherhoodsCurrent", brotherhoodsCurrent);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;

	}
}
