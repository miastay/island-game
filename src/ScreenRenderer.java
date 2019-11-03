import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class ScreenRenderer {
	
	public float cameraLocalX;
	public float cameraLocalY;
	
	public int tileResX = 32;
	public int tileResY = 18;
	
	BufferedImage currentFrame;
	
	void newFrame() {
		currentFrame = new BufferedImage(tileResX * 50, tileResY * 50, BufferedImage.TYPE_INT_ARGB);
	}
	
	
	void addSprite(BufferedImage img, int x, int y) {
		Raster spriteData = img.getData();
		currentFrame.setData(spriteData);
	}
	
	BufferedImage outputFrame() {
		return currentFrame;
	}
	
}