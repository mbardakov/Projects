import java.util.ArrayList;

// object for a single word entry in TextPredict
// contains a key word (e.g. "hello") 
// and a list of all words that came directly after it
// as well as how many times said word came directly after the key
// e.g. if in your text you had "hello world" twice, "hello alice" five times, and "hello bob" twice, then it should have:
// "hello" -> <"alice", 5>, <"world", 2>, <"bob", 2>
// TODO: sort by occurrences

public class WordEntry{
	private String keyWord;
	private int totalEntries; // almost definitely redundant

	// subclass: for key-value pairs (word, # of occurrences after keyWord)
	private class WordCount{
		private String word;
		private int count; 
		
		public String getWord(){
			return word;
		}

		public int getCount(){
			return count;
		}

		// should be the only way to set word
		public WordCount (String inputWord){
			this.word = inputWord;
			this.count = 1;
		}

		// should be the only way to change count
		public void add1(){
			++count;
		}
	}

	ArrayList<WordCount> follows = new ArrayList<WordCount>();

	public WordEntry(String inputWord){
		this.keyWord = inputWord.toLowerCase();
		this.totalEntries = 0;
	}

	public void printAll(){
		System.out.println("WordEntry for: " + keyWord);
		for (int i = 0; i < follows.size(); i++){
			System.out.println("\t" + follows.get(i).getWord() + ": " + follows.get(i).getCount());
		}
	}

	public void insert(String inputWord){
		inputWord = inputWord.toLowerCase();
		System.out.println("Inserting: " + keyWord + ": " + inputWord);

		for (int i = 0; i < follows.size(); i++){
			if (inputWord == follows.get(i).getWord()){
				follows.get(i).add1();

				// preserve order; move the newly updated entry up in the list, if necessary.
				int swapIndex = i;
				while (swapIndex > 0 && follows.get(swapIndex).getCount() >= follows.get(swapIndex-1).getCount()){
					WordCount temp = follows.get(swapIndex);
					follows.set(swapIndex, follows.get(swapIndex-1));
					follows.set(swapIndex-1, temp);
					--swapIndex;
				}
				return;
			}
		}
		follows.add(new WordCount(inputWord));
	}
}
