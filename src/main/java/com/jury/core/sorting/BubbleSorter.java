package com.jury.core.sorting;

/**
 * Iterates first index to last index, comparing adjacent elements and swapping them.
 *
 * Best case O(n)
 * Average case O(n^2)
 * Worst case O(n^2)
 */
public class BubbleSorter<T> extends Sort<T> implements Sorter<T> {

	public BubbleSorter(Compare<T> comparator) {
		super(comparator);
	}

	public void array(T[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				if (comparator.compare(arr[j], arr[j+1])) {
					swapIndexes(arr, j, j + 1);
				}
			}
		}
	}

}
