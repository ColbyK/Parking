import java.text.SimpleDateFormat;
import java.util.Calendar;

// Class for creating parking ticket objects
public class ParkingTicket {
	// The price of the ticket as noted at the entrance
	private double ticketPrice;
	// The time the ticket was created and car enters the lot
	private long inTime;
	// The time the ticket is closed and the car leaves the lot
	private long outTime;
	// The time allowed for each car to stay in the lot
	private long timeSegment;
	// Parking lot associated with the ticket
	private ParkingLot lot;
	
	public ParkingTicket(ParkingLot fromLot, double price, long in, long time) {
		ticketPrice = price;
		inTime = in;
		outTime = -1;
		timeSegment = time;
		lot = fromLot;
	}
	
	// Function for closing a ticket
	// Returns the price of the ticket including overtime charges
	public double outMarker(long outMarker) {
		if(outTime == -1) {
			outTime = outMarker;
			int priceMultiplier = (int)Math.ceil((double)(outTime - inTime) / timeSegment);
			ticketPrice = priceMultiplier * ticketPrice;
			return ticketPrice;
		}
		else {
			return ticketPrice;
		}
	}
	public String toString() {
		if(outTime == -1) {
			return "IN: " + getTimestampString(inTime);
		}
		else {
			return "IN: " + getTimestampString(inTime) + " | OUT: " + getTimestampString(outTime) + " | PAY: $ " + ticketPrice;
		}
	}
	private String getTimestampString(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time*1000);
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(c.getTime());
	}
	public double getPrice() {
		return ticketPrice;
	}
	public long getInTime() {
		return inTime;
	}
	public ParkingLot getLot() {
		return lot;
	}
}
