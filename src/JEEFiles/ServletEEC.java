package JEEFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Statement;

/**
 * Servlet implementation class ServletEEC
 */
@WebServlet("/ServletEEC")
public class ServletEEC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMPORARYERROR = 
			"Sorry, we are experiencing a temporary error...please retry later";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEEC() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// uses an external class (LocalMySQL) for all DB actions
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// open MySQL db
		LocalMySQL myDB = new LocalMySQL();
		if (! myDB.isAvailable()) {
			out.println(TEMPORARYERROR);
			return;
		}

		ResultSet result;  // results of executeQuery() 
		String sql;  // SQL to execute 
		int rowCount;  // number of rows affected from executeUpdate()

		// assert DB is open 
		try {
			myDB.executeUpdate("create database if not exists javaee");
			out.println("<br>create database javaee ");

			myDB.executeUpdate("use javaee");
			out.println("<br>use javaee ");

			myDB.executeUpdate("drop table if exists cities");
			out.println("<br>drop table cities ");

			myDB.executeUpdate("create table cities" 
					+ "(id int auto_increment primary key,"
					+ "name varchar(30),"
					+ "population int)");
			out.println("<br>create table cities ");
			// table creation done

			// insert a little data (normally not hardcoded...)
			sql = "insert into cities (name, population) values " 
					+ " ('Dallas', '3179809'),"
					+ " ('El Paso', '376529'),"
					+ " ('Lubbock', '420948'),"
					+ " ('Corpus Christi', '272192')"
					;
			rowCount = myDB.executeUpdate(sql);
			// could check rowCount if desired
			out.println("<br>insert city data ");

			// check on Dallas
			out.println("<br><br>Check Dallas: ");
			String query = "select * from cities where name='Dallas'";
			result = myDB.executeQuery(query);

			while (result.next()) {
				out.println("<br>" + result.getString("name") 
						+ "/" 
						+ result.getString("population") );
			}

			// add a couple more cities
			sql = "insert into cities (name, population) values " 
					+ " ('Abeline', '179809'),"
					+ " ('Amarillo', '239845')"
					;
			rowCount = myDB.executeUpdate(sql);
			// could check rowCount if desired

			out.println("<br><br>Cities starting with 'A': ");
			sql = "select * from cities where name like 'A%'";
			result = myDB.executeQuery(sql);

			while (result.next()) {
				out.println("<br>" + result.getString("name") 
						+ "/" 
						+ result.getString("population") );
			}
		}
		catch (SQLException ex) {
			out.println("<p>" + TEMPORARYERROR);
			myDB.printTrace(ex);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
