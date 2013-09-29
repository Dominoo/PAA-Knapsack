import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.sun.istack.internal.FinalArrayList;

class KnapsackProblem {

	KnapsackSolution solution;
	int weights[];
	int costs[];
	int maxWeight;
	int id;

	public KnapsackProblem(int[] weights, int[] costs, int maxWeight, int id) {
		super();
		this.weights = weights;
		this.costs = costs;
		this.maxWeight = maxWeight;
		this.id = id;
		this.solution = new KnapsackSolution(this.weights.length);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.id + " "
				+ this.weights.length + " " + solution.cost);
		for (int b : solution.precense) {
			sb.append(" " + b);
		}
		return sb.toString();
	}

}

class KnapsackSolution {
	int precense[];
	int cost;
	long time;

	public KnapsackSolution(int size) {
		super();
		this.cost = 0;
		this.precense = new int[size];
	}
}

public class Knapsack {

	String testFile = "inst/knap_25.inst.dat";
	Scanner sc = null;
	KnapsackProblem currentProblem;
	int mask[];
	int currentCost;

	public void run() throws FileNotFoundException {
		sc = new Scanner(new File(testFile));
		// sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			int id, n, m;
			String line = sc.nextLine();
			String[] split = line.split(" ");
			id = Integer.parseInt(split[0]);
			n = Integer.parseInt(split[1]);
			m = Integer.parseInt(split[2]);
			int w[] = new int[n];
			int c[] = new int[n];

			for (int i = 0; i < n; i++) {
				int index = 3 + (i * 2); // offsetv line
				w[i] = Integer.parseInt(split[index]);
				c[i] = Integer.parseInt(split[index + 1]);
			}

			// RunBruteforce problem
			// obtain result and print it
			bruteForceProblem(id, n, m, w, c);

		}

	}

	private KnapsackProblem bruteForceProblem(int id, int n, int m, int[] w2,
			int[] c) {

		KnapsackProblem problem = new KnapsackProblem(w2, c, m, id);
		currentProblem = problem;
		this.mask = new int[c.length];

		long start = System.nanoTime();

		knapsackBF(c.length - 1, 0, 0, 0);
		knapsackBF(c.length - 1, 1, 0, 0);

		long end = System.nanoTime();

		System.out.println(problem);
		System.out.println(TimeUnit.SECONDS.convert((end - start),
				TimeUnit.NANOSECONDS));
		/*
		 * System.out.print(id + " "+ n +" "+ res); for(int i = 0; i < n;i++) {
		 * System.out.print(" " +problem.solution.pr[i]); }
		 * System.out.println("");
		 */
		return problem;

	}

	/*
	 * This method is slow and deprecated :)
	 * 
	 * private void knapsackBF(int index, int state) {
	 * 
	 * if(index >= 0) { this.mask[index] = state; knapsackBF(index-1, 0);
	 * knapsackBF(index-1, 1);
	 * 
	 * } else { int cost = sumhelper(mask, this.currentProblem.costs); if(cost >
	 * this.currentProblem.solution.cost && sumhelper(mask,
	 * this.currentProblem.weights) <= this.currentProblem.maxWeight ) {
	 * this.currentProblem.solution.cost = sumhelper(mask,
	 * this.currentProblem.costs); this.currentProblem.solution.precense =
	 * this.mask.clone(); }
	 * 
	 * }
	 * 
	 * }
	 */

	private void knapsackBF(int index, int state, int w, int c) {

		if (index >= 0) {
			this.mask[index] = state;
			if (state == 1) {
				w += this.currentProblem.weights[index];
				c += this.currentProblem.costs[index];
			}
			knapsackBF(index - 1, 0, w, c);
			knapsackBF(index - 1, 1, w, c);

		} else {
			// First I try to compare cost because it is more likely to fail
			// this assert
			if (c > this.currentProblem.solution.cost
					&& w <= this.currentProblem.maxWeight) {
				this.currentProblem.solution.cost = c;
				this.currentProblem.solution.precense = this.mask.clone();
			}
		}

	}

	int sumhelper(int mask[], int list[]) {
		int sum = 0;
		for (int i = 0; i < mask.length; i++) {
			if (mask[i] == 1)
				sum += list[i];
		}
		return sum;
	}

	/**
	 * @param args
	 */
	public static void main(String args[]) throws Exception {
		(new Knapsack()).run();

	}

}

class Solution {

}
