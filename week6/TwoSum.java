package week6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import week2.QuickSort;

/**
 * Computes the number of integers that satisfy the two sum problem x + y = t.
 * This problem is typically solved using a hashmap or similar data structure.
 * However, this algorithm does not because T is not an integer. T is a range of
 * Integers. 
 * 
 * @author Eric
 *
 */

public class TwoSum {
	// The upper and lower limits for the target
	private static final int T_UPPER = 10000;
	private static final int T_LOWER = -10000;

	public TwoSum(Long[] A) {
		QuickSort<Long> qs = new QuickSort<Long>();
		qs.sort(A, 0, A.length - 1);
		Set<Long> sums = new HashSet<Long>();
		int start = 0;
		int end = A.length - 1;
		int upperMax = end;
		int count = 0;
		while (A[start] <= T_LOWER) {
			if (A[end] <= T_UPPER) {
				end++;
				start++;
				continue;
			}
			long result = A[start] + A[end];
			// The input never has an integer between -10000 and 10000
			// so we don't account for it.
			// If the result is within the range, then it should be counted.
			if (result >= T_LOWER && result <= T_UPPER) {
				if (!sums.contains(result)) {
					count++;
					sums.add(result);
				}
				end--;
			} else if (result < T_LOWER) {
				start++;
				end = upperMax; // Reset back to the highest possibility for a
								// match
			} else if (result > T_UPPER) {
				end--;
				upperMax = end;
			}
		}
		System.out.println(count);
	}

	public static Long[] readInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/week6/input-twosum.txt"));
		Long[] A = new Long[1000000];
		int i = 0;
		while (scanner.hasNextLong()) {
			A[i++] = scanner.nextLong();
		}
		scanner.close();
		return A;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Long[] A = readInput();

		new TwoSum(A);
	}

}
