/*
 * Letters.java
 *   - Has the Points and Occurrences of each letter stored in HashMaps.
 *
 * CSC444H1F Assignment 1 - Scrabble Solver
 *
 * Date Created : 2011/10/02
 * Created By   : Jeff Lee
 *
 * Code Info:
 *   ~100 lines of code
 *   ~10 minutes to think about the design
 *   ~20 minutes to code
 *   1 defects:
 *     The way that the points and occurrence are stored in an array with its index as value is not expandable if
 *     the value is large.
 *
 */
package com.leejefon.uoft.csc444.scrabble;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Last Modified: 2011/10/17
 *
 * @author Jeff
 */
public class Letters {

	// Stored all the points of each letter
	public static final Map<Character, Integer> POINT;

	// Stored all the occurrences of each letter
	public static final Map<Character, Integer> OCCURRENCE;

	// The number of points is the array index, it is to be loaded to the POINT HashMap
	private static final char[][] POINTS_ARRAY = {
		{'*'},                                               // Blank/Wildcard worth no points
		{'E', 'A', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U'},  // 1 point letters
		{'D', 'G'},                                          // 2 points letters
		{'B', 'C', 'M', 'P'},                                // 3 points letters
		{'F', 'H', 'V', 'W', 'Y'},                           // 4 points letters
		{'K'},                                               // 5 points letter
		null,                                                // no letters worth 6 points
		null,                                                // no letters worth 7 points
		{'J', 'X'},                                          // 8 points letters
		null,                                                // no letters worth 9 points
		{'Q', 'Z'}                                           // 10 points letters
	};

	// Similar logic to the POINTS_ARRAY, the number of occurrence is the index of the array,
	// and will be loaded to the OCCURRENCE HashMap
	private static final char[][] OCCURRENCE_ARRAY = {
		null,
		{'Q', 'Z', 'J', 'X', 'K'},
		{'F', 'H', 'V', 'W', 'Y', 'B', 'C', 'M', 'P'},
		{'G'},
		{'D', 'U', 'S', 'L'},
		null,
		{'T', 'R', 'N'},
		null,
		{'O'},
		{'I', 'A'},
		null,
		null,
		{'E'}
	};

	// Load the values in array to the Map
	static {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < POINTS_ARRAY.length; i++) {
			char[] letters = POINTS_ARRAY[i];

			if (letters != null) {
				for (char letter : letters) {
					map.put(letter, i);
				}
			}
		}
		POINT = Collections.unmodifiableMap(map);
	}

	// Load the values in array to the Map
	static {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < OCCURRENCE_ARRAY.length; i++) {
			char[] letters = OCCURRENCE_ARRAY[i];

			if (letters != null) {
				for (char letter : letters) {
					map.put(letter, i);
				}
			}
		}
		OCCURRENCE = Collections.unmodifiableMap(map);
	}
}