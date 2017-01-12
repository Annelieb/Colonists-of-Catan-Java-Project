package Buildings;

import java.util.ArrayList;
import java.util.Scanner;

import Catan.Empire;
import Population.Population;
import World.ResourceNameGenerator;
import World.Tile;
import World.World;
import arrayMaths.ResourceCalculator;

public class Settlement extends Building {
	
	// VARIABLES
	// Variables to do with building the settlement
	public static final double[] baseBuildingCost = {100, 0, 100, 200, 300, 0}; // Grain, Ore, Wool, Lumber, Bricks, Gold
	public static final double[] baseMaintenanceCost = {0, 0, 5, 10, 15, 0}; // OPM: expensive grain, because people need to eat!
	
	// Variables to do with the settlement itself
	private ArrayList<Building> associatedBuildings = new ArrayList<Building>(); // Each city starts without buildings...
	private ArrayList<Tile> territory = new ArrayList<Tile>();
	private Empire associatedEmpire; // Not that useful for one player, but could become useful for multi-player or computer enemy empires...
    
	// Settlement can become a city.
	private boolean upGradedToCity = false;
	
	// Every settlement has a population
	private Population population;
	
	// Variables to do with the city resources, maintenancecosts, etc.
	private double[] resources = new double[6]; // Each city starts with 0 resources!
	// private double[] maxYield = new double[6]; // Note: Originally I wanted to have the yield vary according to the amount of people working on each field => if for each field there are 10 employment positions, but there are only enough inhabitants to provide each field with 5 employees, the production would be lower. Because of the approaching deadline I temporarily chose not to have the yield vary according to the number of employees.
	// private double[] yearlyBuildingMaintenanceCost = {0, 0, 5, 10, 15, 0}; // When no buildings are there, it starts with the bas maintenance of the settlement itself.
	private double[] accumulatedBuildingMaintenanceCost = new double[6]; // The accumulation of not properly maintaining the settlement.	
	// private double[] populationFoodConsumption = new double[6]; Note: Temporarily deleted due to approaching deadline.
	// private int housing = 300; // If time => add a method to buy more housing...
	private double[] loan = new double[6];
	private double[] payBack = new double[6];
	
	
	// INFO ON CLASS
	public static void getInfo(){
		System.out.println("Each great city started as a small settlement...");
		System.out.println("A settlement has the following costs:");
		for (int i = 0; i<6; i++){
			System.out.println(ResourceNameGenerator.genResourceName(i) + ": building cost: " + baseBuildingCost + " and maintenance cost " + baseMaintenanceCost ); 
		}
		System.out.println("Settlements can be upgraded to a city provided an additional cost of 400 Grain and 300 Ore.");
		System.out.println("Settlements and cities have a population that will grow over time as they become more developped.");
		System.out.println("Settlements and cities will allow you to build other buildings within a radius of 1 or 2 tiles respectively.");
		System.out.println("They also provide the bases for the income of resources from the tiles within their boundaries.");
	}
	
	// CONSTRUCTOR
	private Settlement(String settlementName, double[] buildingCost, World gameWorld, Empire empire, int[] tileRefNums){ // All buildings will be built through the 'CONSTRUCTION SITE' static class method.
		// Create the building
		super(settlementName, buildingCost, baseMaintenanceCost, 0, gameWorld, tileRefNums);
		// Set its empire
		this.associatedEmpire = empire;
		// Pay for the construction of the building
		double[] newResources = ResourceCalculator.deduct(empire.getResources(), buildingCost); //
		empire.setResources(newResources);
		// Set the territory and set the associatedSettlement to the new settlement for all the tiles of the territory.
		findAndAddTerritory(this, tileRefNums, gameWorld, 1);
		// Set the associatedBuilding aspects of the tiles.
		setAssociatedBuildingOfTiles(gameWorld, tileRefNums, this);
		// Set the population
		population = new Population(this);;
	}
	
	// CONSTRUCTION SITE
	public static void build(String settlementName, World gameWorld, Empire empire, int[] tileRefNumbers){
		//1) Check the tiles
		if(!checkTiles(gameWorld, tileRefNumbers, 4)){
			System.out.println("Warning: A settlement can only be built on an uninterrupted sequence of 4 tiles, none of which are mountains and none of which already have a building on them.");
			System.out.println("Because the selected tiles do not comply to these rules, the settlement cannot be built.");
		}  else {
			//2) Check for sufficient funding
			double[] deforestationCost = calculateDeforestationCost(gameWorld, tileRefNumbers);
			double[] buildingCost = ResourceCalculator.add(deforestationCost, baseBuildingCost);
			if(!ResourceCalculator.enoughResources(empire.getResources(), buildingCost)){
				System.out.println("Due to insufficient resources, the settlement cannot be built.");
			} else {
				// If all is well:			
				Settlement newSettlement = new Settlement(settlementName, buildingCost, gameWorld, empire, tileRefNumbers);
				// Add the new settlement to the empire.
				empire.addSettlement(newSettlement);
			}
		}	
	}
	

