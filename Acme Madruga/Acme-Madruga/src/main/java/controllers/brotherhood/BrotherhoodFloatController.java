
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FloatService;
import controllers.AbstractController;
import domain.Float;

@Controller
@RequestMapping("/brotherhood/float/")
public class BrotherhoodFloatController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private FloatService	floatService;

	@Autowired
	private ActorService	actorService;


	public BrotherhoodFloatController() {

		super();

	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Integer currentActorId = this.actorService.findByPrincipal().getId();
		Collection<Float> floats;
		floats = this.floatService.findFloatsByBrotherhood(currentActorId);

		result = new ModelAndView("float/list");
		result.addObject("requestURI", "float/list.do");
		result.addObject("floats", floats);

		return result;
	}

}
