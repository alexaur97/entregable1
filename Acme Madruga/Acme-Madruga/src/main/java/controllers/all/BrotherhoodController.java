package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService		brotherhoodService;


	public BrotherhoodController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;
		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("brotherhood/list");
		result.addObject("requestURI", "brotherhood/list.do");
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}

}
