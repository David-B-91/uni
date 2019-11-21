
/**
 * A node representing an itermediate node in the Huffman code tree, stores left and right children.
 */

public class TreeNode extends HuffNode {
	
	public final HuffNode left;
	public final HuffNode right;

	public TreeNode(HuffNode left, HuffNode right) {
		super(left.frequency + right.frequency);
		this.left = left;
		this.right = right;
	}

	@Override
	public int compareTo(HuffNode o) {
		return this.frequency - o.frequency;
	}

}
