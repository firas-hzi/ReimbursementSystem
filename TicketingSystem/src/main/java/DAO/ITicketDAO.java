package DAO;

import java.util.List;

import Model.Ticket;

public interface ITicketDAO {
	
    List<Ticket> getTicketsByType(String type);
    
    List<Ticket> getTicketsByStatus(String status);
	
	boolean submitNewTicketByEmloyee(Ticket ticket);
	
	List<Ticket> getPendingTickets();
	
	List<Ticket> viewTicketHistory();
	
	List<Ticket> processPendingTickets();
	
	boolean processPendingTicket(int ticket_id, int status_id);


}