
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Character implements GameObject {

	Rectangle2D hitbox;
	private String name;
	private int x, y;

	int getX() {return x;}
	int getY() {return y;}
	void setX(int x) {this.x = x;}
	void setY(int y) {this.y = y;}

	public Character(String name ) {
		this.name = name;
		Game.objects++;
	}
	
	public void update() {
		
	}

}
