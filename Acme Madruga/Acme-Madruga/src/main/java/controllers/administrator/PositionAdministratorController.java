
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.PositionService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Position;

@Controller
@RequestMapping("position/administrator")
public class PositionAdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;
	
	@Autowired
	PositionService	positionService;

	// List -----------------------------------------------------------	
	@RequestMapping(value = "/list")
	public ModelAndView list() {

		ModelAndView result;
		Collection<Position> positions;

		positions = this.positionService.findAll();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("resquestURI", "/position/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Position position;

		position = new Position();
		position.setId(0);

		result = new ModelAndView("position/create");
		result.addObject("position", position);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		final ModelAndView res = new ModelAndView("position/edit");
		final Position position = this.positionService.findOne(positionId);
		res.addObject("position", position);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Position position, final BindingResult binding) {
		ModelAndView res;
		if (position.getId() == 0)
			res = new ModelAndView("position/create");
		else
			res = new ModelAndView("position/edit");

		if (binding.hasErrors())
			res.addObject("position", position);
		else
			try {
				this.positionService.save(position);
				res = new ModelAndView("redirect:/position/administrator/list.do");
			} catch (final Throwable oops) {
				res.addObject("message", "position.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position, final BindingResult binding) {
		ModelAndView result;

		try {
			this.positionService.delete(position);
			result = new ModelAndView("redirect:/position/administrator/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("position/edit");
			result.addObject("position", position);
			result.addObject("message", oops.getMessage());
			String msg = oops.getMessage();
			if(msg.equals("cannotDelete")){
				Boolean cannotDelete = true;
				result.addObject("cannotDelete", cannotDelete);
			}
		}

		return result;
	}

}
