package JEEFiles;

import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Virtual lottery simulator project
 * 
 * Use command line args (the args coming into main()) to obtain inputs for a virtual lottery:
 *    A flag -b followed by an integer number of lotto balls to use (default is 6)
 *    A flag -p followed by the maximum ball number i.e. from 1 to the max (default is 54)
 *    A flag -y followed by the number of years to simulate (default is 1)
 *
 * Define a new class called Lotto and a private method called calc_odds() that returns the odds of winning. Use the following algorithm to exercise your array skills:
 *    Create an array using the -p value as a length and fill the array with the ball numbers e.g. 1 - 54
 *    Multiply the last -b elements in the array together e.g. for 54 balls max and 6 balls in use: 54*53*52*51*50*49
 *
 * A second method in Lotto is called print_results() which reads the results[] array from LotteryDrawing, and prints a report of results as seen below. Each row should have the match count (0, 1, 2, ...) followed by the results for that match count, and percentage of the total for that match count (see picture at end).
 *
 * Define a new class called LotteryDrawing with the following instance methods:
 *    public void run_simulation()
 *        runs drawings at 104 per year (-y parameter gives number of years)
 *        obtains an int[] from pick_numbers() to be the winning lottery numbers for each drawing. Array length is -b
 *        obtains a second int[] from pick_numbers() to be the users quick picks
 *        compares the two int[] arrays counting matches: 0 (no matches) up to 6 (all matched)
 *        keeps track of the number of matches in a private LotteryDrawing array called results[]. Array length should cover all the match possibilites. Results[] should be available using a getter
 *
 *    public int[] pick_numbers() will:
 *        obtain and initialize a java.util.Random generator variable
 *        fill a local int[] with random numbers from 1 to the number of balls (-b parameter)
 *        verify unique numbers in the int[] (no duplicates)
 *
 *    Run a simulation by instantiating LotteryDrawing in Lotto and calling its run_simulation() method, then print_results() to get the report.
 *
 * (Stretch goal): Keep track of the simulation time and print that too. Hint: see the System class.
 * 
 * @author pi
 *
 */

public final class Lotto implements LottoIF {

    private LotteryDrawingIF simulator;
	private int num_lotto_balls = NUM_LOTTO_BALLS;  // default number of balls
	private int max_ball_num = MAX_BALL_NUM;  // default max ball number
	private int simulation_years = SIMULATION_YEARS; // default years to run simulation
	// added for servlet conversion
	private PrintWriter out;
	
	/* (non-Javadoc)
	 * @see edu.acc.java.LottoIF#calc_odds()
	 */
	@Override
	public long calc_odds() {  // made public to include in LottoIF
		long the_odds = 1L;
		int[] temp = new int[max_ball_num];
		for (int i = 0; i < temp.length; i++) temp[i] = i+1;
		for (int i=1; i <= num_lotto_balls; i++ ) {
			the_odds *= temp[max_ball_num - i]; 
		}
		
//      A way that doesn't use arrays: 

//		int the_ball_number = max_ball_num;
//
//		for (int i = 0; i < num_lotto_balls; i++) {
//			the_odds *= the_ball_number--;
//		}
	    return the_odds;	
	}
	
	/* (non-Javadoc)
	 * @see edu.acc.java.LottoIF#print_results()
	 */
	@Override
	public void print_results() {
		out.println("\n--Lottery Drawing Report--");
		int i = 0;  // match counter
		out.println("Matches   Count  Percent");

		for (int next_result : simulator.get_results()) {  // print one line per result
			out.printf("%7s %7s %7.1f%%\n", i++, next_result, 
					          (next_result/(simulation_years*104.))*100.);
		}
	}
		
	public LotteryDrawingIF getSimulator() {
		return simulator;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public void setSimulator(LotteryDrawingIF simulator) {
		this.simulator = simulator;
	}

	public int getNum_lotto_balls() {
		return num_lotto_balls;
	}

	public void setNum_lotto_balls(int num_lotto_balls) {
		this.num_lotto_balls = num_lotto_balls;
	}

	public int getMax_ball_num() {
		return max_ball_num;
	}

	public void setMax_ball_num(int max_ball_num) {
		this.max_ball_num = max_ball_num;
	}

	public int getSimulation_years() {
		return simulation_years;
	}

	public void setSimulation_years(int simulation_years) {
		this.simulation_years = simulation_years;
	}

	/**
	 * Entry point for virtual lottery
	 * @param args
	 */
	public static void main(String[] args) {
		
		Lotto lotto = new Lotto();  
		if (args.length % 2 != 0) die("Invalid number of arguments");
		process_flags(args, lotto);  
		System.out.println("Flags: -b " + lotto.num_lotto_balls + " -p " + lotto.max_ball_num
				            + " -y " + lotto.simulation_years);

		System.out.printf("\nThe odds are 1 in %,d using %d balls and %d ball numbers%n", 
				              lotto.calc_odds(), lotto.num_lotto_balls, lotto.max_ball_num);
		
        lotto.simulator = new LotteryDrawing(lotto.num_lotto_balls,
        		                             lotto.max_ball_num,
        		                             lotto.simulation_years);
		long start_time = Calendar.getInstance().getTimeInMillis();
        lotto.simulator.run_simulation();
//      lotto.simulator.run_simulation_using_Set();
		long elapsed = Calendar.getInstance().getTimeInMillis() - start_time;
        lotto.print_results();
        System.out.println("\nSimulation elapsed time (ms):" + elapsed);
	}

	/**
	 * Basic flags processor with bounds checking
	 * Note: does not handle malformed input (how do we fix that?)
	 * @param args is the command line args array
	 * @param lotto is a Lotto instance
	 * 
	 * Flags:
	 * -b <integer number of lotto balls> (default 6)
	 * -p <integer max ball number> (default 54)
     * -y <integer number of years to simulate> (default 1)
	 */
	public static void process_flags(String[] args, Lotto lotto) {
		for (int i=0; i < args.length;) {
			switch (args[i].trim()) {
			case "-b": lotto.num_lotto_balls = Integer.parseInt(args[++i]);
			           if (lotto.num_lotto_balls > 13) die("max value for -b is 13");
			           if (lotto.num_lotto_balls < 1) die("minimum value for-b is 1");
			           break;
			case "-p": lotto.max_ball_num = Integer.parseInt(args[++i]);
			           if (lotto.max_ball_num <= lotto.num_lotto_balls) die("-p must be greater than -b");
			           break;
			case "-y": lotto.simulation_years = Integer.parseInt(args[++i]);
			           if (lotto.simulation_years <= 0) die("-y must be greater than 0");
			           if (lotto.simulation_years > 100000) die("-y must be less than 100,000");
			           break;
			default:   System.out.println("Invalid option detected...ignoring");
			}
			
			i ++;  // bump to next possible flag
		}
	}
	
	/**
	 * Convenience method to print message and exit
	 * @param msg
	 */
	private static void die(String msg) {
		System.out.println(msg);
		System.exit(127);
	}

}
