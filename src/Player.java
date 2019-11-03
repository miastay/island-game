
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player implements Paintable {

	Rectangle2D hitbox;
	private boolean renderHitbox = true;
	private String name;
	private int x, y;
	
	int getX() {return x;}
	int getY() {return y;}
	void setX(int x) {this.x = x; hitbox.setRect(this.x, y, hitbox.getWidth(), hitbox.getHeight());}
	void setY(int y) {this.y = y; hitbox.setRect(x, this.y, hitbox.getWidth(), hitbox.getHeight());}
	
	public Player(String name) {
		this.name = name;
		setHitbox();
		Game.instances++;
	}
	public Player(String name, int x, int y) {
		this.name = name;
		setHitbox();
		setX(x); setY(y);
		Game.instances++;
	}
	
	private void setHitbox() {
		hitbox = new Rectangle((int)(ResourceHandler.getImageFromKey(name).getWidth()*Game.GRAPHICS_SCALE_FACTOR), (int)(ResourceHandler.getImageFromKey(name).getHeight()*Game.GRAPHICS_SCALE_FACTOR));
	}
	@Override
	public void draw(Graphics G) {
		BufferedImage img = ResourceHandler.getImageFromKey(name);
		G.drawImage(img, x, y, (x + (int)(img.getWidth()*Game.GRAPHICS_SCALE_FACTOR)), (y + (int)(img.getHeight()*Game.GRAPHICS_SCALE_FACTOR)), 0, 0, img.getWidth(), img.getHeight(), null);
		if(renderHitbox) {
			G.setColor(Color.BLUE);
			G.drawRect((int)hitbox.getX(), (int)hitbox.getY(), (int)hitbox.getWidth(), (int)hitbox.getHeight());
		}
		System.out.println(Game.detectItemCollision(hitbox));
		if(Game.detectItemCollision(hitbox)) {
			setX((int)(Math.random()*100));
			setY((int)(Math.random()*100));
		}
	}
}
