import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// Class for creating parking ticket objects
public class ParkingTicket {
	// The price of the ticket as noted at the entrance
	private double ticketPrice;
	// The time the ticket was created and car enters the lot
	private Timestamp inTime;
	private long inTime2;
	// The time the ticket is closed and the car leaves the lot
	private Timestamp outTime;
	private long outTime2;
	// The time allowed for each car to stay in the lot
	private long timeSegment;
	
	public ParkingTicket(double price, Timestamp inTimestamp, long time) {
		ticketPrice = price;
		inTime = inTimestamp;
		timeSegment = time;
	}
	public ParkingTicket(double price, long in, long time) {
		ticketPrice = price;
		inTime2 = in;
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
	public double outMarker(long outMarker) {
		if(outTime == null) {
			outTime2 = outMarker;
			int priceMultiplier = (int)Math.ceil((double)(outTime2 - inTime2) / timeSegment);
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
	public String toString2() {
		if(outTime == null) {
			return "IN: " + getTimestampString2(inTime2);
		}
		else {
			return "IN: " + getTimestampString2(inTime2) + " | OUT: " + getTimestampString2(outTime2) + " | PAY: $" + ticketPrice;
		}
	}
	private String getTimestampString(Timestamp time) {
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(time);
	}
	private String getTimestampString2(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time*1000);
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(c.getTime());
	}
	public double getPrice() {
		return ticketPrice;
	}
	public Timestamp getInTime() {
		return (Timestamp)inTime.clone();
	}
	public long getInTime2() {
		return inTime2;
	}
}
