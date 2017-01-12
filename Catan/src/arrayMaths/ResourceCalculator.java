package arrayMaths;

public class ResourceCalculator {
	public static double[] add(double[] costs1, double[] costs2) throws IllegalArgumentException {
		double[] newCost = new double[6];
		if( ValidCostChecker.isValidCost(costs1) && ValidCostChecker.isValidCost(costs2) ){
			for(int i = 0; i < 6; i++){
				newCost[i] = costs1[i] + costs2[i];
			}
			return newCost;
		}else{
			throw new IllegalArgumentException("Costs must all be positive!");
		}
	}
	
	public static double[] deduct(double[] resources, double[] costs) throws IllegalArgumentException {
		double[] newResources = new double[6];
		if( enoughResources(resources, costs) ){
			for(int i = 0; i < 6; i++){
				double n = resources[i] - costs[i]; 
				newResources[i] = n;
			}
			return newResources;
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	// Checks whether the city has enough resources to build this building.
	public static boolean enoughResources(double[] resources, double[] buildingCost) throws IllegalArgumentException {
		if( !ValidCostChecker.isValidCost(resources) || !ValidCostChecker.isValidCost(buildingCost) ){
			throw new IllegalArgumentException("All costs must be greater or equal to zero!");
		}else{
			for(int i = 0; i < 6; i++){
				double n = resources[i] - buildingCost[i]; 
				if(n < 0){
					return false;
				}; 
			}
			return true;
		}
	}

}


