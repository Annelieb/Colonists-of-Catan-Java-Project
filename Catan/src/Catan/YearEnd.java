package Catan;

import World.World;

public class YearEnd { // Put everything here that needs to happen when the year ends...
	public static void processEndOfTurn(Empire empire, World world){
		for(int i = 0; i < empire.getSettlements().size(); i++){
			empire.getSettlements().get(i).endOfYearAddYieldsAndDeductCosts();
			empire.getSettlements().get(i).getPopulation().endOfTurn();
		}
	}

}
