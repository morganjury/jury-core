package com.jury.core.sorting;

/**
 * Starting with the left-most index, find the lowest value to the right and swap it with the current index value.
 *
 * Best case O(n^2)
 * Average case O(n^2)
 * Worst case O(n^2)
 */
public class SelectionSort<T> extends Sort<T> implements Sorter<T> {

	public SelectionSort(Compare<T> comparator) {
		super(comparator);
	}

	public void array(T[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int indexOfSmallestNumber = i;
			for (int j = i + 1; j < arr.length; j++) {
				// compare index {x to y}
				if (comparator.compare(arr[indexOfSmallestNumber], arr[j])) {
					indexOfSmallestNumber = j;
				}
				T smallestNumber = arr[indexOfSmallestNumber];
				arr[indexOfSmallestNumber] = arr[i];
				arr[i] = smallestNumber;
			}
		}
	}

}
