import java.awt.image.BufferedImage;

public class Sprite {
		BufferedImage image;
		float x;
		float y;
		float scale;
		
		Sprite(BufferedImage img, float x, float y, float scale) {
			this.image = img;
			this.x = x;
			this.y = y;
			this.scale = scale;
		}
}
