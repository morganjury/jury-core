package com.jury.core.sorting;

/**
 * Compare value at an index to all prior indexes, shifting left until value to the left is smaller than the subject.
 *
 * Best case O(n)
 * Average case O(n^2)
 * Worst case O(n^2)
 */
public class InsertionSort<T> extends Sort<T> implements Sorter<T> {

	public InsertionSort(Compare<T> comparator) {
		super(comparator);
	}

	public void array(T[] arr) {
		for (int i = 1; i < arr.length; i++) {
			T valueToSort = arr[i];
			int j;
			for (j = i; j > 0 && comparator.compare(arr[j-1], valueToSort); j--) {
				arr[j] = arr[j - 1];
			}
			arr[j] = valueToSort;
		}
	}

}
