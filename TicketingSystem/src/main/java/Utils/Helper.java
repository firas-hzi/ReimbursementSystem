package Utils;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.server.MultiPartFormInputStream.MultiPart;

import Model.Address;
import Model.Person;
import Model.Role;
import Model.Ticket;
import Model.TicketStatus;
import Model.TicketType;

public class Helper {

	private static Person person;

	public static Person getPerson() {
		return person;
	}

	public static void setPerson(Person person) {
		Helper.person = person;
	}

	public static List<Ticket> populateTickets(ResultSet result) throws SQLException {
		List<Ticket> tickets = new ArrayList<>();
		Ticket ticket;
		while (result.next()) {
			ticket = new Ticket();
			ticket.setID(result.getInt(1));
			ticket.setAmount(result.getDouble(2));
			ticket.setDescription(result.getString(3));
			ticket.setEmployee_id(result.getInt(4));
			if (result.getInt(5) == 1)
				ticket.setStatus(TicketStatus.PENDING);
			else if (result.getInt(5) == 2)
				ticket.setStatus(TicketStatus.APPROVED);
			else
				ticket.setStatus(TicketStatus.DENIED);
			if (result.getInt(6) == 1)
				ticket.setType(TicketType.TRAVEL);
			else if (result.getInt(6) == 2)
				ticket.setType(TicketType.LODGING);
			else if (result.getInt(6) == 3)
				ticket.setType(TicketType.FOOD);
			else
				ticket.setType(TicketType.OTHER);
			ticket.setReceipt_image(result.getString(7));
			ticket.setCreated_date(result.getTimestamp(8));
			tickets.add(ticket);
			System.out.println(ticket.toString());

		}
		return tickets;
	}

	public static List<Person> populatePersons(ResultSet result) throws SQLException {
		List<Person> persons = new ArrayList<>();
		Person person;
		Address address;
		while (result.next()) {
			person = new Person();
			address = populateAddress(result);
			person.setID(result.getInt(1));
			person.setName(result.getString(2));
			person.setEmail(result.getString(3));
			person.setPassword(result.getString(4));
			person.setPicture(result.getString(5));
			person.setAddress(address);
			if (result.getInt(7) == 1)
				person.setRole(Role.MANAGER);
			else if (result.getInt(7) == 2)
				person.setRole(Role.EMPLOYEE);

			persons.add(person);
			System.out.println(person.toString());

		}
		return persons;
	}
	
	private static Address populateAddress(ResultSet result) throws SQLException
	{
		Address address = new Address();
		address.setId(result.getInt(8));
		address.setStreet(result.getString(9));
		address.setCity(result.getString(10));
		address.setState(result.getString(11));
		address.setZipcode(result.getInt(12));

		return address;
	}

	public static Ticket fillTicketWithData(double amount, String description, int type) {
		Ticket ticket = new Ticket();
		TicketType ticketType = TicketType.DEFAULT;
		ticket.setReceipt_image("");
		ticket.setAmount(amount);
		ticket.setDescription(description);
		ticket.setType(ticketType.getValue(type));
		ticket.setEmployee_id(Helper.getPerson().getID());
		ticket.setStatus(TicketStatus.PENDING);
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		ticket.setCreated_date(timestamp);
		return ticket;
	}
}
