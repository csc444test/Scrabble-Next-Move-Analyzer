/*
 * TestDictionary.java
 *   - Test the Dictionary.java file
 *
 * Date Created: 2011/10/10
 * Created By: Jeff Lee
 */
package com.leejefon.uoft.csc444.dictionary;

import java.util.Collection;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Last Modified: 2011/10/17
 *
 * @author Jeff Lee
 */
public class TestDictionary {

	@Test
	public void testSearchNoCombo() {
		Dictionary dic = new Dictionary("resources/dictionary.list");
		Collection<String> list = dic.find("animal", false);
		assertTrue(list.contains("ANIMAL"));
	}

	@Test
	public void testSearchWithCombo() {
		Dictionary dic = new Dictionary("resources/dictionary.list");
		Collection<String> list = dic.find("kcbfaeio", true);

		String[] result = { "back", "cake", "face", "ice", "be", "if", "of" };

		assertTrue(result.length == list.size());
	}

	@Test
	public void testSearchLetterGroup() {
		Dictionary dic = new Dictionary("resources/dictionary.list");
		String[] str = { "A", "I", "MA", "L", "N" };
		Collection<String> list = dic.find(str);

		assertTrue(list.contains("ANIMAL"));
		assertTrue(list.contains("NAIL"));
		assertTrue(list.contains("IN"));
		assertTrue(list.contains("MAN"));
	}

	@Test
	public void testSearchWildcardNoCombo() {
		Dictionary dic = new Dictionary("resources/dictionary.list");
		Collection<String> list = dic.find("ani*al", false);
		assertTrue(list.contains("ANI*AL")); // Result: ANIMAL
	}

	@Test
	public void testSearchWildcardCombo() {
		Dictionary dic = new Dictionary("resources/dictionary.list");
		Collection<String> list = dic.find("an*", true);
		int result = 5; // an* = (and, any, ant), *an = (fan, man), a* = (as, at), n* = (no), *n = (on, in)

		if (result != list.size()) {
			assert false;
		}
	}

	@Test
	public void testCombination() {
		Collection<String> comb = Helper.combination("ABC");

		assertTrue(comb.contains("A"));
		assertTrue(comb.contains("AB"));
		assertTrue(comb.contains("ABC"));
		assertTrue(comb.contains("B"));
		assertTrue(comb.contains("BC"));
		assertTrue(comb.contains("C"));

		// Test for another combination is too long..
	}

	@Test
	public void testPermutation() {
		Collection<String> perm = Helper.permutation("ABC");

		assertTrue(perm.contains("ABC"));
		assertTrue(perm.contains("ACB"));
		assertTrue(perm.contains("BAC"));
		assertTrue(perm.contains("BCA"));
		assertTrue(perm.contains("CAB"));
		assertTrue(perm.contains("CBA"));

		String[] str = { "A", "B", "C" };
		Collection<String> perm2 = Helper.permutation(str);

		assertTrue(perm2.contains("ABC"));
		assertTrue(perm2.contains("ACB"));
		assertTrue(perm2.contains("BAC"));
		assertTrue(perm2.contains("BCA"));
		assertTrue(perm2.contains("CAB"));
		assertTrue(perm2.contains("CBA"));
	}
}