
package controllers.all;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import controllers.AbstractController;
import domain.Member;
import forms.MemberRegisterForm;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private MemberService	memberService;


	public MemberController() {
		super();
	}

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
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final MemberRegisterForm reg = new MemberRegisterForm();
			result = new ModelAndView("member/edit");
			result.addObject("reg", reg);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MemberRegisterForm reg, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("member/edit");
			result.addObject("reg", reg);
		} else
			try {
				final Member member = this.memberService.reconstruct(reg);
				this.memberService.save(member);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("member/edit");
				result.addObject("reg", reg);

				if (reg.getTerms() == false)
					result.addObject("message", "register.terms.error");
				else
					result.addObject("message", "register.commit.error");
			}
		return result;
	}

}
