package World;

import java.util.Random;

import Buildings.Building;
import Buildings.Settlement;
import Catan.Inspectable;

public class Tile implements Inspectable {
	
	// VARIABLES
	private int refType; // 1 = Fields, 2 = Mountains, 3 = Pasture, 4 = Forest, 5 = Hills, 6 = Gold Field.	
	private String type; // Fields, Mountains, Pasture, Forest, Hills, Gold Field
	private String resource; // Grain, Ore, Wool, Lumber, Bricks, Gold
	
	private double yield = 100; // As soon as this tile is associated to settlement it will start producing resources. 

	private String mapSymbol; // This is the symbol that appears on the map.

	private Settlement associatedSettlement = null;  
	private Building associatedBuilding = null;
		
	
	// CONSTRUCTORS
	// Create a specific tile   !!!!!!! // OPM: need a system to check if the input is correct	
	public Tile(int refType){		
		int refTypeTemp;		
		if(refType == 0){ // 0 indicates that the tileType should be chosen randomly...
			Random ran = new Random();
			refTypeTemp = ran.nextInt(6) + 1; // this returns a random number between (and including) 1 and 6.
		}else{
			refTypeTemp = refType;
		}		
		setRefTypeResource(refTypeTemp); 					
	}
	
	// GETTERS
	public int getRefType(){
		return this.refType;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getResource(){
		return this.resource;
	}
	
	public double getYield(){
		return this.yield;
	}
	
	public String getMapSymbol(){
		return this.mapSymbol;
	}
	
	public Settlement getAssociatedSettlement(){
		return this.associatedSettlement;
	}
	
	public Building getAssociatedBuilding(){
		return this.associatedBuilding;
	}
		
	
	// SETTERS
	// Note: One setter for refType, type, resource and mapSymbol, since these should always change together!
	void setRefTypeResource(int refType) throws IllegalArgumentException{ 
		this.refType = refType;
		switch(refType){
		case 1: this.type = "Field";
				this.resource = "Grain"; 
				this.mapSymbol = "Fi";
				break; 
		case 2: this.type = "Mountain";
				this.resource = "Ore"; 
				this.mapSymbol = "Mo";
				break; 
		case 3: this.type = "Pasture";
				this.resource = "Wool"; 
				this.mapSymbol = "Pa";
				break; 
		case 4: this.type = "Forest";
				this.resource = "Lumber";
				this.mapSymbol = "Fo";
				break; 
		case 5: this.type = "Hill";
				this.resource = "Brick"; 
				this.mapSymbol = "Hi";
				break; 
		case 6: this.type = "Gold Field";
				this.resource = "Gold"; 
				this.mapSymbol = "Go";
				break;
		default: throw new IllegalArgumentException("The input of setRefTypeResource should be an integer between and including 1 and 6. These refer to Field, Mountain, Pasture, Forest, Hill and Gold Field respectively.");
		}
	}
		
	public void setYield(double newYield) throws IllegalArgumentException{
		if (isValidYield(newYield)){
			this.yield = newYield;
		} else {
			throw new IllegalArgumentException("The yield must be a positive double.");
		}		
	}
		
	// Setting the mapSymbol according to a string input with two letters, indicating a settlement or building.	
	public void setMapSymbol(String twoLettersIndicatingTheNewBuilding) throws IllegalArgumentException {
		if (isValidMapSymbol(twoLettersIndicatingTheNewBuilding)){
			this.mapSymbol = twoLettersIndicatingTheNewBuilding;
		} else {
			throw new IllegalArgumentException("The input of setMapSymbol should be a string of two letters depicting a settlement or building.");
		}
	}
	
	public void setAssociatedSettlement(Settlement associatedSettlement){
		this.associatedSettlement = associatedSettlement;
	}
	
	public void setAssociatedBuilding(Building associatedBuilding){
		this.associatedBuilding = associatedBuilding;
	}
	
	
	// INTERFACE METHOD
	@Override
	public void inspect(){
		if(associatedBuilding != null){
			System.out.println("A " + associatedBuilding.getName() + " has been built here.");
			System.out.println("Let me inspect this building for you...");
			this.associatedBuilding.inspect();
		} else if(associatedSettlement == null){
			System.out.println("This is a " + this.type + " outside of the reach of any settlement.");
			System.out.println("Build a settlement nearby to start obtaining " + this.resource + " or to build a building on this tile.");			
		} else {
			System.out.println("This is a " + this.type + " within the reach of the settlement " + associatedSettlement.getName() + ".");
			System.out.println("This " + this.type + " currently yields " + this.yield + this.resource + " each turn.");
		}
		
		if(this.type.equals("Mountain")){
			System.out.println(this.type + "s are very difficult to build on.");
			System.out.println("The only building that can be built here is a mine, which will significantly improve the yield of ore from this tile.");
		}
		else if(this.type.equals("Forest")){
			System.out.println("For some buildings, the " + this.type + " needs to be cut down first to be able to build on it. This might increase the building cost slightly.");
		} else {
			System.out.println( this.type + " are easy to build on. Almost any buildings can be built here, without additional building costs.");
		}
	}
	
	// METHOD to get info on this class
	public static void getInfo(){
		System.out.println("A tile depicts the unit element of a map.");
		System.out.println("At its creation, each tile is given a specific type and corresponding resource.");
		System.out.println("Some examples are type Forset and resource Lumber, type Mountain and resource Ore.");
		System.out.println("Each tile also has a yield, depicting how much of the resource can be obtained at each turn when the tile is within the boundaries of a settlement.");
		System.out.println("Buildings can be built on tiles within the boundaries of a settlement or city.");
	}
	
	// OTHER METHODS
	
	public boolean isValidYield(double newYield){
		if (newYield < 0){
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isValidMapSymbol(String twoLettersIndicatingTheNewBuilding){
		if(twoLettersIndicatingTheNewBuilding.length() == 2 && !twoLettersIndicatingTheNewBuilding.contains(" ")){
			return true;
		} else {
			return false;
		}
	}	

}
