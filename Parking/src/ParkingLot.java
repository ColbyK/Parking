import java.sql.Timestamp;
import java.util.ArrayList;

// Class for parking lot data
public class ParkingLot {
	 private ArrayList<ParkingTicket> activeTickets;
	 private ParkingTicket[] ticketLog;
	 private int logSize;
	 private int maxSpots;
	 private double currentPrice;
	 private long overtimeLength;
	 
	 private final int MAX_LOG_SIZE = 10000;
	 
	 public ParkingLot(int spots, double price, long overtime) {
		 maxSpots = spots;
		 currentPrice = price;
		 overtimeLength = overtime;
		 activeTickets = new ArrayList<ParkingTicket>();
		 ticketLog = new ParkingTicket[MAX_LOG_SIZE];
		 currentPrice = 0;
	 }
	 
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
	 
	 public boolean isFull() {
		 return (activeTickets.size() >= maxSpots);
	 }
	 public double getPrice() {
		 return currentPrice;
	 }
	 private void logTicket(ParkingTicket ticket) {
		 if(logSize < MAX_LOG_SIZE) {
			 ticketLog[logSize++] = ticket;
		 }
		 else {
			 System.out.println("Max log size reached. Cannot log anymore tickets");
		 }
	 }
}
