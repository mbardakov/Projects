import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
// main program/point of entry
// reads text from standard input, creates a map of word -> follow-list, where a "follow-list"
// is a list of all words that come after the given word, and how often they occur directly after said word

public class TextPredict{
	public static final String DELIMS = "!?.";
	public static void main(String[] args){
		// args is a list of files to read in and analyze
		int currentFileIndex = 0;
		Scanner fileScan = new Scanner(/*new File(args[currentFileIndex])*/"symbols..!?words!words? words words?");
		ArrayList<String> readWords = new ArrayList<String>();
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
		
		HashMap<String, WordEntry> dictionary = new HashMap<String, WordEntry>();
		for (int currentWord = 0; currentWord < readWords.length() - 1; currentWord++){
			if (hasOverlap(readWords.at(currentWord)), DELIMS){
				// IF CURRENTWORD IS A BLOCK OF SYMBOLS, SKIP IT
				// IF NEXT WORD IS A BLOCK OF SYMBOLS, SKIP IT
				// TODO
			}
			if (!dictionary.containsKey(readWords.at(currentWord))){
				dictionary.put(readWords.at(currentWord), new WordEntry(readWords.at(currentWord)));
			}
			dictionary.get(readWords.at(currentWord)).insert(readWords.at(currentWord+1));
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