// Class for Customers currently holding tickets
public class Customer {
	// The ticket a customer is holding
	private ParkingTicket ticket;
	
	public Customer(ParkingTicket newTicket) {
		ticket = newTicket;
	}
	
	// Returns the ticket the customer is holding
	public ParkingTicket getTicket() {
		return ticket;
	}
}
