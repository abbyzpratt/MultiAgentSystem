import java.util.logging.Level;

import madkit.gui.AgentFrame;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Madkit;
import madkit.kernel.Message;
import madkit.message.StringMessage;

import java.util.Random; 
import java.util.function.Supplier;
public class TraderAgent extends Agent{
	
	int bank = (int) (300*Math.random());
	int shares = 10;
	int p = 0;
	AgentAddress market = null;  
	
	protected void activate() {
		//getLogger().setLevel(Level.FINEST);
		createGroup("World", "Trader");
		requestRole("World", "Trader", "Trader");
		pause(750); 
	} 
	
	public void live() {
		
		AgentAddress someone = enterMarket(); // enter market
		pause(200);
		 p = setPrice();										
		sendMessage(someone, new StringMessage("I " + Integer.toString(p))); // set initial price of stock
		StringMessage m = getNextMessage();
		
		while (!parseMarketReply(m).equals("END")) {
	     m =  getNextMessage();
	       parseMarketReply(m ); 
	   	pause(500);
		}
	

	} 
	
	private StringMessage getNextMessage() {
		StringMessage m = (StringMessage) nextMessage();
		 while (m == null) {
	    	   pause(300);
	    	   m = (StringMessage) nextMessage();
	       }
		 return m;
		 
	}
	
	private String parseMarketReply(StringMessage mes) {
	
		String message = mes.getContent();
		String[] str = message.split(" ");
		String m = str[0];
		p =  Integer.parseInt(str[1]);
		
		 if(m.equals("+")){
				bank = bank-p;
				shares++;
				getLogger().info(this.getName()+ " Buys 1 share for $" + p + "\n  Bank = " + bank + "\n shares = " + shares  );
				return "+";
			} 
		 if(m.equals("-")) {
				bank = bank+p;
				shares--;
				getLogger().info(this.getName()+ "  Sold 1 share for $" + p + "\n  Bank = " + bank + "\n shares = " + shares );
			}
		 if(m.equals("/")) {
				getLogger().info(this.getName() + " Will make no trades  " + "\n  Bank = " + bank + "\n shares = " + shares  );
			}
		 
		 return "END"; 
	}

	private int setPrice() {
		return (int) (bank*Math.random());
	}
	
	private AgentAddress enterMarket() {
		AgentAddress someone = null;  
		while(someone == null) {
			//look for others
			someone = getAgentWithRole("World", "Trader", "Market");
		}
			getLogger().info(this.getName() + " has entered the market");
			return someone;
	}
	
	public void setupFrame(AgentFrame frame) {
	       super.setupFrame(frame);
	       frame.setLocation(100, 320 * (hashCode() - 2));
	    }
	 
	 @SuppressWarnings("unused")
	public static void main(String[] argss) {
	       new Madkit("--launchAgents", TraderAgent.class.getName() + ",true, 4;", Market.class.getName() + ",true,1");
	    }
	
}


/*
 * // market gives price first, you decide to buy or not after
// or do you give the price first and itll buy or sell for you   you send price, not buy/sell
//then trades are made for you , '+' means you bought 1, "-" means you sold
//
 
*/ 
