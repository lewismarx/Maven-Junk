package JEEFiles;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Runs simulation part of virtual lottery (see Lotto class)
 * 
 * @author pi
 *
 */
public class LotteryDrawing implements LotteryDrawingIF {
	
	
	private int num_lotto_balls;  // number of balls used in each drawing
	private int max_ball_number;  // max ball number
	private int simulation_drawings;  // number of drawings in simulation @ 2/week
	private int[] results;  		// keeps track of simulation results
	private Random rand = new Random();  // random number source
	
	/* (non-Javadoc)
	 * @see edu.acc.java.LottaryDrawingIF#get_results()
	 */
	@Override
	public int[] get_results() {
		return results;
	}
	
	public LotteryDrawing() {}
	public LotteryDrawing(int num_lotto_balls,
			              int max_ball_number,
			              int simulation_years) {
		this.num_lotto_balls = num_lotto_balls;
		this.max_ball_number = max_ball_number;
		simulation_drawings = simulation_years*104;  // two drawings per week
		results = new int[num_lotto_balls + 1];  // holds simulation results: one element for number
		                                         // of balls matched plus zero if no matches
	}
	
	/* (non-Javadoc)
	 * @see edu.acc.java.LottaryDrawingIF#run_simulation()
	 */
	@Override
	public void run_simulation() {
		for (int drawing = 0; drawing < this.simulation_drawings; drawing++) {
    		int[] official_picks = pick_numbers(); // get the official winning numbers
		    int[] user_picks = pick_numbers(); // get the user's quick picks
		    results[intersection(official_picks, user_picks)]++;  // compute matches
		}
	}

	/**
	 * Calculate matches between arrays
	 * @param a
	 * @param b
	 * @return count of numbers in common over a and b
	 */
	public int intersection(int[] a, int[] b) {
		// returns the intersection (numbers in common) between int[] a and int[] b
		int matches = 0;
		for (int next_a : a) {
			for (int next_b : b) {
				if (next_a == next_b) {
					matches++;
					break;
				}
			}
		}
		return matches;
	}
	
	/**
	 * Returns true if int[] a contains int n, else false
	 * @param a
	 * @param n
	 * @return
	 */
	public boolean already_have(int[] a, int n) {
		for (int next_a : a) {
			if (next_a == n)  return true;
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see edu.acc.java.LottaryDrawingIF#pick_numbers()
	 */
	@Override
	public int[] pick_numbers() {
        int[] new_picks = new int[num_lotto_balls];  // establish new picks array
        int new_pick;
        
        for (int i=0; i < num_lotto_balls; i++) {
        	do {
                new_pick = rand.nextInt(max_ball_number) + 1;
        	} while (already_have(new_picks, new_pick)) ;  // generate again if already have this number
            new_picks[i] = new_pick;  // have a unique one so go with it
            }
		return new_picks;
        }
	
	/**
	 * New implementation of run_simulation using Set
	 */
	public void run_simulation_using_Set() {
		for (int drawing = 0; drawing < this.simulation_drawings; drawing++) {
			Set<Integer> official_picks = pick_numbers_using_Set();  // official drawing
			Set<Integer> user_picks = pick_numbers_using_Set();  // user quick picks
		    official_picks.retainAll(user_picks);  // only keep matches 
		    results[official_picks.size()]++;  // bump the appropriate match count
		}
	}

	/**
	 * New implementation to pick a set of ball numbers using Set
	 * @return a Set of num_lotto_balls size
	 */
	public Set<Integer> pick_numbers_using_Set() {
		Set<Integer> new_picks = new HashSet<>();
		do {
		    new_picks.add(rand.nextInt(max_ball_number) + 1);	// new ball will be ignored if duplicate
		} while (new_picks.size() < num_lotto_balls);
		return new_picks;
	}
	
	public void setResults(int[] results) {
		this.results = results;
	}

	public int[] getResults() {
		return results;
	}

	public void setNum_lotto_balls(int num_lotto_balls) {
		this.num_lotto_balls = num_lotto_balls;
	}

	public void setMax_ball_number(int max_ball_number) {
		this.max_ball_number = max_ball_number;
	}

	public void setSimulation_drawings(int simulation_drawings) {
		this.simulation_drawings = simulation_drawings;
	}

	public int getSimulation_drawings() {
		return simulation_drawings;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

}
