package swen221.assignment3.shapes;

public class Rectangle implements Shape {
	
	int x, y, width, height;
	
	public Rectangle(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean contains(int x, int y) {
		
		int minX = this.x;
		int maxX = this.x + width;
		int minY = this.y;
		int maxY = this.y + height;
		
		if (x >= minX && x < maxX && y >= minY && y < maxY) return true;
		else return false;
	}

	@Override
	public Rectangle boundingBox() {
		return this;
	}

}
