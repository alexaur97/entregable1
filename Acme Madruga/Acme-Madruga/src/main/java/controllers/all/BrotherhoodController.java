
package controllers.all;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
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

	@Autowired
	private ActorService		actorService;


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
			final BrotherhoodRegisterForm registerForm = new BrotherhoodRegisterForm();
			result = this.createEditModelAndView(registerForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BrotherhoodRegisterForm brotherhoodRegisterForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = new ModelAndView("brotherhood/edit");
		else
			try {
				final Brotherhood brotherhood = this.brotherhoodService.reconstruct(brotherhoodRegisterForm);
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(brotherhoodRegisterForm);

				final Collection<String> accounts = this.actorService.findAllAccounts();
				final Collection<String> emails = this.actorService.findAllEmails();

				if (accounts.contains(brotherhoodRegisterForm.getUsername()))
					result.addObject("message", "register.username.error");
				else if (emails.contains(brotherhoodRegisterForm.getEmail()))
					result.addObject("message", "register.email.error");
				else if (!brotherhoodRegisterForm.getConfirmPassword().equals(brotherhoodRegisterForm.getPassword()))
					result.addObject("message", "register.password.error");
				else
					result.addObject("message", "register.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final BrotherhoodRegisterForm brotherhoodRegisterForm) {
		return this.createEditModelAndView(brotherhoodRegisterForm, null);
	}

	protected ModelAndView createEditModelAndView(final BrotherhoodRegisterForm brotherhoodRegisterForm, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhoodRegisterForm", brotherhoodRegisterForm);
		result.addObject("message", messageCode);

		return result;
	}
}
