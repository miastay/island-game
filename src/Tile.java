import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Tile implements Paintable, Animatable {
	
	private String name;		//reference for ResourceHandler
	private String[] frames;	//used for animated tiles
	private final int x, y;
	public final Type type;
	private boolean showFrame = true;

	int getX() {return x;}
	int getY() {return y;}
	
	public enum Type {
		ANIMATED,
		STATIC;
	}

	public Tile(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		Game.instances++;
		type = Type.STATIC;
	}
	public Tile(String[] names, int x, int y) {
		this.frames = names;
		this.x = x;
		this.y = y;
		Game.instances++;
		type = Type.ANIMATED;
	}
	@Override
	public void draw(Graphics G) {
		// TODO Auto-generated method stub
		if(type == Type.ANIMATED) {
			animate(G);
		} else {
			BufferedImage img = ResourceHandler.getImageFromKey(name);
				if(img == null)
					System.out.println("Image not found");
			
			G.drawImage(img, x*Game.TILE_SIZE, y*Game.TILE_SIZE, (x*Game.TILE_SIZE + (int)(img.getWidth()*Game.GRAPHICS_SCALE_FACTOR)), (y*Game.TILE_SIZE + (int)(img.getHeight()*Game.GRAPHICS_SCALE_FACTOR)), 0, 0, img.getWidth(), img.getHeight(), null);
			if(showFrame) {
				G.setColor(Color.GREEN);
				G.drawRect(x*Game.TILE_SIZE, y*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
			}
		}
	}
	@Override
	public void animate(Graphics G) {
		// TODO Auto-generated method stub
		BufferedImage img = ResourceHandler.getImageFromKey(frames[Game.FRAME % (frames.length)]);
		G.drawImage(img, x, y, (x + (int)(img.getWidth()*Game.GRAPHICS_SCALE_FACTOR)), (y + (int)(img.getHeight()*Game.GRAPHICS_SCALE_FACTOR)), 0, 0, img.getWidth(), img.getHeight(), null);
	}
	
}
