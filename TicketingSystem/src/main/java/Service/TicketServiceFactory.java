package Service;

import java.util.List;

import DAO.ITicketDAO;
import Model.Ticket;

public class TicketServiceFactory {

	public static TicketService createTicketService(ITicketDAO iTicketDAO)
	{
		
		return new TicketService(iTicketDAO);
	}
}
