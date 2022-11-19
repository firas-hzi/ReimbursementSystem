package Service;
import DAO.IAuthDAO;

public class AuthServiceFactory {

public static AuthService createAuthService(IAuthDAO iAuthDAO)
{
	
return new AuthService(iAuthDAO);
}
}
