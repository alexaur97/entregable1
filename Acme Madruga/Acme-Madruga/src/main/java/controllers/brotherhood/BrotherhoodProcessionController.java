
package controllers.brotherhood;

import java.util.Collection;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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

import domain.DropOut;
import domain.Enrolment;
import domain.Member;
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
		try{
		final Integer currentActorId = this.actorService.findByPrincipal().getId();
		Collection<Procession> processions;
		processions = this.processionService.findProcessionsByBrotherhood(currentActorId);

		result = new ModelAndView("procession/list");
		result.addObject("requestURI", "procession/list.do");
		result.addObject("processions", processions);
		}catch (Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		
		ModelAndView result;
		Procession procession;
		procession = new Procession();
		
		try{
		Brotherhood bh = this.brotherhoodService.findByPrincipal();
		
		procession.setId(0);
		procession.setBrotherhood(bh);
		
	
		
		Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());

		result = new ModelAndView("procession/edit");
		result.addObject("procession", procession);
		result.addObject("floats", floats);
		}catch (final Throwable oops) {
			final String msg = oops.getMessage();
			result = createEditModelAndView(procession, msg);
			
		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId ) {
		 ModelAndView res = new ModelAndView("procession/edit");
		final Procession procession = this.processionService.findOne(processionId);
		
		try{
			
		Brotherhood bh = this.brotherhoodService.findByPrincipal();
		Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());
		res.addObject("procession", procession);
		res.addObject("floats", floats);
		}catch (final Throwable oops) {
			final String msg = oops.getMessage();
			res = createEditModelAndView(procession, msg);
			
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Procession procession,  BindingResult binding) {
		ModelAndView res;
		
		procession = processionService.reconstruct(procession, binding);
		
		if (binding.hasErrors()){
			res = createEditModelAndView(procession);
		}else
			try {
				this.processionService.save(procession);
				res = new ModelAndView("redirect:/brotherhood/procession/list.do");
			} catch (final Throwable oops) {
				final String msg = oops.getMessage();
				res = createEditModelAndView(procession, msg);
				
			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete( Procession procession, BindingResult binding) {
		ModelAndView result;

		try {
			
			this.processionService.delete(procession);
			result = new ModelAndView("redirect:/brotherhood/procession/list.do");
		} catch (final Throwable oops) {
			final String msg = oops.getMessage();
			result = createEditModelAndView(procession, msg);
			
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;

		try {
			Assert.notNull(processionId);

			procession = this.processionService.findOne(processionId);
			Collection<Float> floats = procession.getFloats();
			result = new ModelAndView("procession/show");
		//	result.addObject("requestURI", "procession/show.do?=" + processionId);
			result.addObject("procession", procession);
			result.addObject("floats", floats);

			
		} catch (Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(final Procession procession) {
		return this.createEditModelAndView(procession, null);
	}
	protected ModelAndView createEditModelAndView(final Procession procession, final String messageCode) {
		final ModelAndView res;
		res = new ModelAndView("procession/edit");
		Brotherhood bh = this.brotherhoodService.findByPrincipal();
		Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());
		res.addObject("procession", procession);
		res.addObject("floats", floats);

		return res;
	}

}
