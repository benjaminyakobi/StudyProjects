package vehicles;

public enum Orientation {
	
	NORTH("north"), 
	SOUTH("south"),
	WEST("west"),
	EAST("east");
	
	private String value;

	private Orientation (String value){
	  this.value = value;
	}

	public String getValue(){
	  return this.value;
	}
	
}
