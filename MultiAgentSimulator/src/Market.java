


import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Collections;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.logging.Level;
import java.util.logging.Level;

import madkit.gui.AgentFrame;
import madkit.kernel.Agent;
import madkit.kernel.AgentAddress;
import madkit.kernel.Madkit;
//import madkit.kernel.Message;
import madkit.message.StringMessage;

public class Market extends Agent {
	 List<Bid> bids=new ArrayList<Bid>();
	int numAgents = 4;
	int numTrades = 4;
	List<Integer> prices = new ArrayList<Integer>();
	

	
	protected void activate() {
		//getLogger().setLevel(Level.FINEST);
		createGroup("World", "Trader");
		requestRole("World", "Trader", "Market");
		
		pause(750);
	}
	
	
	public void live() {
	  StringMessage m;
	  
	  for(int i =0; i<numAgents; i++) {
		  	m =getNextMessage();
		  	String[] mes = parseBid(m.getContent());
		  	 int agentBid =  Integer.parseInt(mes[1]);
		  	 
		  	// get bids
		  	if(mes[0].equals("I")) {
		  		bids.add(new Bid(m.getSender(),agentBid )); // add a new agent to the arraylist 
		  	}
		  	else { 
		  		int j =0;
		  		while(!(bids.get(j).getName().equals(m.getSender()))) {
		  			j++;
		  		}
		  		bids.get(j).setPrice(agentBid);
		  		prices.add((Integer)calcPrice());
		  	}
		  	
		  	pause(500);
	  } 
	  
	  // calculate price
	  Integer pr = (Integer)calcPrice(); 
	  
	  
	  // deal with trades
	  for(int ii = 0; ii< bids.size(); ii++) {
		  int agentBid = bids.get(ii).getPrice();
		  AgentAddress aa =  bids.get(ii).getName();
		
			 System.out.println("BID: " + agentBid);
			 System.out.println("current price: " + pr);
			 
			 if(pr< agentBid) {
				 sendMessage(aa, new StringMessage("+ " + pr.toString()));
			 }
			 else if( pr> agentBid) {
				 sendMessage(aa, new StringMessage("- " + pr.toString()));
			 }
			 else {
				 sendMessage(aa, new StringMessage ("/ "+ pr.toString()));
			 }
			 sendMessage(aa, new StringMessage("END " + "0"));
	  }
	  
	  
	  //sendMessage(aa, new StringMessage("END"));
			 
			//m=(StringMessage) nextMessage();
	  
		
	}
	
	private StringMessage getNextMessage() {
		StringMessage m = (StringMessage) nextMessage();
		 while (m == null) {
	    	   pause(300);
	    	   m = (StringMessage) nextMessage();
	       }
		 return m;
		 
	}
	
	private String[] parseBid(String m ) {

		String[] str = m.split(" ");
		
		return str;
		
		/*if (traders.contains(m.getSender())) {
			int index = traders.indexOf(m.getSender());
			Double pr =  Double.parseDouble(m.toString());
			prices.set(index, pr); 
		}
		else {
			traders.add(m.getSender());
			int index = traders.indexOf(m.getSender());
			Double pr =  Double.parseDouble(m.toString());
			prices.set(index, pr); 
		}
		*/	
	}
	
	private int calcPrice() {
		// assuming all prices are filled
		//*** somehow figure out how create a comparataor**
		int size = bids.size();
		Collections.sort(bids, Comparator.comparing(Bid::getPrice)); 		// sort bids by price;
		int median;
		if ( size% 2 == 0)
		    median = (int) ((bids.get(size/2).getPrice() + bids.get(size/2 -1).getPrice())/2);
		else
		    median = (int) (bids.get(size/2).getPrice());
		return median;
	}
	
	public static void main(String[] argss) {
	       new Madkit("--launchAgents", TraderAgent.class.getName() + ",true, 3;", Market.class.getName() + ",true,1;");
	    }

	    /*
	     * Setting where the agent's window will be.
	     */
	    @Override
	    public void setupFrame(AgentFrame frame) {
	       super.setupFrame(frame);
	    }

}









/*
Double p; // current low price
int aa; // index of agent with lowest big 
pause(100);
nextMessage();
StringMessage m = new StringMessage("+");
sendMessage(other, m);

*/

//get all agents (i thnk
//send current price of stolk to all agents

// accept prices of stalk
 //if price is above 1 agents price, sell ?

//if price is below price buy

//if equal do nothing 
//int i = 0;
//while( i<3) {
	// getLogger().info(nextMessage().toString());
//	 pause(1000);
//}


/** 
 * ok so the market collects all the bids, one at a time. the market then compares each bids to the v
 * current price and adgust
 * 
 * for eaxmple. Agent 1 values it at $10, the current proce is 6, he buys for 6, the value is now 10
 * for his stalks but agent 3 onlyh prices them at 9
 * 
 * Agent 2 values them at 10, he buys them for 9, price is now 10 
 * 
 * agent 3 wants to sell, but no one is bidding so nothing happens 
 * 
 * 
 * I need to keep current low price as current price, as well as all current valueationa
 * so that wehne someone sells the least price then goes to the next leat proce. 
**/



