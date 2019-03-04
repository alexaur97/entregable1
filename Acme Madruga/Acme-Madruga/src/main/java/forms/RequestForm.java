package forms;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Procession;

public class RequestForm {
	
	private Procession procession;
	private int row;
	private int column;
	private int requestId;

	// Constructor
	public RequestForm() {
		super();
	}
	
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}
	
	@NotNull
	public int getRequestId() {
		return this.requestId;
	}

	public void setRequestId(final int requestId) {
		this.requestId = requestId;
	}
	
	@Min(0)
	public Integer getRow() {
		return this.row;
	}

	public void setRow(final Integer row) {
		this.row = row;
	}

	@Min(0)
	public Integer getColumn() {
		return this.column;
	}
	
	public void setColumn(final Integer column) {
		this.column = column;
	}


}
