import java.awt.Color; 
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Game extends JFrame {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static int objects;
	public static int FRAME;
        public static float deltaTime;
        public static int framesPerSecond;
        	public static int lastFPS;
	public static long lastFrameMillis;
	public static ResourceHandler resGrab;
	public static List<Paintable> paintableObjects = new ArrayList<Paintable>();
		//public static float GRAPHICS_SCALE_FACTOR = 1f;
		public static int TILE_PIXELS = 50;
		/////
	public static ArrayList<Object> allComponents = new ArrayList<>();
		public static List<Item> items = new ArrayList<Item>();
		public static List<Tile> tiles = new ArrayList<Tile>();
	///
	public static Player player;
	public static Map map;
		public static File mapLocation = new File("./res/newdefaultmap.csv");
		
	public static KeyboardListener keylist = new KeyboardListener();
	public static ScreenRenderer renderer = new ScreenRenderer();
	
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
		
	}
	
	private void updateVars() {
		TILE_PIXELS = 50;
        deltaTime = (System.currentTimeMillis() - lastFrameMillis) / 1000f;
        lastFrameMillis = System.currentTimeMillis();
        framesPerSecond = (int)(deltaTime*1000);
		FRAME++;
	}
	private void createComponents() {
		//fill in whole list
		allComponents.add(items);
		allComponents.add(tiles);
		allComponents.add(player);
		/*
		 * Create all components
		 */
			addNewInstance(new Item("crystal", 5, 5));
			player = new Player("crystal", 1, 1);
			addNewInstance(player);
	}
	
	public void paint(Graphics G) {
		G.setColor(Color.BLUE);
		G.drawString(objects + "", 50, 50);
		
		map.draw(G);
		
		for(Paintable obj : paintableObjects) {
			obj.draw(G);
		}
		
		G.drawImage(renderer.outputAllLayers(), 0, 0, null);
		G.setColor(Color.RED);
		
		
		
		if(FRAME % 20 == 1)
			lastFPS = framesPerSecond;
		G.drawString(lastFPS + "fps", 150, 40);
		
        updateVars();
        try{
            TimeUnit.MILLISECONDS.sleep(10);
        } 
        catch (Exception e){
        	e.printStackTrace();
        }
        
        toolkit.sync();
        repaint();
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
	
	public static void addNewInstance(Object o) {
		if(o instanceof Paintable) {
			paintableObjects.add((Paintable) o);
		}
		if(o instanceof Item) {
			items.add((Item) o);
		}
		if(o instanceof Tile) {
			tiles.add((Tile) o);
		}
	}
}
