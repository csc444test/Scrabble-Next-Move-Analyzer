/*
 * Trie.java
 *   - The data structure used to store the dictionary.
 *
 * CSC444H1F Assignment 1 - Scrabble Solver
 *
 * Date Created : 2011/10/03
 * Created By   : Jeff Lee
 *
 * Code Info:
 *   ~100 lines of code
 *   ~60 minutes to think about the design and research online
 *   ~30 minutes to code
 *   1 defects:
 *     ??
 *
 */
package com.leejefon.uoft.csc444.dictionary;

import java.util.HashMap;
import java.util.Map;

public class Trie {

	protected static class Node {

		/// indicates a complete word
		protected boolean isWord = false;

		// indicates how many words have the prefix
		protected int prefixes = 0;

		// references to all possible children
		protected Map<Character, Node> children = new HashMap<Character, Node>();
	}

	//
	protected Node root = new Node();

	/**
	 * Inserts a new word into the Trie
	 *
	 * @param word
	 */
	public void insert(String word) {
		if (search(word) == true) {
			return;
		}

		Node current = root;
		for (char c : word.toUpperCase().toCharArray()) {
			if (current.children.containsKey(Character.valueOf(c))) {
				Node child = current.children.get(Character.valueOf(c));
				child.prefixes++;
				current = child;
			} else {
				Node child = new Node();
				child.prefixes = 1;
				current.children.put(Character.valueOf(c), child);
				current = child;
			}
		}

		// reached the end of the word, hence mark it true
		// if a search reached the end of the search string and this flag is still false,
		// then the search string is not a valid word in the trie but is a prefix
		current.isWord = true;
	}

	/**
	 * Searches for a full word in the Trie
	 *
	 * @param word
	 * @return boolean - true if found; false if not found or a prefix
	 */
	public boolean search(String word) {
		Node current = root;
		for (char c : word.toUpperCase().toCharArray()) {
			if (current.children.containsKey(Character.valueOf(c))) {
				current = (Node) current.children.get(Character.valueOf(c));
			} else {
				return false;
			}
		}

		return current.isWord;
	}

	/**
	 * Deletes a word from the trie
	 *
	 * @param word
	 */
	public void delete(String word) {
		if (search(word) == false) {
			return;
		}

		Node current = root;
		for (char c : word.toUpperCase().toCharArray()) {
			Node child = (Node) current.children.get(Character.valueOf(c));
			if (child.prefixes == 1) {
				current.children.remove(Character.valueOf(c));
				return;
			} else {
				child.prefixes--;
				current = child;
			}
		}

		// the word is removed, set the flag to false
		current.isWord = false;
	}
}