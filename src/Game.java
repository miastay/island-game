import java.awt.Color; 
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class Game extends JFrame {

	public static int objects;
	public static int FRAME;
    public static float deltaTime;
	public static long lastFrameMillis;
	public static ResourceHandler resGrab;
	public static ArrayList<GameObject> activeObjects = new ArrayList<>();
		//public static float GRAPHICS_SCALE_FACTOR = 1f;
		public static int TILE_PIXELS = 50;
		/////
		public static List<Item> items = new ArrayList<Item>();
		public static List<Tile> tiles = new ArrayList<Tile>();
	///
	public static Player player;
	public static Map map;
		public static File mapLocation = new File("./res/newdefaultmap.csv");
		
	public static KeyboardListener keylist = new KeyboardListener();
	public static ScreenRenderer renderer = new ScreenRenderer();
	
	public static BufferedImage currentFrame;
	public static int currentFPS;
	public static boolean showFPS = true;
	
	public Game() {
		Game.objects++;
		resGrab = new ResourceHandler();
		setSize(1200, 700); 
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		map = new Map();
		
		/*
		 * 
		 * 
		 * 
		 */
		createComponents();
		keylist.StartKeyListener();
		
		currentFrame = renderer.outputAllLayers(new boolean[] {true, true, true});
		FrameLoop();
	}
	
	private void updateVars() {
        deltaTime = (System.currentTimeMillis() - lastFrameMillis) / 1000f;
        lastFrameMillis = System.currentTimeMillis();
		FRAME++;
	}
	private void createComponents() {
		//fill in whole list
		
		for(Item item : items) {
			activeObjects.add(item);
		}
		for(Tile tile : tiles) {
			activeObjects.add(tile);
		}
		/*
		 * Create all components
		 * Player has to come first
		 */
		Instantiate(player = new Player("crystal", 1, 1));
		Instantiate(new Item("crystal", 5, 5));

	}
	
	
	public void FrameLoop() {
		while(true) {
			for(GameObject obj : activeObjects) {
				obj.update();
			}
			
			currentFrame = renderer.outputAllLayers(new boolean[] {false, true, true});
			if(FRAME % 60 == 1)
				currentFPS = (int)(1 / deltaTime);
			repaint();
	        updateVars();
		}
	}
	
	public void paint(Graphics G) {
		G.drawImage(currentFrame, 0, 0, null);
	}

	public static boolean detectItemPlayerCollision(Rectangle2D hitbox) {
		if(player.hitbox.intersects(hitbox))
			return true;
		return false;
	}
	public static boolean detectItemCollision(Rectangle2D hitbox) {
		for(Item i : items) {
			if(i.hitbox.intersects(hitbox))
				return true;
		}
		return false;
	}
	
	public static void Instantiate(GameObject o) {
		activeObjects.add((GameObject) o);
		
		if(o instanceof Item) {
			items.add((Item) o);
		}
		if(o instanceof Tile) {
			tiles.add((Tile) o);
		}
	}
}
