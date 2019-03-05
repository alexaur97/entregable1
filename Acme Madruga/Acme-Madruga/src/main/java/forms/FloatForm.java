
package forms;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class FloatForm {

	private String				title;
	private String				description;
	private Collection<String>	pictures;
	private int					floatId;


	@NotNull
	public int getFloatId() {
		return this.floatId;
	}

	public void setFloatId(final int floatId) {
		this.floatId = floatId;
	}

	public FloatForm() {
		super();
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

}
