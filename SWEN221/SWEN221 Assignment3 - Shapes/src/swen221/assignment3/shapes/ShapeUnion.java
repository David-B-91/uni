package swen221.assignment3.shapes;

public class ShapeUnion extends ShapeOperator {
	
	public ShapeUnion(Shape a, Shape b) {
		super(a,b);
	}

	@Override
	public boolean contains(int x, int y) {		
		return (!(!shapeA.contains(x,y) && !shapeB.contains(x,y)));
	}

	@Override
	public Rectangle boundingBox() {
		int minX = Math.min(shapeA.boundingBox().x, shapeB.boundingBox().x);
		int maxX = Math.max(shapeA.boundingBox().x + shapeA.boundingBox().width, shapeB.boundingBox().x + shapeB.boundingBox().width);
		int minY = Math.min(shapeA.boundingBox().y, shapeB.boundingBox().y);
		int maxY = Math.max(shapeA.boundingBox().y + shapeA.boundingBox().height, shapeB.boundingBox().y + shapeB.boundingBox().height);
		return new Rectangle(minX,minY,maxX-minX,maxY-minY);
	}

}
