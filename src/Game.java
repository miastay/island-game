import java.awt.Color; 
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.swing.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame {

	public static int instances;
	public static int FRAME;
	public static Timer timer;
	public static ResourceHandler resGrab;
	public static List<Paintable> paintableObjects = new ArrayList<Paintable>();
		public static float GRAPHICS_SCALE_FACTOR = 11f;
		public static int TILE_SIZE = (int) (10 * GRAPHICS_SCALE_FACTOR);
		/////
	public static ArrayList<Object> allComponents = new ArrayList<>();
		public static List<Item> items = new ArrayList<Item>();
		public static List<Tile> tiles = new ArrayList<Tile>();
	///
	public static Player player;
	public static Map map;
	
	public Game() {
		Game.instances++;
		setSize(1200, 700); 
		setVisible(true);
		
		timer = new Timer();
		timer.schedule(new RunTimer(), 5);
		
		resGrab = new ResourceHandler();
		
		/*
		 * 
		 * 
		 * 
		 */
		createComponents();
		
		map = new Map();
		
		
	}
	private void createComponents() {
		//fill in whole list
		allComponents.add(items);
		allComponents.add(tiles);
		allComponents.add(player);
		/*
		 * Create all components
		 */
			addNewInstance(new Item("Crystal", 100, 100));
			player = new Player("Crystal", 65, 110);
			addNewInstance(player);
			String[] s = {"water1", "water2", "water3"};
			addNewInstance(new Tile(s, 100, 100));
	}
	public void paint(Graphics G) {
		
//		G.clearRect(0, 0, 1200, 700);
		G.setColor(Color.BLUE);
		G.drawString(instances + "", 50, 50);
		
		map.draw(G);
		
		for(Paintable obj : paintableObjects) {
			obj.draw(G);
		}
		
	}
	
	public class RunTimer extends TimerTask {
		@Override
		public void run() {
			repaint();
			FRAME++;
			timer.schedule(new RunTimer(), 500);
		}
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
