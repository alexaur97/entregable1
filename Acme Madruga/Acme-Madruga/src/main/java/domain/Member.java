
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	// Atributos Públicos

	public Collection<Enrolment>	enrolments;
	public Collection<Request>		requests;
	public Collection<Request>		dropOuts;


	// Getters y Setters

	@OneToMany
	public Collection<Request> getDropOuts() {
		return this.dropOuts;
	}

	public void setDropOuts(final Collection<Request> dropOuts) {
		this.dropOuts = dropOuts;
	}

	@OneToMany
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

	@OneToMany
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

}
