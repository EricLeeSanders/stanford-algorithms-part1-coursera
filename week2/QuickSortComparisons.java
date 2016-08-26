package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Computes the number of comparisons quick sort performs.
 * @author Eric
 *
 */
public class QuickSortComparisons {
	private final QuickSort<Integer> qs = new QuickSort<Integer>();
	private static long comparisons = 0;

	public void sort(Integer[] A, int start, int stop) {
		if (start >= stop) {
			return;
		}

		int pivotPos = partition(A, start, stop);
		sort(A, start, pivotPos - 1);
		sort(A, pivotPos + 1, stop);
	}

	private int partition(Integer[] A, int start, int stop) {
		comparisons += stop - start;

		/*
		 * Case 1 Pivot is always the first element
		 */

		// Integer pivot = A[start];

		/*
		 * Case 2 Pivot is always the second element
		 */

		// Integer pivot = A[stop];
		// swap(A, start, stop);

		/*
		 * Case 3 Pivot is the median of the first, middle and last element
		 */

		int median = 0;
		if (((stop - start + 1) % 2) == 0) {
			median = start + ((stop - start + 1) / 2 - 1);
		} else {
			median = start + ((stop - start) / 2);
		}
		Integer[] pivotsChoices = new Integer[] { A[start], A[median], A[stop] };
		Integer pivot = chooseMedianPivot(pivotsChoices);
		int pivotPos = 0;
		if (pivot.compareTo(A[start]) == 0) {
			pivotPos = start;
		} else if (pivot.compareTo(A[stop]) == 0) {
			pivotPos = stop;
		} else {
			pivotPos = median;
		}
		swap(A, start, pivotPos);

		// **********************************************
		// *************End Pivot Choices****************
		// **********************************************

		int i = start + 1;
		for (int j = start + 1; j <= stop; j++) {
			if (A[j].compareTo(pivot) < 0) {
				swap(A, j, i);
				i++;
			}
		}
		swap(A, start, i - 1);
		return i - 1;
	}

	/**
	 * Finds the median between an array of possible pivots. The assignment asks
	 * for three, but this method will work for any number of choices.
	 * 
	 * @param pivotChoices
	 *            - possible choices for the pivot
	 * @return Integer - the median pivot
	 */
	private Integer chooseMedianPivot(Integer[] pivotChoices) {
		this.qs.sort(pivotChoices);
		if ((pivotChoices.length % 2) == 0) {
			return (pivotChoices[pivotChoices.length / 2 - 1]);
		} else {
			return (pivotChoices[pivotChoices.length / 2]);
		}
	}

	private void swap(Integer[] A, int i, int j) {
		Integer temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/week2/input.txt"));

		Integer[] A = new Integer[10000];
		int j = 0;
		while (scanner.hasNextInt()) {
			A[j] = scanner.nextInt();
			j++;
		}
		scanner.close();
		QuickSortComparisons qs = new QuickSortComparisons();
		qs.sort(A, 0, A.length - 1);
		System.out.println("Number of comparisons: " + comparisons);
	}
}
