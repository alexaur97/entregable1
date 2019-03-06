
package controllers.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.MemberService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private MemberService			memberService;
	@Autowired
	private AdministratorService	administratorService;


	//JAVI
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int actorId) {
		ModelAndView result;
		try {
			Actor actor = this.actorService.findOne(actorId);
			Assert.notNull(actor);
			result = new ModelAndView("actor/edit");
			result.addObject("actor", actor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(Actor actor, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = new ModelAndView("actor/edit");
			res.addObject("actor", actor);
		} else
			try {

				if (this.actorService.auth(actor, "BROTHERHOOD")) {
					Brotherhood brotherhood = this.brotherhoodService.reconstructEdit(actor);
					this.brotherhoodService.save(brotherhood);
				} else if (this.actorService.auth(actor, "MEMBER")) {
					Member member = this.memberService.reconstructEdit(actor);
					this.memberService.save(member);
				} else {
					Administrator administrator = this.administratorService.reconstructEdit(actor);
					this.administratorService.save(administrator);
				}
				res = new ModelAndView("redirect:/#");
			} catch (final Throwable oops) {
				res = new ModelAndView("actor/edit");
				res.addObject("requestURI", "actor/edit.do");
				res.addObject("message", "actor.commit.error");
			}
		return res;
	}
	//JAVI

}
