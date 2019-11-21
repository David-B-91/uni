package renderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.Polygon;

/**
 * The Pipeline class has method stubs for all the major components of the
 * rendering pipeline, for you to fill in.
 * 
 * Some of these methods can get quite long, in which case you should strongly
 * consider moving them out into their own file. You'll need to update the
 * imports in the test suite if you do.
 */
public class Pipeline {

	/**
	 * Returns true if the given polygon is facing away from the camera (and so
	 * should be hidden), and false otherwise.
	 */
	public static boolean isHidden(Polygon poly) {
		
		// TODO fill this in. DONE?
		return poly.isHidden();
	}

	/**
	 * Computes the colour of a polygon on the screen, once the lights, their
	 * angles relative to the polygon's face, and the reflectance of the polygon
	 * have been accounted for.
	 * 
	 * @param lightDirection
	 *            The Vector3D pointing to the directional light read in from
	 *            the file.
	 * @param lightColor
	 *            The color of that directional light.
	 * @param ambientLight
	 *            The ambient light in the scene, i.e. light that doesn't depend
	 *            on the direction.
	 */
	public static Color getShading(Polygon poly, Vector3D lightDirection, Color lightColor, Color ambientLight) {
		if (poly.getNormal() == null) {
			poly.normal();
		}
//		
		
		Vector3D normal = poly.getNormal();
		float costh = normal.cosTheta(lightDirection);
		
		float r = ((ambientLight.getRed() / 255f) + (0.5f * costh)) * (poly.getReflectance().getRed() / 255f);
		float g = ((ambientLight.getGreen() / 255f) + (0.5f * costh)) * (poly.getReflectance().getGreen() / 255f);
		float b = ((ambientLight.getBlue() / 255f) + (0.5f * costh)) * (poly.getReflectance().getBlue() / 255f);
		
		if (r < 0) r = 0; else if (r > 1) r = 1; 
		if (g < 0) g = 0; else if (g > 1) g = 1;
		if (b < 0) b = 0; else if (b > 1) b = 1;
		
		return new Color(r,g,b);
	}

	/**
	 * This method should rotate the polygons and light such that the viewer is
	 * looking down the Z-axis. The idea is that it returns an entirely new
	 * Scene object, filled with new Polygons, that have been rotated.
	 * 
	 * @param scene
	 *            The original Scene.
	 * @param xRot
	 *            An angle describing the viewer's rotation in the YZ-plane (i.e
	 *            around the X-axis).
	 * @param yRot
	 *            An angle describing the viewer's rotation in the XZ-plane (i.e
	 *            around the Y-axis).
	 * @return A new Scene where all the polygons and the light source have been
	 *         rotated accordingly.
	 */
	public static Scene rotateScene(Scene scene, float xRot, float yRot) {
		
		List<Polygon> newPolys = new ArrayList<>();
		Transform xRotation = Transform.newXRotation(xRot);
		Transform yRotation = Transform.newYRotation(yRot);
		Transform totalRotation = xRotation.compose(yRotation);
		for (Polygon p : scene.getPolygons()){
			Vector3D a = totalRotation.multiply(p.getVertices()[0]);
			Vector3D b = totalRotation.multiply(p.getVertices()[1]);
			Vector3D c = totalRotation.multiply(p.getVertices()[2]);
			newPolys.add(new Polygon(a,b,c,p.getReflectance()));
		}
		return new Scene(newPolys,totalRotation.multiply(scene.getLight()),scene.getAmbientLight(),scene.getHeight());	// TODO fill this in.
	}

	/**
	 * This should translate the scene by the appropriate amount.
	 * 
	 * @param scene
	 * @return
	 */
	public static Scene translateScene(Scene scene, Transform baseTransform) {
		Transform translation = Transform.newTranslation(GUI.CANVAS_WIDTH/2, GUI.CANVAS_HEIGHT/2, 0).compose(baseTransform);
		Vector3D lightPos = scene.getLight();
		List<Polygon> newPolys = new ArrayList<>();
		for (Polygon p : scene.getPolygons()){
			Vector3D a = translation.multiply(p.getVertices()[0]);
			Vector3D b = translation.multiply(p.getVertices()[1]);
			Vector3D c = translation.multiply(p.getVertices()[2]);
			newPolys.add(new Polygon(a,b,c,p.getReflectance()));
		}
		
		return new Scene(newPolys,translation.multiply(lightPos),scene.getAmbientLight(),scene.getHeight());
	}

