/*
 * TestScrabbleBoard.java
 *   - Tests for testing ScrabbleBoard class
 *
 */
package com.leejefon.uoft.csc444.scrabble;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 *
 * @author Jeff
 */
public class TestScrabbleBoard {

	/**
	 * Example from Scrabble Official Website (http://www.hasbro.com/scrabble/en_US/scoringRules.cfm)
	 */
	@Test
	public void testCalcPoints() {
		ScrabbleBoard sb = new ScrabbleBoard("resources/board2.map", "resources/play2.map");

		assertEquals(16, sb.calcPoints(4, 5, false, "mob"));
		assertEquals(16, sb.calcPoints(1, 7, false, "bit"));
	}

	@Test
	public void testGetRanges() {
		ScrabbleBoard sb = new ScrabbleBoard("resources/board2.map", "resources/play2.map");

		assertEquals(1, sb.getRowRange()[0]);
		assertEquals(7, sb.getRowRange()[1]);
		assertEquals(1, sb.getColRange()[0]);
		assertEquals(7, sb.getColRange()[1]);
	}

	@Test
	public void testGetLetters() {
		ScrabbleBoard sb = new ScrabbleBoard("resources/board2.map", "resources/play2.map");
		String result = "";

		for (String s : sb.getRowLetters(6)) {
			result += s;
		}
		assertEquals("PASTE", result);

		result = "";
		for (String s : sb.getColLetters(4)) {
			result += s;
		}
		assertEquals("FARMS", result);
	}
}
