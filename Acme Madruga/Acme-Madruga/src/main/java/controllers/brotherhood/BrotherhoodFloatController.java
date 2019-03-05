
package controllers.brotherhood;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FloatService;
import controllers.AbstractController;
import domain.Float;
import forms.FloatForm;

@Controller
@RequestMapping("/float/brotherhood/")
public class BrotherhoodFloatController extends AbstractController {

	@Autowired
	private FloatService	floatService;

	@Autowired
	private ActorService	actorService;


	public BrotherhoodFloatController() {
		super();
	}

	// List -----------------------------------------------------------
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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FloatForm floatForm;

		floatForm = new FloatForm();
		floatForm.setFloatId(0);

		result = new ModelAndView("float/create");
		result.addObject("floatForm", floatForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int floatId) {
		final ModelAndView res = new ModelAndView("float/edit");
		final FloatForm floatForm = this.floatService.toForm(floatId);
		res.addObject("floatForm", floatForm);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FloatForm floatForm, final BindingResult binding) {
		ModelAndView res;
		if (floatForm.getFloatId() == 0)
			res = new ModelAndView("float/create");
		else
			res = new ModelAndView("float/edit");

		if (binding.hasErrors())
			res.addObject("floatForm", floatForm);
		else
			try {
				final Float floaat = this.floatService.reconstruct(floatForm);
				this.floatService.save(floaat);
				res = new ModelAndView("redirect:/brotherhood/float/list.do");
			} catch (final Throwable oops) {
				res.addObject("message", "float.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FloatForm floatForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final Float floaat = this.floatService.reconstruct(floatForm);
			this.floatService.delete(floaat);
			result = new ModelAndView("redirect:/brotherhood/float/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("float/edit");
			result.addObject("floatForm", floatForm);
			result.addObject("message", oops.getMessage());
			final String msg = oops.getMessage();
			if (msg.equals("cannotDelete")) {
				final Boolean cannotDelete = true;
				result.addObject("cannotDelete", cannotDelete);
			}
		}

		return result;
	}

}
