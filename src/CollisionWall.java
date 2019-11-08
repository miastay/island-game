
public class CollisionWall {
	float x, y;
	float length;
	Orientation orientaion;
	
	static enum Orientation {
		HORIZONTAL,
		VERTICAL
	}
	
	CollisionWall(float x, float y, float length, Orientation orientation){
		this.x = x;
		this.y = y;
		this.length = length;
		this.orientaion = orientation;
	}
}
