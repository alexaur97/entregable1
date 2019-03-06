
package controllers.all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import forms.ActorEditForm;

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
	public ModelAndView edit() {
		ModelAndView result;

		try {
			Actor actor = this.actorService.findByPrincipal();
			ActorEditForm actorEditForm = this.actorService.toForm(actor);
			result = new ModelAndView("actor/edit");
			result.addObject("actorEditForm", actorEditForm);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(ActorEditForm actorEditForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("actor/edit");
			res.addObject("actorEditForm", actorEditForm);
		} else {
			try {
				Actor actor = this.actorService.findByPrincipal();
				if (this.actorService.authEdit(actor, "BROTHERHOOD")) {
					Brotherhood brotherhood = this.brotherhoodService.reconstructEdit(actorEditForm);
					this.brotherhoodService.save(brotherhood);
				} else if (this.actorService.authEdit(actor, "MEMBER")) {
					Member member = this.memberService.reconstructEdit(actorEditForm);
					this.memberService.save(member);
				} else {
					Administrator administrator = this.administratorService.reconstructEdit(actorEditForm);
					this.administratorService.save(administrator);
				}
				res = new ModelAndView("redirect:/#");
			} catch (final Throwable oops) {
				res = new ModelAndView("actor/edit");
				res.addObject("requestURI", "actor/edit.do");
				res.addObject("message", "actor.commit.error");
			}
		}
		return res;
	}
	//JAVI

}
