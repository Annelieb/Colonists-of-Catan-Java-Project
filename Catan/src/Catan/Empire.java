package Catan;

import java.util.ArrayList;

import Buildings.Settlement;
import World.ResourceNameGenerator;
import World.World;
import arrayMaths.ValidCostChecker;

public class Empire implements Inspectable{
	// VARIABLES	
	private String empireName;
	private String emperorName;
	// The list of Settlements and Cities that will make up our Empire: 
	private ArrayList<Settlement> settlements = new ArrayList<Settlement>();	
	// Empire Resources: The resources that specifically belong to the empire. Aside from resources from the empire, settlements and cities also each have their own resources.
	private double[] empireResources = {1000, 1000, 1000, 1000, 1000, 1000}; // These are the starting amounts of Grain, Ore, Wool, Lumber, Brick and Gold.	
	// Each empire is built in a world... 
	private World associatedWorld;
	
	// CONSTRUCTOR METHOD
	public Empire(String empireName, String emperorName, World associatedWorld){
		this.empireName = empireName;
		this.emperorName = emperorName;
		this.associatedWorld = associatedWorld;
	}
	
	public Empire(World associatedWorld){
		this("BoredomTown","Boring", associatedWorld);
	}
	
	// Getters
	public ArrayList<Settlement> getSettlements(){
		return this.settlements;
	}
	
	public double[] getResources(){
		return this.empireResources;
	}
	
	public String getEmpireName(){
		return this.empireName; 		
	}
	
	public String getEmperorName(){
		return this.emperorName; 		
	}
	
	public World getAssociatedWorld(){
		return this.associatedWorld;
	}
	
	// Setters
	public void setResources(double[] newResources) throws IllegalArgumentException{
		if(ValidCostChecker.isValidCost(newResources)){ // makes sure all resources are greater or equal to zero. => it is impossible to have negative amount of resources.
			this.empireResources = newResources;
		} else {
			throw new IllegalArgumentException("The resources cannot be negative!");
		}
	}
		
	public void addSettlement(Settlement newSettlement){
		this.settlements.add(newSettlement);
	}
	
	public void changeEmpireName(String newName){
		this.empireName = newName;
	}
	
	public void changeEmperorName(String newName){
		this.emperorName = newName;
	}
	
	// INTERFACE METHOD
	public void inspect(){
		System.out.println("\n");
		System.out.println("The great empire " + this.empireName + ", and its " + this.calculateTotalNofInhabitants() +  " inhabitants, is govourned by " + this.emperorName + ".");		
		System.out.println("Throughought the past " + associatedWorld.getAge() + " years, " + this.empireName + " has expanded and now consists of the following " + this.settlements.size() + " settlements:");
		for (int i = 0; i < this.settlements.size(); i++){
			System.out.println("\n");
			if(this.settlements.get(i).getUpGradedToCity()){
				System.out.println("The city " + this.settlements.get(i).getName() + " with " + this.settlements.get(i).getPopulation().getSize() + " inhabitants.");
			} else{
				System.out.println("The village " + this.settlements.get(i).getName() + " with " + this.settlements.get(i).getPopulation().getSize() + " inhabitants.");
			}			
		}
		System.out.println("The following resources are available at the empire level and can be used for further expansion of the empire:");
		for (int i = 0; i<6; i++){
			System.out.println(ResourceNameGenerator.genResourceName(i) + ": " + this.empireResources[i]); 
		}
		/*System.out.println("The sum of resources of both the empire level and settlement level is:");
		double[] totResources = this.calculateTotalResources();
		for (int i = 0; i<6; i++){
			System.out.println(ResourceNameGenerator.genResourceName(i) + ": " + totResources[i]); 
		}*/
	}
	
	// OTHER METHODS
	public int calculateTotalNofInhabitants(){
		int totInhabitants = 0;
		for(int i = 0; i<this.settlements.size(); i++){
			totInhabitants += this.settlements.get(i).getPopulation().getSize();
		}
		return totInhabitants;		
	}
	
	public double[] calculateTotalResources(){
		double[] totResources = new double[6];
		for(int i = 0; i<this.settlements.size(); i++){
			for(int t = 0; t< 6; t++){
				totResources[t] += this.settlements.get(i).getResources()[t];
			}
		}
		return totResources;
	}
}
