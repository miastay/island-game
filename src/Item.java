
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Item implements Paintable {
	
	Rectangle2D hitbox;
	private boolean renderHitbox = true;
	private String name;
	private int x, y;

	int getX() {return x;}
	int getY() {return y;}
	void setX(int x) {this.x = x; hitbox.setRect(this.x, y, hitbox.getWidth(), hitbox.getHeight());}
	void setY(int y) {this.y = y; hitbox.setRect(x, this.y, hitbox.getWidth(), hitbox.getHeight());}

	public Item(String name) {
		this.name = name;
		setHitbox();
		Game.objects++;
	}
	public Item(String name, int x, int y) {
		this.name = name;
		setHitbox();
		setX(x); setY(y);
		Game.objects++;
	}

	private void setHitbox() {
		hitbox = new Rectangle((int)(ResourceHandler.getImageFromKey(name).getWidth()*Game.GRAPHICS_SCALE_FACTOR), (int)(ResourceHandler.getImageFromKey(name).getHeight()*Game.GRAPHICS_SCALE_FACTOR));
	}
	private void updateHitbox() {
		hitbox.setRect(x*Game.TILE_SIZE, y*Game.TILE_SIZE, hitbox.getWidth()*Game.GRAPHICS_SCALE_FACTOR, hitbox.getHeight()*Game.GRAPHICS_SCALE_FACTOR);
	}
	@Override
	public void draw(Graphics G) {
		BufferedImage img = ResourceHandler.getImageFromKey(name);
		Game.renderer.addSprite(img, x*Game.TILE_SIZE, y*Game.TILE_SIZE, Game.GRAPHICS_SCALE_FACTOR, 0);
		if(renderHitbox) { 
			updateHitbox();
			G.setColor(Color.orange);
			G.drawRect((int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
		}
	}
	

}
