package week6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import week5.MinHeap;

/**
 * Computes the median of a running list of integers. Uses two heaps, a max heap
 * and a min heap.
 * 
 * @author Eric
 *
 */
public class HeapMedian {
	//max heap for lower values, min heap for higher values
	private final MaxHeap<Integer> maxHeap = new MaxHeap<Integer>(); 
	private final MinHeap<Integer> minHeap = new MinHeap<Integer>(); 

	/**
	 * Inserts an Integer into either the max heap or the min heap.
	 * 
	 * @param val
	 */
	public void insert(Integer val) {
		// If one of the heaps is empty, put the value in the empty heap
		if (this.minHeap.isEmpty()) {
			this.minHeap.insert(val);
		} else if (this.maxHeap.isEmpty()) {
			this.maxHeap.insert(val);
		}
		// find which heap to put the value into.
		if (val.compareTo(this.minHeap.peak()) < 0) {
			this.maxHeap.insert(val);
		} else {
			this.minHeap.insert(val);
		}
		int maxSize = this.maxHeap.getSize();
		int minSize = this.minHeap.getSize();
		// Determine if the heaps need to be resized
		if (Math.abs(maxSize - minSize) >= 2) {
			resizeHeaps(maxSize, minSize);
		}
	}

	/**
	 * Resizes the heaps. The size difference between the two heaps should never
	 * be greater than 1.
	 * 
	 * @param maxSize
	 * @param minSize
	 */
	private void resizeHeaps(int maxSize, int minSize) {
		if (maxSize > minSize) {
			Integer maxValue = this.maxHeap.poll();
			this.minHeap.insert(maxValue);
		} else {
			Integer minValue = this.minHeap.poll();
			this.maxHeap.insert(minValue);
		}
	}

	/**
	 * Finds the median
	 * 
	 * @return
	 */
	public int findMedian() {
		int maxSize = this.maxHeap.getSize();
		int minSize = this.minHeap.getSize();
		if ((maxSize - minSize) == 0) {
			return this.maxHeap.peak();
		} else if (maxSize > minSize) {
			return this.maxHeap.peak();
		} else {
			return this.minHeap.peak();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/week6/input-median.txt"));
		HeapMedian hm = new HeapMedian();
		long medianTotal = 0;
		while (scanner.hasNextInt()) {
			hm.insert(scanner.nextInt());
			int median = hm.findMedian();
			medianTotal += median;
		}		
		scanner.close();
		System.out.println(medianTotal % 10000);

	}
}
