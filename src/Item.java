import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Item implements Updateable {
	
	Rectangle2D.Float hitbox;
	private Sprite sprite;
	private boolean renderHitbox = true;
	private String name;
	private float x, y;
	private float width, height;

	float getX() {return x;}
	float getY() {return y;}
	void setX(float x) {this.x = x;}
	void setY(float y) {this.y = y;}

	public Item(String name) {
		this.name = name;
		setHitbox();
		Game.objects++;
		sprite = new Sprite(ResourceHandler.getImageFromKey(name), 0, 0, 1, 1);
		Game.renderer.addSprite(sprite);
	}
	public Item(String name, float x, float y) {
		this.name = name;
		setX(x); setY(y);
		setHitbox();
		Game.objects++;
		sprite = new Sprite(ResourceHandler.getImageFromKey(name), x, y, 1, 1);
		Game.renderer.addSprite(sprite);
	}

	private void setHitbox() {
		width = (ResourceHandler.getImageFromKey(name).getWidth() + 0f )/ Game.TILE_PIXELS;
		height = (ResourceHandler.getImageFromKey(name).getHeight() + 0f )/ Game.TILE_PIXELS;
		System.out.println(width + "," + height);
		hitbox = new Rectangle.Float(getX(), getY(), width, height);
	}
	private void updateHitbox() {
		hitbox.setRect(x, y, width, height);
	}

	public void update() {
			updateHitbox();
		/*if(hitbox.intersects(Game.player.hitbox) && hitbox != null) {
			System.out.println(hitbox.x + ", " + hitbox.y);
			System.out.println(Game.player.hitbox.x + ", " + Game.player.hitbox.y);
			Game.player.itemCollision(this);
		}*/
	}
	

}
