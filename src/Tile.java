import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile implements Paintable, Animatable {
	
	private Sprite tileSprite;
	private String name;		//reference for ResourceHandler
	private String[] frames;	//used for animated tiles
        private float frameRate;
        private int currentFrame;
        private long lastFrameMillis;
	private final int x, y;
	public final Type type;
	private boolean showFrame = false;
	private Contact contactType = Contact.NONE;
	
	public static enum Contact {
		COLLIDE,
		NONE
	}

	float getX() {return x;}
	float getY() {return y;}
	
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
			if(name.contains("tree"))
				this.contactType = Contact.COLLIDE;
		tileSprite = new Sprite(ResourceHandler.getImageFromKey(name), x, y, 1, 0);
		Game.renderer.addSprite(tileSprite);
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
        
        tileSprite = new Sprite(ResourceHandler.getImageFromKey(names[0]), x, y, 1, 0);
		Game.renderer.addSprite(tileSprite);
	}
	@Override
	public void draw(Graphics G) {
		// TODO Auto-generated method stub
		//take this out for lag reduction? :
			
		if(type == Type.ANIMATED) {
            if((System.currentTimeMillis() - lastFrameMillis) / 1000f > 1 / frameRate){
                    currentFrame = (currentFrame == frames.length - 1) ? 0 : currentFrame + 1;
                    tileSprite.image = ResourceHandler.getImageFromKey(frames[currentFrame]);
                    lastFrameMillis = System.currentTimeMillis();
            }
		} else {
			tileSprite.image = ResourceHandler.getImageFromKey(name);
		}
		if(showFrame) {
			G.setColor(Color.GREEN);
			G.drawRect(x, y, Game.TILE_PIXELS, Game.TILE_PIXELS);
		}
	}
	@Override
	public void animate(Graphics G) {
		// TODO Auto-generated method stub
	}
	
}
