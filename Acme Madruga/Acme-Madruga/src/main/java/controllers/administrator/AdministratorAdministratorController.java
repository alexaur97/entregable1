
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorRegisterForm;

@Controller
@RequestMapping("administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final AdministratorRegisterForm registerForm = new AdministratorRegisterForm();
			result = this.createEditModelAndView(registerForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdministratorRegisterForm administratorRegisterForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = new ModelAndView("administrator/edit");
		else
			try {
				final Administrator administrator = this.administratorService.reconstruct(administratorRegisterForm);
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administratorRegisterForm);

				final Collection<String> accounts = this.actorService.findAllAccounts();
				final Collection<String> emails = this.actorService.findAllEmails();

				if (accounts.contains(administratorRegisterForm.getUsername()))
					result.addObject("message", "register.username.error");
				else if (emails.contains(administratorRegisterForm.getEmail()))
					result.addObject("message", "register.email.error");
				else if (!administratorRegisterForm.getConfirmPassword().equals(administratorRegisterForm.getPassword()))
					result.addObject("message", "register.password.error");
				else
					result.addObject("message", "register.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorRegisterForm administratorRegisterForm) {
		return this.createEditModelAndView(administratorRegisterForm, null);
	}

	protected ModelAndView createEditModelAndView(final AdministratorRegisterForm administratorRegisterForm, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("administrator/edit");
		result.addObject("administratorRegisterForm", administratorRegisterForm);
		result.addObject("message", messageCode);

		return result;
	}

}
