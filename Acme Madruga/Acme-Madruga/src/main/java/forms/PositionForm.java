
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class PositionForm {

	private String	name;
	private String 	nameEs;
	private int		positionId;


	public PositionForm() {
		super();
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getNameEs() {
		return this.nameEs;
	}

	public void setNameEs(final String nameEs) {
		this.nameEs = nameEs;
	}
	
	@NotNull
	public int getPositionId() {
		return this.positionId;
	}

	public void setPositionId(final int positionId) {
		this.positionId = positionId;
	}

}
