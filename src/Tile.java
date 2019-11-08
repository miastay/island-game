public class Tile implements Updateable{
	
	Sprite tileSprite;
	private String spriteName;	//reference for ResourceHandler
	private Animation tileAnim;	//used for animated tiles
        private int currentFrame;
        private long lastFrameMillis;
	private final int x, y;
	public final Type type;
	private Contact contactType = Contact.NONE;
	
	public static enum Contact {
		COLLIDE,
		NONE
	}

	int getX() {return x;}
	int getY() {return y;}
	String getName() {return spriteName;}
	
	public enum Type {
		ANIMATED,
		STATIC;
	}

	public Tile(String spriteName, int x, int y, int layer) {
		this.spriteName = spriteName;
		this.x = x;
		this.y = y;
		Game.objects++;
		type = Type.STATIC;
		tileSprite = new Sprite(ResourceHandler.getImageFromKey(spriteName), x, y, 1, layer);
		Game.renderer.addSprite(tileSprite);
		
	}
	public Tile(Animation tileAnim, int x, int y, int layer) {
		this.tileAnim = tileAnim;
		this.x = x;
		this.y = y;
		Game.objects++;
		type = Type.ANIMATED;
                                
        currentFrame = 0;
        lastFrameMillis = System.currentTimeMillis();
        
        tileSprite = new Sprite(tileAnim.frames[0], (float)x, (float)y, 1.0f, layer);
		Game.renderer.addSprite(tileSprite);
	}
	public void update() {
		if(type == Type.ANIMATED) {
            animate();
		} else {
			tileSprite.image = ResourceHandler.getImageFromKey(spriteName);
		}
	}
	
	void animate() {
		if((System.currentTimeMillis() - lastFrameMillis) / 1000f > 1 / tileAnim.frameRate){
            currentFrame = (currentFrame == tileAnim.frames.length - 1) ? 0 : currentFrame + 1;
            tileSprite.image = tileAnim.frames[currentFrame];
            lastFrameMillis = System.currentTimeMillis();
		}
	}
}
