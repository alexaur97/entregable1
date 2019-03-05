
package forms;

import domain.Member;
import domain.Position;

public class EnrolmentForm {

	public Position	position;
	public Member	member;

	private int		id;


	public EnrolmentForm() {
		super();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
