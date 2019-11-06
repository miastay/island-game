import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Map {
	
	public static Tile[][] baseArray = new Tile[56][52];
	public static Tile[][] overlayArray = new Tile[56][52];
	File baseLocation, overlayLocation;
	public static List<Rectangle2D.Float> collisionTiles = new ArrayList<Rectangle2D.Float>();
	
	public Map() {
		baseLocation = Game.mapLocation;
		overlayLocation = Game.mapOverlayLocation;
		readMapsFromCSV();
	}

	public void update() {
		for(int i = 0; i < baseArray.length; i++) {
			for(int j = 0; j < baseArray[0].length; j++) {
				if(baseArray[i][j] != null)
					baseArray[i][j].update();
				if(overlayArray[i][j] != null)
					overlayArray[i][j].update();
			}
		}
	}
	
	private void readMapsFromCSV() {
		try {
			String line;
			BufferedReader csvReader = new BufferedReader(new FileReader(baseLocation));
			
			int j = 0;
			
			while ((line = csvReader.readLine()) != null) {
			    String[] data = line.split(",");
			    //creates new key from name in first column and path in second
			    for(int i = 0; i < data.length; i++) {
			    	Tile t;
			    	if(data[i].equals("water")) {
			    		String[] s = {"water1","water2","water3","water4","water5","water6","water7","water8","water9"};
			    		t = new Tile(s, i, j, 2);
			    	} else {
			    		t = new Tile(data[i] + "", i, j, 0);
			    	}
			    	if(data[i].equals("tree-grass1"))
			    		collisionTiles.add(new Rectangle2D.Float(i, j, Game.TILE_PIXELS, Game.TILE_PIXELS));
//			    	Game.addNewInstance(t);
			    	baseArray[i][j] = t;
			    	Game.activeObjects.add(t);
			    }
			    j++;
			}
			csvReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//now handle overlay tiles
		try {
			String line;
			BufferedReader csvReader = new BufferedReader(new FileReader(overlayLocation));
			
			int j = 0;
			
			while ((line = csvReader.readLine()) != null) {
			    String[] data = line.split(",");
			    //creates new key from name in first column and path in second
			    for(int i = 0; i < data.length; i++) {
			    	if(data[i] != "") {
				    	Tile t;
				    	//if overlay tile is going over a water tile, don't put it in layer 2
				    	t = new Tile(data[i] + "", i, j, data[i].contains("sand") ? 1 : 2);
				    	overlayArray[i][j] = t;
				    	Game.activeObjects.add(t);
			    	}
			    }
			    j++;
			}
			csvReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
