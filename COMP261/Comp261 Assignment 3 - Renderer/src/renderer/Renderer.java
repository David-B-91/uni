package renderer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;;

public class Renderer extends GUI {
	
	private Scene scene;
	private int[] ambientLight = getAmbientLight();
	private int height = CANVAS_HEIGHT;
	private List<Polygon> polygons = new ArrayList<>();
	Transform transformation = Transform.identity();
	
	//for parsing
	private float[]points = new float[9];
	private int[] colour = new int[3];
	private Vector3D lightPos;
	
	/* (non-Javadoc)
	 * @see renderer.GUI#onLoad(java.io.File)
	 */
	@Override
	protected void onLoad(File file) {
						
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line; 
			String lighting = reader.readLine(); // read the first line of the file for lighting.
			String[] lightTokens = lighting.split(" "); //tokenise the lighitng line.
			
			//System.out.println(lightTokens[0]);
			
			//process the lighting file into a light vector.
			float lightX = Float.parseFloat(lightTokens[0]);
			float lightY = Float.parseFloat(lightTokens[1]);
			float lightZ = Float.parseFloat(lightTokens[2]);
			lightPos = new Vector3D(lightX, lightY, lightZ);
			
			//process all the polygons in the file.
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				
				Vector3D v1 = new Vector3D(Float.parseFloat(tokens[0]),	Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2]));
				Vector3D v2 = new Vector3D(Float.parseFloat(tokens[3]),	Float.parseFloat(tokens[4]),Float.parseFloat(tokens[5]));
				Vector3D v3 = new Vector3D(Float.parseFloat(tokens[6]),	Float.parseFloat(tokens[7]),Float.parseFloat(tokens[8]));
				Color c = new Color(Integer.parseInt(tokens[9]),Integer.parseInt(tokens[10]),Integer.parseInt(tokens[11]));
				
				//add each polygon into a list.
				polygons.add(new Polygon(v1,v2,v3,c));
			}
			
			//create a new scene with the data.
			scene = new Scene(polygons,lightPos,getLight(),height);
			reader.close();
			transformation = boundingBox();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Transform boundingBox(){
		float minX = Float.POSITIVE_INFINITY;
		float maxX = Float.NEGATIVE_INFINITY;
		float minY = Float.POSITIVE_INFINITY;
		float maxY = Float.NEGATIVE_INFINITY;
		float minZ = Float.POSITIVE_INFINITY;
		float maxZ = Float.NEGATIVE_INFINITY;
		
		for (Polygon p : scene.getPolygons()) {
			for (Vector3D v : p.getVertices()){
				minX = Math.min(minX, v.x);
				maxX = Math.max(maxX, v.x);
				minY = Math.min(minY, v.y);
				maxY = Math.max(maxY, v.y);
				minZ = Math.min(minZ, v.z);
				maxZ = Math.max(maxZ, v.z);
			}
		}
		
		float width = maxX - minX;
		float height = maxY - minY;
		float depth = maxZ - minZ;
		
		float centerX = minX + width / 2;
		float centerY = minY + height / 2;
		float centerZ = minZ + depth / 2;
		
		float sHeight = (GUI.CANVAS_HEIGHT)/height;
		float sWidth = (GUI.CANVAS_WIDTH)/width;
		float scale = Math.min(sHeight, sWidth);
		
		Transform transform = Transform.newTranslation(-centerX, -centerY, -centerZ);
		Transform t = Transform.newScale(scale,scale,scale).compose(transform);
		return t;
	}

	@Override
	protected void onKeyPress(KeyEvent ev) {
		// TODO fill this in.

		/*
		 * This method should be used to rotate the user's viewpoint.
		 */
	}

	@Override
	protected BufferedImage render() {
		if (scene != null) {
			
			ZBuffer zBuffer = new ZBuffer();
			zBuffer.setColor(new Color [CANVAS_WIDTH][CANVAS_HEIGHT]);
			zBuffer.setDepth(new float [CANVAS_WIDTH][CANVAS_HEIGHT]);
			for (int x = 0 ; x < CANVAS_WIDTH ; x++){
				for (int y = 0 ; y < CANVAS_HEIGHT ; y++){
					zBuffer.depth[x][y] = Float.POSITIVE_INFINITY;
				}
			}
			
			scene = Pipeline.translateScene(scene, transformation);
			
			Color[][] image = Pipeline.computeZBuffer(scene,zBuffer);
		
			return convertBitmapToImage(image);
		}
		
		return null;
		
		// TODO fill this in.

		/*
		 * This method should put together the pieces of your renderer, as
		 * described in the lecture. This will involve calling each of the
		 * static method stubs in the Pipeline class, which you also need to
		 * fill in.
		 */
		
	}

	public Color getLight() {
		Color aLight = new Color(ambientLight[0],ambientLight[1],ambientLight[2]);
		return aLight;
	}

	/**
	 * Converts a 2D array of Colors to a BufferedImage. Assumes that bitmap is
	 * indexed by column then row and has imageHeight rows and imageWidth
	 * columns. Note that image.setRGB requires x (col) and y (row) are given in
	 * that order.
	 */
	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				
				//TODO fix this if can be bothered
				if (bitmap[x][y] == null){
					bitmap[x][y] = Color.DARK_GRAY;
				}
				
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}
	
	

	public static void main(String[] args) {
		new Renderer();
	}
}

// code for comp261 assignments
