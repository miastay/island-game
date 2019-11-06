
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player implements Updateable {

	private float speed = 3;
	
	Rectangle2D.Float hitbox;
		private float width, height;
		private boolean isColliding;
	private boolean renderHitbox = true;
	private String name;
	private Sprite playerSprite;
	private float x, y;
	
	public boolean canControl = true;
	
	float getX() {return x;}
	float getY() {return y;}
	void setX(float x) {this.x = x; playerSprite.x = x;}
	void setY(float y) {this.y = y; playerSprite.y = y;}
	
	private float lastX, oldestX, lastY, oldestY;
	
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
		if(canControl && !isColliding) {
			oldestX = lastX;
			oldestY = lastY;
			lastX = getX();
			lastY = getY();
			
			float movementX = 0.0f;
			float movementY = 0.0f;
			
			if(Game.keylist.getKey(KeyEvent.VK_A)) {
				movementX = -speed * Game.deltaTime;
			}
			if(Game.keylist.getKey(KeyEvent.VK_W)) {
				movementY = -speed * Game.deltaTime;
			}
			if(Game.keylist.getKey(KeyEvent.VK_S)) {
				movementY = speed * Game.deltaTime;
			}
			if(Game.keylist.getKey(KeyEvent.VK_D)) {
				movementX = speed * Game.deltaTime;
			}

			Rectangle2D movementBox = new Rectangle2D.Float(getX() + movementX, getY() + movementY, width, height);
			Rectangle2D movementBoxX = new Rectangle2D.Float(getX() + movementX, getY(), width, height);
			Rectangle2D movementBoxY = new Rectangle2D.Float(getX(), getY() + movementY, width, height);
			for(Item item : Game.items) {
				
				
				//float closestX = getX() + movementX - item.getX() < item.getX() + item.hitbox.width - getX() ? getX() + movementX - item.getX() : item.getX() + item.hitbox.width - getX();
				//float closestY = getY() + movementY - item.getY() < item.getY() + item.hitbox.height - getY() ? getY() + movementY - item.getY() : item.getY() + item.hitbox.height - getY();

				if(movementBox.intersects(item.hitbox)){
					if(movementBoxX.intersects(item.hitbox)) {
						setX(movementX > 0 ? item.getX() - (item.hitbox.width / 2 + hitbox.width / 2) : item.getX() + (item.hitbox.width / 2 + hitbox.width / 2));
						movementX = 0;
					}
					if(movementBoxY.intersects(item.hitbox)) {
						setY(movementY > 0 ? item.getY() - (item.hitbox.height / 2 + hitbox.height / 2) : item.getY() + (item.hitbox.height / 2 + hitbox.height / 2));
						movementY = 0;
					}
				}
			}
			if(movementX != 0 && movementY != 0) {
				movementX *= 1 / Math.sqrt(2);
				movementY *= 1 / Math.sqrt(2);
			}
			
			setX(getX() + movementX);
			setY(getY() + movementY);
		}
	}
	public void itemCollision(Item i) {
		System.out.println("collision");
		isColliding = true;
		setY(oldestY);
		setX(oldestX);
//		setY(lastY > getY() ? (lastY+Game.deltaTime*speed) : (lastY-Game.deltaTime*speed));
//		setX(lastX > getX() ? (lastX+Game.deltaTime*speed) : (lastX-Game.deltaTime*speed));
		isColliding = false;
//		switch(outcode) {
//		case Rectangle2D.OUT_BOTTOM :
//			setY(lastY + Game.deltaTime);
//		case Rectangle2D.OUT_LEFT :
//			setX(lastX - Game.deltaTime);
//		case Rectangle2D.OUT_RIGHT :
//			setX(lastX + Game.deltaTime);
//		case Rectangle2D.OUT_TOP :
//			setY(lastY - Game.deltaTime);
//		}
			
	}
	
	private float dist(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}
	
	public void update() {
		updateHitbox();
		checkKeys();
	}
}
