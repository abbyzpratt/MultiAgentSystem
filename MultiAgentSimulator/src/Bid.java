import java.util.Comparator;

import madkit.kernel.AgentAddress;

public class Bid {
	AgentAddress name;
	int price;
	Bid(AgentAddress Name, int Price){
		name = Name;
		price = Price; 
	}
	
	public AgentAddress getName() {
		return name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int p ) {
		price = p;
	}
}
	
	
	
	
	


