package week2;

/**
 * Shuffles an array using Knuth's algorithm
 * 
 * @author Eric
 *
 * @param <E>
 */
public class KnuthShuffle<E> {
	public void shuffle(E[] A) {
		int n = A.length;
		for (int i = 0; i < n; i++) {
			int rand = i + (int) (Math.random() * (n - i)); // between i and n-1
			swap(A, i, rand);
		}
	}

	private void swap(E[] A, int i, int j) {
		E tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}
}
