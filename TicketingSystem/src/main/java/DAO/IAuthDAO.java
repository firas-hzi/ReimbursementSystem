package DAO;

import java.awt.Image;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import Model.Person;

public interface IAuthDAO {

	Person Login(String username , String password);
	
	List<Person> getAllUsers();
	
	boolean Register(Person person) throws SQLException;
	
	boolean updateUserProfile(Person user);
	
	boolean uploadUserPicture(String path);

	boolean updatePersonRole(int person_id, int role_id);

	boolean deleteUserById(int user_id);
	
	
}
