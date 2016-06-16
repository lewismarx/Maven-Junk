package JEEFiles;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 * @author Murach Servlets and JSPs 3rd edition
 * Singleton Connection pool class with minor mod for null pool check and scaffolding.
 *
 */
public class ConnectionPool {
	
	private static ConnectionPool pool = null;  // use Connection pooling
	private static DataSource dataSource = null; 
	
	private ConnectionPool() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc");
		}
		catch (NamingException ex) {
			System.out.println(ex);
		}
	}
	
	public static synchronized ConnectionPool getInstance() {
		if (pool == null) {
			System.out.println(new Date() 
					+ " Building new Connection pool...");
			pool = new ConnectionPool();
		}
		return pool;
	}
	
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		}
		catch (SQLException ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public void freeConnection(Connection c) {
		try {
			c.close();
		}
		catch (SQLException ex) {
			System.out.println(ex);
		}
	}

}
