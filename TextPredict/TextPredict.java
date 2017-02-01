import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
// main program/point of entry
// reads text from standard input, creates a map of word -> follow-list, where a "follow-list"
// is a list of all words that come after the given word, and how often they occur directly after said word

public class TextPredict{
	public static final String DELIMS = "!?.";
	public static void main(String[] args){
		// args is a list of files to read in and analyze
		ArrayList<String> readWords = new ArrayList<String>();
		HashMap<String, WordEntry> dictionary = new HashMap<String, WordEntry>();
		try {
			int currentFileIndex = 0;
			Scanner fileScan = new Scanner(new File(args[currentFileIndex]));
			fileScan.useDelimiter("[^\\w\\" + DELIMS + "]");
			// fileScan.useDelimiter("[^\\w]");
			while (fileScan.hasNext()){
				String wordsJustRead = fileScan.next();
				int leftIndex = 0;
				int rightIndex = 0;
				while (rightIndex < wordsJustRead.length() - 1){
					rightIndex = getFirstSymWord(wordsJustRead, DELIMS, leftIndex);
					System.out.println("adding: " + wordsJustRead.substring(leftIndex, rightIndex));
					readWords.add(wordsJustRead.substring(leftIndex, rightIndex));
					leftIndex = rightIndex;
				}
			}
			// NOW AT THIS POINT: readWords is an arrayList of all words and clusters of symbols.
			// Now you can just add them all to the WordEntry structure (:

			for (int currentWord = 0; currentWord < readWords.size() - 1; currentWord++){
				if (!hasOverlap(readWords.get(currentWord), DELIMS) &&
						!hasOverlap(readWords.get(currentWord + 1), DELIMS)){
					if (!dictionary.containsKey(readWords.get(currentWord))){
						dictionary.put(readWords.get(currentWord), new WordEntry(readWords.get(currentWord)));
					} else {
						dictionary.get(readWords.get(currentWord)).insert(readWords.get(currentWord+1));
					}	
				}
			}
		} catch (FileNotFoundException ex) {
			System.err.println("Caught FileNotFoundException: " + ex);
			System.err.println("Will attempt to continue...");
		} 
		Scanner stdin = new Scanner(System.in);
		stdin.useDelimiter("[^\\w\\" + DELIMS + "]");

		// read stdin and find the top 3 words for whatever was input	
		while (stdin.hasNext()){
			String searchWord = stdin.next();
			if (dictionary.containsKey(searchWord)){
				dictionary.get(searchWord).printTop3();
			} else {
				System.out.println("Error: " + searchWord + " did not appear mid-sentence in any reference material.");
			}
		}
	}

	// getFirstSymWord: String String -> String
	// separates blocks of symbols and text mixed together into separate strings, one per call
	// e.g. 
	// getFirstSymWord("hey!fred?", "!?"); -> returns "hey", turns string into "!fred?"
	// then getFirstSymWord("!fred?", "!?"); -> returns "!", turns string into "fred?"
	// etc.
	public static int getFirstSymWord(String block, String symbols, int start){
		int end = start;
		// case 1: check for alphanumeric characters first
		while (end < block.length() && symbols.indexOf(block.charAt(end)) < 0){
			end++;
		}
		// case 2: we didn't find any alphanumeric characters; must have symbols
		if (end == start){
			while (end < block.length() && symbols.indexOf(block.charAt(end)) >= 0){
				end++;
			}
		}
		return end;
	}

	// returns true if there is any overlap between word1 and word2
	// Naive: O(n^2) implementation
	// can be O(nlogn) if you sort both strings first
	public static boolean hasOverlap(String word1, String word2){
		for (int i = 0; i < word1.length(); i++){
			if (word2.indexOf(word1.charAt(i)) >= 0){
				return true;
			}
		}
		return false;
	}
}
