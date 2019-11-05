import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class ScreenRenderer {
	
	public float cameraLocalX;
	public float cameraLocalY;
	
	public int tileResX = 35;
	public int tileResY = 18;
	
	private BufferedImage[] layers = new BufferedImage[3];
	
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	void addSprite(Sprite newSprite) {
		sprites.add(newSprite);
	}
	
	/*BufferedImage outputLayerFrame(int layer) {
		return new BufferedImage(ColorModel.getRGBdefault(), renderSprites(layer), ColorModel.getRGBdefault().isAlphaPremultiplied(), null);
	}*/
	
	BufferedImage outputAllLayers(boolean[] redrawLayers) {
		updateFrameRegion();
		BufferedImage finalFrame = new BufferedImage(tileResX * Game.TILE_PIXELS, tileResY * Game.TILE_PIXELS, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = finalFrame.createGraphics();
		for(int i = 0; i < redrawLayers.length; i++) {
			layers[i] = redrawLayers[i] ? renderSprites(i) : layers[i];
			g.drawImage(layers[i], 0, 0, null);
		}
		if(Game.showFPS) {
			g.setColor(Color.RED);
			g.drawString(Game.currentFPS + "fps", 150, 40);
		}
		
		g.dispose();
		return finalFrame;
	}
	
	BufferedImage renderSprites(int layer) {
		BufferedImage currentLayer = new BufferedImage(tileResX * Game.TILE_PIXELS, tileResY * Game.TILE_PIXELS, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = currentLayer.createGraphics();
		for(Sprite currentSprite : sprites) {
			if(currentSprite.renderLayer == layer) {
				g.drawImage(currentSprite.image, (int)(currentSprite.x * Game.TILE_PIXELS), (int)(currentSprite.y * Game.TILE_PIXELS), null);
			} 
		}
		g.dispose();
		return currentLayer;
	}
	
	void updateFrameRegion() {
		tileResX = Game.getFrames()[0].getWidth()/Game.TILE_PIXELS + 1;
		tileResY = Game.getFrames()[0].getHeight()/Game.TILE_PIXELS + 1;
	}
}

/*class SpritePriotityQueue {
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
}*/