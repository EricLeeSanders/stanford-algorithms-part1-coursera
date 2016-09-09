package week5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Minimum heap data structure. Heap can be built in linear time. Uses a hashmap
 * for constant time look up.
 *
 * @author Eric
 *
 * @param <T>
 */
public class MinHeap<T extends Comparable<T>> {
	private T[] heap;
	private Map<T, Integer> heapMap;
	private int last = 0;

	public MinHeap() {
		this(16);
	}

	@SuppressWarnings("unchecked")
	public MinHeap(int capacity) {
		this.heap = (T[]) new Comparable[capacity + 1];
		this.heapMap = new HashMap<T, Integer>(capacity + 1);
	}

	@SuppressWarnings("unchecked")
	public MinHeap(T[] h) {
		this.heap = (T[]) new Comparable[h.length + 1];
		for(int i = 0; i < h.length; i++){
			heap[i+1] = h[i];
		}
		this.heapMap = new HashMap<T, Integer>(h.length + 1);
		this.last = h.length;
		buildHeap();
	}

	/**
	 * Builds the heap - O(n)
	 */
	private void buildHeap() {
		for (int i = 1; i < this.heap.length; i++) {
			this.heapMap.put(this.heap[i], i);
		}
		int n = this.heap.length;
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
		T tmp = this.heap[x];
		this.heap[x] = this.heap[y];
		this.heap[y] = tmp;
		this.heapMap.put(this.heap[x], x);
		this.heapMap.put(this.heap[y], y);
	}

	/**
	 * Insert into the heap - O(log n)
	 *
	 * @param t
	 */
	public void insert(T t) {
		if (contains(t)) {
			return;
		}
		if (isFull()) {
			resize();
		}
		this.heap[++this.last] = t;
		this.heapMap.put(t, this.last);
		bubbleUp(this.last);
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
		this.heapMap.remove(this.heap[1]);
		T rootcopy = this.heap[1];
		this.heap[1] = this.heap[this.last--];
		this.heapMap.put(this.heap[1], 1);
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
		return this.heap[1];
	}

	/**
	 * Moves the node up the heap until it reaches a parent that is less than
	 * its value - O(log n)
	 *
	 * @param pos
	 */
	private void bubbleUp(int pos) {
		// at the root
		if (pos == 1) {
			return;
		}
		if (this.heap[parent(pos)].compareTo(this.heap[pos]) <= 0) {
			return;
		}
		swap(pos, parent(pos));
		bubbleUp(parent(pos));
	}

	/**
	 * Moves the node down the heap until it reaches children that are greater
	 * than its value - O(log n)
	 *
	 * @param pos
	 */
	private void bubbleDown(int pos) {
		int numKids = countKids(pos);
		if (numKids == 0) {
			return;
		}

		int leftChildPos = leftChild(pos);
		int smallChildPos = leftChildPos;
		// right child is smaller
		if (numKids > 1) {
			int rightChildPos = rightChild(pos);
			if (this.heap[rightChildPos].compareTo(this.heap[leftChildPos]) < 0) {
				smallChildPos = rightChildPos;
			}
		}
		if (this.heap[smallChildPos].compareTo(this.heap[pos]) < 0) {
			swap(smallChildPos, pos);
			bubbleDown(smallChildPos);
		}
	}

	/**
	 * Reevaluates a node and moves it up the heap (in the case of a min-heap).
	 * This is typically used after updating a node with a value that increases
	 * its priority. - O(log n)
	 *
	 * @param t
	 */
	public void decreaseKey(T t) {
		if (!contains(t)) {
			return;
		}
		int pos = this.heapMap.get(t);
		bubbleUp(pos);
	}

	/**
	 * Reevaluates a node and moves it down the heap (in the case of a
	 * min-heap). This is typically used after updating a node with a value that
	 * decreases its priority. - O(log n)
	 *
	 * @param t
	 */
	public void increaseKey(T t) {
		if (!contains(t)) {
			return;
		}
		int pos = this.heapMap.get(t);
		bubbleDown(pos);
	}

	private int countKids(int pos) {
		if (this.last < leftChild(pos)) {
			return 0;
		} else if (this.last == leftChild(pos)) {
			return 1;
		}
		return 2;
	}

	private void resize() {
		this.heap = Arrays.copyOf(this.heap, this.heap.length * 2);
	}

	public boolean isFull() {
		return ((this.last + 1) >= this.heap.length);
	}

	public boolean isEmpty() {
		return (this.last <= 0);
	}

	public boolean contains(T t) {
		return this.heapMap.containsKey(t);
	}

	public int getSize() {
		return this.last;
	}
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[10000];
		Random rand = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = rand.nextInt(10000) + 1;
			
		}
		MinHeap<Integer> heap = new MinHeap<Integer>(arr);
		List<Integer> sortedList = new ArrayList<Integer>(arr.length);
		while (!heap.isEmpty()) {
			sortedList.add(heap.poll());
		}
		for (int i = 0; i < sortedList.size() - 1; i++) {
			if (sortedList.get(i) > sortedList.get(i + 1)) {
				System.out.println("Failed: " + sortedList.get(i) + " - " + sortedList.get(i + 1));
			}
		}

	}
}
