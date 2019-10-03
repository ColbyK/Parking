import java.sql.Timestamp;
import java.text.SimpleDateFormat;

// Class for creating parking ticket objects
public class ParkingTicket {
	// The price of the ticket as noted at the entrance
	private double ticketPrice;
	// The time the ticket was created and car enters the lot
	private Timestamp inTime;
	// The time the ticket is closed and the car leaves the lot
	private Timestamp outTime;
	// The time allowed for each car to stay in the lot
	private long timeSegment;
	
	public ParkingTicket(double price, Timestamp inTimestamp, long time) {
		ticketPrice = price;
		inTime = inTimestamp;
		timeSegment = time;
	}
	
	// Function for closing a ticket
	// Returns the price of the ticket including overtime charges
	public double outMarker(Timestamp outTimestamp) {
		if(outTime == null) {
			outTime = outTimestamp;
			int priceMultiplier = (int)Math.ceil((double)(outTime.getTime() - inTime.getTime()) / timeSegment);
			ticketPrice = priceMultiplier * ticketPrice;
			return ticketPrice;
		}
		else {
			return ticketPrice;
		}
	}
	
	public String toString() {
		if(outTime == null) {
			return "IN: " + getTimestampString(inTime);
		}
		else {
			return "IN: " + getTimestampString(inTime) + " | OUT: " + getTimestampString(outTime) + " | PAY: $" + ticketPrice;
		}	
	}
	private String getTimestampString(Timestamp time) {
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(time);
	}
}
