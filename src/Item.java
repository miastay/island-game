
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Item implements Paintable {
	
	Rectangle2D hitbox;
	private boolean renderHitbox = true;
	private String name;
	private float x, y;

	float getX() {return x;}
	float getY() {return y;}
	void setX(float x) {this.x = x; hitbox.setRect(this.x, y, hitbox.getWidth(), hitbox.getHeight());}
	void setY(float y) {this.y = y; hitbox.setRect(x, this.y, hitbox.getWidth(), hitbox.getHeight());}

	public Item(String name) {
		this.name = name;
		setHitbox();
		Game.objects++;
	}
	public Item(String name, float x, float y) {
		this.name = name;
		setHitbox();
		setX(x); setY(y);
		Game.objects++;
	}

	private void setHitbox() {
		hitbox = new Rectangle((int)(ResourceHandler.getImageFromKey(name).getWidth()), (int)(ResourceHandler.getImageFromKey(name).getHeight()));
	}
	private void updateHitbox() {
		hitbox.setRect(x, y, hitbox.getWidth(), hitbox.getHeight());
	}
	@Override
	public void draw(Graphics G) {
		BufferedImage img = ResourceHandler.getImageFromKey(name);
		Game.renderer.addSprite(img, x, y, 1, 0);
		if(renderHitbox) { 
			updateHitbox();
			G.setColor(Color.orange);
			G.drawRect((int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
		}
	}
	

}
