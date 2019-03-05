
package controllers.brotherhood;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EnrolmentService;
import services.MemberService;
import services.PositionService;
import controllers.AbstractController;
import domain.Enrolment;
import domain.Member;
import domain.Position;
import forms.EnrolmentForm;

@Controller
@RequestMapping("enrolment/brotherhood")
public class EnrolmentBrotherhoodController extends AbstractController {

	@Autowired
	EnrolmentService	enrolmentService;

	@Autowired
	PositionService		positionService;

	@Autowired
	MemberService		memberService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EnrolmentForm enrolmentForm;
		enrolmentForm = new EnrolmentForm();
		enrolmentForm.setId(0);
		Collection<Member> members = this.memberService.findAll();
		Collection<Position> positions = this.positionService.findAll();
		result = new ModelAndView("enrolment/create");
		result.addObject("enrolmentForm", enrolmentForm);
		result.addObject("members", members);
		result.addObject("positions", positions);
		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final EnrolmentForm enrolmentForm, final BindingResult binding) {
		ModelAndView res;
		res = new ModelAndView("enrolment/create");
		if (binding.hasErrors())
			//res = createEditModelAndView(enrolmentForm);
			res.addObject("enrolmentForm", enrolmentForm);

		else
			try {
				final Enrolment enrolment = this.enrolmentService.reconstruct(enrolmentForm);
				this.enrolmentService.save(enrolment);
				res = new ModelAndView("redirect:/member/brotherhood/list.do");
			} catch (final Throwable oops) {
				res.addObject("message", "enrolment.commit.error");
			}

		return res;
	}
}
