package com.jury.core.sorting;

public class Sort<T> {

	Sorter.Compare<T> comparator;

	public Sort(Sorter.Compare<T> comparator) {
		this.comparator = comparator;
	}

	public static <T> void array(T[] arr, Sorter.Algorithm algorithm, Sorter.Compare<T> comparator) {
		Sorter<T> sorter = getSort(algorithm, comparator);
		sorter.array(arr);
	}

	private static <T> Sorter<T> getSort(Sorter.Algorithm algorithm, Sorter.Compare<T> comparator) {
		switch (algorithm) {
			case BUBBLE:
				return new BubbleSorter<>(comparator);
			case HEAP:
				return new HeapSort<>(comparator);
			case INSERTION:
				return new InsertionSort<>(comparator);
			case MERGE:
				return new MergeSort<>(comparator);
			case QUICK:
				return new QuickSort<>(comparator);
			case SELECTION:
				return new SelectionSort<>(comparator);
			default:
				throw new IllegalArgumentException("");
		}
	}

	void swapIndexes(T[] arr, int i, int j) {
		T temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
