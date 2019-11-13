import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class Game extends JFrame {

	public static int objects;
	public static int FRAME;
    public static float deltaTime;
	public static long lastFrameMillis;
	public static boolean isTrueFullScreen;
	public static ResourceHandler resGrab;
	public static ArrayList<Updateable> activeObjects = new ArrayList<>();
		//public static float GRAPHICS_SCALE_FACTOR = 1f;
		public static int TILE_PIXELS = 50;
		/////
		public static List<Item> items = new ArrayList<Item>();
		public static List<Tile> tiles = new ArrayList<Tile>();
	///
	public static Player player;
	public static Map map;
		public static File mapLocation = new File("./res/newdefaultmap.csv");
		public static File mapOverlayLocation = new File("./res/newoverlaymap.csv");
		
	public static KeyboardListener keylist = new KeyboardListener();
	public static ScreenRenderer renderer = new ScreenRenderer();
	
	public static BufferedImage currentFrame;
	public static int currentFPS;
	public static boolean showDebug = true;
	
	public static JInternalFrame uiFrame;
		public static JButton resumeButton;
		public static JButton optionsButton;
		public static JButton exitButton;
	public static int UI_FRAME_SCALE = 10;
	public static boolean isMenuShown;
	
	public Game() {
		Game.objects++;
		resGrab = new ResourceHandler();
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
//		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(isTrueFullScreen);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	
		createInternalFrame();
		
		map = new Map();
		
		/*
		 * 
		 * 
		 * 
		 */
		createComponents();
			//for debug screen
			keylist.addKey(KeyEvent.VK_F1);
			//for menu
			keylist.addKey(KeyEvent.VK_ESCAPE);
		keylist.StartKeyListener();
		
		renderer.forceLayerUpdate(0, true);
		currentFrame = renderer.outputAllLayers();
		startFrameTimer();
		
		this.setFocusable(true);
		this.requestFocus();
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
		Instantiate(player = new Player("player-front", 7, 13));
		Instantiate(new Item("crystal", 5, 5));

	}
	
	
	public void startFrameTimer() {
		java.util.Timer timer = new java.util.Timer();
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				keylist.update();
				for(Updateable obj : activeObjects) {
					obj.update();
				}
				
				currentFrame = renderer.outputAllLayers();
				if(FRAME % 60 == 1)
					currentFPS = (int)(1 / deltaTime);
				if(keylist.getKeyDown(KeyEvent.VK_F1)) {
					showDebug = !showDebug;
				}
				if(keylist.getKeyDown(KeyEvent.VK_ESCAPE)) {
					isMenuShown = !isMenuShown;
				}
				repaint();
		        updateVars();
			}
			
		}, new Date(), 1);
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
	
	public static void Instantiate(Updateable o) {
		activeObjects.add((Updateable) o);
		
		if(o instanceof Item) {
			items.add((Item) o);
		}
		if(o instanceof Tile) {
			tiles.add((Tile) o);
		}
	}
	private void createInternalFrame() {
		
		uiFrame = new JInternalFrame();
		
			resumeButton = new JButton("Resume");
				resumeButton.addActionListener(new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {
					    isMenuShown = !isMenuShown;
					  }
				});
			resumeButton.setMaximumSize(new Dimension(30*UI_FRAME_SCALE, 6*UI_FRAME_SCALE));
			optionsButton = new JButton("Settings");
			optionsButton.setMaximumSize(new Dimension(30*UI_FRAME_SCALE, 6*UI_FRAME_SCALE));
				optionsButton.addActionListener(new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {
					    System.exit(0);
					  }
				});
			exitButton = new JButton("Exit");
			exitButton.setMaximumSize(new Dimension(30*UI_FRAME_SCALE, 6*UI_FRAME_SCALE));
				exitButton.addActionListener(new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {
					    System.exit(0);
					  }
				});
		uiFrame.add(resumeButton); uiFrame.add(optionsButton); uiFrame.add(exitButton);
		uiFrame.setResizable(false);
		uiFrame.setBorder(null);
		uiFrame.setLayout(new BoxLayout(uiFrame.getContentPane(), BoxLayout.Y_AXIS));
		add(uiFrame);
		uiFrame.setVisible(true);
	}
}
