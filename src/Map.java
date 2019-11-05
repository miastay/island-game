import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map {
	
	public static Tile[][] array = new Tile[56][52];
	File location;
	
	public Map() {
		location = Game.mapLocation;
		readMapFromCSV();
	}

	public void update() {
		for(Tile[] t : array) {
			for(Tile i : t) {
				if(i != null)
					i.update();
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
			    	Tile t;
			    	if(data[i].equals("water")) {
			    		String[] s = {"water1","water2","water3","water4","water5","water6","water7","water8","water9"};
			    		t = new Tile(s, i, j, 2);
			    	} else {
			    		t = new Tile(data[i] + "", i, j);
			    	}
//			    	Game.addNewInstance(t);
			    	array[i][j] = t;
			    	Game.activeObjects.add(t);
			    }
			    j++;
			}
			csvReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
