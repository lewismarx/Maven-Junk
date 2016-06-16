package JEEFiles;

import java.sql.SQLException;

/**
	 * 
	 * Utility JBDC accessor class.
	 * This version supports PreparedStatement and Connection pooling.
	 *
	 */

public interface PreparedBaseJDBCPool extends PreparedBaseJDBC {

	/**
	 * 
	 * @param user valid MySQL user
	 */
	@Deprecated
	void setUser(String user);

	/**
	 * 
	 * @param pswd MySQL password for user
	 */
	@Deprecated
	void setPswd(String pswd) throws SQLException;

		void CloseDB();



}
