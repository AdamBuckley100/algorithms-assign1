package models;

import imageprocessing.WeightedQuickUnion;

import org.junit.Test;

public class WeightedQuickUnionTest extends junit.framework.TestCase {

	WeightedQuickUnion weightQuickUnion = new WeightedQuickUnion(100*100);

	@Test
	public void testCount()
	{
		assertEquals(10000, weightQuickUnion.count());
	}

	@Test
	public void testSize()
	{
		assertEquals(weightQuickUnion.getSize(), 10000);
	}

	@Test
	public void testRoot()
	{
		assertEquals(8, weightQuickUnion.root(8));
	}

	@Test
	public void testConnected()
	{
		int inputOne = 6;
		int inputTwo = 9;
		int inputThree = 9;

		assertEquals(false, weightQuickUnion.connected(inputOne, inputTwo));
		assertEquals(true, weightQuickUnion.connected(inputTwo , inputThree));
	}

	@Test
	public void testUnion()
	{
		int inputOne = 3;
		int inputTwo = 7;

		weightQuickUnion.union(inputOne, inputTwo);
		assertEquals(9999, weightQuickUnion.count());	
	}
}