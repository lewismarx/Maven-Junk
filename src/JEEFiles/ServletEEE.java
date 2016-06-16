package JEEFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletEEE
 */
@WebServlet("/ServletEEE")
public class ServletEEE extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMPORARYERROR = 
			"Sorry, we are experiencing a temporary error...please retry later";


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEEE() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// uses LocalMySQL for all DB actions; uses form input
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// get the city parameters
		String city = request.getParameter("city");
		String population = request.getParameter("population");
		if (city == null || population == null) {
			out.println("<h2>City or population not available...");
			return;
		}
		out.println("<br>Received " + city + "/" + population);

		// open MySQL db
		LocalMySQL myDB = new LocalMySQL();
		if (! myDB.isAvailable()) {
			out.println(TEMPORARYERROR);
			return;
		}

		ResultSet result;  // results of executeQuery() 
		String sql;  // SQL to execute 
		int rowCount;  // how many rows affected
		String[] parms;  // substitution parameters for SQL statement

		// assert DB is open, javaee database exists, cities table exists
		try {
			// or specify db as part of table name
			sql = "use javaee";
			myDB.executeUpdate(sql, new String[]{});  // no "?" means no parms; could also use null

			sql = "select * from cities where name=?";
			parms = new String[]{city};  
			result = myDB.executeQuery(sql,parms);
			// any rows in ResultSet?
			if (result.first()) {
				out.println("<br>" + city + " found -- updating population");
				sql = "update cities set population=? where name=?";
			    parms = new String[]{population, city};  
				rowCount = myDB.executeUpdate(sql, parms);
			}
			else {
				out.println("<br>" + city + " not found -- inserting");
				sql = "insert into cities (name, population) values (?,?)";
			    parms = new String[]{city, population};  
				rowCount = myDB.executeUpdate(sql, parms);
			}
		}
		catch (SQLException ex) {
			out.println("<p>" + TEMPORARYERROR);
			myDB.printTrace(ex);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
