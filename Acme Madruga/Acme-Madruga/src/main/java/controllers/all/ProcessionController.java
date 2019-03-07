
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private ProcessionService	processionService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(final int brotherhoodId) {
		ModelAndView result;
		Collection<Procession> processions;

		try {
			Assert.notNull(brotherhoodId);

			processions = this.processionService.findFinalProcessionsByBrotherhood(brotherhoodId);

			result = new ModelAndView("procession/listByBrotherhood");
		//	result.addObject("requestURI", "procession/listByBrotherhood.do?=" + brotherhoodId);
			result.addObject("processions", processions);
		} catch (Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
