package controllers.procession;



import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.ProcessionService;

import controllers.AbstractController;
import domain.Brotherhood;
import domain.Procession;

@Controller
@RequestMapping("/brotherhood/procession/")
public class BrotherhoodProcessionController extends AbstractController {

	// Supporting services ----------------------------------------------------

		@Autowired
		private ProcessionService		processionService;	
		
		@Autowired
		private ActorService		actorService;

		public BrotherhoodProcessionController() {
			super();
		}

		// List -----------------------------------------------------------
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Integer currentActorId = this.actorService.findByPrincipal().getId();
			Collection<Procession> processions;
			processions = this.processionService.findProcessionsByBrotherhood(currentActorId);

			result = new ModelAndView("procession/list");
			result.addObject("requestURI", "procession/list.do");
			result.addObject("processions", processions);

			return result;
		}
}
