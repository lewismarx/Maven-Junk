package JEEFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 
 * @author pi
 * 
 * Separates MySQL database concerns from servlet business logic.
 * This version uses PreparedStatement for SQL Injection hardening.
 *
 */
public class LocalMySQL implements PreparedBaseJDBC {

	private Connection conn;  // database Connection
	private String myDB = "jdbc:mysql://localhost:3306";  // local connection string
	private String user = "root";  // local user
	private String pswd = "javaEE7!"; // local MySQL root passwd

	public LocalMySQL() {
		this.conn = openDB();  // if needed, user can alter userid/password and call openDB()
	}

	@Override
	public boolean isAvailable() {
		return this.conn == null ? false : true;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	@Override
	public ResultSet executeQuery(String query) 
			throws SQLException {
		throw new SQLException("must supply substitution parameters for SQL query");
	}
	
	@Override
	public ResultSet executeQuery(String query, String[] parms) 
			throws SQLException {
		PreparedStatement statement = this.conn.prepareStatement(query);
		if (parms != null) {
			int i = 1;
			for (String parm : parms) {
				statement.setString(i, parm);
				i++;
			}
		}
		ResultSet users = statement.executeQuery();
		return users;
	}

	@Override
	public int executeUpdate(String query) 
			throws SQLException {
		throw new SQLException("must supply substitution parameters for SQL query");
	}
	
	@Override
	public int executeUpdate(String query, String[] parms) 
			throws SQLException {
		PreparedStatement statement = this.conn.prepareStatement(query);
		if (parms != null) {
			int i = 1;
			for (String parm : parms) {
				statement.setString(i, parm);
				i++;
			}
		}
		int count = statement.executeUpdate();
		return count;
	}


	@Override
	public Connection openDB() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(myDB, user, pswd);
		}
		catch (ClassNotFoundException ex) {
			System.out.println("<br>Can't load JDBC driver");  // log error to console
		}
		catch (SQLException ex) {
			printTrace(ex);
		}

		return conn;  
	}

	@Override
	public void printTrace(SQLException ex) {
		for (Throwable t : ex) {
			t.printStackTrace(System.out);  // stack trace to console
		}
	}

}
