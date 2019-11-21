package swen221.assignment3.shapes;

public class ShapeIntersection extends ShapeOperator {

	public ShapeIntersection(Shape a, Shape b) {
		super(a, b);
	}

	@Override
	public boolean contains(int x, int y) {
		return (shapeA.contains(x,y) && shapeB.contains(x,y));
	}

	@Override
	public Rectangle boundingBox() {
		int minX = -1, minY = -1, maxX = 0, maxY = 0;
		int width = shapeA.boundingBox().width + shapeB.boundingBox().width;
		int height = shapeA.boundingBox().width + shapeB.boundingBox().height;
		int oldMinX = Math.min(shapeA.boundingBox().x, shapeB.boundingBox().x);
		int oldMinY = Math.min(shapeA.boundingBox().y, shapeB.boundingBox().y);
		
		for (int x = oldMinX ; x < width ; x++){
			for (int y = oldMinY ; y < height ; y++){
				if (shapeA.contains(x, y) && shapeB.contains(x, y)) {
					if (minX == -1 && minY == -1) {
						minX = x;
						minY = y;
					} else {
						maxX = x;
						maxY = y;
					}
				}
			}
		}
		return new Rectangle(minX, minY, maxX - minX +1, maxY - minY + 1);
	}

}
