package Catan;

import java.util.Scanner;

import Buildings.Settlement;
import World.ResourceNameGenerator;
import World.World;

public class Interface {

	public static void main(String[] args) {
		
		String emporerName; 
		String empireName;
		int xWidth; 
		int yWidth;
		Scanner input = new Scanner(System.in);
		String answer;
		World gameWorld;
		
		// Intro to the game:
		System.out.println("Captain Ricky: 'Greetings, it is my understanding that you have requested me here to discuss a great journey ahead?'");
		System.out.println("Captain Ricky: 'Though I am curious to hear the details, it is but human descency to introduce ourselves.'");
		System.out.println("Captain Ricky: 'Of course you have heard of me, the great Captain Ricky, conqueror of the seas,...'");
		System.out.println("Captain Ricky: 'Now, how may I call you?'");
		
		// Getting emporerName
		System.out.println("Please enter your (fictional) name:");		
		emporerName = input.nextLine();
		
		System.out.println("You: 'You may call me " + emporerName +"'\nNow shall we get on to the more pressing discussions?");
		System.out.println("Captain Ricky: 'Ah yes, of course! Rumors have it that you wish to embark an a journey towards new lands?");
		System.out.println("Captain Ricky: 'Can you tell me a little more about these lands you are looking for?");
		System.out.println("\n");
		
		// Getting gameSettings & Making the World
		System.out.println("Would you like to chose the size of your game?");
		answer = input.nextLine();
		
		while(!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
			System.out.println("I'm sorry, I did not understand your answer. Please answer with 'yes' or 'no'.");
			System.out.println("Would you like to chose the size of your game?");
			answer = input.nextLine();
		}
		
		if(answer.equalsIgnoreCase("no")){
			System.out.println("A fully random game is being generated...");
			gameWorld = new World();
		}else{
			System.out.println("Please enter the width of your game: ");		
			xWidth = input.nextInt();
			
			while(xWidth < 6 || xWidth > 50){
				System.out.println("Mmmmm I would really recommend a minimum width of 6 and a maximum of 50... Please try again: ");
				xWidth = input.nextInt();
			}
			
			System.out.println("Please enter the height of your game: ");		
			yWidth = input.nextInt();
			
			while(yWidth < 6 || yWidth > 50){
				System.out.println("Mmmmm I would really recommend a minimum height of 6 and a maximum of 50... Please try again: ");
				yWidth = input.nextInt();
			}
			
			System.out.println("Would you like to further specify the tiles of the game map?");
			System.out.println("Yes/No: ");
			answer = "empty"; // just making sure that it isn't remembering the previous answer...
			answer = input.nextLine();
			
			while(!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
				System.out.println("I'm sorry, I did not understand your answer. Please answer with 'yes' or 'no'.");
				System.out.println("Would you like to chose the size of your game?");
				answer = input.nextLine();
			}
			
			if (answer.equalsIgnoreCase("no")){
				System.out.println("A random map of size " + xWidth + " x " + yWidth + " is being generated...");
				gameWorld = new World(xWidth, yWidth);
			}else{

				int[] locations = new int[xWidth * yWidth];
				int[] tileRefNumbers = new int[xWidth * yWidth];
				
				System.out.println("Please enter a list of locations and tile reference numbers that you would like to specify.");
				System.out.println("A random type will be chosen for tiles that are not specified in your list.");
				System.out.println("Please enter '9999' to indicate the end of your list");
				System.out.println("The format is as follows: tileNumber,typeReference");
				System.out.println("Each combination gives the tileNumber and typeReference number seperated by a single comma. Press enter to continue to the following combination. ");
				System.out.println("After entering all combinations, type 9999 and press enter.");
				System.out.println("Here is an example specifying 3 tiles. Tile 0 as a Field, tile 9 as a Gold Field and tile 17 as a Pasture: ");
				System.out.println("Combination 1: 0,1 ");
				System.out.println("Combination 2: 9,6 ");
				System.out.println("Combination 3: 17,3 "); 
				System.out.println("Combination 4: 9999");
				System.out.println("Here is a reminder for the typeReferences: 1 = Field, 2 = Mountain, 3 = Pasture, 4 = Forest, 5 = Hill, 6 = Gold Field.");
				System.out.println("\n");				
				System.out.println("Enter your combinations: ");
				
				// Note: Something seems to go wrong... the map is not set correcltly... 
				int i = 1;
				System.out.println("Combination " + i + ": ");
				double combo = input.nextDouble();
				
				if(combo == 9999){
					System.out.println("No combinations were given, a random map of size " + xWidth + " x " + yWidth + " is being generated...");	
					gameWorld = new World(xWidth, yWidth);
				}else{
				
				while(combo != 9999){				
					int max = (xWidth * yWidth) - 1;
					
					if(combo < 0 || combo > (xWidth * yWidth) - 1){
						System.out.println("The tile you have specified lies outside of the range of your land, please choose tile values between 0 and " + max + ".");
					}else{
						int tempRef = 0;
						int tempLoc = 0;
						tempLoc = (int) (Math.floor(combo));
						tempRef = (int) ( (combo - tempLoc)*10 );
						
						if(tempRef < 1 || tempRef > 6){
							System.out.println("The type reference number must be equal to 1, 2, 3, 4, 5 or 6. Please try again. ");
						} else {
							locations[i] = tempLoc;
							tileRefNumbers[i] = tempRef;
							i++; // A valid combination was given, now we can continue to the next...
						}
					}
					
					System.out.println("Combination " + i + ": ");
					combo = input.nextDouble();	
				}
				System.out.println( i + " valid combinations were given. A map of size " + xWidth + " x " + yWidth + " containing these specifications is being generated...");
				gameWorld = new World(xWidth, yWidth, locations, tileRefNumbers);
				}
			}
		}
		
		// Making the empire
		System.out.println("Captain Rick: '" + emporerName + ", we have arrived!. Oh dearest leader, after months of sailing across the most dangerous of seas, I almost started doubting you, but you were right afterall!'");
		System.out.println("Captain Rick: 'James just came back from a fast exploration of the new lands and has roughly mapped out the territory...'");
		System.out.println("James: 'It is my pleasure to be pleasing you, great leader, " +emporerName + ", would you like to see the map I drew?'");
		
		answer = "empty"; // just making sure that it isn't remembering the previous answer...
		answer = input.nextLine();
		
		while(!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
			System.out.println("James: 'I'm sorry, I did not understand your answer.' (Please answer with 'yes' or 'no'.)");
			System.out.println("James: 'Would you like to see the map?'");
			answer = input.nextLine();
		}
		
		if(answer.equalsIgnoreCase("yes")){
			gameWorld.draw();
		}
		
		System.out.println("James: 'As you can see on the map, these new found lands are fertile, and full of usefull resources!'");
		System.out.println("Captain Rick: 'Indeed, master " + emporerName + ", as you said before we embarked on this journey, these lands would be a great place to start a new civilization, a new empire...'");
		System.out.println("Captain Rick: 'Lucy! Lucy! Where are you?! Come over here!'");
		System.out.println("Lucy: 'I'm here! What is it Captain?'");
		System.out.println("Captain Rick: 'Send out a messenger right away!'");
		System.out.println("Captain Rick: 'Send a message to the king that we will be starting a new settlment here...'");
		System.out.println("Captain Rick: 'Send a message to the king that we will be starting a new settlment here...'");
		System.out.println("Captain Rick: '... we will build an empire ...'");
		System.out.println("Captain Rick: '... the greatest empire the civalized world has ever seen!'");
		System.out.println("Lucy: '... Uhm,... Sure captain Rick,... and uhm... What would the name of this new empire be?'");
		
		System.out.println("Empire name: "); 
		empireName = input.nextLine();
		
		Empire newEmpire = new Empire(empireName, emporerName, gameWorld);
		
		System.out.println("James: 'Great! The first thing we should do is set up a first settlement, so that we can start harvesting the resources from these lands...'");
		System.out.println("Lucy: 'Good idea James! So where do you recon we could best do that?'");
		System.out.println("James: 'Uhm... I don't know... I'm just a simple fool...'");
		System.out.println("Captain Rick: '" + emporerName + "Perhaps you can guide us?'");
		System.out.println("Captain Rick: 'It is indeed recommended that we start out by building a settlment, but the choice is entirely up to you!");
		
		// Start the actual game play!
		boolean endGame = false; 
		while(!endGame){
			boolean endTurn = false;
			while(!endTurn){
				System.out.print("Captain Rick: 'What would you like to do next?'");
				System.out.print("For a list of commands type 'help'.");
				System.out.print("Type your command here: ");
				
				answer = input.nextLine();
				
				switch(answer){
				
				case "build settlement": 
					
					int TN;
					int[] tileNums = new int[6];
					
					System.out.println("Captain Rick: 'Great idea! Where would you like to build a new settlment?'");
					System.out.println("<A settlement must be built on 4 neighboring tiles, none of which are mountains or already have buildings on them.>");
					System.out.println("Please enter the tiles one by one.");
					
					int i = 1;
					while(i < 5){
						System.out.println("Tile " + i + ": ");
						TN = input.nextInt();
						if(TN < 0 || TN > (gameWorld.getXWidth()*gameWorld.getYWidth()-1)){
							System.out.println("The tile you have specified lies outside of the range of your land, please choose tile values between 0 and " + (gameWorld.getXWidth()*gameWorld.getYWidth()-1) + ".");
						}else{
							tileNums[i] = TN; 
							i++;
						}						
					}
					
					System.out.println("Captain Rick: 'Great! I will send out the explorers immediately!'");
					System.out.println("Captain Rick: 'What shall the name of the new settlement be?'");
					System.out.println("Type the settlement name: ");
					answer = input.nextLine();

					Settlement.build(answer, gameWorld, newEmpire, tileNums);
					
					break;
					
				case "build ...": break; 
				// For each building there should be a case... 
				
				case "upgrade settlement": 
					System.out.println("Captain Rick: 'Ah every successful settlement eventually grows into a thriving city!'"); 
					System.out.println("Captain Rick: 'Which settlement caught your eye?'"); 
					System.out.println("Enter any tile number that is part of the settlement territory: "); 
					
					int tileN = input.nextInt();
					
					if(tileN < 0 || tileN > (gameWorld.getXWidth()*gameWorld.getYWidth()-1)){
						System.out.println("The tile you have specified lies outside of the range of your land, please choose tile values between 0 and " + (gameWorld.getXWidth()*gameWorld.getYWidth()-1) + ".");
					}else{
						if(gameWorld.getTiles(tileN).getAssociatedSettlement() == null){
							System.out.println("This tile does not lie in the territory of a settlement.");
						}else{
							gameWorld.getTiles(tileN).getAssociatedSettlement().upgradeToCity();
						}
					}

				case "get tile info": 
					System.out.println("Which tile would you like to get info on?: ");
					
					tileN = input.nextInt();					
					if(tileN < 0 || tileN > (gameWorld.getXWidth()*gameWorld.getYWidth()-1)){
						System.out.println("The tile you have specified lies outside of the range of your land, please choose tile values between 0 and " + (gameWorld.getXWidth()*gameWorld.getYWidth()-1) + ".");
					}else{
						gameWorld.getTiles(tileN).inspect();
					}
					break;
					
				case "get building info": 
					System.out.println("Which building would you like to get the info on? Enter any tile on which your building is built: ");
					
					tileN = input.nextInt();					
					if(tileN < 0 || tileN > (gameWorld.getXWidth()*gameWorld.getYWidth()-1)){
						System.out.println("The tile you have specified lies outside of the range of your land, please choose tile values between 0 and " + (gameWorld.getXWidth()*gameWorld.getYWidth()-1) + ".");
					}else{
						if(gameWorld.getTiles(tileN).getAssociatedBuilding()==null){
							System.out.println("There is no building on this tile.");
						}else{
							gameWorld.getTiles(tileN).getAssociatedBuilding().inspect();
						}
					}
					break;
				
				case "get settlement info": 
					System.out.println("Which settlement would you like to get the info on? Enter any tile withing the territory of the settlement: ");
					
					tileN = input.nextInt();					
					if(tileN < 0 || tileN > (gameWorld.getXWidth()*gameWorld.getYWidth()-1)){
						System.out.println("The tile you have specified lies outside of the range of your land, please choose tile values between 0 and " + (gameWorld.getXWidth()*gameWorld.getYWidth()-1) + ".");
					}else{
						if(gameWorld.getTiles(tileN).getAssociatedSettlement() == null){
							System.out.println("This tile does not lie within the territory of any settlement.");
						}else{
							gameWorld.getTiles(tileN).getAssociatedSettlement().inspect();
						}
					}
					break;
					
				case "get empire info": 
					newEmpire.inspect();
					break;
				
				case "see map": 
					gameWorld.draw();
					break; 
				
				case "help": 
					System.out.println("Here is a list of valid commands: ");
					System.out.println("build settlement"
							+ "\n upgrade settlement"
							+ "\n get tile info"
							+ "\n get building info"
							+ "\n get settlement info"
							+ "\n get empire info"
							+ "\n see map"
							+ "\n help"
							+ "\n end turn"
							+ "\n end game"
							+ "\n ask loan");

					break; 
				
				case "transfer resources": break; 
				
				case "end turn":
					YearEnd.processEndOfTurn(newEmpire, gameWorld);
					endTurn = true; 
					break; 
				
				case "end game": 
					endTurn = true; 
					endGame = true;
					break;
					
				case "ask loan":
					System.out.println("Specify for each resource how much you would like to loan and how much you would like to pay back each year.");
					double[] loan = new double[6]; 
					double[] payBack = new double[6]; 
					
					for(i = 0; i < 6; i++){
						System.out.println(ResourceNameGenerator.genResourceName(i) + " lending  amount: "); 
						loan[i] = input.nextDouble(); 
						
						while (loan[i] < 0){
							System.out.println("You cannot loan a negative amount. Try again:");
							loan[i] = input.nextDouble();
						}
						
						System.out.println(ResourceNameGenerator.genResourceName(i) + " payBack  amount: "); 
						
						payBack[i] = input.nextDouble();
						while (payBack[i] < 0){
							System.out.println("You cannot pay back a negative amount. Try again:");
							payBack[i] = input.nextDouble();
						}
					}

					System.out.println("Which settlement is asking for the loan? Enter any tile within the territory of the settlement: ");					
					tileN = input.nextInt();					
					if(tileN < 0 || tileN > (gameWorld.getXWidth()*gameWorld.getYWidth()-1)){
						System.out.println("The tile you have specified lies outside of the range of your land, please choose tile values between 0 and " + (gameWorld.getXWidth()*gameWorld.getYWidth()-1) + ".");
					}else{
						if(gameWorld.getTiles(tileN).getAssociatedSettlement() == null){
							System.out.println("This tile does not lie within the territory of any settlement.");
						}else{
							gameWorld.getTiles(tileN).getAssociatedSettlement().askloan(loan, payBack);
						}
					}

				default: System.out.println("Captain Rick: 'I'm sorry, I did not understand your request.'");
			
				}
			}
		}
		
		input.close();
		

	
		
	}

/* 	Note: Some mistakes in this code :-( -> no input checkers... don't know why they don't work... :-(
	
	Problem: For some reason, instead of asking for user input again, 
	the system keeps printing out "The input must be an integer, please try again.",
	without stopping to ask for new user input.
	I don't know why...
	
	public static int scanInt(boolean canBePositive, boolean canBeNegative){
		Scanner scan = new Scanner(System.in);
		int output = 999;
		boolean success = false;
		while(!success){
			try{
				output = scan.nextInt();
				
				if(!canBePositive && output > 0){
					System.out.println("The input must be negative (or 0)!");
					success = false;
				} else if(!canBeNegative && output < 0){
					System.out.println("The input must be negative (or 0)!");
					success = false;
				} else { 
					success = true;
					}
			} catch (InputMismatchException ime){
				System.out.println("The input must be an integer, please try again.");
				success = false;
			}
		}
		scan.close();
		return output;		
	}
	*/
}
