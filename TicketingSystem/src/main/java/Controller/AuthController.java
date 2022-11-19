package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.IAuthDAO;
import Model.Person;
import Model.Role;
import Model.Ticket;
import Service.AuthService;
import Service.AuthServiceFactory;
import Utils.Helper;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class AuthController {

	IAuthDAO iAuthDAO;
	private ObjectMapper objectMapper;
	private AuthService authService;

	public AuthController(IAuthDAO iAuthDAO) {

		super();
		this.objectMapper = new ObjectMapper();
		this.iAuthDAO = iAuthDAO;
		authService = AuthServiceFactory.createAuthService(iAuthDAO);
	}

	public Handler handleRegister = (context) -> {

		Person person = objectMapper.readValue(context.body(), Person.class);
		System.out.println(person);
		boolean emailExist = authService.Register(person);
		if (!emailExist) {
			context.status(403);
			context.result("This email is already registered, please use another email");
		} else {
			context.status(201);
			context.result("You are registered, please login");
		}
	};

	public Handler handleLogin = (context) -> {

		Map<String, String> body = objectMapper.readValue(context.body(), LinkedHashMap.class);
		Person login = authService.Login(body.get("email"), body.get("password"));
		if (login == null)
			context.status(404);
		else {
			Helper.setPerson(login);
			context.req().setAttribute("user_id", login.getID());
			context.status(200);
			context.result(objectMapper.writeValueAsString(login));
		}
	};

	public Handler handleEditProfile = (context) -> {
		if (Helper.getPerson() == null) {
			System.out.println("You are not authorized to edit profile");
			context.status(401);
		} else {
			Person person = objectMapper.readValue(context.body(), Person.class);
			authService.EditProfile(person);
			context.status(200);
			context.result(objectMapper.writeValueAsString(person));
		}
	};

	public Handler handleChangeRole = (context) -> {
		if (Helper.getPerson() == null || Helper.getPerson().getRole() == Role.EMPLOYEE) {
			context.status(401);
			context.result("You are not authorized to change the roles");

		} else {
			Map<String, Integer> body = objectMapper.readValue(context.body(), LinkedHashMap.class);
			authService.changePersonRole(body.get("employee_id"), body.get("role_id"));

			context.status(200);
			String roleName = body.get("role_id") == 1 ? "manager" : "employee";
			context.result(
					"You have changed the role for employee with id " + body.get("employee_id") + " to " + roleName);
		}
	};

	public Handler handleUploadPicture = (context) -> {
		if (Helper.getPerson() == null) {
			context.status(401);
			context.result("Please login to upload a picture");
		} else {
			UploadedFile uploadedFile = context.uploadedFile("profile");
			String name = context.formParam("name");
			try (InputStream inputStream = uploadedFile.content()) {
				File targetFile = new File("src/main/resources/profiles/" + uploadedFile.filename());
				FileUtils.copyInputStreamToFile(inputStream, targetFile);
				String path = targetFile.getPath();
				authService.UploadPicture(path);
				context.status(200);
				context.result("Profile picture was updated");

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	};

	public Handler handleGetAllUsers = (context) -> {

		List<Person> pList = authService.getAllUsers();
		context.status(200);
		context.result(objectMapper.writeValueAsString(pList));

	};

	public Handler handleDeleteUserById = (context) -> {
		if (Helper.getPerson() == null) {
			context.status(401);
			context.result("You are not autorized to delete a user");

		}
		else {
		Map<String, Integer> body = objectMapper.readValue(context.body(), LinkedHashMap.class);
		boolean deleted = authService.deleteUserByID(body.get("user_id"));

		if (deleted) {
			context.status(200);
			context.result("You have deleted the user with id " + body.get("user_id"));
		} else {
			context.status(400);
			context.result("Unable to delete the user with id " + body.get("user_id"));
		}
		}
	};
	public Handler handleLogout = (context) -> {
		if (Helper.getPerson() == null) {
			context.status(400);
			context.result("Only logged in users can logout");
		} else {
			context.req().getSession().invalidate();
			context.status(200);
			context.result("Bye " + Helper.getPerson().getID());
			Helper.setPerson(null);
		}
	};
}
