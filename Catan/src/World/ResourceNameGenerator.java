package World;

public class ResourceNameGenerator {
	public static String genResourceName(int resourceRefNum) throws IllegalArgumentException {
		switch(resourceRefNum+1){
			case 1: return("Grain");
			case 2: return("Ore");
			case 3: return("Wool");
			case 4: return("Lumber");
			case 5: return("Brick");
			case 6: return("Gold");
			default: throw new IllegalArgumentException("The input of setRefTypeResource should be an integer between and including 1 and 6. These refer to Field, Mountain, Pasture, Forest, Hill and Gold Field respectively.");
		}
	}
}
