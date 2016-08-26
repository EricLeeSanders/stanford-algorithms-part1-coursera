package week1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Implementation of merge sort. Smallest to largest.
 * 
 * @author Eric
 *
 * @param <T>
 */
public class MergeSort<T extends Comparable<T>> {
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
		MergeSort<Integer> mergeSort = new MergeSort<Integer>();
		mergeSort.sort(A);
		for (Integer element : A) {
			System.out.print(element + ", ");
		}

	}

}
