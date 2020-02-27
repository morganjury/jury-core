package com.jury.core.sorting;

public interface Sorter<T> {

	void array(T[] arr);

	enum Algorithm {
		BUBBLE, HEAP, INSERTION, MERGE, QUICK, SELECTION
	}

	interface Compare<T> {
		boolean compare(T x, T y);
	}

	static Compare<Integer> compareInt() {
		return (x,y) -> x > y;
	}

	static Compare<String> compareString() {
		return (x,y) -> x.compareTo(y) > 0;
	}

}
