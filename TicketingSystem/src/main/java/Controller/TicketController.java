package Controller;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import DAO.ITicketDAO;
import Exception.EmptyDescriptionException;
import Exception.InvalidAmountException;
import Model.Role;
import Model.Ticket;
import Model.TicketStatus;
import Model.TicketType;
import Service.TicketService;
import Service.TicketServiceFactory;
import Utils.Helper;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class TicketController {

	ITicketDAO iTicketDAO;
	private ObjectMapper objectMapper;
	private TicketService ticketService;

	public TicketController(ITicketDAO iTicketDAO) {
		super();
		this.objectMapper = new ObjectMapper();
		this.iTicketDAO = iTicketDAO;
		ticketService = TicketServiceFactory.createTicketService(iTicketDAO);
	}

	public Handler handleSubmitTicket = (context) -> {
		Double amount=0d;
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.MANAGER) {
			context.status(401);
			context.result("Only employees are authorized to submit new tickets");
		} else {

			try {
				 amount = Double.parseDouble(context.formParam("amount"));
			} catch (NumberFormatException e1) {
				throw new InvalidAmountException();
			}
			String description = context.formParam("description");
			int type = Integer.parseInt(context.formParam("type"));
			UploadedFile uploadedFile = context.uploadedFile("receiptFile");

			if (amount <= 0 || amount == null) {
				context.status(400);
				context.result("Please enter a valid amount");
				throw new InvalidAmountException();

			} else if (description.trim().isEmpty() || description == null) {
				context.status(400);
				context.result("Please enter a valid description");
				throw new EmptyDescriptionException();

			} else {

			}
			if (uploadedFile != null) {
				try (InputStream inputStream = uploadedFile.content()) {
					File targetFile = new File("src/main/resources/receipts/" + uploadedFile.filename());
					FileUtils.copyInputStreamToFile(inputStream, targetFile);
					String path = targetFile.getPath();
					Ticket ticket = Helper.fillTicketWithData(amount, description, type);
					ticket.setReceipt_image(path);
					ticketService.submitNewTicketByEmloyee(ticket);
					context.status(201);
					context.result(objectMapper.writeValueAsString(ticket));
				}
			} else {

				Ticket ticket = Helper.fillTicketWithData(amount, description, type);
				ticketService.submitNewTicketByEmloyee(ticket);
				context.status(201);

				context.result(objectMapper.writeValueAsString(ticket));

			}
		}
	};
	public Handler handleViewTicketHistory = (context) -> {
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.MANAGER) {
			context.status(401);
			context.result("Only employees are authorized to view their tickets history");
		} else {

			List<Ticket> pList = ticketService.viewTicketHistory();
			context.status(200);
			context.result(objectMapper.writeValueAsString(pList));
		}

	};

	public Handler handleGetTicketsByType = (context) -> {
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.MANAGER) {

			context.status(401);
			context.result("Only employees are authorized to view their tickets by type");
		} else {
			Map<String, String> body = objectMapper.readValue(context.body(), LinkedHashMap.class);
			List<Ticket> pList = ticketService.getTicketsBYType(body.get("type"));
			context.status(200);
			context.result(objectMapper.writeValueAsString(pList));
		}
	};

	public Handler handleGetTicketsByStatus = (context) -> {
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.MANAGER) {
			context.status(401);
			context.result("Only employees are authorized to view their tickets by status");
		} else {
			Map<String, String> body = objectMapper.readValue(context.body(), LinkedHashMap.class);
			List<Ticket> pList = ticketService.getTicketsBYStatus(body.get("status"));
			context.status(200);
			context.result(objectMapper.writeValueAsString(pList));
		}
	};

	public Handler handleViewPendingTickets = (context) -> {
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.EMPLOYEE) {
			context.status(401);
			context.result("Only managers can view all pending tickets");
		} else {
			List<Ticket> pList = ticketService.getPendingTickets();
			context.status(200);
			context.result(objectMapper.writeValueAsString(pList));
		}
	};

	public Handler handleProcessPendingTickets = (context) -> {
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.EMPLOYEE) {
			context.status(401);
			context.result("Only managers can process pending tickets");
		} else {
			List<Ticket> tickets = ticketService.processPendingTickets();

			context.status(200);
			context.result("Tickets were processed");
		}
	};

	public Handler handleProcessPendingTicket = (context) -> {
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.EMPLOYEE) {
			context.status(401);
			context.result("Only managers can process pending ticket");
		} else {
			Map<String, Integer> body = objectMapper.readValue(context.body(), LinkedHashMap.class);
			boolean updated = ticketService.processPendingTicket(body.get("ticket_id"), body.get("status"));
			if (updated) {
				context.status(200);
				if (body.get("status") == 2)
					context.result("Ticket " + body.get("ticket_id") + " was approved");
				else
					context.result("Ticket " + body.get("ticket_id") + " was denied");
			} else {
				context.status(403);
				context.result("Ticket " + body.get("ticket_id") + " already processed");
			}
		}
	};

}
