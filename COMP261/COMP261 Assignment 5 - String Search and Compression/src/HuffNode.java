
/**
 * A node to be used in HuffmanCoding, with two subclasses (LeafNode and TreeNode). 
 */

public abstract class HuffNode implements Comparable<HuffNode>{
	
	public final int frequency;
	
	public HuffNode(int frequency){
		this.frequency = frequency;
	}
	
}
