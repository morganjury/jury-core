package com.jury.core.sorting;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BubbleSorterTest {

	@Test
	public void intArray() {
		Integer[] arr = {5,3,4,8,9,6,1,2,7};
		Integer[] expected = {1,2,3,4,5,6,7,8,9};
		Sort.array(arr, Sorter.Algorithm.BUBBLE, Sorter.compareInt());
		for (int i = 1; i < 9; i++) {
			assertEquals(expected[i], arr[i]);
		}
	}

	@Test
	public void stringArray() {
		String[] arr = {"","","","","","","","","",""};
		String[] expected = {"","","","","","","","","",""};
		Sort.array(arr, Sorter.Algorithm.BUBBLE, Sorter.compareString());
		for (int i = 1; i < 10; i++) {
			assertEquals(expected[i], arr[i]);
		}
	}

}
