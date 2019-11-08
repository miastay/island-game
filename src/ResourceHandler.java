import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class ResourceHandler {

	File source;
	File resnames;
	private static Hashtable<String, BufferedImage> images = new Hashtable<String, BufferedImage>();
	private static Hashtable<String, BufferedImage[]> animations = new Hashtable<String, BufferedImage[]>();
	
	public ResourceHandler( ) {
		this.source = new File("./res/");
		this.resnames = new File("./res/filenames.csv");
		extractImages();
	}
	
	private void extractImages() {
		
		try {
			String line;
			BufferedReader csvReader = new BufferedReader(new FileReader(resnames));
			while ((line = csvReader.readLine()) != null) {
			    String[] data = line.split(",");
			    //creates new key from name in first column and path in second
			    if(data[2].equals("sprite")) {
			    	images.put(data[0], ImageIO.read(new File(source + "/" + data[1])));
			    } else if (data[2].equals("anim")) {
			    	int frameRes = stringToInt(data[3]);
			    	int frameCount = stringToInt(data[4]);
			    	BufferedImage[] frames = new BufferedImage[frameCount];
			    	BufferedImage spriteSheet = ImageIO.read(new File(source + "/" + data[1]));
			    	int framesPerRow = spriteSheet.getWidth() / frameRes;
			    	System.out.println(spriteSheet.getWidth() + " " + frameRes);
			    	
			    	for(int y = 0; y < (frameCount / framesPerRow) + 1; y++) {
			    		for(int x = 0; y * framesPerRow + x < frameCount && x < framesPerRow; x++) {
			    			frames[y * framesPerRow + x] = spriteSheet.getSubimage(x * frameRes, y * frameRes, frameRes, frameRes);
			    		}
			    	}
			    	
			    	animations.put(data[0], frames);
			    }
			    
			    System.out.println(data[0] + "," + data[1]);
			}
			csvReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(images.size() + animations.size());
		
	}
	
	public static BufferedImage getImageFromKey(String key) {
		return images.get(key);

	}
	public static BufferedImage[] getAnimationFromKey(String key) {
		return animations.get(key);
	}
	
	
	static int stringToInt(String input) {
		char[] chars = input.toCharArray();
		int currentCount = 0;
		for(int i = 0; i < chars.length; i++) {
			currentCount *= 10;
			currentCount += java.lang.Character.getNumericValue(chars[i]);
		}
		return currentCount;
	}
}
