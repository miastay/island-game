import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
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
			layers[i] = !layersStatic[i] ? renderViewedSprites(i) : layers[i];
			g.drawImage(layers[i], (int)(-cameraLocalX * Game.TILE_PIXELS), (int)(-cameraLocalY * Game.TILE_PIXELS), null);
		}
		
		if(Game.showDebug) {
			g.setColor(Color.RED);
			g.drawString(Game.currentFPS + "fps", 150, 40);
		}
		
		g.dispose();
		return finalFrame;
	}
	
	
	BufferedImage renderViewedSprites(int layerIndex) {
		BufferedImage currentLayer = new BufferedImage(Game.map.baseArray.length * Game.TILE_PIXELS, Game.map.baseArray[0].length * Game.TILE_PIXELS, BufferedImage.TYPE_INT_ARGB);
		Rectangle2D.Float screenRect = new Rectangle.Float(cameraLocalX, cameraLocalY, tilesViewedX, tilesViewedY);
		Graphics2D g = currentLayer.createGraphics();
		for(Sprite currentSprite : sprites) {
			if(currentSprite.renderLayer == layerIndex && currentSprite.image != null) {
				Rectangle2D.Float spriteRect = new Rectangle.Float(currentSprite.x, currentSprite.y, currentSprite.image.getWidth(), currentSprite.image.getHeight());
				if(spriteRect.intersects(screenRect)) {
					g.drawImage(currentSprite.image, (int)(currentSprite.x * Game.TILE_PIXELS), (int)(currentSprite.y * Game.TILE_PIXELS), null);
				}
			} 
		}
		g.dispose();
		return currentLayer;
	}
	
	BufferedImage renderAllSprites(int layerIndex) {
		BufferedImage currentLayer =  new BufferedImage(Game.map.baseArray.length * Game.TILE_PIXELS, Game.map.baseArray[0].length * Game.TILE_PIXELS, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = currentLayer.createGraphics();
		for(Sprite currentSprite : sprites) {
			if(currentSprite.renderLayer == layerIndex) {
				g.drawImage(currentSprite.image, (int)(currentSprite.x * Game.TILE_PIXELS), (int)(currentSprite.y * Game.TILE_PIXELS), null);
			} 
		}
		g.dispose();
		return currentLayer;
	}
	
	
	void updateFrameRegion() {
		tilesViewedX = Game.getFrames()[0].getWidth()/Game.TILE_PIXELS + 1;
		tilesViewedY = Game.getFrames()[0].getHeight()/Game.TILE_PIXELS + 1;
	}
	
	void forceLayerUpdate(int layerIndex, boolean renderAllSprites) {
		updateFrameRegion();
		if(renderAllSprites) {
			layers[layerIndex] = renderAllSprites(layerIndex);
		} else {
			layers[layerIndex] = renderViewedSprites(layerIndex);
		}
	}
}