package Service;

import java.time.LocalDateTime;
import java.util.List;

import DAO.ITicketDAO;
import Exception.EmptyDescriptionException;
import Exception.InvalidAmountException;
import Model.Ticket;
import Utils.Helper;
import Utils.Logging;

public class TicketService {
	ITicketDAO iTicketDAO;

	public TicketService(ITicketDAO iTicketDAO) {
		super();
		this.iTicketDAO = iTicketDAO;
	}

	public List<Ticket> getTicketsBYType(String type) {
		
		List<Ticket> tickets=  iTicketDAO.getTicketsByType(type);
		if(tickets ==null) return null;
		Logging.getLogger().info("Get Tickets by type was called by "+Helper.getPerson().getEmail() +" on "+LocalDateTime.now());
        return tickets;
	}

	public List<Ticket> getTicketsBYStatus(String status) {
		List<Ticket> tickets=  iTicketDAO.getTicketsByStatus(status);
		if(tickets ==null) return null;
		Logging.getLogger().info("Get Tickets by status was called by "+Helper.getPerson().getEmail() +" on "+LocalDateTime.now());
        return tickets;
		 
	}

	public boolean submitNewTicketByEmloyee(Ticket ticket) {
		
		boolean result=  iTicketDAO.submitNewTicketByEmloyee(ticket);
		if(result) {
		Logging.getLogger().info("The employee with email "+Helper.getPerson().getEmail() +"submitted a new ticket  on "+LocalDateTime.now());
        return result;
	}
        return result; 
	}

	public List<Ticket> getPendingTickets() {
		List<Ticket> tickets= iTicketDAO.getPendingTickets();
		if(tickets ==null) return null;
		Logging.getLogger().info("Get pending tickets was called by manager "+Helper.getPerson().getEmail() +" on "+LocalDateTime.now());
        return tickets;
	}

	public List<Ticket> viewTicketHistory() {
		List<Ticket> tickets=iTicketDAO.viewTicketHistory();
		if(tickets ==null) return null;
		Logging.getLogger().info("View ticket history was called by employee with email "+Helper.getPerson().getEmail() +" on "+LocalDateTime.now());
        return tickets;
		
	}

	public List<Ticket> processPendingTickets() {
		List<Ticket> tickets=iTicketDAO.processPendingTickets();
		if(tickets ==null) return null;
		Logging.getLogger().info("All Pending tickets was processed by manager with email "+Helper.getPerson().getEmail() +" on "+LocalDateTime.now());
        return tickets;
	}
	
	public boolean processPendingTicket(int ticket_id, int status_id) {
		boolean result= iTicketDAO.processPendingTicket(ticket_id, status_id);
		if(result) {
		Logging.getLogger().info("Pending tickets id= " +ticket_id+ " was processed by manager with email "+Helper.getPerson().getEmail() +" status id =" + status_id+ " on "+LocalDateTime.now());
        return result;
		}
		return  result;
	}

	

}
