package week1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Computes the number of inversions in an array of comparable elements.
 * 
 * @author Eric
 *
 * @param <T>
 */
public class InversionCount<T extends Comparable<T>> {
	public static long inversionCount = 0;

	public void sort(T[] A) {
		@SuppressWarnings("unchecked")
		T[] tempArray = (T[]) new Comparable[A.length];
		mergeSort_rec(A, tempArray, 0, A.length - 1);
	}

	private void mergeSort_rec(T[] A, T[] tempArray, int low, int high) {
		if (high <= low) {
			return;
		}

		int mid = low + (high - low) / 2; // find the mid point
		mergeSort_rec(A, tempArray, low, mid); // recurse left half
		mergeSort_rec(A, tempArray, mid + 1, high); // recurse right half
		merge(A, tempArray, low, mid, high);
	}

	private void merge(T[] A, T[] tempArray, int low, int mid, int high) {
		// copy
		for (int k = low; k <= high; k++) {
			tempArray[k] = A[k];
		}
		// merge
		int i = low;
		int j = mid + 1;
		for (int k = low; k <= high; k++) {
			if (i > mid) {
				A[k] = tempArray[j++];
			} else if (j > high) {
				A[k] = tempArray[i++];
			} else if ((tempArray[i].compareTo(tempArray[j]) < 0)) {
				A[k] = tempArray[i++];
			} else {
				inversionCount += (mid + 1 - i); // get the number of inversions
				A[k] = tempArray[j++];
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		Scanner input = new Scanner(new File("src/week1/input.txt"));
		Integer[] A = new Integer[100000];
		for (int i = 0; i < A.length; i++) {
			A[i] = input.nextInt();
		}

		input.close();
		InversionCount<Integer> ic = new InversionCount<Integer>();
		ic.sort(A);
		System.out.println("Inversion count: " + InversionCount.inversionCount);

	}

}
