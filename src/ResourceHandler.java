import java.awt.Image.*; 
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class ResourceHandler {

	File source;
	File resnames;
	private static Hashtable<String, BufferedImage> resources = new Hashtable<String, BufferedImage>();
	
	public ResourceHandler( ) {
		this.source = new File("./res/");
		this.resnames = new File("./res/names.csv");
		extractImages();
	}
	
	private void extractImages() {
		
		try {
			String line;
			BufferedReader csvReader = new BufferedReader(new FileReader(resnames));
			while ((line = csvReader.readLine()) != null) {
			    String[] data = line.split(",");
			    //creates new key from name in first column and path in second
			    resources.put(data[0], ImageIO.read(new File(source + "/" + data[1])));
			    System.out.println(data[0] + "," + data[1]);
			}
			csvReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(resources.size());
		
	}
	
	public static BufferedImage getImageFromKey(String key) {
		return resources.get(key);
	}
	
}
