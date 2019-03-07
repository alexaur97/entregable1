
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

import services.ActorService;
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
	@Autowired
	ActorService		actorService;


	// List -----------------------------------------------------------	
	@RequestMapping(value = "/list")
	public ModelAndView list() {

		ModelAndView result;
		Collection<Request> pendingRequests;
		Collection<Request> acceptedRequests;
		Collection<Request> rejectedRequests;
		result = new ModelAndView("request/list");
		try {
			Assert.isTrue(this.actorService.authEdit(this.actorService.findByPrincipal(), "BROTHERHOOD"));
			pendingRequests = this.requestService.findRequestByStatusAndBrotherhood("PENDING");
			acceptedRequests = this.requestService.findRequestByStatusAndBrotherhood("APPROVED");
			rejectedRequests = this.requestService.findRequestByStatusAndBrotherhood("REJECTED");

			result = new ModelAndView("request/list");

			result.addObject("pendingRequests", pendingRequests);
			result.addObject("acceptedRequests", acceptedRequests);
			result.addObject("rejectedRequests", rejectedRequests);
			result.addObject("resquestURI", "/request/brotherhood/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int requestId) {
		ModelAndView res = new ModelAndView("request/reject");

		final Request request = this.requestService.findOne(requestId);

		try {
			Assert.notNull(requestId);
			Assert.notNull(request);
			Assert.isTrue(request.getProcession().getBrotherhood().getId() == this.brotherhoodService.findByPrincipal().getId());
			res = new ModelAndView("request/reject");
			Assert.notNull(request);
			Assert.isTrue(request.getProcession().getBrotherhood().getId() == this.brotherhoodService.findByPrincipal().getId());
			res.addObject("request", request);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");

			//			res.addObject("message", "request.commit.error");
		}
		return res;
	}
	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView saveR(final Request request, final BindingResult binding) {
		ModelAndView res;
		final Request requestFinal = this.requestService.rejectRecostruction(request, binding);
		Collection<Request> pendingRequests;
		Collection<Request> acceptedRequests;
		Collection<Request> rejectedRequests;
		if (binding.hasErrors())
			res = new ModelAndView("request/reject");
		try {
			Assert.notNull(request.getExplanation());
			Assert.isTrue(!request.getExplanation().isEmpty());
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getId() == request.getProcession().getBrotherhood().getId());
			res = new ModelAndView("request/list");
			Assert.isTrue(this.actorService.authEdit(this.actorService.findByPrincipal(), "BROTHERHOOD"));
			this.requestService.save(requestFinal);
			pendingRequests = this.requestService.findRequestByStatusAndBrotherhood("PENDING");
			acceptedRequests = this.requestService.findRequestByStatusAndBrotherhood("APPROVED");
			rejectedRequests = this.requestService.findRequestByStatusAndBrotherhood("REJECTED");
			res.addObject("pendingRequests", pendingRequests);
			res.addObject("acceptedRequests", acceptedRequests);
			res.addObject("rejectedRequests", rejectedRequests);
			res.addObject("requestURI", "/request/brotherhood/list.do");
		} catch (final Throwable oops) {
			if (request.getExplanation() == null || request.getExplanation().isEmpty()) {
				res = new ModelAndView("request/reject");
				res.addObject("request", request);
				res.addObject("message", "request.explanation.error");
			} else
				res = new ModelAndView("redirect:/#");
		}

		return res;
	}
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int requestId) {
		ModelAndView res;
		try {
			Assert.notNull(requestId);
			res = new ModelAndView("request/accept");
			Assert.notNull(requestId);
			final Request request = this.requestService.findOne(requestId);
			Assert.notNull(request);
			Assert.isTrue(request.getProcession().getBrotherhood().getId() == this.brotherhoodService.findByPrincipal().getId());
			final String pos = this.requestService.findPos(request.getProcession().getId());
			res.addObject("pos", pos);
			res.addObject("request", request);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");

		}
		return res;
	}
	@RequestMapping(value = "/accept", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("request") final Request request, final BindingResult binding) {

		ModelAndView res;
		final Request requestFinal = this.requestService.acceptRecostruction(request, binding);
		Collection<Request> pendingRequests;
		Collection<Request> acceptedRequests;
		Collection<Request> rejectedRequests;
		if (binding.hasErrors())
			res = new ModelAndView("request/accept");
		try {
			res = new ModelAndView("request/list");
			Assert.isTrue(this.requestService.posDisp(request.getProcession().getId(), request.getColumn(), request.getRow()));
			Assert.isTrue(this.actorService.authEdit(this.actorService.findByPrincipal(), "BROTHERHOOD"));
			this.requestService.save(requestFinal);
			pendingRequests = this.requestService.findRequestByStatusAndBrotherhood("PENDING");
			acceptedRequests = this.requestService.findRequestByStatusAndBrotherhood("APPROVED");
			rejectedRequests = this.requestService.findRequestByStatusAndBrotherhood("REJECTED");
			res.addObject("pendingRequests", pendingRequests);
			res.addObject("acceptedRequests", acceptedRequests);
			res.addObject("rejectedRequests", rejectedRequests);
			res.addObject("requestURI", "/request/brotherhood/list.do");

		} catch (final Throwable oops) {
			if (!this.requestService.posDisp(request.getProcession().getId(), request.getColumn(), request.getRow())) {
				res = new ModelAndView("request/accept");
				final String pos = this.requestService.findPos(request.getProcession().getId());
				res.addObject("pos", pos);
				res.addObject("request", request);
				res.addObject("message", "request.pos.error");
			} else
				res = new ModelAndView("redirect:/#");
		}

		return res;
	}
}
