package swen221.assignment3.shapes;
 /**
  *  helpful  in  eliminating  any  duplicate  code found in the classes for the different shape operators. - Assignment handout
  *
  */
public abstract class ShapeOperator implements Shape{
	public Shape shapeA;
	public Shape shapeB;
	
	public ShapeOperator(Shape a, Shape b) {
		this.shapeA = a;
		this.shapeB = b;
	}
}
