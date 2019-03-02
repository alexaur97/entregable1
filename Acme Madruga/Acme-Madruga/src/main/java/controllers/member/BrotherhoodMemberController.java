package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood/member/")
public class BrotherhoodMemberController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService		brotherhoodService;	
	
	@Autowired
	private ActorService		actorService;

	public BrotherhoodMemberController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Integer currentActorId = this.actorService.findByPrincipal().getId();
		Collection<Brotherhood> brotherhoodsPast;
		Collection<Brotherhood> brotherhoodsCurrent;
		brotherhoodsPast = this.brotherhoodService.findBrotherhoodByMemberHasBelonged(currentActorId);
		brotherhoodsCurrent = this.brotherhoodService.findBrotherhoodByMemberBelong(currentActorId);

		result = new ModelAndView("brotherhood/myList");
		result.addObject("requestURI", "brotherhood/list.do");
		result.addObject("brotherhoodsPast", brotherhoodsPast);
		result.addObject("brotherhoodsCurrent", brotherhoodsCurrent);

		return result;
	}

}
