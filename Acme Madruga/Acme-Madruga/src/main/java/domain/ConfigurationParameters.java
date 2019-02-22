
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameters extends DomainEntity {

	private String				name;
	private String				banner;
	private String				message;
	private String				messageEs;
	private String				countryCode;
	private Collection<String>	position;
	private Collection<String>	positionEs;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@NotBlank
	public String getMessageEs() {
		return this.messageEs;
	}

	public void setMessageEs(final String messageEs) {
		this.messageEs = messageEs;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getPosition() {
		return this.position;
	}

	public void setPosition(final Collection<String> position) {
		this.position = position;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getPositionEs() {
		return this.positionEs;
	}

	public void setPositionEs(final Collection<String> positionEs) {
		this.positionEs = positionEs;
	}

}