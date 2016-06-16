package JEEFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DriverVersion
 */
@WebServlet("/DriverVersion")
public class DriverVersion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMPORARYERROR = "Sorry, we are experiencing a temporary error...please retry later";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverVersion() {
        super();
        }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // demonstrate connecting to a db
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// open MySQL db
		Connection conn = openDB();
		if (conn == null) {
			out.println(TEMPORARYERROR);
			return;
		}

		// assert DB is open. now get driver metadata
		try {
			DatabaseMetaData md = conn.getMetaData();
			out.print("<br>Connector/J level is: " + md.getDriverVersion());
			out.print("<br>JDBC level is: " + md.getJDBCMajorVersion()+
					                "." + md.getJDBCMinorVersion());
		}
		catch (SQLException ex) {
			out.println("<p>" + TEMPORARYERROR);
			printTrace(ex);
		}
	}

	private Connection openDB() {
		// opens the local mysql db
		String myDB = "jdbc:mysql://localhost:3306";
		String user = "root";
		String pswd = "javaEE7!";
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(myDB, user, pswd);
		}
		catch (ClassNotFoundException ex) {
			System.out.println("<br>Can't load JDBC driver");  // log the error
		}
		catch (SQLException ex) {
			printTrace(ex);
		}

		return conn;  
	}

	private void printTrace(SQLException ex) {
		// prints stack for given Exception to console
		for (Throwable t : ex) {
			t.printStackTrace(System.out);  
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
