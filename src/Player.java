import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Player implements Updateable {

	private float speed = 5;
	
	Rectangle2D.Float hitbox;
		private float width, height;
		private boolean isColliding;
	private String name;
	private Sprite playerSprite;
	private float x, y;
	
	public boolean canControl = true;
	
	float getX() {return x;}
	float getY() {return y;}
	void setX(float x) {this.x = x; playerSprite.x = x;}
	void setY(float y) {this.y = y; playerSprite.y = y;}
	
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
		width = (ResourceHandler.getImageFromKey(name).getWidth() + 0f ) / Game.TILE_PIXELS;
		height = (ResourceHandler.getImageFromKey(name).getHeight() + 0f ) / Game.TILE_PIXELS;
		hitbox = new Rectangle.Float(getX(), getY(), width, height);
	}
	private void updateHitbox() {
		hitbox.setRect(x, y, width, height);
	}
	private void checkKeys() {
		if(canControl && !isColliding) {
			
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
			
				if(movementBox.intersects(item.hitbox)){
					if(movementBoxX.intersects(item.hitbox)) {
						setX(getX() < item.getX() ? item.getX() - hitbox.width : item.getX() + item.hitbox.width);
						movementX = 0;
					}
					if(movementBoxY.intersects(item.hitbox)) {
						setY(getY() < item.getY() ? item.getY() - hitbox.height : item.getY() + item.hitbox.height);
						movementY = 0;
					}
				}
			}
			for(Tile tile : Map.collisionTiles) {
				Rectangle2D.Float tileBox = new Rectangle.Float(tile.getX(), tile.getY(), 1.0f, 1.0f);
				if(movementBox.intersects(tileBox)){
					if(movementBoxX.intersects(tileBox)) {
						setX(getX() < tile.getX() ? tile.getX() - hitbox.width - 1.0f / Game.TILE_PIXELS : tile.getX() + tileBox.width);
						movementX = 0;
					}
					if(movementBoxY.intersects(tileBox)) {
						setY(getY() < tile.getY() ? tile.getY() - hitbox.height - 1.0f / Game.TILE_PIXELS : tile.getY() + tileBox.height);
						movementY = 0;
					}
				}
			}
			for(CollisionWall wall : Map.collisionWalls) {
				if(wall.orientaion == CollisionWall.Orientation.VERTICAL) {
					if(getY() + hitbox.height > wall.y && getY() < wall.y + wall.length) {
						if(movementX > 0 && getX() + hitbox.width <= wall.x && getX() + movementX + hitbox.width > wall.x) {
							movementX = 0;
							setX(wall.x - hitbox.width); 
						} else if(movementX < 0 && getX() >= wall.x && getX() + movementX < wall.x){
							movementX = 0;
							setX(wall.x);
						}
					}
				} else if (wall.orientaion == CollisionWall.Orientation.HORIZONTAL) {
					if(getX() + hitbox.width > wall.x && getX() < wall.x + wall.length) {
						if(movementY > 0 && getY() + hitbox.height <= wall.y && getY() + movementY + hitbox.height > wall.y) {
							movementY = 0;
							setY(wall.y - hitbox.height); 
						} else if(movementY < 0 && getY() >= wall.y && getY() + movementY < wall.y){
							movementY = 0;
							setY(wall.y);
						}
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
	
	/*private float dist(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}*/
	
	public void update() {
		updateHitbox();
		checkKeys();
		if(getX() > Game.renderer.tilesViewedX / 2 && getX() < Map.baseArray.length - Game.renderer.tilesViewedX / 2) {
			Game.renderer.cameraLocalX = getX() - Game.renderer.tilesViewedX / 2;
		}
		if(getY() > Game.renderer.tilesViewedY / 2 && getY() < Map.baseArray[0].length - Game.renderer.tilesViewedY / 2) {
			Game.renderer.cameraLocalY = getY() - Game.renderer.tilesViewedY / 2;
		}
	}
}
