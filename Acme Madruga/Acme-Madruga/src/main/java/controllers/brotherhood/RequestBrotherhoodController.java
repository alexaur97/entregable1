
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("request/brotherhood")
public class RequestBrotherhoodController extends AbstractController {

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	RequestService		requestService;

	@Autowired
	ProcessionService	processionService;


	// List -----------------------------------------------------------	
	@RequestMapping(value = "/list")
	public ModelAndView list() {

		ModelAndView result;
		Collection<Request> pendingRequests;
		Collection<Request> acceptedRequests;
		Collection<Request> rejectedRequests;

		pendingRequests = this.requestService.findRequestByStatusAndBrotherhood("PENDING");
		acceptedRequests = this.requestService.findRequestByStatusAndBrotherhood("APPROVED");
		rejectedRequests = this.requestService.findRequestByStatusAndBrotherhood("REJECTED");

		result = new ModelAndView("request/list");

		result.addObject("pendingRequests", pendingRequests);
		result.addObject("acceptedRequests", acceptedRequests);
		result.addObject("rejectedRequests", rejectedRequests);
		result.addObject("resquestURI", "/request/brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int requestId) {
		final ModelAndView res = new ModelAndView("request/reject");
		Request request;
		request = new Request();
		try {
			request = this.requestService.findOne(requestId);
			res.addObject("request", request);
		} catch (final Throwable oops) {
			res.addObject("message", "request.commit.error");
		}
		return res;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView saveR(Request request, final BindingResult binding) {
		ModelAndView res;
		Request requestFinal = this.requestService.rejectRecostruction(request, binding);

		if (binding.hasErrors()) {
			res = new ModelAndView("request/reject");
		}
		try {
			this.requestService.save(requestFinal);
			res = new ModelAndView("redirect:/request/brotherhood/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("request/reject");
			res.addObject("message", "request.commit.error");
			res.addObject("request", request);

		}

		return res;
	}
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int requestId) {
		final ModelAndView res = new ModelAndView("request/accept");
		Request request = this.requestService.findOne(requestId);
		try {
			String pos = this.requestService.findPos(request.getProcession().getId(), request.getColumn(), request.getRow());
			res.addObject("pos", pos);
			res.addObject("request", request);
		} catch (final Throwable oops) {
			res.addObject("message", "request.commit.error");
		}
		return res;
	}
	@RequestMapping(value = "/accept", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("request") Request request, final BindingResult binding) {

		ModelAndView res;
		Request requestFinal = this.requestService.acceptRecostruction(request, binding);

		if (binding.hasErrors()) {
			res = new ModelAndView("request/accept");
		}
		try {
			Assert.isTrue(this.requestService.posDisp(request.getProcession().getId(), request.getColumn(), request.getRow()));
			this.requestService.save(requestFinal);
			res = new ModelAndView("redirect:/request/brotherhood/list.do");

		} catch (final Throwable oops) {
			res = new ModelAndView("request/accept");
			res.addObject("message", "request.pos.error");
			res.addObject("request", request);

		}

		return res;
	}
}
