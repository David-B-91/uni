
/**
 * A node representing a leaf on the huffman code tree, storing the character value it represents.
 */

public class LeafNode extends HuffNode {
	
	public final char value;
	
	public LeafNode(int frequency, char value){
		super(frequency);
		this.value = value;
	}

	@Override
	public int compareTo(HuffNode o) {
		return this.frequency - o.frequency;
	}

}
