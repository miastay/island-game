public class Animation {
	String frames;
	float frameRate;
	boolean loopTime;
	Animation defaultExit;
	
	Animation(String frames, float frameRate){
		this.frames = frames;
		this.frameRate = frameRate;
		loopTime = true;
	}
	Animation(String frames, float frameRate, Animation exitTo){
		this.frames = frames;
		this.frameRate = frameRate;
		loopTime = false;
		defaultExit = exitTo;
	}
}
