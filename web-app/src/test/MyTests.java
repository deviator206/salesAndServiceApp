package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyTests {

	@Test
	public void multiplicationOfNumbers(){
		MyTestableClass mc = new MyTestableClass();
		assertEquals("10 x 0 must be 0",0,mc.multiply(10,0));
	}
}
