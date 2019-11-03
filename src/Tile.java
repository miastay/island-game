import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Tile implements Paintable, Animatable {
	
	private String name;		//reference for ResourceHandler
	private String[] frames;	//used for animated tiles
        private float frameRate;
        private int currentFrame;
        private long lastFrameMillis;
	private final int x, y;
	public final Type type;
	private boolean showFrame = false;

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
		Game.objects++;
		type = Type.STATIC;
	}
	public Tile(String[] names, int x, int y, float frameRate) {
		this.frames = names;
		this.x = x;
		this.y = y;
		Game.objects++;
		type = Type.ANIMATED;
                                
                currentFrame = 0;
                this.frameRate = frameRate;
                lastFrameMillis = System.currentTimeMillis();
	}
	@Override
	public void draw(Graphics G) {
		// TODO Auto-generated method stub
		BufferedImage img;
		if(type == Type.ANIMATED) {
                        if((System.currentTimeMillis() - lastFrameMillis) / 1000f > 1 / frameRate){
                                currentFrame = (currentFrame == frames.length - 1) ? 0 : currentFrame + 1;
                                lastFrameMillis = System.currentTimeMillis();
                        }
                    
			img = ResourceHandler.getImageFromKey(frames[currentFrame]);
			if(img == null)
				System.out.println("Image not found");
			Game.renderer.addSprite(img, x*Game.TILE_SIZE, y*Game.TILE_SIZE, Game.GRAPHICS_SCALE_FACTOR, 0);
		} else {
			img = ResourceHandler.getImageFromKey(name);
				if(img == null)
					System.out.println("Image not found");
				Game.renderer.addSprite(img, x*Game.TILE_SIZE, y*Game.TILE_SIZE, Game.GRAPHICS_SCALE_FACTOR, 0);
		}
		if(showFrame) {
			G.setColor(Color.GREEN);
			G.drawRect(x*Game.TILE_SIZE, y*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
		}
	}
	@Override
	public void animate(Graphics G) {
		// TODO Auto-generated method stub
		BufferedImage img = ResourceHandler.getImageFromKey(frames[Game.FRAME % (frames.length)]);
		Game.renderer.addSprite(img, x*Game.TILE_SIZE, y*Game.TILE_SIZE, Game.GRAPHICS_SCALE_FACTOR, 0);
	}
	
}
