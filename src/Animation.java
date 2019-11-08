import java.awt.image.BufferedImage;

public class Animation {
	BufferedImage[] frames;
	float frameRate;
	boolean loopTime;
	Animation defaultExit;
	
	Animation(String[] frames, float frameRate){
		for(int i = 0; i < frames.length; i++) {
			this.frames[i] = ResourceHandler.getImageFromKey(frames[i]);
		}
		this.frameRate = frameRate;
		loopTime = true;
	}
	Animation(String[] frames, float frameRate, Animation exitTo){
		for(int i = 0; i < frames.length; i++) {
			this.frames[i] = ResourceHandler.getImageFromKey(frames[i]);
		}
		this.frameRate = frameRate;
		loopTime = false;
		defaultExit = exitTo;
	}
}
