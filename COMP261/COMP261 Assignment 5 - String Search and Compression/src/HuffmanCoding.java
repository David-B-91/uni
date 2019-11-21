import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
	
	public Map<Character,Integer> frequencyMap;
	public Map<Character,String> encodingMap = new HashMap<Character,String>();
	public Map<String,Character> decodingMap = new HashMap<String,Character>();
	public HuffNode tree;
	
	
	/**
	 * This would be a good place to compute and store the tree.
	 */
	public HuffmanCoding(String text) {
		
		frequencyMap = computeFrequencyMap(text);
		tree = computeHuffmanTree(frequencyMap);
		computeBinaryStrings(tree, new StringBuilder());
		
		/*for (char c : encodingMap.keySet()){
			System.out.println("Char: "+c+": "+encodingMap.get(c));
		}*/
		
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		
		StringBuilder encoded = new StringBuilder();
		
		for (char c: text.toCharArray()){
			encoded.append(encodingMap.get(c));
		}
		return encoded.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		
		StringBuilder decoded = new StringBuilder();
		StringBuilder current = new StringBuilder();
		
		for (char c : encoded.toCharArray()){
			current.append(c); 
			//if the current String sequence being worked on has a match in the map, add its value to the decoded text, and reset current.
			if (decodingMap.containsKey(current.toString())){
				decoded.append(decodingMap.get(current.toString()));
				current = new StringBuilder(); 
			}
		}
		
		return decoded.toString();
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		return "";
	}
	
	/**
	 * Takes the text and returns a character to frequency map.
	 * @param text
	 * @return
	 */
	private Map<Character, Integer> computeFrequencyMap(String text) {
		
		char[] string = text.toCharArray();
		Set<Character> alphabet = new HashSet<Character>();
		Map<Character, Integer> map = new HashMap<Character,Integer>();
		
		for (char c : string){
			
			if (!alphabet.isEmpty()){
				if (alphabet.contains(c)){
					map.put(c, map.get(c)+1); //update the frequency integer, by 1, for character c in the map.
				} else { //character is not in the set.
					alphabet.add(c);
					map.put(c, 1);
				}
			} else { //set is empty, add character.
				alphabet.add(c);
				map.put(c, 1);
			}
		}
		
		/*for (char c : map.keySet()){
			System.out.println("Character: "+c+"-"+map.get(c));
		}*/
		
		return map;
	}
	
	/**
	 * Takes the map of characters and frequencies, and creates the HuffmanTree.
	 * @param fMap
	 * @return The Root Node of the tree.
	 */
	private HuffNode computeHuffmanTree(Map<Character, Integer> fMap) {
		
		PriorityQueue<HuffNode> nodes = new PriorityQueue<HuffNode>();
		
		//add all characters onto the queue.
		for (char c : fMap.keySet()){
			nodes.offer(new LeafNode(fMap.get(c), c));
		}
		
		while (nodes.size() > 1){
			HuffNode a = nodes.poll();
			HuffNode b = nodes.poll();
			nodes.offer(new TreeNode(a,b));
		}
		
		return nodes.poll();
	}
	
	/**
	 * Takes the tree root, and calculates binary string value for each character.
	 * @param tree
	 * 
	 */
	private void computeBinaryStrings(HuffNode tree, StringBuilder code) {
		
		if (tree instanceof TreeNode){
			
			TreeNode node = (TreeNode)tree;
			
			code.append('0');
			computeBinaryStrings(node.left, code);
			code.deleteCharAt(code.length()-1);
			
			code.append('1');
			computeBinaryStrings(node.right, code);
			code.deleteCharAt(code.length()-1);
			
		} else if (tree instanceof LeafNode){
			
			LeafNode leaf = (LeafNode)tree;
			encodingMap.put(leaf.value, new String(code));
			decodingMap.put(new String(code), leaf.value);
			}
	}


}
