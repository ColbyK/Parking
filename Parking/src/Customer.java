
// Class for Customers currently holding tickets
public class Customer {
	// The ticket a customer is holding
	private ParkingTicket ticket;
	// Intended time of stay
	private long stayTime;
	
	public Customer(ParkingTicket newTicket, long time) {
		ticket = newTicket;
		stayTime = varyTime(time);
	}
	
	// Returns the ticket the customer is holding
	public ParkingTicket getTicket() {
		return ticket;
	}
	// Checks if the customer is ready to leave
	public boolean readyToLeave(long currentTime) {
		return (ticket.getInTime() + stayTime < currentTime);
	}
	// Gets a length of time with variance equation
	// Graph: https://i.imgur.com/ZEIFiHk.png
	private long varyTime(long time) {
		double rand = Math.random();
		if(rand < 0.5) {
			return (long) ((1 + 7 * Math.pow(rand - .5, 3)) * time);
		}
		else {
			return (long) ((1 + 6 * Math.pow(rand - .5, 2)) * time);
		}
	}
}
