package arrayMaths;
public class ValidCostChecker {
	public static boolean isValidCost(double newCost[]){
		if(newCost.length != 6){
			return false; 
		}else{
			for(int i = 0; i < 6; i++){
				if (newCost[i] < 0){
					return false; 		
				}
			}
			return true;
		}
	}
}
