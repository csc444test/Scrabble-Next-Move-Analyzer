/*
 * TestScrabble.java
 *   - Test the main function Scrabble (getBestMove)
 *
 * Date Created: 2011/10/16
 * Created by: Jeff Lee
 */
package com.leejefon.uoft.csc444.scrabble;

import com.leejefon.uoft.csc444.scrabble.Scrabble.Result;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Last Modified: 2011/10/17
 *
 * @author Jeff Lee
 */
public class TestScrabble {

	@Test
	public void testGetBestMove1() {
		Scrabble scrabble = new Scrabble("resources/board.map", "resources/play.map");
		Result bestResult = scrabble.getBestMove("NI*SA");
		assertEquals("PAIN*", bestResult.WORD); // Result: PAINT
	}

	@Test
	public void testGetBestMove2() {
		Scrabble scrabble = new Scrabble("resources/board.map", "resources/play.map");
		Result bestResult = scrabble.getBestMove("AEIOUC");
		assertEquals("VOICE", bestResult.WORD);
	}

	@Test
	public void testGetBestMove3() {
		Scrabble scrabble = new Scrabble("resources/board.map", "resources/play.map");
		Result bestResult = scrabble.getBestMove("ZQWYMI");
		assertEquals("SWIM", bestResult.WORD);
	}
}