	/**
	 * This should scale the scene.
	 * 
	 * @param scene
	 * @return
	 */
	public static Scene scaleScene(Scene scene) {
		// TODO fill this in.
		return null;
	}

	/**
	 * Computes the edgelist of a single provided polygon, as per the lecture
	 * slides.
	 */
	public static float[][] computeEdgeList(Polygon poly) {
		Vector3D[] vertices = poly.getVertices();	
		float[][] edgelist = new float [(int) poly.getMaxY()][4];
				
		for (int i = (int) poly.getMinY() ; i < edgelist.length ; i++) {
			edgelist [i][0] = Float.POSITIVE_INFINITY;
			edgelist [i][1] = Float.POSITIVE_INFINITY;
			edgelist [i][2] = Float.NEGATIVE_INFINITY;
			edgelist [i][3] = Float.NEGATIVE_INFINITY;
		}
				
		for(int i = 0; i<3 ; i++) {
			Vector3D vA = vertices[i];
			Vector3D vB = vertices[(i+1)%3];
			
			if (vA.y > vB.y) {
				Vector3D temp = vB;
				vB = vA;
				vA = temp;
			}
			
			float mX = (vB.x - vA.x) / (vB.y-vA.y);
			float mZ = (vB.z - vA.z) / (vB.y-vA.y);
			float x = (float) Math.floor(vA.x);
			float z = (float) Math.floor(vA.z);
			
			int j = (int) Math.floor(vA.y);
			int maxj = (int) Math.floor(vB.y);
			
			while (j < maxj) {
				if (x < edgelist[j][0]) {
					edgelist[j][0] = x;
					edgelist[j][1] = z;
				} /*else*/ if (x > edgelist[j][2]) {
					edgelist[j][2] = x;
					edgelist[j][3] = z;
				}
				j++;
				x += mX;
				z += mZ;
			}
		}
		
		return edgelist;
	}
	

	/**
	 * Fills a zbuffer with the contents of a single edge list according to the
	 * lecture slides.
	 * 
	 * The idea here is to make zbuffer and zdepth arrays in your main loop, and
	 * pass them into the method to be modified.
	 * 
	 * @param zbuffer
	 *            A double array of colours representing the Color at each pixel
	 *            so far.
	 * @param zdepth
	 *            A double array of floats storing the z-value of each pixel
	 *            that has been coloured in so far.
	 * @param polyEdgeList
	 *            The edgelist of the polygon to add into the zbuffer.
	 * @param polyColor
	 *            The colour of the polygon to add into the zbuffer.
	 */
	public static Color[][] computeZBuffer(Scene scene, ZBuffer zBuffer) {
		
		for (Polygon p : scene.getVisiblePolygons()) {
			
			Color shading = getShading(p, scene.getLight(), new Color(0,0,0), scene.getAmbientLight());
			
			float[][]edgelist = computeEdgeList(p);
			for(int i = 0 ; i < edgelist.length ; i++) {
				float x = (float) Math.floor(edgelist[i][0]);
				float z = edgelist[i][1];
				float mZ = (edgelist[i][3]) - (edgelist[i][1]) / (edgelist[i][2]) - (edgelist[i][0]);
				
				while (x < Math.floor(edgelist[i][2])) {
					//System.out.println("x:"+x+" sceneHeight:"+scene.getHeight()+" z:"+z+" zdepth:"+zDepth[(int)x][i]);
					if (x >= 0 && x < scene.getHeight() && z < zBuffer.depth[(int)x][i]) {
						zBuffer.depth[(int)x][i] = z;
						zBuffer.color[(int) Math.floor(x)][i] = shading;						
						
					}
					x++;
					z += mZ;
				}
				
			} 
			
		}		
		return zBuffer.color;
		
		
	}
	
}

// code for comp261 assignments
