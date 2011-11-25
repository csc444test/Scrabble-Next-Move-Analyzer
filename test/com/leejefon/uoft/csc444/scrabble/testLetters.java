/*
 * testLetters.java
 *
 * Date Created: 2010/10/02
 */
package com.leejefon.uoft.csc444.scrabble;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Last Modified: 2011/10/16
 *
 * @author Jeff
 */
public class testLetters {

	@Test
	public void testGetPoints() {
		assertEquals(1, (int) Letters.POINT.get('A'));
		assertEquals(2, (int) Letters.POINT.get('D'));
		assertEquals(3, (int) Letters.POINT.get('C'));
		assertEquals(4, (int) Letters.POINT.get('Y'));
		assertEquals(5, (int) Letters.POINT.get('K'));
		assertEquals(8, (int) Letters.POINT.get('J'));
		assertEquals(10, (int) Letters.POINT.get('Z'));
	}

	@Test
	public void testGetOccurrence() {
		assertEquals(1, (int) Letters.OCCURRENCE.get('Z'));
		assertEquals(2, (int) Letters.OCCURRENCE.get('F'));
		assertEquals(3, (int) Letters.OCCURRENCE.get('G'));
		assertEquals(4, (int) Letters.OCCURRENCE.get('U'));
		assertEquals(6, (int) Letters.OCCURRENCE.get('T'));
		assertEquals(8, (int) Letters.OCCURRENCE.get('O'));
		assertEquals(9, (int) Letters.OCCURRENCE.get('I'));
		assertEquals(12, (int) Letters.OCCURRENCE.get('E'));
	}
}
