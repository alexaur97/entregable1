
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Request;
import forms.RejectForm;

@Controller
@RequestMapping("request/brotherhood")
public class RequestBrotherhoodController extends AbstractController {

	@Autowired
	AdministratorService	memberService;

	@Autowired
	RequestService			requestService;

	@Autowired
	ProcessionService		processionService;


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
		RejectForm rejectForm;
		rejectForm = new RejectForm();
		try {
			rejectForm.setRequestId(requestId);
			res.addObject("rejectForm", rejectForm);
		} catch (final Throwable oops) {
			res.addObject("message", "request.commit.error");
		}
		return res;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RejectForm rejectForm, final BindingResult binding) {
		ModelAndView res = new ModelAndView("redirect:/request/brotherhood/list.do");
		try {
			this.requestService.reject(rejectForm.getRequestId(), rejectForm.getExplanation());
			res = new ModelAndView("redirect:/request/brotherhood/list.do");
		} catch (final Throwable oops) {
			res.addObject("message", "request.commit.error");

		}

		return res;
	}

}
