package com.example.demo;

import org.junit.Test;

import com.example.demo.emma.CrunchifyFibonacci;

public class CrunchifyFibonacciSeriesTest {
	int totalNumber = 5;

	@Test
	public void testCheckFibonacciRecursion() {
		CrunchifyFibonacci.fibonacciRecusion(totalNumber);
	}

	@Test
	public void testCheckFibonacciLoop() {
		CrunchifyFibonacci.fibonacciLoop(totalNumber);
	}
}
