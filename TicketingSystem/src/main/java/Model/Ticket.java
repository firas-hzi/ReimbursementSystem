package Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	private int ID;
	private double amount;
	private String description;
	private TicketStatus status;
	private TicketType type;
	private int employee_id;
	private String receipt_image;
	private Timestamp created_date;

	public Ticket() {
		super();
	}

	public Ticket(int iD, double amount, String description, TicketStatus status, TicketType type, int employee_id,
			String receipt_image) {
		super();
		ID = iD;
		this.amount = amount;
		this.description = description;
		this.status = status;
		this.type = type;
		this.employee_id = employee_id;
		this.receipt_image = receipt_image;
		this.created_date = Timestamp.valueOf(LocalDateTime.now());
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

	public TicketType getType() {
		return type;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getReceipt_image() {
		return receipt_image;
	}

	public void setReceipt_image(String receipt_image) {
		this.receipt_image = receipt_image;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	

	@Override
	public String toString() {
		return "Ticket [ID=" + ID + ", amount=" + amount + ", description=" + description + ", status=" + status
				+ ", type=" + type + ", Employee ID=" + employee_id + ", receipt_image=" + receipt_image
				+ ", created_date=" + created_date.toLocalDateTime() + "]";
	}

	
	
}
