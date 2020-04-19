package vehicles;

public enum Color {
	
	RED("red"), 
	GREEN("green"),
	SILVER("silver"),
	WHITE("white");
	
	private String color;

	private Color (String color){
	  this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
}
