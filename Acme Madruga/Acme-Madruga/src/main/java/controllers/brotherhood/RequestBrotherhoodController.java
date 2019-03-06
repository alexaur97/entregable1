
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Request;

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

}
