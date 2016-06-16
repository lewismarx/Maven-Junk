package JEEFiles;

import java.sql.ResultSet;
import java.sql.SQLException;

import JEEFiles.LocalMySQLCP;

public class LotteryDrawingDB extends JEEFiles.LotteryDrawing {

	protected LocalMySQLCP myDB;  // database Connection object
	protected ResultSet user_picks = null;

	public LotteryDrawingDB(int num_lotto_balls,
			int max_ball_number, 
			int simulation_drawings,
			LocalMySQLCP myDB) {

		// could have done what instead?
		setNum_lotto_balls(num_lotto_balls); 
		setMax_ball_number(max_ball_number);
		setSimulation_drawings(simulation_drawings);
		setResults(new int[num_lotto_balls + 1]);  // create results array
		//for (int i = 0; i < getResults().length; i++) getResults()[i] = 0;  // initialize results 
		try {
			user_picks = myDB.executeQuery("select * from quickpicks", null);
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());	
		}
	}

	public void run_simulation() {
		// simulates a virtual lottery 
		for (int drawing = 0; drawing < getSimulation_drawings(); drawing++) {
			int[] official_picks = pick_numbers(); // get the official winning numbers
			int[] user_picks = user_numbers(); // get the user's picks 
			if (user_picks == null) break;  // exit simulation if error getting user picks
			getResults()[intersection(official_picks, user_picks)]++;  // bump the results bucket
		}
	}	

	protected int[] user_numbers() {
		// returns one row of user picks from database 
		if (user_picks != null) {
			try {
				user_picks.next();
				return new int[]{
						user_picks.getInt("n1"),
						user_picks.getInt("n2"),
						user_picks.getInt("n3"),
						user_picks.getInt("n4"),
						user_picks.getInt("n5"),
						user_picks.getInt("n6")
						};
			}
			catch (SQLException ex) {
				System.out.println(ex.getMessage());	
			}
		}
		System.out.println("Invalid simulation results!");
		return null;
	}
}
