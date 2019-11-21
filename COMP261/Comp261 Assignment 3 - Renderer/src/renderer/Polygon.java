package renderer;

import java.awt.Color;

/**
 * Polygon stores data about a single polygon in a scene, keeping track of
 * (at least!) its three vertices and its reflectance.
     *
     * This class has been done for you.
 */
public class Polygon {
	
	public Vector3D[] vertices = new Vector3D[3];
	public Vector3D v1,v2,v3;
	public Color reflectance;
	public boolean hidden = false;
	public Vector3D normal;
	
	public int minX, maxX, minY, maxY;
	

	/**
	 * @param points
	 *            An array of floats with 9 elements, corresponding to the
	 *            (x,y,z) coordinates of the three vertices that make up
	 *            this polygon. If the three vertices are A, B, C then the
	 *            array should be [A_x, A_y, A_z, B_x, B_y, B_z, C_x, C_y,
	 *            C_z].
	 * @param color
	 *            An array of three ints corresponding to the RGB values of
	 *            the polygon, i.e. [r, g, b] where all values are between 0
	 *            and 255.
	 */
	public Polygon(float[] points, int[] color) {
		
		this.vertices = new Vector3D[3];

		float x, y, z;
		
		for (int i = 0; i < 3; i++) {
			x = points[i * 3];
			y = points[i * 3 + 1];
			z = points[i * 3 + 2];
			this.vertices[i] = new Vector3D(x, y, z);
		}
		

		this.v1 = vertices[0];
		this.v2 = vertices[1];
		this.v3 = vertices[2];

		int r = color[0];
		int g = color[1];
		int b = color[2];
				
		this.reflectance = new Color(r, g, b);
		
		normal();
	}

	/**
	 * An alternative constructor that directly takes three Vector3D objects
	 * and a Color object.
	 */
	public Polygon(Vector3D a, Vector3D b, Vector3D c, Color color) {
		this.vertices = new Vector3D[] { a, b, c };
		this.reflectance = color;

		this.v1 = vertices[0];
		this.v2 = vertices[1];
		this.v3 = vertices[2];
		
		normal();

	}

	public Vector3D[] getVertices() {
		return vertices;
	}

	public Color getReflectance() {
		return reflectance;
	}
	
	public Vector3D getNormal() {
		return normal;
	}
	
	

	
	/**
	 * calculates normal of this polygon.
	 */
	public void normal() {
		
		normal = (v2.minus(v1).crossProduct(v3.minus(v2)));
		
		if (normal.z > 0) 
			hidden = true;
		else
			hidden = false;
	}
	
	public double getMaxY(){
		return Math.max(v1.y, Math.max(v2.y, v3.y));
	}
	
	public double getMinY(){
		return Math.min(v1.y, Math.min(v2.y, v3.y));
	}
	
	/**
	 * true if not facing the camera.
	 * @return
	 */
	public boolean isHidden() {
		normal();
		return hidden;
	}
	
	
	
	@Override
	public String toString() {
		String str = "polygon:";

		for (Vector3D p : vertices)
			str += "\n  " + p.toString();

		str += "\n  " + reflectance.toString();

		return str;
	}
}