
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player implements GameObject {

	private float speed = 3;
	
	Rectangle2D.Float hitbox;
		private float width, height;
	private boolean renderHitbox = true;
	private String name;
	private Sprite playerSprite;
	private float x, y;
	
	public boolean canControl = true;
	
	float getX() {return x;}
	float getY() {return y;}
	void setX(float x) {this.x = x; playerSprite.x = x;}
	void setY(float y) {this.y = y; playerSprite.y = y;}
	
	private float lastX;
	private float lastY;
	
	public Player(String name) {
		this.name = name;
		playerSprite = new Sprite(ResourceHandler.getImageFromKey(name), 0, 0, 1, 1);
		setHitbox();
		Game.objects++;
		instantiateKeys();
		Game.renderer.addSprite(playerSprite);
	}
	
	public Player(String name, float x, float y) {
		this.name = name;
		playerSprite = new Sprite(ResourceHandler.getImageFromKey(name), x, y, 1, 1);
		setHitbox();
		setX(x); setY(y);
		Game.objects++;
		instantiateKeys();
		Game.renderer.addSprite(playerSprite);
	}
	private void instantiateKeys() {
		Game.keylist.addKey(KeyEvent.VK_A);
		Game.keylist.addKey(KeyEvent.VK_S);
		Game.keylist.addKey(KeyEvent.VK_W);
		Game.keylist.addKey(KeyEvent.VK_D);
	}
	private void setHitbox() {
		width = (ResourceHandler.getImageFromKey(name).getWidth() + 0f )/ Game.TILE_PIXELS;
		height = (ResourceHandler.getImageFromKey(name).getHeight() + 0f )/ Game.TILE_PIXELS;
		hitbox = new Rectangle.Float(getX(), getY(), width, height);
	}
	private void updateHitbox() {
		hitbox.setRect(x, y, width, height);
	}
	private void checkKeys() {
		if(canControl) {
			lastX = getX();
			lastY = getY();
			if(Game.keylist.getKey(KeyEvent.VK_A)) {
				setX(getX() - speed * Game.deltaTime);
			}
			if(Game.keylist.getKey(KeyEvent.VK_W)) {
				setY(getY() - speed * Game.deltaTime);
			}
			if(Game.keylist.getKey(KeyEvent.VK_S)) {
				setY(getY() + speed * Game.deltaTime);
			}
			if(Game.keylist.getKey(KeyEvent.VK_D)) {
				setX(getX() + speed * Game.deltaTime);

			}

		}
	}
	public void itemCollision(Item i) {
		System.out.println("collision");
//		setX(lastX);
	}
	
	public void update() {
		updateHitbox();
		checkKeys();
	}
}
