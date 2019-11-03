import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class ScreenRenderer {
	
	public float cameraLocalX;
	public float cameraLocalY;
	
	public int tileResX = 32;
	public int tileResY = 18;
	
	BufferedImage currentFrame;
	WritableRaster frameRaster;
	SpritePriotityQueue queue;
	
	void newFrame() {
		currentFrame = new BufferedImage(tileResX * 50, tileResY * 50, BufferedImage.TYPE_INT_ARGB);
		frameRaster = currentFrame.getRaster();
		queue = new SpritePriotityQueue();
	}
	
	void addSprite(BufferedImage img, int x, int y, float scale, int layer) {
		queue.Queue(new Sprite(img, x, y, scale), layer);
	}
	
	BufferedImage outputFrame() {
		renderSprites();
		currentFrame = new BufferedImage(ColorModel.getRGBdefault(), frameRaster, ColorModel.getRGBdefault().isAlphaPremultiplied(), null);
		return currentFrame;
	}
	
	void renderSprites() {
		while(queue.sprites.size() != 0) {
			Sprite currentSprite = queue.Dequeue();
			Raster spriteData = currentSprite.image.getData();
			frameRaster.setRect((int)currentSprite.x, (int)currentSprite.y, spriteData);
		}
	}
}

class SpritePriotityQueue {
	ArrayList<Sprite> sprites;
	ArrayList<Integer> layers;
	
	SpritePriotityQueue(){
		sprites = new ArrayList<Sprite>();
		layers = new ArrayList<Integer>();
	}
	
	void Queue(Sprite sprite, int layer) {
		sprites.add(sprite);
		layers.add(layer);
	}
	
	Sprite Dequeue() {
		int lowestLayer = Integer.MAX_VALUE;
		int lowestLayerIndex = 0;
		for(int i = 0; i < sprites.size(); i++) {
			if(layers.get(i) < lowestLayer) {
				lowestLayerIndex = i;
				lowestLayer = layers.get(i);
			}
		}
		Sprite output = sprites.get(lowestLayerIndex);
		sprites.remove(lowestLayerIndex);
		layers.remove(lowestLayerIndex);
		return output;
	}
}