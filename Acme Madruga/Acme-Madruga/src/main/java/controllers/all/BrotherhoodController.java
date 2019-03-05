
package controllers.all;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brotherhood;
import forms.BrotherhoodRegisterForm;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService	brotherhoodService;


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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final BrotherhoodRegisterForm reg = new BrotherhoodRegisterForm();
			result = new ModelAndView("brotherhood/create");
			result.addObject("reg", reg);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BrotherhoodRegisterForm reg, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("brotherhood/create");
			result.addObject("reg", reg);
		} else
			try {
				final Brotherhood brotherhood = this.brotherhoodService.reconstruct(reg);
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("brotherhood/create");
				result.addObject("reg", reg);

				if (reg.getTerms() == false)
					result.addObject("message", "register.terms.error");
				else
					result.addObject("message", "register.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		final ModelAndView result = this.createEditModelAndView(brotherhood, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Brotherhood brotherhood, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("brotherhood/edit");

		result.addObject("brotherhood", brotherhood);
		result.addObject("message", messageCode);

		final UserAccount userAccount = brotherhood.getUserAccount();
		result.addObject("userAccount", userAccount);

		return result;
	}

}
