package atlas.utils;

public enum Units {
	Min_Sec(60), Hour_Sec(3600), Day_Sec(86400), Year_Sec(31536000);
	
	private int number;
	
	private Units(int u) {
		this.number = u;
	}
	
	public int getValue() {
		return this.number;
	}

}
