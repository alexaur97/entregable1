
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.FloatService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Float;

@Controller
@RequestMapping("/brotherhood/float/")
public class BrotherhoodFloatController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private FloatService		floatService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	public BrotherhoodFloatController() {

		super();

	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Integer currentActorId = this.actorService.findByPrincipal().getId();
			Collection<Float> floats;
			floats = this.floatService.findFloatsByBrotherhood(currentActorId);

			result = new ModelAndView("float/list");
			result.addObject("requestURI", "float/list.do");
			result.addObject("floats", floats);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Float floaat;
		floaat = new Float();

		try {
			final Brotherhood bh = this.brotherhoodService.findByPrincipal();
			floaat.setId(0);
			floaat.setBrotherhood(bh);

			result = new ModelAndView("float/list");
			result.addObject("floaat", floaat);
			result = this.createEditModelAndView(floaat);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int floatId) {
		ModelAndView res = new ModelAndView("float/edit");
		try {
			final Float floaat = this.floatService.findOne(floatId);
			Assert.notNull(floaat);
			final Integer idB = this.brotherhoodService.findByPrincipal().getId();
			final Collection<Float> floats = this.floatService.findFloatsByBrotherhood(idB);
			Assert.isTrue(floats.contains(floaat));
			res.addObject("floaat", floaat);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("floaat") Float floaat, final BindingResult binding) {
		ModelAndView res;

		floaat = this.floatService.reconstruct(floaat, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(floaat);
		else
			try {
				final Boolean b = this.floatService.validatePictures(floaat.getPictures());
				if (!b)
					res = this.createEditModelAndView(floaat, "float.picture.error");
				else {
					this.floatService.save(floaat);
					res = new ModelAndView("redirect:/brotherhood/float/list.do");
				}
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(floaat, "float.commit.error");
			}
		return res;
	}
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Float floaat, final BindingResult binding) {
		ModelAndView result;

		try {

			this.floatService.delete(floaat);
			result = new ModelAndView("redirect:/brotherhood/float/list.do");
		} catch (final Throwable oops) {
			final String msg = oops.getMessage();
			result = this.createEditModelAndView(floaat, msg);

		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int floatId) {
		ModelAndView result;

		try {
			Assert.notNull(floatId);

			final Float floaat = this.floatService.findOne(floatId);
			Assert.notNull(floaat);
			final Integer idB = this.brotherhoodService.findByPrincipal().getId();
			final Collection<Float> floats = this.floatService.findFloatsByBrotherhood(idB);
			Assert.isTrue(floats.contains(floaat));

			final Boolean b = this.floatService.validatePictures(floaat.getPictures());
			if (!b)
				result = new ModelAndView("redirect:/#");
			else {
				result = new ModelAndView("float/show");
				result.addObject("floaat", floaat);
				result.addObject("pictures", floaat.getPictures());
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(final Float floaat) {
		return this.createEditModelAndView(floaat, null);
	}
	protected ModelAndView createEditModelAndView(final Float floaat, final String messageCode) {
		final ModelAndView res;
		res = new ModelAndView("float/edit");
		res.addObject("floaat", floaat);

		return res;
	}

}
