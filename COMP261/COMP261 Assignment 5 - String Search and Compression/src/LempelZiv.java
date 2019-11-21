/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	public String compress(String input) {
		
		int cursor = 0;
		int windowSize = 1000;
		char [] text = input.toCharArray();
		StringBuilder output = new StringBuilder();
				
		while (cursor < text.length -1){
			
			int searchStart = Math.max(cursor-windowSize, 0);	
			int matchLength = 0;
			boolean foundMatch = false;
			int matchOffset = 0;
			int finalMatchLength = 0;
			
			
			while ((searchStart + matchLength) < cursor){
				
				int windowEnd = Math.min(searchStart + matchLength, input.length());
				int lookaheadBufferEnd = Math.min(cursor + matchLength, input.length());
				
				String windowMatch = input.substring(searchStart, windowEnd);
				String lookaheadMatch = input.substring(cursor, lookaheadBufferEnd);
				
				if (windowMatch.equals(lookaheadMatch)) {
					matchLength++;
					foundMatch = true;
				} else {
					
					if (foundMatch && ((matchLength-1) > finalMatchLength)) {
						matchOffset = cursor - searchStart - (matchLength-1);
						finalMatchLength = matchLength-1;
					}
					
					matchLength = 0;
					searchStart++;
					foundMatch = false;
				}
			}
			if (matchLength != 0) {
				output.append("["+matchOffset+"|"+finalMatchLength+"|"+text[cursor+matchLength-1]+"]");
				cursor+=matchLength;
			} else {
				output.append("[0|0|"+text[cursor]+"]");
				cursor++;
			}
				
				
				
				
				
				/*if (text[(cursor+lookahead>text.length-1)?text.length-1:cursor+lookahead] == text[(cursor<windowSize)?0:cursor-windowSize]){
					if (firstMatch == 0) firstMatch = cursor;
					prevMatch = text[(cursor<windowSize)?0:cursor-windowSize];
					lookahead++;
				} else {
					output.append("["+firstMatch+"|"+((lookahead-1<0?0:lookahead-1))+"|"+text[cursor+lookahead-1]+"]");
					if (lookahead == 0) lookahead = 1;
					if (firstMatch != 0) firstMatch = 0;
					cursor = cursor+lookahead;					
					break;
				}*/
			
			
		}
		
		return output.toString();
	}


	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
		
		/*StringBuilder output = new StringBuilder();
		int index = 0;
		
		while (index < compressed.length()) {
			
			char current = compressed.charAt(index);
			int offset = 0;
			int length = 0;
			char nextChar = 0;
			int dcIndex = 0;
			
			if (current == '[') {
				index++;
				// should be int next
				offset = compressed.charAt(index);
				index+=2;
				length = compressed.charAt(index);
				index+=2;
				nextChar = compressed.charAt(index);
				index+=2;
			}
			
			String decompressed = "";
		
			for (int i = 0 ; i < length ; i++) {
				output.append(output.charAt(output.length()-offset+i));
			}
			output.append(nextChar);
			dcIndex++;
			
		}
		
		return output.toString();*/
		return "";
		}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {
		return "";
	}
}
