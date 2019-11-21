package swen221.assignment3.shapes;

public class ShapeDifference extends ShapeOperator {

	public ShapeDifference(Shape a, Shape b) {
		super(a, b);
	}

	@Override
	public boolean contains(int x, int y) {
		return (shapeA.contains(x, y) && !shapeB.contains(x, y));
	}

	@Override
	public Rectangle boundingBox() {
		return (new Rectangle(shapeA.boundingBox().x, shapeA.boundingBox().y, shapeA.boundingBox().width, shapeA.boundingBox().height));
	}

}
