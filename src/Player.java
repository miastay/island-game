
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player implements Paintable {

	private float speed = 3;
	
	Rectangle2D hitbox;
	private boolean renderHitbox = true;
	private String name;
	private Sprite playerSprite;
	private float x, y;
	
	float getX() {return x;}
	float getY() {return y;}
	void setX(float x) {this.x = x; hitbox.setRect(this.x, y, hitbox.getWidth(), hitbox.getHeight()); playerSprite.x = x;}
	void setY(float y) {this.y = y; hitbox.setRect(x, this.y, hitbox.getWidth(), hitbox.getHeight()); playerSprite.y = y;}
	
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
		hitbox = new Rectangle((int)(ResourceHandler.getImageFromKey(name).getWidth()), (int)(ResourceHandler.getImageFromKey(name).getHeight()));
	}
	private void updateHitbox() {
		hitbox.setRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}
	private void checkKeys() {
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
	
	@Override
	public void draw(Graphics G) {
		if(renderHitbox) {
			updateHitbox();
			G.setColor(Color.ORANGE);
			G.drawRect((int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
		}
		checkKeys();
	}
}
