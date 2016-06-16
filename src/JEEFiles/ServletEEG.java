package JEEFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ServletEEG
 */
@WebServlet("/ServletEEG")
public class ServletEEG extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMPORARYERROR = "Sorry, we are experiencing a temporary error...please retry later";
	private static final int MAX_BALL_NUM = 54;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEEG() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// uses LocalMySQLCP for all DB actions; uses form input
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		// get the city parameters
		String quickpicks = request.getParameter("quickpicks");
		if (quickpicks == null) {
			out.println("quickpicks count not available...aborting");
			return;
		}

		int quickpick_count = 10;
		try {
			quickpick_count = Integer.parseInt(quickpicks);
		} catch (Exception ex) {
			out.println("Enter number of quick picks -- using 10 this time");
		}

		// open MySQL db
		LocalMySQLCP myDB = new LocalMySQLCP();
		if (!myDB.isAvailable()) {
			out.println(TEMPORARYERROR);
			return;
		}

		load_quickpicks(out, quickpick_count, myDB);
		run_lottery(out, quickpick_count, myDB);
	}

	/**
	 * Generates given number of quick picks into quickpicks table
	 * @param out is response Stream
	 * @param quickpick_count is desired number of quick picks
	 * @param myDB is database accessor reference (assumes javaee schema exists)
	 */
	private void load_quickpicks(PrintWriter out, int quickpick_count,
			LocalMySQLCP myDB) {

		ResultSet result; // results of executeQuery()
		String sql; // SQL to execute
		int rowCount; // how many rows affected
		String[] parms; // substitution parameters for SQL statement

		// assert DB is open, javaee database exists, cities table exists
		try {
			// or specify db as part of table name
			sql = "use javaee";
			myDB.executeUpdate(sql, null);

			sql = "create table if not exists quickpicks"
					+ " (n1 int, n2 int, n3 int, n4 int, n5 int, n6 int)";
			myDB.executeUpdate(sql, null);

			// dump old quickpicks
			sql = "truncate table quickpicks";
			myDB.executeUpdate(sql, null);

			Random rand = new Random();

			for (int i = 0; i < quickpick_count; i++) {
				// insert 6 random integers into db
				sql = "insert into quickpicks (n1, n2, n3, n4, n5, n6) values (?,?,?,?,?,?)";
				parms = new String[] {
						String.valueOf(rand.nextInt(MAX_BALL_NUM) + 1),
						String.valueOf(rand.nextInt(MAX_BALL_NUM) + 1),
						String.valueOf(rand.nextInt(MAX_BALL_NUM) + 1),
						String.valueOf(rand.nextInt(MAX_BALL_NUM) + 1),
						String.valueOf(rand.nextInt(MAX_BALL_NUM) + 1),
						String.valueOf(rand.nextInt(MAX_BALL_NUM) + 1), };
				rowCount = myDB.executeUpdate(sql, parms);
			}
		} catch (SQLException ex) {
			out.println("<p>" + TEMPORARYERROR);
			myDB.printTrace(ex);
		}
	}

	/**
	 * Lottery runner
	 * @param out is response Stream
	 * @param quickpick_count is number of quick picks stored for simulation
	 * @param myDB is database accessor (assumes javaee schema exists)
	 */
	private void run_lottery(PrintWriter out, int quickpick_count,
			LocalMySQLCP myDB) {
		out.printf("Running simulation with %d quickpicks", quickpick_count);

		Lotto lotto = new Lotto();
		lotto.setOut(out); // lotto needs the output stream

		out.println("\nThe odds are 1 in " + lotto.calc_odds() + " using "
				+ lotto.getNum_lotto_balls() + " balls and "
				+ lotto.getMax_ball_num() + " ball numbers");

		// run a simulation using the database quickpicks
		lotto.setSimulator(new LotteryDrawingDB(lotto.getNum_lotto_balls(),
				lotto.getMax_ball_num(), quickpick_count, myDB));
		lotto.getSimulator().run_simulation();
		lotto.print_results();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
