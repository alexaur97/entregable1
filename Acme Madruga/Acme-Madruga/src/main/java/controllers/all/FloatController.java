
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FloatService;
import controllers.AbstractController;
import domain.Float;

@Controller
@RequestMapping("/float")
public class FloatController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private FloatService	floatService;


	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(final int brotherhoodId) {
		ModelAndView result;
		Collection<Float> floats;

		try {
			Assert.notNull(brotherhoodId);

			floats = this.floatService.findFloatsByBrotherhood(brotherhoodId);

			result = new ModelAndView("float/listByBrotherhood");
			result.addObject("requestURI", "float/listByBrotherhood.do?=" + brotherhoodId);
			result.addObject("floats", floats);
		} catch (final Throwable e) {
			System.out.println(e.getLocalizedMessage()); //new ModelAndView("redirect:/#");
			System.out.println(e.getCause());
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());

			super.panic(e);
			result = null;
		}

		return result;
	}

}
