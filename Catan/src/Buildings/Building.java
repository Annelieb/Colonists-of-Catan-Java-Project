package Buildings;

import Catan.Inspectable;
import World.Tile;
import World.World;

public abstract class Building implements Inspectable{
	
	final private String name; // Final: the name of the building should not be changed once initialized.
	protected String mapSymbol; // The symbol that should appear on the map. Not final, since it can be changed when a settlement upgrades to a city!
	final protected double[] buildingCost; // Once the building is built => object is created, the building cost is payed and the variable is only kept for indicating how much it had cost. Since we cannot time travel, the cost of the building does not change after it has been built.
	protected double[] maintenanceCost;
	final protected Tile[] associatedTiles; // The tiles the building is built on. 
	final protected int buildingDate; // The turn in which the building was built.
	final protected int employmentOpportunity;
	
	// CONSTRUCTOR; protected so that it can only be called from its subclasses, after verification of the inputs...
	protected Building(String name, double[] buildingCost, double[] maintenanceCost, int employmentOpportunity, World gameWorld, int[] tileRefNums){
		this.name = name; 
		this.mapSymbol = name.substring(0,2); 
		this.buildingCost = buildingCost; 
		this.maintenanceCost = maintenanceCost;
		this.buildingDate = gameWorld.getAge();
		this.associatedTiles = getTfromR(tileRefNums, gameWorld);
		this.employmentOpportunity = employmentOpportunity;
		// Sets the yield for the associated tiles to 0
		for(int i = 0; i < this.associatedTiles.length; i++){
			this.associatedTiles[i].setYield(0);
		};
	}
	
	// GETTERS
	public String getName(){
		return this.name;
	}
	
	public String getMapSymbol(){
		return this.mapSymbol;
	}
	
	public double[] getBuildingCost(){
		return this.buildingCost;
	}
	
	public double[] getMaintenanceCost(){
		return this.maintenanceCost;
	}
	
	public Tile[] getAssociatedTiles(){
		return this.associatedTiles;
	}
	
	public int getBuildingDate(){
		return this.buildingDate;
	}
	
	public int getEmploymentOpportunity(){
		return this.employmentOpportunity;
	}
	
	// SETTERS: Since the variables defined here should not change after initialization, no setters are needed.
	
	// OTHER METHODS: Mostly to check whether a building is buildable.
	/* Checks the validity of the tiles: must be expected number of tiles, must all be in an uninterrupted sequence, 
	   must all be unique, must be free to build on and must not lie on a mountain. */
	protected static boolean checkTiles(World gameWorld, int[] tileRefNumbers, int expectedNumber){		
		if(expectedNumber != tileRefNumbers.length)	{ // Checks whether the number of tiles is as expected.
			return false; 
		}else if(!areDistinct(tileRefNumbers)){ // Checks whether all tiles are unique.
			return false;			
		}else if(!areNeighbors(tileRefNumbers, gameWorld.getXWidth())){ // Checks whether all tiles form one uninterrupted sequence		
			return false;
		}else{
			for(int i = 0; i<expectedNumber; i++){
				Tile toEvaluate = gameWorld.getTiles(tileRefNumbers[i]);
				if(toEvaluate.getAssociatedBuilding() != null){ // Checks whether the tile is still free to build a building on.
					return false;
				}else if(toEvaluate.getRefType() == 2){ // Checks whether the tile is a mountain. (Most buildings cannot be built on a mountain.)
					return false;
				}
			}
			return true;
		}
	}
	
	// Check if all the given tiles are part of an uninterrupted sequence.
	protected static boolean areNeighbors(int[] tileRefNumbers, int xWidth){
		int[] numOfNeighbors = new int[tileRefNumbers.length];
		int totNON = 0;
		for(int i = 0; i < tileRefNumbers.length-1; i++){
			int iyDim = tileRefNumbers[i]/xWidth;
			int ixDim = tileRefNumbers[i]%xWidth;
			if(numOfNeighbors[i] < 2){ // If two different tiles have already been neighbors of i, then we don't need to evaluate this anymore.
				for(int j = i+1; j < tileRefNumbers.length; j++){
					int jyDim = tileRefNumbers[j]/xWidth;
					int jxDim = tileRefNumbers[j]%xWidth;
					if(iyDim == jyDim || ixDim == jxDim) {
						numOfNeighbors[i]++;
						numOfNeighbors[j]++;
					}
					if(numOfNeighbors[i] >= 2){
						break;
					}
				}
			}
			totNON += numOfNeighbors[i];		
		}
		totNON += numOfNeighbors[tileRefNumbers.length-1];
		if(totNON >= (tileRefNumbers.length*2)-2){
			return true;
		}else{
			return false;
		}
	}
	
	// Check for distinct values
	protected static boolean areDistinct(int[] arr){
	    for (int i = 0; i < arr.length-1; i++) {
	        for (int j = i+1; j < arr.length; j++) {
	             if (arr[i] == arr[j]) {
	                 return false;
	             }
	        }
	    }              
	    return true;          
	}
	
	
	// Checks whether all tiles are part of a settlement that is upgraded to a city. 
	public static boolean inCity(int[] tileRefNumbers, World gameWorld){
		for(int i = 0; i < tileRefNumbers.length; i++){
			if(!gameWorld.getTiles(i).getAssociatedSettlement().getUpGradedToCity()){
				return false;
			}
		}
		return true;
	}
	
	// Calculate deforestationCost
	public static double[] calculateDeforestationCost(World gameWorld, int[] tileRefNumbers){
		int NoForest = 0;
		for(int i = 0; i < tileRefNumbers.length; i++){
			if(gameWorld.getTiles(i).getType().equals("Forest")){
				NoForest++;
			}
		}
		double totGrain = NoForest * 75;
		double[] cost = {totGrain, 0, 0, 0, 0, 0};
		return cost;
	}
	
	// Get tiles from references
	public static Tile[] getTfromR(int[] tileRefNumbers, World gameWorld){
		Tile[] tiles = new Tile[tileRefNumbers.length];
		for(int i = 0; i < tileRefNumbers.length; i++){
			tiles[i] = gameWorld.getTiles(i);
		}
		return tiles;
	}
	
	// Set associatedBuilding aspect of tiles
	public static void setAssociatedBuildingOfTiles(World gameWorld, int[] refNumTiles, Building newBuilding){
		for(int i = 0; i < refNumTiles.length; i++){
			gameWorld.getTiles(refNumTiles[i]).setAssociatedBuilding(newBuilding);
		}
	}
}