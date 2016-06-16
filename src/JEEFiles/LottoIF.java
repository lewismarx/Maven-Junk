package JEEFiles;

public interface LottoIF {

	static final int NUM_LOTTO_BALLS = 6;  // default number of balls
	static final int MAX_BALL_NUM = 54;  // default max ball number
	static final int SIMULATION_YEARS = 1;  // default years to run simulation 

	/**
	 * Calculate odds of winning with given number of lotto balls and ball range
	 * @return the odds i.e. 1 in the_odds chance of winning and picked in order of drawing
	 */
	long calc_odds();

	/**
	 * Prints a short report of simulation results
	 */
	void print_results();

}