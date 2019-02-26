
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("position/administrator")
public class PositionAdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.create();
		result = this.createEditModelAndView(administrator);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		result = this.createEditModelAndView(administrator);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(administrator);
		else
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "actor.commit.error");
			}
		return result;
	}
	/*
	 * public ModelAndView delete() {
	 * final ModelAndView result;
	 * return result;
	 * }
	 */
	private ModelAndView createEditModelAndView(final Administrator administrator) {
		return this.createEditModelAndView(administrator, null);
	}

	private ModelAndView createEditModelAndView(final Administrator administrator, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("actor/edit");
		result.addObject("message", messageCode);

		return result;
	}

}
