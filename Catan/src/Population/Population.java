package Population;

import Buildings.Settlement;
import Catan.Inspectable;

public class Population implements Inspectable {
	
	// VARIABLES
	private int size = 100; // Each settlement starts with 100 inhabitants.
	//private double numberHomeless = 0;
	private double overallHappiness;
	private final Settlement associatedSettlement;
	private int numberOfUnemployed;
	private int expectedImmigration;
	private int povertyIndex; // if greater than 0 => not enough grain to feed the population!
	
	// CONSTRUCTOR
	public Population(Settlement settlement){
		associatedSettlement = settlement;
		numberOfUnemployed = calculateNumberOfUnemployed();
		povertyIndex = calculatePoverty();
		overallHappiness = calculateOverallHappinessIndex();
		expectedImmigration = calculateExcpectedImmigration();
	}
		
	// END OF TURN METHODS
	public void endOfTurn(){
		this.numberOfUnemployed = calculateNumberOfUnemployed();
		this.povertyIndex = calculatePoverty();
		this.overallHappiness = calculateOverallHappinessIndex();
		this.expectedImmigration = calculateExcpectedImmigration();
		this.size = size + expectedImmigration;
	}
	
	private double calculateOverallHappinessIndex() { 
		return ((1.3-this.associatedSettlement.getSettlementDecayIndex())-(this.numberOfUnemployed + this.povertyIndex)/this.size);
	}


	private int calculatePoverty() {
		return (int) (this.associatedSettlement.getAccumulatedBuildingMaintenanceCost()[1]);
	}


	private int calculateExcpectedImmigration() {
		return (int) (size*calculateOverallHappinessIndex()-size);
	}


	private int calculateNumberOfUnemployed() {
		int employmentOpportunity = 0;
		for(int i = 0; i < this.associatedSettlement.getAssociatedBuildings().size(); i++){
			employmentOpportunity += this.associatedSettlement.getAssociatedBuildings().get(i).getEmploymentOpportunity();
		}
		
		employmentOpportunity += 50; // Each settlement has a base employement of 50 just to run the settlement...
		
		int unemployed = (int) (this.size - employmentOpportunity - (0.3*this.size)); // 30% of its population are small entrepeneurs or don't need a job (children, etc.)
		
		return unemployed;
	}


	// GETTERS
	public int getSize(){
		return this.size;
	}
	
	public double getOverallHappiness(){
		return this.overallHappiness;
	}
	
	public Settlement getAssociatedSettlment(){
		return this.associatedSettlement;
	}
	
	public int getUnemployed(){
		return this.numberOfUnemployed;
	}
	
	public int getExpectedImmigration(){
		return this.expectedImmigration;
	}
	
	// SETTERS: No setters needed, since these will be changed only from withing this class.
	
	// INTERFACE METHOD
	public void inspect(){
		System.out.println(this.associatedSettlement.getName() + " has a population of " + size + " inhabitants.");
		if(this.numberOfUnemployed<0){
			System.out.println("There are "+ this.numberOfUnemployed +" of job opportunities that still need to be filled.");
		}else{
			System.out.println(this.numberOfUnemployed +" inhabitants are unemployed.");
		}
		System.out.println(this.povertyIndex + " inhabitants struggle to get food on their table.");
	}

}
