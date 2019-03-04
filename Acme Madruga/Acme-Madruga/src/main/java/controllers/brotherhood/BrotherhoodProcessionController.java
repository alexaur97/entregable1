
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
import services.ProcessionService;
import controllers.AbstractController;
import domain.Procession;
import forms.ProcessionForm;

@Controller
@RequestMapping("/brotherhood/procession/")
public class BrotherhoodProcessionController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private ActorService		actorService;


	public BrotherhoodProcessionController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Integer currentActorId = this.actorService.findByPrincipal().getId();
		Collection<Procession> processions;
		processions = this.processionService.findProcessionsByBrotherhood(currentActorId);

		result = new ModelAndView("procession/list");
		result.addObject("requestURI", "procession/list.do");
		result.addObject("processions", processions);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProcessionForm processionForm;

		processionForm = new ProcessionForm();
		processionForm.setProcessionId(0);

		result = new ModelAndView("procession/create");
		result.addObject("processionForm", processionForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		final ModelAndView res = new ModelAndView("procession/edit");
		final ProcessionForm processionForm = this.processionService.toForm(processionId);
		res.addObject("processionForm", processionForm);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProcessionForm processionForm, final BindingResult binding) {
		ModelAndView res;
		if (processionForm.getProcessionId() == 0)
			res = new ModelAndView("procession/create");
		else
			res = new ModelAndView("procession/edit");

		if (binding.hasErrors())
			res.addObject("processionForm", processionForm);
		else
			try {
				final Procession procession = this.processionService.reconstruct(processionForm);
				this.processionService.save(procession);
				res = new ModelAndView("redirect:/brotherhood/procession/list.do");
			} catch (final Throwable oops) {
				res.addObject("message", "procession.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProcessionForm processionForm, final BindingResult binding) {
		ModelAndView result;

		try {
			final Procession procession = this.processionService.reconstruct(processionForm);
			this.processionService.delete(procession);
			result = new ModelAndView("redirect:/brotherhood/procession/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("procession/edit");
			result.addObject("processionForm", processionForm);
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
