import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import DAO.IAuthDAO;
import Exception.EmailAlreadyExistException;
import Exception.PersonNotFoundException;
import Model.Person;
import Service.AuthService;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTests {

	AuthService authService;

	@Mock
	IAuthDAO iAuthDAO;

	@Before
	public void setAuthService() {
		authService = new AuthService(iAuthDAO);
	}

	@Test
	public void testGetAllRegistered() {
		List<Person> testList = new ArrayList<>();
		testList.add(new Person());
		testList.add(new Person());

		when(iAuthDAO.getAllUsers()).thenReturn(testList);

		List<Person> resultList = authService.getAllUsers();

		assertEquals(testList, resultList);
	}

	@Test
	public void testLoginValid() {
		Person p = new Person();

		when(iAuthDAO.Login(anyString(), anyString())).thenReturn(p);

		Person resultPerson = authService.Login("email", "password");

		assertEquals(p, resultPerson);
	}

	@Test
	public void testLoginInvalid() {
		when(iAuthDAO.Login(anyString(), anyString())).thenReturn(null);

		assertThrows(PersonNotFoundException.class, () -> authService.Login("bad email", "bad password"));
	}

	@Test
	public void testRegisterFails() throws Exception {
		doThrow(new SQLException()).when(iAuthDAO).Register(any(Person.class));

		assertThrows(EmailAlreadyExistException.class, () -> authService.Register(new Person()));

	}

	@Test
	public void testRegisterAddsPerson() {
		Person testPerson = new Person();

		authService.Register(testPerson);

		try {
			verify(iAuthDAO).Register(testPerson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
