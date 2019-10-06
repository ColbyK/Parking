import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

// Class for parking lot data
public class ParkingLot {
	// Active tickets for the parking lot, in other words: Tickets that had been created but not yet closed
	private ArrayList<ParkingTicket> activeTickets;
	// A log of all tickets for the parking lot
	private DayParkingLog[] dayLogs;
	// The current size of the ticketLog
	private int dayLogSize;
	// Maximum number of slots in the parking lot
	private int maxSpots;
	// The current price of a ticket 
	private double currentPrice;
	// The time allowed for being in the lot, after that time essentially pay for a new ticket (price is doubled then tripled aand so on).
	private long overtimeLength;
	// Maximum size for the ticketLog
	private static final int MAX_DAY_LOG_SIZE = 250;
	
	public ParkingLot(int spots, double price, long overtime) {
		maxSpots = spots;
		currentPrice = price;
		overtimeLength = overtime;
		activeTickets = new ArrayList<ParkingTicket>();
		dayLogs = new DayParkingLog[MAX_DAY_LOG_SIZE];
		dayLogSize = 0;
	}
	// Creates a ticket with with given time, logs and returns it
	public ParkingTicket requestTicket(long time) {
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
	public double returnTicket(ParkingTicket ticket, long time) {
		for(int i = 0; i < activeTickets.size(); i++) {
			if(ticket == activeTickets.get(i)) {
				activeTickets.remove(i);
				return ticket.outMarker(time);
			}
		}
		System.out.println("No active ticket found. Lost ticket fee charged.");
		if(ticket != null) {
			return ticket.getPrice() * 5;
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
	// Gets the time allowed for the current price
	public long getTimeAllowed() {
		return overtimeLength;
	}
	// Helper method to add tickets to log
	private void logTicket(ParkingTicket ticket) {
		// Check for currently existing day log that pairs with the ticket day. If log exists, add ticket to that log. If log does not exist, create new log and add ticket
		Calendar c = Calendar.getInstance();
		for(int i = 0; i < dayLogSize; i++) {
			c.setTimeInMillis(ticket.getInTime()*1000);
			c.get(Calendar.DATE);
			//System.out.println("BEFORE MONTH : " + dayLogs[i].getMonth() + " == " );
			if(dayLogs[i].getMonth() == c.get(Calendar.MONTH) && dayLogs[i].getDay() == c.get(Calendar.DATE)) {
				dayLogs[i].addTicket(ticket);
				return;
			}
		}
		if(dayLogSize < MAX_DAY_LOG_SIZE) {
			dayLogs[dayLogSize++] = new DayParkingLog(ticket.getInTime());
			dayLogs[dayLogSize - 1].addTicket(ticket);
		}
		else {
			System.out.println("Max day log size reached. Cannot log anymore tickets");
		}
	}
	public void getGeneralReport(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			for(int i = 0; i < dayLogSize; i++) {
				writer.write(dayLogs[i].getGeneralReport());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void getFullReport(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			for(int i = 0; i < dayLogSize; i++) {
				writer.write(dayLogs[i].getFullReport());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
