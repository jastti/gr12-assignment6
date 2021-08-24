package WordFrequency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class frequency{
	static HashMap<String, Integer> allWords = new HashMap<String, Integer>();
	static ArrayList<Map.Entry<String, Integer>> sortedwords = new ArrayList<Map.Entry<String, Integer>>
	(allWords.entrySet());
	final static int BUFFER_SIZE = 0x003000;
	// Create HashMap and ArrayList of it to save the words to be read, and initialize the buffer size
	static String[] arrCriteria = { "isn't", "aren't", "doesn't", "don't", "didn't", "haven't", "hadn't", "hasn't",
			"can't", "couldn't", "wasn't", "weren't", "wouldn't", "she's", "he's", "they're", "we're", "you're", "i'm",
			"it's" };
	// Create an Array of the special words
	

	public static void readFile() throws IOException {
		long start = System.currentTimeMillis();
		// count the time-use from beginning
		BufferedReader in = new BufferedReader(new FileReader("moby.TXT"));

		char[] buff = new char[BUFFER_SIZE];
		int len = 0;
		String s1 = null;
		while ((s1 = in.readLine()) != null) {
			getWordCount(s1.toLowerCase());
			// read the file line by line and convert to lower case
		}
		in.close();

		long end = System.currentTimeMillis();
		System.out.println("It takes " + (end - start) + "ms to read");
	}
	
	public static void getWordCount(String line1) {
		final String[] words = line1.split("[\\p{Punct}|\\s&&[^']&&[^-]]+");
		// Once there is any punctuation marks(except for ' and -), split words
		int count;

		for (String word : words) {
			// traverse word in the array words
			if (word.contains("'") && Arrays.binarySearch(arrCriteria, word) < 0) {
				// if the word contains "'", but it doesn't show in the special word array
				if (word.matches("\\p{L}+\\'s")) {
					word = word.replace("'s", "");
					// if the word ends with "'s", replace "'s" to a ""
				}else {
					word = word.replace("'", "");
					// else replace "'s" directly
				}				

			} else if (word.endsWith("-")) {
				// if the word ends with "-"
				word = word.replace("-", "");
				//replace "-" directly
			}else if (word.matches("[^a-z]")) {
				// if there are still something left that are not characters
				word = "";
				// ignore them
			}
//			else {
//				System.out.println(word + " is ok");
//			}
			if (!(word.isEmpty())) {
				// if the word is not empty
				if (allWords.containsKey(word)) {
					count = allWords.get(word);
					allWords.put(word, count + 1);
					// if there are duplicates, add the time of the word appears
				} else {
					allWords.put(word, 1);
					// else add the word directly
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Arrays.sort(arrCriteria);
		// sort the special word array
		long start = System.currentTimeMillis();
		try {
			readFile();
			// read the file
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sortedwords = sortWordCount(allWords);
		// sort all words
		System.out.println("top 20: " + sortedwords.subList(0, 20));

		long end = System.currentTimeMillis();
		System.out.println("the total time is: " + (end - start));

	}
	

	public static ArrayList<Map.Entry<String, Integer>> sortWordCount(HashMap<String, Integer> map) {
		// sort words by using comparator
		ArrayList<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>(){
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
				// sort words from the most frequent words to the least frequent words
			}
		});
		return infoIds;

	}

}
