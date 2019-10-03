import java.sql.Timestamp;
import java.util.ArrayList;

// Class for parking lot data
public class ParkingLot {
	// Active tickets for the parking lot, in other words: Tickets that had been created but not yet closed
	private ArrayList<ParkingTicket> activeTickets;
	// A log of all tickets for the parking lot
	private ParkingTicket[] ticketLog;
	// The current size of the ticketLog
	private int logSize;
	// Maximum number of slots in the parking lot
	private int maxSpots;
	// The current price of a ticket 
	private double currentPrice;
	// The time allowed for being in the lot, after that time essentially pay for a new ticket (price is doubled then tripled aand so on).
	private long overtimeLength;
	// Maximum size for the ticketLog
	private static final int MAX_LOG_SIZE = 25000;
	
	public ParkingLot(int spots, double price, long overtime) {
		maxSpots = spots;
		currentPrice = price;
		overtimeLength = overtime;
		activeTickets = new ArrayList<ParkingTicket>();
		ticketLog = new ParkingTicket[MAX_LOG_SIZE];
		currentPrice = 0;
	}
	// Creates a ticket with with given time, logs and returns it
	public ParkingTicket requestTicket(Timestamp time) {
		if(!isFull()) {
			ParkingTicket ticket = new ParkingTicket(currentPrice, time, overtimeLength);
			logTicket(ticket);
			activeTickets.add(ticket);
			return ticket;
		}
		else {
			return null;
		}
	}
	// Returns a given ticket, and returns the price
	public double returnTicket(ParkingTicket ticket, Timestamp time) {
		for(int i = 0; i < activeTickets.size(); i++) {
			if(ticket == activeTickets.get(i)) {
				activeTickets.remove(i);
				return ticket.outMarker(time);
			}
		}
		System.out.println("No active ticket found. Lost ticket fee charged.");
		if(ticket != null) {
			return ticket.getBasePrice() * 5;
		}
		return currentPrice * 5;
	}
	// Checks if the there is space in the parking lot
	public boolean isFull() {
		return (activeTickets.size() >= maxSpots);
	}
	// Gets the current price for parking in the lot for overTimeLength amount of time
	public double getPrice() {
		return currentPrice;
	}
	// Helper method to add tickets to log
	private void logTicket(ParkingTicket ticket) {
		if(logSize < MAX_LOG_SIZE) {
			ticketLog[logSize++] = ticket;
		}
		else {
			System.out.println("Max log size reached. Cannot log anymore tickets");
		}
	}
}
