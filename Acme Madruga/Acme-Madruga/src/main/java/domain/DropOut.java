
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class DropOut extends DomainEntity {

	// Atributos Privados

	private Date		moment;

	// Atributos P�blicos

	public Brootherhood	brotherhood;


	// Getters y Setters

	@ManyToOne(optional = false)
	public Brootherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brootherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
