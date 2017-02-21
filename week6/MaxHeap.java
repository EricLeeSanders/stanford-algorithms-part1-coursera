package week6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Maximum heap data structure. Heap can be built in linear time. Uses a hashmap
 * for constant time look up. Does not support duplicate items.
 *
 * @author Eric
 *
 * @param <T>
 */
public class MaxHeap<T extends Comparable<T>> {
	private T[] heap;
	private Map<T, Integer> heapMap;
	private int last = 0;

	public MaxHeap() {
		this(16);
	}

	@SuppressWarnings("unchecked")
	public MaxHeap(int capacity) {
		heap = (T[]) new Comparable[capacity + 1];
		heapMap = new HashMap<T, Integer>(capacity + 1);
	}

	@SuppressWarnings("unchecked")
	public MaxHeap(T[] h) {
		heap = (T[]) new Comparable[h.length + 1];
		for(int i = 0; i < h.length; i++){
			heap[i+1] = h[i];
		}
		heapMap = new HashMap<T, Integer>(h.length + 1);
		last = h.length;
		buildHeap();
	}
	
	/**
	 * Builds the heap - O(n)
	 */
	private void buildHeap() {
		for (int i = 1; i < heap.length; i++) {
			heapMap.put(heap[i], i);
		}
		int n = heap.length;
		for (int i = n / 2; i > 0; i--) {
			bubbleDown(i);
		}
	}

	private int parent(int pos) {
		return pos / 2;
	}

	private int leftChild(int pos) {
		return 2 * pos;
	}

	private int rightChild(int pos) {
		return leftChild(pos) + 1;
	}

	private void swap(int x, int y) {
		T tmp = heap[x];
		heap[x] = heap[y];
		heap[y] = tmp;
		heapMap.put(heap[x], x);
		heapMap.put(heap[y], y);
	}

    /**
     * Insert into the heap.
     * Duplicate items cannot be inserted.
     * - O(log n)
     * 
     *
     * @param t
     */
	public void insert(T t) {
	        return;
	    }
		if (isFull()) {
			resize();
		}
		heap[++last] = t;
		heapMap.put(t, last);
		bubbleUp(last);
	}

	/**
	 * Removes and returns the top element - O(log n)
	 *
	 * @return
	 */
	public T poll() {
		if (isEmpty()) {
			return null;
		}
		heapMap.remove(heap[1]);
		T rootcopy = heap[1];
		heap[1] = heap[last--];
		heapMap.put(heap[1], 1);
		bubbleDown(1);

		return rootcopy;
	}

	/**
	 * Returns the top element but does not remove it O(1)
	 *
	 * @return
	 */
	public T peak() {
		if (isEmpty()) {
			return null;
		}
		return heap[1];
	}

	/**
	 * Moves the node up the heap until it reaches a parent that is greater than
	 * its value - O(log n)
	 *
	 * @param pos
	 */
	private void bubbleUp(int pos) {
		// at the root
		if (pos == 1) {
			return;
		}
		if (heap[parent(pos)].compareTo(heap[pos]) >= 0) {
			return;
		}
		swap(pos, parent(pos));
		bubbleUp(parent(pos));
	}

	/**
	 * Moves the node down the heap until it reaches children that are less than
	 * its value - O(log n)
	 *
	 * @param pos
	 */
	private void bubbleDown(int pos) {
		int numKids = countKids(pos);
		if (numKids == 0) {
			return;
		}

		int leftChildPos = leftChild(pos);
		int largeChildPos = leftChildPos;
		// right child is larger
		if (numKids > 1) {
			int rightChildPos = rightChild(pos);
			if (heap[rightChildPos].compareTo(heap[leftChildPos]) > 0) {
				largeChildPos = rightChildPos;
			}
		}
		if (heap[largeChildPos].compareTo(heap[pos]) > 0) {
			swap(largeChildPos, pos);
			bubbleDown(largeChildPos);
		}
	}

	/**
	 * Reevaluates a node and moves it up the down (in the case of a max-heap).
	 * This is typically used after updating a node with a value that decreases
	 * its priority. - O(log n)
	 *
	 * @param t
	 */
	public void decreaseKey(T t) {
		if (!contains(t)) {
			return;
		}
		int pos = heapMap.get(t);
		bubbleDown(pos);
	}

	/**
	 * Reevaluates a node and moves it up the heap (in the case of a max-heap).
	 * This is typically used after updating a node with a value that increases
	 * its priority. - O(log n)
	 *
	 * @param t
	 */
	public void increaseKey(T t) {
		if (!contains(t)) {
			return;
		}
		int pos = heapMap.get(t);
		bubbleUp(pos);
	}

	private int countKids(int pos) {
		if (last < leftChild(pos)) {
			return 0;
		} else if (last == leftChild(pos)) {
			return 1;
		}
		return 2;
	}

	private void resize() {
		heap = Arrays.copyOf(heap, heap.length * 2);
	}

	public boolean isFull() {
		return ((last + 1) >= heap.length);
	}

	public boolean isEmpty() {
		return (last <= 0);
	}

	public boolean contains(T t) {
		return heapMap.containsKey(t);
	}

	public int getSize() {
		return last;
	}

	public static void main(String[] args) {
		Integer[] arr = new Integer[10000];
		Random rand = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = rand.nextInt(10000) + 1;
			
		}
		MaxHeap<Integer> heap = new MaxHeap<Integer>(arr);
		List<Integer> sortedList = new ArrayList<Integer>(arr.length);
		while (!heap.isEmpty()) {
			sortedList.add(heap.poll());
		}
		for (int i = 0; i < sortedList.size() - 1; i++) {
			if (sortedList.get(i) < sortedList.get(i + 1)) {
				System.out.println("Failed: " + sortedList.get(i) + " - " + sortedList.get(i + 1));
			}
		}
	}
}
