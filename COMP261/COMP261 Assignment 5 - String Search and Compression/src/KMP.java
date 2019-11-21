/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
	
	@SuppressWarnings("unused")
	private String text;
	private int[] mTable;

	public KMP(String pattern, String text) {
		this.text = text;
		createMatchTable(pattern);
	}

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
	public int search(String p, String txt) {
		
		char[] pattern = p.toCharArray();
		char[] text = txt.toCharArray();
		
		int s = 0; //position of current character in pattern.
		int t = 0; //start of current match in text.
		
		while (t + s < text.length) {
			if (pattern[s] == text[t+s]){
				s++;
				if (s == pattern.length) return t;				
			} else if (mTable[s] == -1){
				s = 0;
				t = t + s + 1;
			} else {
				t = t + s - mTable[s];
				s = mTable[s];
			}
		}
		
		return -1;
	}
	
	private void createMatchTable(String pattern){
		
		char[] s = pattern.toCharArray();
		
		int pos = 2;
		int j = 0;
		
		mTable = new int[pattern.length()];
		mTable[0] = -1;
		mTable[1] = 0;
		
		while (pos < mTable.length){
			//there is a char match.
			if (s[pos-1] == s[j]){
				mTable[pos] = j + 1;
				j++;
				pos++;
			//mismatch, reset prefix.
			} else if (j > 0){
				j = mTable[j];
			//out of prefix candidates.
			} else {
				mTable[pos] = 0;
				pos++;
			}
		}
		
		/*for (int i : mTable){
			System.out.println(i);
		}*/
		
	}
}
