
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Atributos Privados

	private String		status;
	private Integer		row;
	private Integer		column;

	// Atributos Públicos

	public Procession	procession;


	// Getters y Setters

	@ManyToOne
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

	@NotBlank
	@Pattern(regexp = "^PENDING|APPROVED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Min(0)
	@Column(name = "`row`")
	public Integer getRow() {
		return this.row;
	}

	public void setRow(final Integer row) {
		this.row = row;
	}

	@Min(0)
	@Column(name = "`column`")
	public Integer getColumn() {
		return this.column;
	}

	public void setColumn(final Integer column) {
		this.column = column;
	}

}