	// GETTERS
	public boolean getUpGradedToCity(){
		return this.upGradedToCity;
	}
	
	public Population getPopulation(){
		return this.population;
	}
	
	public double[] getResources(){
		return this.resources;
	}
	
	public ArrayList<Tile> getTerritory(){
		return this.territory;
	}
	
	public ArrayList<Building> getAssociatedBuildings(){
		return this.associatedBuildings;
	}
	
	public Empire getAssociatedEmpire(){
		return this.associatedEmpire;
	}
	
	public double[] getLoan(){
		return this.loan;
	}
	
	public double[] getPayBack(){
		return this.payBack;
	}
	
	/*public double[] getYearlyBuildingMaintenanceCost(){
		return this.yearlyBuildingMaintenanceCost;
	}*/
	
	public double[] getAccumulatedBuildingMaintenanceCost(){
		return this.accumulatedBuildingMaintenanceCost;
	}
	
	/*
	public double[] getMaxYield(){
		return this.maxYield;
	}
	*/
	
	// SETTERS
	public void setTerritory(ArrayList<Tile> territory){
		this.territory = territory;
	}
	
	public void addAssociatedBuildings(Building building){
		this.associatedBuildings.add(building);
	}
	
	/*public void setAccumulatedBuildingMainenanceCost(double[] newCosts){
		this.accumulatedBuildingMaintenanceCost = newCosts;
	}
	*/
	
	public void setResources(double[] newResources){
		this.resources = newResources;
	}
	
	// METHOD TO SET A LOAN
	public void askloan(double[] loan, double[] payBack ){
		this.loan = ResourceCalculator.add(this.loan,loan); 
		this.payBack = ResourceCalculator.add(this.payBack,payBack);;
		this.resources = ResourceCalculator.add(loan, this.resources);
	}
	
	
	// Methods to calculate city income and expenses:	
	/*public double[] calculateFoodConsumption(){
		
	}
	
	public double[] calculateYield(){
		
	}
    */
	
	// METHODS: END OF TURN CALCULATIONS

	// Calculate the yield
	public double[] getYield(){
		double[] yield = new double[6];
		for(int i = 0; i < this.territory.size(); i++){
			yield[this.territory.get(i).getRefType()] += this.territory.get(i).getYield();
		}
		return yield;
	}
	
	// Calculate the maintenanceCost
	public double[] getTotalSettlementMaintenanceCost(){
		double[] maintenance = this.maintenanceCost;
		for(int i = 0; i < this.associatedBuildings.size(); i++){
			maintenance = ResourceCalculator.add(maintenance, this.associatedBuildings.get(i).getMaintenanceCost());
		}
		return maintenance;
	}
	
