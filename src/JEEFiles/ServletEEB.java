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
 * Servlet implementation class ServletEEB
 */
@WebServlet("/ServletEEB")
public class ServletEEB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMPORARYERROR = "Sorry, we are experiencing a temporary error...please retry later";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEEB() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// use executeQuery() and executeUpdate()
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// open MySQL db
		Connection conn = openDB();
		if (conn == null) {
			out.println(TEMPORARYERROR);
			return;
		}

		ResultSet result;  // results of executeQuery() 
		String sql;  // SQL to execute 
		
		// assert DB is open 
		try {
			int rowCount = 0;
			rowCount = executeUpdate(conn, "create database if not exists javaee");
			out.println("<br>create database javaee ");

			rowCount = executeUpdate(conn, "use javaee");
			out.println("<br>use javaee ");

			rowCount = executeUpdate(conn, "drop table if exists cities");
			out.println("<br>drop table cities ");

			rowCount = executeUpdate(conn, "create table cities" 
					+ "(id int auto_increment primary key,"
					+ "name varchar(30),"
					+ "population int)");
			out.println("<br>create table cities ");
			// table creation done

			// insert a little data 
			sql = "insert into cities (name, population) values " 
					+ " ('Dallas', '3179809'),"
					+ " ('El Paso', '376529'),"
					+ " ('Lubbock', '420948'),"
					+ " ('Corpus Christi', '272192')"
					;
			rowCount = executeUpdate(conn, sql);
			out.println("<br>insert city data ");

			// check on Dallas
			out.println("<br><br>Check Dallas: ");
			String query = "select * from cities where name='Dallas'";
			result = executeQuery(conn, query);

			while (result.next()) {
				out.println("<br>" + result.getString("name") 
						+ " [ population: " 
						+ result.getString("population") 
				        + "]");
			}

			// add a couple more cities
			sql = "insert into cities (name, population) values " 
					+ " ('Abeline', '179809'),"
					+ " ('Amarillo', '239845')"
					;
			rowCount = executeUpdate(conn, sql);

			out.println("<br><br>Cities starting with 'A': ");
			sql = "select * from cities where name like 'A%'";
			result = executeQuery(conn, sql);

			while (result.next()) {
				out.println("<br>" + result.getString("name") 
						+ " [ population: " 
						+ result.getString("population") 
				        + "]");
			}
		}
		catch (SQLException ex) {
			out.println("<p>" + TEMPORARYERROR);
			printTrace(ex);
		}
	}

	private ResultSet executeQuery(Connection conn, String query) throws SQLException {
		// returns ResultSet for the given SQL select query, else SQLException if error
		Statement statement = conn.createStatement();
		ResultSet users = statement.executeQuery(query);
		return users;
	}

	private int executeUpdate(Connection conn, String query) throws SQLException {
		// returns row count for the given SQL update query, else SQLException if error
		Statement statement = conn.createStatement();
		int count = statement.executeUpdate(query);
		return count;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
