
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class RejectForm {

	private String	explanation;
	private int		requestId;


	@NotBlank
	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	@NotNull
	public int getRequestId() {
		return this.requestId;
	}

	public void setRequestId(final int requestId) {
		this.requestId = requestId;
	}

}
