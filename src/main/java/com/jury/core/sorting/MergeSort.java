package com.jury.core.sorting;

/**
 * Break list into sublists of ~1/2 size of parent until lists have 1 element.
 * Merge sublists in sorted order until 1 list remains.
 *
 * Best case O(nlogn)
 * Average case O(nlogn)
 * Worst case O(nlogn)
 */
public class MergeSort<T> extends Sort<T> implements Sorter<T> {

	public MergeSort(Compare<T> comparator) {
		super(comparator);
	}

	public void array(T[] arr) {
		sort(arr, 0, arr.length - 1);
	}

	public void sort(T[] arr, int start, int end) {
		int mid = (start + end) / 2;
		if (start < end) {
			sort(arr, start, mid);
			sort(arr, mid + 1, end);
			merge(arr, start, mid, end);
		}
	}

	private void merge(T[] arr, int start, int mid, int end) {
		Object[] tempArr = new Object[arr.length];
		int tempArrIndex = start;
		int startIndex = start;
		int midIndex = mid + 1;
		while (startIndex <= mid && midIndex <= end) {
			if (comparator.compare(arr[startIndex], arr[midIndex])) {
				tempArr[tempArrIndex++] = arr[startIndex++];
			} else {
				tempArr[tempArrIndex++] = arr[midIndex++];
			}
		}
		while (startIndex <= mid) {
			tempArr[tempArrIndex++] = arr[startIndex++];
		}
		while (midIndex <= end) {
			tempArr[tempArrIndex++] = arr[midIndex++];
		}
		if (end + 1 - start >= 0) System.arraycopy(tempArr, start, arr, start, end + 1 - start);
	}

}
