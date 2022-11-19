package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exception.EmailAlreadyExistException;
import Model.Address;
import Model.Person;
import Model.Role;
import Utils.Helper;
import Utils.JDBCConnectionUtil;

public class AuthDAO implements IAuthDAO {
	private JDBCConnectionUtil conUtil = JDBCConnectionUtil.getInstance();
	private Connection connection = conUtil.getConnection();
	private String sql = "";
	private PreparedStatement prepared;

	public Person Login(String email, String password) {
		Person person = null;
		try {

			Address address;
			sql = "SELECT * FROM Person p left join Address a on p.address_id=a.address_id  WHERE p.email=? and p.password=?";

			prepared = connection.prepareStatement(sql);

			prepared.setString(1, email);
			prepared.setString(2, password);

			ResultSet result = prepared.executeQuery();
			

			while (result.next()) {
				person = new Person();
				address = new Address();
				person.setID(result.getInt(1));
				person.setName(result.getString(2));
				person.setEmail(result.getString(3));
				person.setPassword(result.getString(4));
				person.setPicture(result.getString(5));
				if (result.getInt(7) == 1) {
					person.setRole(Role.MANAGER);
				} else {
					person.setRole(Role.EMPLOYEE);
				}
				address.setId(result.getInt(8));
				address.setStreet(result.getString(9));
				address.setCity(result.getString(10));
				address.setState(result.getString(11));
				address.setZipcode(result.getInt(12));
				person.setAddress(address);
				System.out.println(person.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return person;
	}

	public boolean Register(Person user) throws SQLException {
		int address_id = 0;
			if (checkEmailIsAvailable(user.getEmail())) {
				if (user.getAddress() != null) {
					address_id = insertAddress(user);
					sql = "insert into Person(name, email, password,picture, role_id, address_id)"
							+ "VALUES (?,?,?,?,?,?)";
				} else
					sql = "insert into Person(name, email, password,picture, role_id)" + "VALUES (?,?,?,?,?)";

				prepared = connection.prepareStatement(sql);

				int role = user.getRole() != null ? user.getRole().ordinal() : 2;

				prepared.setString(1, user.getName());
				prepared.setString(2, user.getEmail());
				prepared.setString(3, user.getPassword());
				prepared.setString(4, user.getPicture());
				prepared.setInt(5, role);
				if (address_id > 0)
					prepared.setInt(6, address_id);
				prepared.execute();
				return true;
			} else {
				System.out.println("email already exist");
				return false;
			}

	}

	public boolean uploadUserPicture(String path) {
		try {
			sql = "update person SET picture = ? WHERE person_id = ?";

			prepared = connection.prepareStatement(sql);

			prepared.setString(1, path);
			prepared.setInt(2, Helper.getPerson().getID());
			int affectedRows = prepared.executeUpdate();
			if (affectedRows > 0)
				return true;
			return false;
		} catch (SQLException e1) {
			return false;
		}
	}

	private int insertAddress(Person user) throws SQLException {
		sql = "insert into Address(street, city, state,zipcode)" + "VALUES (?,?,?,?)";
		prepared = connection.prepareStatement(sql);

		prepared.setString(1, user.getAddress().getStreet());
		prepared.setString(2, user.getAddress().getCity());
		prepared.setString(3, user.getAddress().getState());
		prepared.setInt(4, user.getAddress().getZipcode());
		prepared.execute();

		sql = "select Max(address_id) from Address";
		prepared = connection.prepareStatement(sql);
		ResultSet result = prepared.executeQuery();
		int address_id = 0;
		if (result.next())
			address_id = result.getInt(1);
		return address_id;
	}

	private boolean checkEmailIsAvailable(String email) throws SQLException {
		sql = "select * from Person where email='" + email + "'";
		prepared = connection.prepareStatement(sql);
		ResultSet result = prepared.executeQuery();
		if (result.next())
			return false;
		return true;
	}

	@Override
	public boolean updateUserProfile(Person user) {
		int address_id = 0;
		try {
			if (user.getAddress() != null) {
				System.out.println("address is not null");
				address_id = insertAddress(user);
				System.out.println("the address id is =" + address_id);
			}
			if (address_id > 0)
				sql = "update person SET name = ? , password=?, picture=?, address_id=?  WHERE person_id = ?";
			else
				sql = "update person SET name = ? , password=?, picture=?  WHERE person_id = ?";

			prepared = connection.prepareStatement(sql);
			prepared.setString(1, user.getName());
			prepared.setString(2, user.getPassword());
			prepared.setString(3, user.getPicture());
			if (address_id > 0) {
				prepared.setInt(4, address_id);
				prepared.setInt(5, Helper.getPerson().getID());
			} else
				prepared.setInt(5, Helper.getPerson().getID());

			int affectedRows = prepared.executeUpdate();
			if (affectedRows > 0)
				return true;
			return false;
		} catch (SQLException e1) {
			return false;
		}
	}

	@Override
	public boolean updatePersonRole(int person_id, int role_id) {
		try {
			sql = "update person SET role_id = ? WHERE person_id = ?";

			prepared = connection.prepareStatement(sql);

			prepared.setInt(1, role_id);
			prepared.setInt(2, person_id);
			int affectedRows = prepared.executeUpdate();
			if (affectedRows > 0)
				return true;
			return false;
		} catch (SQLException e1) {
			return false;
		}

	}

	@Override
	public List<Person> getAllUsers() {
		List<Person> persons = new ArrayList<>();
		try {
			sql = "select * from person p left join address a on a.address_id = p.address_id";

			prepared = connection.prepareStatement(sql);
			ResultSet result = prepared.executeQuery();
			persons = Helper.populatePersons(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persons;
	}

	@Override
	public boolean deleteUserById(int user_id) {
		try {
			sql = "delete from person where person_id="+user_id;

			prepared = connection.prepareStatement(sql);
			int affectedRows = prepared.executeUpdate();
			if(affectedRows>0)return true;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

}
}
