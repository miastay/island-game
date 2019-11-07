import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ScreenRenderer {
	
	public float cameraLocalX = 0.0f;
	public float cameraLocalY = 0.0f;
	
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
		
		if(Game.showFPS) {
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
					g.drawImage(currentSprite.image, (int)((currentSprite.x - cameraLocalX) * Game.TILE_PIXELS), (int)((currentSprite.y - cameraLocalY) * Game.TILE_PIXELS), null);
				} else {
					g.drawImage(currentSprite.image, (int)((currentSprite.x - cameraLocalX) * Game.TILE_PIXELS), (int)((currentSprite.y - cameraLocalY) * Game.TILE_PIXELS), null);	
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