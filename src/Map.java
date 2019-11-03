import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

public class Map implements Paintable {
	
	public static Tile[][] array = new Tile[26][26];
	File location;
	
	public Map() {
		location = new File("./res/defaultmap.csv");
		readMapFromCSV();
	}

	@Override
	public void draw(Graphics G) {
		for(Tile[] t : array) {
			for(Tile i : t) {
				if(i != null)
					i.draw(G);
			}
		}
	}
	
	private void readMapFromCSV() {
		try {
			String line;
			BufferedReader csvReader = new BufferedReader(new FileReader(location));
			
			int j = 0;
			
			while ((line = csvReader.readLine()) != null) {
			    String[] data = line.split(",");
			    //creates new key from name in first column and path in second
			    for(int i = 0; i < data.length; i++) {
			    	Tile t = new Tile(data[i] + "", i, j);
			    	Game.addNewInstance(t);
			    	array[i][j] = t;
			    }
			    j++;
			}
			csvReader.close();
		} catch(Exception e) {
			System.out.println("sucks");
			e.printStackTrace();
		}
		
	}

}
