package com.jury.core.sorting;

/**
 * Choose a pivot and divide into two sub lists, one list containing values lower than pivot and the other values higher.
 * Each iteration, pivot ends up at its correct index.
 * 4623918 pivot 3: 2134698 pivot 1 (left): 1234698 pivot 6 (right): 1234698 pivot 9 (right): 1234689
 *
 * Best case O(nlogn)
 * Average case O(nlogn)
 * Worst case O(n^2)
 */
public class QuickSort<T> extends Sort<T> implements Sorter<T> {

	public QuickSort(Compare<T> comparator) {
		super(comparator);
	}

	public void array(T[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		quickSort(arr, 0, arr.length - 1);
	}

	private void quickSort(T[] arr, int left, int right) {
		int i = left;
		int j = right;
		T pivot = arr[left + (right - left) / 2];
		while (i <= j) {
			while (comparator.compare(pivot, arr[i])) {
				i++;
			}
			while (comparator.compare(pivot, arr[j])) {
				j--;
			}
			// generic swap at index {x and y} method
			if (i <= j) {
				swapIndexes(arr, i, j);
				i++;
				j--;
			}
		}
		if (left < j) {
			quickSort(arr, left, j);
		}
		if (i < right) {
			quickSort(arr, i, right);
		}
	}

}
