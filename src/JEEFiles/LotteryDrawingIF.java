package JEEFiles;

public interface LotteryDrawingIF {

	int[] get_results();

	/**
	 * Simulator core
	 */
 	void run_simulation();
	void run_simulation_using_Set();

	/**
	 * Returns an int[] of unique random integers from 1 .. max_ball_num
	 * Depends on external rand variable as a Random generator
	 * @return
	 */
	int[] pick_numbers();

}