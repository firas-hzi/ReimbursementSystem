package Utils;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnectionUtil {
	
	private static JDBCConnectionUtil util;
	
	private static Properties props = new Properties();
	
	private JDBCConnectionUtil() {}
	
	public static JDBCConnectionUtil getInstance() {
		if(util == null) {
			util = new JDBCConnectionUtil();
		}
		
		return util;
	}
	
	public Connection getConnection() {
		
		Connection con = null;
		
		try {
			
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream in = classLoader.getResourceAsStream("jdbc.properties");

			String url="";
			String username="";
			String password="";
			
			props.load(in);
			
			url = props.getProperty("url");
			username = props.getProperty("username");
			password = props.getProperty("password");
			
			con = DriverManager.getConnection(url, username, password);
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return con;
		
	}
	
	

}
