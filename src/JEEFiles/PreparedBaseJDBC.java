package JEEFiles;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 
 * Utility JBDC accessor class.
 * This version supports PreparedStatement
 *
 */
public interface PreparedBaseJDBC extends BaseJDBC {

	@Deprecated
	ResultSet executeQuery(String query) throws SQLException;
	
	@Deprecated
	int executeUpdate(String query) throws SQLException;
	
	/**
	 * 
	 * @param query the SQL syntax query to execute
	 * @param parms the SQL parameters to substitute
	 * @return ResultSet for given query 
	 * @throws SQLException
	 */
	ResultSet executeQuery(String query, String[] parms) throws SQLException;

	/**
	 * 
	 * @param query the SQL syntax query to execute
	 * @param parms the SQL parameters to substitute
	 * @return int for number of rows affected
	 * @throws SQLException
	 */
	int executeUpdate(String query, String[] parms) throws SQLException;

}