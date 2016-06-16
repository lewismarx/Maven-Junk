package JEEFiles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * JBDC accessor methods.
 *
 */
public interface BaseJDBC {

	/**
	 * 
	 * @return true if DB is available
	 * 
	 */
	boolean isAvailable();

	/**
	 * 
	 * @param user valid MySQL user
	 */
	void setUser(String user);

	/**
	 * 
	 * @param pswd MySQL password for user
	 */
	void setPswd(String pswd) throws SQLException;

	/**
	 * 
	 * @param query the SQL syntax query to execute
	 * @return ResultSet for given query 
	 * @throws SQLException
	 */
	ResultSet executeQuery(String query) throws SQLException;

	/**
	 * 
	 * @param query the SQL syntax query to execute
	 * @return int for number of rows affected
	 * @throws SQLException
	 */
	int executeUpdate(String query) throws SQLException;

	/**
	 * 
	 * @return Connection object if DB opened successfully, else null
	 */
	Connection openDB();

	/**
	 * 
	 * @param ex SQLException to print nested stack traces to console
	 */
	void printTrace(SQLException ex);

}
