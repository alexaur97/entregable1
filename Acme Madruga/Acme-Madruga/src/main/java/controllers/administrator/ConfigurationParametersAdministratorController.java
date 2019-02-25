
package controllers.administrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ConfigurationParameters c, final BindingResult b) {
		ModelAndView result;
		if (b.hasErrors())
			result = this.createEditModelAndView(c);
		else
			try {
				this.configurationParametersService.save(c);

				//Creamos el model and view de la página a la que nos lleva el form
				result = new ModelAndView("welcome/index");

				//rellenamos los datos necesarios para que en la página de welcome aparezcan el banner, el mensaje del
				//sistema, la hora, etc.
				SimpleDateFormat formatter;
				String moment;

				//el tipo Locale nos permite hacer operaciones con el idioma del sistema
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();

				final String name = c.getName();
				String sysMessage = "";

				if (lang == "en")
					sysMessage = sysMessage + c.getSysMessage();
				else if (lang == "es")
					sysMessage = sysMessage + c.getSysMessageEs();

				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());

				//añadimos en el modelo todos los elementos necesarios>>>>>
				result.addObject("name", name);
				result.addObject("sysMessage", sysMessage);
				result.addObject("moment", moment);
				result.addObject("lang", lang);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(c, "configurationParameters.commit.error");
			}

		Assert.notNull(c);
		return result;

	}

	private ModelAndView createEditModelAndView(final ConfigurationParameters config) {
		return this.createEditModelAndView(config, null);
	}

	private ModelAndView createEditModelAndView(final ConfigurationParameters config, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("configurationParameters/edit");
		result.addObject("config", config);
		result.addObject("message", messageCode);
		final String banner = config.getBanner();
		result.addObject("banner", banner);
		return result;
	}

}
