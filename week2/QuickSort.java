package week2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Implementation of quick sort using the first element of the partition as the
 * pivot. Smallest to largest.
 * 
 * @author Eric
 *
 * @param <T>
 */
public class QuickSort<T extends Comparable<T>> {
	public void sort(T[] A) {
		sort(A, 0, A.length - 1);
	}

	public void sort(T[] A, int start, int stop) {
		if (start >= stop) {
			return;
		}

		int pivotPos = partition(A, start, stop);
		sort(A, start, pivotPos - 1);
		sort(A, pivotPos + 1, stop);
	}

	// Partitions the array using the first element
	private int partition(T[] A, int start, int stop) {
		T pivot = A[start];
		int i = start + 1;
		for (int j = i; j <= stop; j++) {
			if (A[j].compareTo(pivot) < 0) {
				swap(A, j, i);
				i++;
			}
		}
		swap(A, start, i - 1);
		return i - 1;
	}

	private void swap(T[] A, int i, int j) {
		T temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/week2/input.txt"));
		Integer[] A = new Integer[10000];
		int j = 0;
		while (scanner.hasNextInt()) {
			A[j++] = scanner.nextInt();
		}
		scanner.close();
		QuickSort<Integer> qs = new QuickSort<Integer>();
		KnuthShuffle<Integer> knuthShuffle = new KnuthShuffle<Integer>();
		knuthShuffle.shuffle(A);
		qs.sort(A, 0, A.length - 1);
		for (Integer element : A) {
			System.out.println(element);
		}

	}
}
