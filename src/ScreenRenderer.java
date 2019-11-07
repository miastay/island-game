import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ScreenRenderer {
	
	public float cameraLocalX;
	public float cameraLocalY;
	
	public int tilesViewedX;
	public int tilesViewedY;
	
	private BufferedImage[] layers = new BufferedImage[3];
	private boolean[] layersStatic = new boolean[] {true, false, false};
	
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	void addSprite(Sprite newSprite) {
		sprites.add(newSprite);
	}
	
	BufferedImage outputAllLayers() {
		updateFrameRegion();
		BufferedImage finalFrame = new BufferedImage(tilesViewedX * Game.TILE_PIXELS, tilesViewedY * Game.TILE_PIXELS, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = finalFrame.createGraphics();
		for(int i = 0; i < layers.length; i++) {
			layers[i] = !layersStatic[i] ? renderSprites(i) : layers[i];
			g.drawImage(layers[i], 0, 0, null);
		}
		
		if(Game.showDebug) {
			g.setColor(Color.RED);
			g.drawString(Game.currentFPS + "fps", 150, 40);
		}
		
		g.dispose();
		return finalFrame;
	}
	
	BufferedImage renderSprites(int layerIndex) {
		BufferedImage currentLayer = new BufferedImage(tilesViewedX * Game.TILE_PIXELS, tilesViewedY * Game.TILE_PIXELS, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = currentLayer.createGraphics();
		for(Sprite currentSprite : sprites) {
			if(currentSprite.renderLayer == layerIndex) {
				if(layersStatic[layerIndex]) {
					g.drawImage(currentSprite.image, (int)(currentSprite.x * Game.TILE_PIXELS), (int)(currentSprite.y * Game.TILE_PIXELS), null);
				} else {
					g.drawImage(currentSprite.image, (int)(currentSprite.x * Game.TILE_PIXELS), (int)(currentSprite.y * Game.TILE_PIXELS), null);	
				}
			} 
		}
		g.dispose();
		return currentLayer;
	}
	
	void updateFrameRegion() {
		tilesViewedX = Game.getFrames()[0].getWidth()/Game.TILE_PIXELS + 1;
		tilesViewedY = Game.getFrames()[0].getHeight()/Game.TILE_PIXELS + 1;
	}
	
	void forceLayerUpdate(int layerIndex) {
		updateFrameRegion();
		layers[layerIndex] = renderSprites(layerIndex);
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