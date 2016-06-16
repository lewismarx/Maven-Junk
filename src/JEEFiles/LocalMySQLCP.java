package JEEFiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author pi
 * 
 * Utility class for JDBC MySQL using connection pool
 */

public class LocalMySQLCP implements PreparedBaseJDBCPool {

	private static ConnectionPool pool = null;  // for Connection pooling
	private Connection conn = null;  // our Connection from pool

	public LocalMySQLCP() {
		this.conn = openDB();
	}
	
	@Override
	public boolean isAvailable() {
		return this.conn != null;
	}
	
	@Deprecated
	public void setUser(String user) {
		System.out.println("See context.xml to set DB user");
	}

	@Deprecated
	public void setPswd(String pswd) {
		System.out.println("See context.xml to set DB user password");
	}

	public void CloseDB() {

	}

	public void CloseDB(Connection conn) throws SQLException { if(conn.isClosed() != true) {
		conn.close();
	}

	}

	@Override
	public ResultSet executeQuery(String query) 
			throws SQLException {
		throw new SQLException("must supply query substitution parameters");
	}

	@Override
	public int executeUpdate(String query) 
			throws SQLException {
		throw new SQLException("must supply query substitution parameters");
	}
	
	@Override
	public ResultSet executeQuery(String query, String[] parms) 
			throws SQLException {
		PreparedStatement statement = prepSQL(query, parms);
		System.out.println(new Date()+ " Query: " + statement.toString());
		ResultSet users = statement.executeQuery();
		return users;
	}

	@Override
	public int executeUpdate(String query, String[] parms) 
			throws SQLException {
		PreparedStatement statement = prepSQL(query, parms);
		System.out.println(new Date()+ " Update: " + statement.toString());
		int count = statement.executeUpdate();
		return count;
	}

	private PreparedStatement prepSQL(String query, String[] parms)
			throws SQLException {
		PreparedStatement statement = this.conn.prepareStatement(query);
		if (parms != null) {
			int i = 1;
			for (String parm : parms) {
				statement.setString(i, parm);
				i++;
			}
		}
		return statement;
	}
	
	@Override
	public Connection openDB() {
		pool = ConnectionPool.getInstance();
		if (pool != null) {
		    this.conn = pool.getConnection();  
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

