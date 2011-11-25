/*
 * Dictionary.java
 *   - Reads the list of words from the file, and stores them in a Trie.
 *
 * CSC444H1F Assignment 1 - Scrabble Solver
 *
 * Date Created : 2011/10/02
 * Created By   : Jeff Lee
 *
 * Code Info:
 *   ~170 lines of code
 *   ~40 minutes to think about the design and research online
 *   ~100 minutes to code
 *   1 defects:
 *	 ??
 *
 */
package com.leejefon.uoft.csc444.dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Last Modified: 2011/10/16
 *
 * @author Jeff Lee
 */
public class Dictionary extends Trie {

	/**
	 *
	 */
	public Dictionary(String dictFile) {
		try {
			BufferedReader list = new BufferedReader(new FileReader(dictFile));
			String word;
			while ((word = list.readLine()) != null) {
				if (word.length() > 1) {
					this.insert(word);
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} catch (IOException ex) {

		}
	}

	/**
	 *
	 * @param letters  Available letters in the rack plus letters already on the board.
	 * @return
	 */
	public Collection<String> find(String letters, boolean doPerm) {

		Collection<String> list = new ArrayList<String>();
		boolean hasWildcard = false;

		letters = letters.toUpperCase();

		for (char letter : letters.toCharArray()) {
			if (letter == '*') {
				hasWildcard = true;
				break;
			}
		}

		if (doPerm == true) {
			for (String str : Helper.combination(letters)) {
				for (String str2 : Helper.permutation(str)) {
					if (hasWildcard == false) {
						if (this.search(str2) == true) {
							list.add(str2);
						}
					} else {
						if (this.searchWildcard(root, str2) == true) {
							list.add(str2);
						}
					}
				}
			}
		} else {
			if (hasWildcard == false) {
				if (this.search(letters) == true) {
					list.add(letters);
				}
			} else {
				if (this.searchWildcard(root, letters) == true) {
					list.add(letters);
				}
			}
		}

		return list;
	}

	/**
	 *
	 * @param letters  Available letters in the rack plus letters already on the board.
	 * @return
	 */
	public Collection<String> find(String[] letters) {

		Collection<String> list = new ArrayList<String>();
		boolean hasWildcard = false;

		for (String[] str : Helper.combination(letters)) {
			for (String str2 : Helper.permutation(str)) {
				for (char letter : str2.toCharArray()) {
					if (letter == '*') {
						hasWildcard = true;
						break;
					}
				}
				if (hasWildcard == false) {
					if (this.search(str2) == true) {
						list.add(str2);
					}
				} else {
					if (this.searchWildcard(root, str2) == true) {
						list.add(str2);
					}
				}
			}
		}

		return Helper.removeDuplicate(list);
	}

	/**
	 *
	 * @param regex
	 * @return
	 */
	private boolean searchWildcard(Node root, String str) {

		Node current = root;

		int i = 0;
		for (char c : str.toCharArray()) {
			if (c == '*') {
				for (Character key : current.children.keySet()) {
					if (searchWildcard(current.children.get(key), str.substring(i + 1)) == true) {
						return true;
					}
				}
				return false;
			}

			i++;

			if (current.children.containsKey(Character.valueOf(c))) {
				current = current.children.get(Character.valueOf(c));
			} else {
				return false;
			}
		}

		return current.isWord;
	}
}

/**
 * Helper class that does permutation and combination and other radom tasks such as remove duplicates in a collection
 *
 * @author Jeff Lee
 */
class Helper {

	public static Collection<String> permutation(String s) {
		Collection<String> list = new ArrayList<String>();
		permutation("", s, list);
		return list;
	}

	private static void permutation(String prefix, String s, Collection<String> list) {
		int N = s.length();
		if (N == 0) {
			//System.out.println(prefix);
			list.add(prefix);
		} else {
			for (int i = 0; i < N; i++) {
				permutation(prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, N), list);
			}
		}
	}

	public static Collection<String> permutation(String[] s) {
		Collection<String[]> list = new ArrayList<String[]>();
		permutation(null, s, list);
		Collection<String> result = convert(list);
		return result;
	}

	private static void permutation(String[] prefix, String[] s, Collection<String[]> list) {
		int N = s.length;
		if (N == 0) {
			list.add(prefix);
		} else {
			for (int i = 0; i < N; i++) {
				permutation(add(prefix, s[i]), merge(sub(s, 0, i), sub(s, i + 1, N)), list);
			}
		}
	}

	public static Collection<String> combination(String s) {
		Collection<String> list = new ArrayList<String>();
		combination("", s, list);
		return list;
	}

	private static void combination(String prefix, String s, Collection<String> list) {
		//System.out.println(prefix);
		list.add(prefix);
		for (int i = 0; i < s.length(); i++) {
			combination(prefix + s.charAt(i), s.substring(i + 1), list);
		}
	}

	public static Collection<String[]> combination(String[] s) {
		Collection<String[]> list = new ArrayList<String[]>();
		combination(null, s, list);
		return list;
	}

	private static void combination(String[] prefix, String[] s, Collection<String[]> list) {
		if (prefix != null) {
			list.add(prefix);
		}

		for (int i = 0; i < s.length; i++) {
			combination(add(prefix, s[i]), sub(s, i + 1, s.length), list);
		}
	}

	private static String[] add(String[] s, String str) {
		if (s == null) {
			String[] result = new String[1];
			result[0] = str;
			return result;
		}

		String[] result = new String[s.length + 1];
		int i = 0;

		for (i = 0; i < s.length; i++) {
			result[i] = s[i];
		}

		result[i] = str;

		return result;
	}

	private static String[] sub(String[] s, int from, int to) {
		String[] result = new String[to - from];
		for (int i = 0; i < to - from; i++) {
			result[i] = s[i + from];
		}
		return result;
	}

	private static String[] merge(String[]... arr) {
		int arrSize = 0;
		for (String[] array : arr) {
			arrSize += array.length;
		}
		String[] result = new String[arrSize];
		int j = 0;
		for (String[] array : arr) {
			for (String s : array) {
				result[j++] = s;
			}
		}
		return result;
	}

	private static Collection<String> convert(Collection<String[]> list) {
		Collection<String> result = new ArrayList<String>();

		for (String[] s : list) {
			String temp = "";
			for (String str : s) {
				temp += str;
			}
			result.add(temp.toUpperCase());
		}

		return result;
	}

	public static Collection<String> removeDuplicate(Collection<String> list) {
		Collection<String> result = new ArrayList<String>();

		for (String str : list) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}

		return result;
	}
}