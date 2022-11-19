package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Model.Person;
import Model.Ticket;
import Model.TicketStatus;
import Model.TicketType;
import Utils.Helper;
import Utils.JDBCConnectionUtil;

public class TicketDAO implements ITicketDAO {

	private JDBCConnectionUtil conUtil = JDBCConnectionUtil.getInstance();
	Connection connection = conUtil.getConnection();
	String sql = "";
	PreparedStatement prepared;

	public List<Ticket> getTicketsByType(String type) {
		List<Ticket> tickets = new ArrayList<>();
		try {
			Ticket ticket = null;
			TicketType ticketType = TicketType.DEFAULT;
			sql = "SELECT * FROM ticket WHERE employee_id=? and type_id=?";

			prepared = connection.prepareStatement(sql);
			prepared.setInt(1, Helper.getPerson().getID());
			prepared.setInt(2, ticketType.getValue(type));

			ResultSet result = prepared.executeQuery();
			tickets = Helper.populateTickets(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}

	public boolean submitNewTicketByEmloyee(Ticket ticket) {
		try {
			sql = "insert into ticket (amount, description, employee_id, type_id, receipt_image, created_date) VALUES (?,?,?,?,?,?)";
			prepared = connection.prepareStatement(sql);

			prepared.setDouble(1, ticket.getAmount());
			prepared.setString(2, ticket.getDescription());
			prepared.setInt(3, ticket.getEmployee_id());
			prepared.setInt(4, ticket.getType().ordinal() + 1);
			prepared.setString(5, ticket.getReceipt_image());
			prepared.setTimestamp(6, ticket.getCreated_date());
			int affectedRows = prepared.executeUpdate();

			if (affectedRows > 0)
				return true;
			return false;
		} catch (Exception e1) {
			System.out.println("Cannot submit a ticket");
			e1.printStackTrace();
			return false;
		}
	}

	public List<Ticket> getPendingTickets() {
		List<Ticket> tickets = new ArrayList<>();
		try {
			Ticket ticket = null;

			sql = "select * from ticket WHERE status_id=? order by created_date desc";

			prepared = connection.prepareStatement(sql);
			prepared.setInt(1, 1);
			ResultSet result = prepared.executeQuery();
			tickets = Helper.populateTickets(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}

	public List<Ticket> viewTicketHistory() {
		List<Ticket> tickets = new ArrayList<>();
		try {

			sql = "SELECT * FROM ticket WHERE employee_id=?";

			prepared = connection.prepareStatement(sql);
			prepared.setInt(1, Helper.getPerson().getID());
			ResultSet result = prepared.executeQuery();
			tickets = Helper.populateTickets(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}

	public List<Ticket> processPendingTickets() {
		List<Ticket> tickets = getPendingTickets();
		for (Ticket ticket : tickets) {
			if (ticket.getAmount() > 0 && !ticket.getDescription().isEmpty()) {
				updateTickets(ticket.getID(), 2);
			} else
				updateTickets(ticket.getID(), 3);

		}
		return null;
	}

	private void updateTickets(int ticket_id, int status_id) {
		try {
			sql = "update ticket SET status_id = ? WHERE status_id =? and ticket_id = ?";

			PreparedStatement prepared = connection.prepareStatement(sql);

			prepared.setInt(1, status_id);
			prepared.setInt(2, 1);
			prepared.setInt(3, ticket_id);
			prepared.execute();
		} catch (SQLException e1) {
		}
	}

	@Override
	public List<Ticket> getTicketsByStatus(String status) {
		List<Ticket> tickets = new ArrayList<>();
		try {
			Ticket ticket = null;

			TicketStatus ticketStatus = TicketStatus.DENIED;
			sql = "SELECT * FROM ticket WHERE employee_id=? and status_id=?";

			prepared = connection.prepareStatement(sql);
			prepared.setInt(1, Helper.getPerson().getID());
			prepared.setInt(2, ticketStatus.getValue(status));

			ResultSet result = prepared.executeQuery();
			tickets = Helper.populateTickets(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}

	@Override
	public boolean processPendingTicket(int ticket_id, int status_id) {

		try {
			sql = "update ticket SET status_id = ? where status_id=? and ticket_id = ?";

			PreparedStatement prepared = connection.prepareStatement(sql);

			prepared.setInt(1, status_id);
			prepared.setInt(2, 1);
			prepared.setInt(3, ticket_id);
			int affectedRows = prepared.executeUpdate();
			if (affectedRows > 0)
				return true;
			return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}

	}

}
