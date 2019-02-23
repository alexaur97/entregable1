
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;
import controllers.AbstractController;
import domain.ConfigurationParameters;

@Controller
@RequestMapping("/configurationParameters/administrator")
public class ConfigurationParametersAdministratorController extends AbstractController {

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final ConfigurationParameters config = this.configurationParametersService.find();
		result = this.createEditModelAndView(config);
		return result;

	}

	private ModelAndView createEditModelAndView(final ConfigurationParameters config) {
		return this.createEditModelAndView(config, null);
	}

	private ModelAndView createEditModelAndView(final ConfigurationParameters config, final String messageCode) {
		final ModelAndView result;
		return null;
	}

}
