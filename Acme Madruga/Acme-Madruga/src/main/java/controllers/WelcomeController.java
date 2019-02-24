/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;
import domain.ConfigurationParameters;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		final ConfigurationParameters config = this.configurationParametersService.find();
		final String name;
		final String banner;

		final Locale l = LocaleContextHolder.getLocale();
		final String lang = l.getLanguage();

		name = config.getName();
		banner = config.getBanner();
		String sysMessage = "";

		if (lang == "en")
			sysMessage = sysMessage + config.getMessage();
		else if (lang == "es")
			sysMessage = sysMessage + config.getMessageEs();

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("banner", banner);
		result.addObject("sysMessage", sysMessage);
		result.addObject("moment", moment);
		result.addObject("lang", lang);

		return result;
	}
}
