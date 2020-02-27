package com.jury.core.sorting;

/**
 * Generate a max heap (root node is largest value and child nodes are less than parent).
 * Swap root node with nth node in list (i.e. largest element is far right of list) and remove element from heap.
 *
 * Best case O(nlogn)
 * Average case O(nlogn)
 * Worst case O(nlogn)
 */
public class HeapSort<T> extends Sort<T> implements Sorter<T> {

	public HeapSort(Compare<T> comparator) {
		super(comparator);
	}

	public void array(T[] arr) {
		buildHeap(arr);
		int size = arr.length - 1;
		for (int i = size; i > 0; i--) {
			swapIndexes(arr, 0, i);
			size--;
			heapify(arr, 0, size);
		}
	}

	private void buildHeap(T[] arr) {
		for (int i = (arr.length-1)/2; i >= 0; i--) {
			heapify(arr, i, arr.length - 1);
		}
	}

	private void heapify(T[] arr, int i, int size) {
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		int max;
		if (left <= size && comparator.compare(arr[left], arr[i])) {
			max = left;
		} else {
			max = i;
		}
		if (right <= size && comparator.compare(arr[right], arr[max])) {
			max = right;
		}
		if (max != i) {
			swapIndexes(arr, i, max);
			heapify(arr, max, size);
		}
	}

}