	// End of year calculations
	public void endOfYearAddYieldsAndDeductCosts(){
		this.resources = ResourceCalculator.add(this.resources, this.getYield());
		double[] expenses = this.getTotalSettlementMaintenanceCost();
		expenses[1] += 0.3*this.population.getSize(); // Food cost...
		
		if(ResourceCalculator.enoughResources(loan, payBack)){
			this.loan = ResourceCalculator.deduct(loan, payBack);
			expenses = ResourceCalculator.add(expenses, this.payBack);
		}else{
			for(int i = 0; i < 6; i++){
				if(this.loan[i] >= payBack[i] ){
					this.loan[i] = this.loan[i] - payBack[i]; 
				}else{
					expenses[i] = expenses[i] + (this.payBack[i] - this.loan[i]);
					this.loan[i] = 0;
					this.payBack[i] = 0;				
				}
			}
		}
		
		expenses = ResourceCalculator.add(expenses, this.payBack);
		if(ResourceCalculator.enoughResources(this.resources, expenses) ){
			this.resources = ResourceCalculator.deduct(this.resources, expenses);
		} else {
			for(int i = 0; i < 6; i++){
				if(this.resources[i] >= expenses[i] ){
					this.resources[i] = this.resources[i] - expenses[i]; 
				}else{
					this.resources[i] = 0;
					this.accumulatedBuildingMaintenanceCost[i] = expenses[i] - resources[i];
				}
			}
		}		
	}
	
	
	public int getSettlementDecayIndex(){
		int index;
		int sumAccCost = 0;
		int sumMCost=0;
		for(int i = 1; i < 5; i++){
			sumAccCost += accumulatedBuildingMaintenanceCost[i];
			sumMCost += getTotalSettlementMaintenanceCost()[i];
		}
		index = sumAccCost/sumMCost;
		return index;		
	}
	
	
	// INTERFACE METHOD	
	@Override
	public void inspect(){
		String cityOrSettlement; 
		if(upGradedToCity){
			cityOrSettlement = "city";
		} else {
			cityOrSettlement = "settlement";
		}
		
		System.out.println("The " + cityOrSettlement + " " + this.getName() + " was built in the year " + this.getBuildingDate() + ".");
		
		int decayIndex = getSettlementDecayIndex();
		if( decayIndex == 0){
			System.out.println(this.getName() + " is a wonderful "+ cityOrSettlement +" that is known far and wide to be a center of beauty and flawlessnes!");
		}else if( decayIndex < 0.5){
			System.out.println(this.getName() + " is a beautiful "+ cityOrSettlement +" though as most " + cityOrSettlement + "s a few there are minor restaurations to be done.");
		}else if(decayIndex < 1.5){
			System.out.println(this.getName() + " is in slight decay. Maintenance works are due!");
		}else if (decayIndex < 2.5){
			System.out.println(this.getName() + " is in severe decay!");
		}else{
			System.out.println(this.getName() + " is known far and wide for its horrible infrastructure and unthinkable decay!");
		}
		
		System.out.println("The following buildings were built here: "); 
		for(int i = 0; i < associatedBuildings.size(); i++){
			System.out.print(associatedBuildings.get(i).getName() + "; ");
		}
		
		Scanner scan = new Scanner(System.in);	
		System.out.println("Would you like some more information on the economics of " + this.getName() + "? y/n");

		String answer = scan.nextLine();
		
		if(answer.equals("y")){
			System.out.println("Here is some economical information on the city: "); 		
			double[] maintenance = getTotalSettlementMaintenanceCost();
			double[] yield = getYield();
			for (int i = 0; i<6; i++){
				System.out.println(ResourceNameGenerator.genResourceName(i) + ": currently storred = " + this.resources[i] + "; expected yield = "  + yield[i] + "; expected expenses = " + maintenance[i] + "; extra needed to combat city decay = " + this.accumulatedBuildingMaintenanceCost[i] + "; paying back " + payBack[i] + " of " + loan[i] + " remaining loan."); 
			}
		}
		
		System.out.println(this.getName() + " has " + this.population.getSize() + " inhabitants.");
		
		System.out.println("Would you like some more information on the population of " + this.getName() + "? y/n");

		answer = scan.nextLine();
		
		if(answer.equals("y")){
			this.population.inspect();
		}
		
		scan.close();
	}

	// OTHER METHODS
	private static void findAndAddTerritory(Settlement newSettlement, int[] tileRefNumbers, World gameWorld, int range){
		// Note: the method is set to static because it is called by the static method 'build',
		// 		but it is set to private to ensure that the method can only be called from within this class.
		for(int i = 0; i < tileRefNumbers.length; i++){
			int yDim = tileRefNumbers[i]/gameWorld.getXWidth();
			int xDim = tileRefNumbers[i]%gameWorld.getXWidth();
			int yrmin = yDim - range;
			int yrmax = yDim + range;
			for(int yr = yrmin; yr <= yrmax ; yr ++){
				for(int xr = xDim - range; xr <= xDim + range; xr++){
					if(yr >= 0 && xr >= 0 && yr < gameWorld.getYWidth() && xr < gameWorld.getXWidth()){
						int k = yr * gameWorld.getXWidth() + xr;
						if(gameWorld.getTiles(k).getAssociatedSettlement() == null){ // The tile will only become part of the settlements territory if it did not belong to another settlement already.
							// Set the associatedSettlement aspect of each of the tile
							gameWorld.getTiles(k).setAssociatedSettlement(newSettlement);
							// Add the tile to the settlement territory
							newSettlement.territory.add(gameWorld.getTiles(k));
						}
					}
				}
			}
		}
	}
	
	// Method to upgrade the settlement to a city.	
	public void upgradeToCity(){
		double[] cost = {400, 300, 0, 0, 0, 0};
		if(this.upGradedToCity){
			System.out.println(this.getName() + " is already a city!");
		}else if(ResourceCalculator.enoughResources(this.resources, cost)){
			// Pay
			this.resources = ResourceCalculator.deduct(resources, cost);
			// Upgrade
			this.upGradedToCity = true;
			// Enlarge the territory
			int [] tileRefNums = findIndexOfTiles(this.associatedTiles, this.associatedEmpire.getAssociatedWorld()); 
			findAndAddTerritory(this, tileRefNums, this.associatedEmpire.getAssociatedWorld(), 2);
		}else{
			System.out.println("The settlement has insufficent resources to upgrade to a city.");
		}					
	}
	
	public int[] findIndexOfTiles(Tile[] tiles, World yourWorld){
		int [] indeces = new int[tiles.length];
		for(int i = 0; i < tiles.length; i++){
			for (int j = 0; j < yourWorld.getTiles().length; j++){
				if(tiles[i] == yourWorld.getTiles(j)){
					indeces[i] = j;
				}
			}
		}
		return indeces;
	}
	



}
