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

/**
 * Servlet implementation class ServletConnectDB
 */
@WebServlet("/ServletConnectDB")
public class ServletConnectDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConnectDB() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // demonstrate connecting to a db
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String myDB = "jdbc:mysql://localhost:3306";
		String user = "root";
		String pswd = "javaEE7!";
                Connection conn = null;

		try {
		Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(myDB, user, pswd);
    		out.println("<h2>");
    		out.println("<br>Connected successfully!<br>");
    		out.println("</h2>");
		}
		catch (ClassNotFoundException ex) {
			out.println("Sorry Charlie...can't load JDBC driver");
		}
		catch (SQLException ex) {
			for (Throwable t : ex) {
				t.printStackTrace(out);
			return;
			}
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
