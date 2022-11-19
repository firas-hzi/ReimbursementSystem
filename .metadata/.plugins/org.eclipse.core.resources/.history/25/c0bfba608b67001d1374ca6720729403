import java.awt.Desktop;
import java.io.File;

import Controller.AuthController;
import Controller.TicketController;
import DAO.AuthDAO;
import DAO.IAuthDAO;
import DAO.ITicketDAO;
import DAO.TicketDAO;
import io.javalin.Javalin;

public class TicketingSystemClass {
	
	                                                                                                                                                                                                                                                                                                                                                                                                                               
	
	public static void main (String [] args)
	{
		
		IAuthDAO iAuthDAO = new AuthDAO();
		ITicketDAO iTicketDAO = new TicketDAO();
		
		AuthController authController = new AuthController(iAuthDAO);
	    TicketController ticketController = new TicketController(iTicketDAO);    
		
		Javalin app = Javalin.create(config -> {
		    config.plugins.enableCors(cors -> {
		        cors.add(it -> {
		            it.anyHost();                                          
		        });
		    });                                             
		});
		
		app.get("/users", authController.handleGetAllUsers);
		app.get("/user/logout", authController.handleLogout);
		app.delete("/user", authController.handleDeleteUserById);
		app.post("/user/register", authController.handleRegister);
		app.get("/user/login", authController.handleLogin);
     	app.put("/user/profile-management", authController.handleEditProfile);
 	 	app.put("/user/profile-picture", authController.handleUploadPicture);
		app.put("/user/role-management", authController.handleChangeRole);
		app.get("/ticket/ticket-type", ticketController.handleGetTicketsByType);
		app.get("/ticket/ticket-status", ticketController.handleGetTicketsByStatus);
		app.get("/ticket/tickets", ticketController.handleViewPendingTickets);
		app.post("ticket/ticket-new", ticketController.handleSubmitTicket);
		app.post("ticket/ticket-management", ticketController.handleProcessPendingTicket);
		app.get("ticket/tickets-management", ticketController.handleProcessPendingTickets);
		app.get("/ticket/ticket-history", ticketController.handleViewTicketHistory);
		
		app.start(8000);
		
	
	}
	
	
}
