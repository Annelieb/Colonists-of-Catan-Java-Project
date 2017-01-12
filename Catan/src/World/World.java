package World;

import java.util.Random;

public class World {
	
	// VARIABLES 		
	final private Tile[] tiles; // ???? Not sure if this needs to be 'final' // This is the map the game will be played on. 
	final private int xWidth; // final because once the game is set, the size of the game will not change during the game. Private to ensure that we don't accidentally try to change it from outside of this class. 
	final private int yWidth; // "
	private int age = 0; // Number of years since the new world has been discovered... (= number of turns played)
	
	// CONSTRUCTOR METHOD: Several constructors will be made so that the player can choose some game settings.	
	// The following setting allows the player to choose the width in both directions and the location of several or all tileTypes
	// Recommended minimum width is 6! 
	public World(int xWidth, int yWidth, int[] locations, int[] refTileTypes) throws IllegalArgumentException {
		
		if (locations.length == refTileTypes.length && isValidWidth(xWidth) && isValidWidth(yWidth)){			
			
			int [] tempRefTypes = new int[xWidth*yWidth]; // Creates an array of zeros. 		
			
			for(int i = 0; i < locations.length; i++){
				int l = locations[i];
				tempRefTypes[l] = refTileTypes[i];
			}
			
			this.xWidth = xWidth;
			this.yWidth = yWidth;
				
			tiles = new Tile[(this.xWidth*this.yWidth)];
			
			for(int i = 0; i < (this.xWidth * this.yWidth); i++){
				int t = tempRefTypes[i];
				this.tiles[i] = new Tile(t);		
			}
				
		} else {
			throw new IllegalArgumentException("The lengths of the integer arrays locations and refTileTypes must be equal! Widths must be greater than 0.");
		}
	}
		
	// Choice in xWidth and yWidth, with random tiles
	public World(int xWidth, int yWidth){ 
		this.xWidth = xWidth;
		this.yWidth = yWidth;
			
		tiles = new Tile[(this.xWidth*this.yWidth)];
					
		for(int i = 0; i < (this.xWidth * this.yWidth); i++){
			this.tiles[i] = new Tile(0);		
		}
	}
		
	// Choice in size of a square shaped map
	public World(int sizeXYSquare){
		this(sizeXYSquare, sizeXYSquare);
	}
			
	// Fully random map generation
	public World(){
		int maximum = 20; // !!!!!!! Should check whether this is a good maximum...
		int minimum = 6; // This will allow space to build 1 city. I recommend to make the game much larger! 
			
		Random rn = new Random();
		int range = maximum - minimum + 1;
		this.xWidth =  rn.nextInt(range) + minimum;	
		this.yWidth =  rn.nextInt(range) + minimum;
					
		tiles = new Tile[(this.xWidth*this.yWidth)];
			
		for(int i = 0; i < (this.xWidth * this.yWidth); i++){
			this.tiles[i] = new Tile(0);		
		};	
	}
	
	// GETTERS
	public Tile[] getTiles(){
		return this.tiles;
	}
	
	public Tile getTiles(int i){
		return this.tiles[i];
	}
	
	public int getXWidth(){
		return this.xWidth;
	}
	
	public int getYWidth(){
		return this.yWidth;
	}
	
	public int getAge(){
		return this.age;
	}

	// SETTERS
	public void yearEnd(){
		age++;
	}
	
	// OTHER METHODS
	// Method to draw the map. 
	public void draw(){
		System.out.print("\n");				
			
		for (int i = 0; i < tiles.length; i++){  // Note: Instead of using a for loop and going through this loop every time the map needs to be drawn, it might be better to keep an array of mapSymbols in the map class, have a method change the correct symbol each time a new building is built... 
			if(this.tiles[i].getAssociatedBuilding() == null){
				System.out.print(this.tiles[i].getMapSymbol() + "   ");
			} else {
				System.out.print(this.tiles[i].getAssociatedBuilding().getMapSymbol() + "   ");
			}			
			if((i+1) % this.xWidth == 0){
				System.out.print("\n");				
			}
		}		
		System.out.print("\n");				
	}
		
	// Method to evaluate the width input. 
	public boolean isValidWidth(int width){
		if(width > 0){
			return true;
		} else {
			return false; 
		}
	}		
}
