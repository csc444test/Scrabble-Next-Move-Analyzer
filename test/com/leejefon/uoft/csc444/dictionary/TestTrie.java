/*
 * TrieTest.java
 *   - Test basic Trie data structure operations.
 *
 * CSC444H1F Assignment 1 - Scrabble Solver
 *
 * Date Created : 2011/10/03
 * Created By   : Jeff Lee
 *
 * Code Info:
 *   ~60 lines of code
 *   ~3 minutes to code
 *   1 defects:
 *     Test will success, need to check output, which is a very bad way.  Need to fix that
 *
 */
package com.leejefon.uoft.csc444.dictionary;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author Jeff
 */
public class TestTrie {

	@Test
	public void testInsert() {
		Trie trie = new Trie();

		trie.insert("ball");
		trie.insert("balls");
		trie.insert("bat");
		trie.insert("doll");
	}

	@Test
	public void testDelete() {
		Trie trie = new Trie();

		trie.insert("ball");
		assertTrue(trie.search("ball"));
		trie.delete("ball");
		assertFalse(trie.search("ball"));
	}

	@Test
	public void testSearch() {
		Trie trie = new Trie();

		assertFalse(trie.search("dumb"));
		trie.insert("dumb");
		assertTrue(trie.search("dumb"));
	}
}
