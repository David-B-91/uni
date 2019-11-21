package renderer;

import java.awt.Color;
import java.util.*;

/**
 * The Scene class is where we store data about a 3D model and light source
 * inside our renderer. It also contains a static inner class that represents one
 * single polygon.
 * 
 * Method stubs have been provided, but you'll need to fill them in.
 * 
 * If you were to implement more fancy rendering, e.g. Phong shading, you'd want
 * to store more information in this class.
 */
public class Scene {
	
	private List<Polygon> polygons;
	private Vector3D lightPos;
	private List <Polygon> visiblePolygons;
	private Color ambientLight;
	private int height;

	public Scene(List<Polygon> polygons, Vector3D lightPos, Color ambientLight, int height) {
         this.polygons = polygons;
         this.lightPos = lightPos;
         this.ambientLight = ambientLight;
         this.height = height;
         normalize();
         calculateVisiblePolygons();
         
		// TODO fill this in.
	}
	
	private void calculateVisiblePolygons() {
		visiblePolygons = new ArrayList<>();
		visiblePolygons.clear();
		for (Polygon p : polygons) {
			if(!p.isHidden()) {
				visiblePolygons.add(p);
			}
		}
	}

	private void normalize(){
		for(Polygon p : polygons){
			p.normal();
		}
	}
	
	public List<Polygon> getVisiblePolygons() {
		return visiblePolygons;
	}

	public Vector3D getLight() {
          return lightPos;
	}

	public List<Polygon> getPolygons() {
          return polygons;
	}

	public int getHeight() {
		return height;
	}

	public Color getAmbientLight() {
		return ambientLight;
	}

	}

// code for COMP261 assignments
