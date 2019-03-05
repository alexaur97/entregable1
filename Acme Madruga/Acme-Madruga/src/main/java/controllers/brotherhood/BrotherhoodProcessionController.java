
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
import services.BrotherhoodService;
import services.FloatService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Procession;
import domain.Float;


@Controller
@RequestMapping("/brotherhood/procession/")
public class BrotherhoodProcessionController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private ActorService		actorService;
	
	@Autowired
	private BrotherhoodService	brotherhoodService;
	
	@Autowired
	private FloatService floatService;
	
	


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
		Procession procession;
		procession = new Procession();
		Brotherhood bh = this.brotherhoodService.findByPrincipal();
		//processionForm = new ProcessionForm();
		procession.setId(0);
		procession.setBrotherhood(bh);
		
	
		
		Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());

		result = new ModelAndView("procession/edit");
		result.addObject("procession", procession);
		result.addObject("floats", floats);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId ) {
		final ModelAndView res = new ModelAndView("procession/edit");
		final Procession procession = this.processionService.findOne(processionId);
		Brotherhood bh = this.brotherhoodService.findByPrincipal();
		Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());
		res.addObject("procession", procession);
		res.addObject("floats", floats);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Procession procession,  BindingResult binding) {
		ModelAndView res;
		
		procession = processionService.reconstruct(procession, binding);
		
		if (binding.hasErrors()){
			res = new ModelAndView("procession/edit");
			Brotherhood bh = this.brotherhoodService.findByPrincipal();
			Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());
			res.addObject("procession", procession);
			res.addObject("floats", floats);
		}else
			try {
				this.processionService.save(procession);
				res = new ModelAndView("redirect:/brotherhood/procession/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/brotherhood/procession/edit.do");
				res.addObject("message", "procession.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete( Procession procession, final BindingResult binding) {
		ModelAndView result;

		try {
			 procession = this.processionService.reconstruct(procession,binding);
			this.processionService.delete(procession);
			result = new ModelAndView("redirect:/brotherhood/procession/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("procession/edit");
			result.addObject("procession", procession);
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
