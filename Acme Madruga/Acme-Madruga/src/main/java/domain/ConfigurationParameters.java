
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameters extends DomainEntity {

	private String	name;
	private String	banner;
	private String	sysMessage;
	private String	sysMessageEs;
	private String	countryCode;


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
	@Pattern(regexp = "^\\+\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
	@NotBlank
	public String getSysMessage() {
		return this.sysMessage;
	}

	public void setSysMessage(final String sysMessage) {
		this.sysMessage = sysMessage;
	}

	@NotBlank
	public String getSysMessageEs() {
		return this.sysMessageEs;
	}

	public void setSysMessageEs(final String sysMessageEs) {
		this.sysMessageEs = sysMessageEs;
	}
}
