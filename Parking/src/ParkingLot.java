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
	// The time allowed for being in the lot, after that time essentially pay for a new ticket (price is doubled then tripled and so on).
	private long overtimeLength;
	// Time since start of day for open time of the lot
	private long openTime;
	// Time since start of day for close time of the lot
	private long closeTime;
	// Flag to
	//private boolean priceBranchFlag;
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
	public ParkingLot(int spots, double price, long overtime, long open, long close) {
		maxSpots = spots;
		currentPrice = price;
		overtimeLength = overtime;
		activeTickets = new ArrayList<ParkingTicket>();
		dayLogs = new DayParkingLog[MAX_DAY_LOG_SIZE];
		dayLogSize = 0;
		openTime = open;
		closeTime = close;
	}
	// Creates a ticket with with given time, logs and returns it
	public ParkingTicket requestTicket(long time) {
		if(!isFull()) {
			ParkingTicket ticket = new ParkingTicket(this, currentPrice, time, overtimeLength);
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
	public long getStartDayTime() {
		return openTime;
	}
	public long getCloseDayTime() {
		return closeTime;
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
			dayLogs[dayLogSize++] = new DayParkingLog(ticket.getInTime(), ticket.getPrice());
			dayLogs[dayLogSize - 1].addTicket(ticket);
		}
		else {
			System.out.println("Max day log size reached. Cannot log anymore tickets");
		}
	}
	public void getGeneralReport(String fileName, String name) {
		try {
			PrintWriter writer = new PrintWriter(fileName + name, "UTF-8");
			String concatReport = "";
			for(int i = 0; i < dayLogSize; i++) {
				//writer.write(dayLogs[i].getGeneralReport());
				concatReport += dayLogs[i].getGeneralReport();
			}
			writer.write(concatReport);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void getFullReport(String fileName, String name) {
		try {
			PrintWriter writer = new PrintWriter(fileName + name, "UTF-8");
			String concatReport = "";
			for(int i = 0; i < dayLogSize; i++) {
				//writer.write(dayLogs[i].getFullReport());
				concatReport += dayLogs[i].getFullReport();
			}
			writer.write(concatReport);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void getGeneralForR(String fileName, String name) {
		try {
			PrintWriter writer = new PrintWriter(fileName + name, "UTF-8");
			String concatReport = "";
			for(int i = 0; i < dayLogSize; i++) {
				//writer.write(dayLogs[i].getFullReport());
				concatReport += dayLogs[i].getGeneralReportForR();
			}
			writer.write(concatReport);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void reevaluatePrice() {
		if(dayLogSize == 0) {
			return;
		}
		double originalBasePrice = dayLogs[0].getBasePrice();
		if(dayLogSize == 1) { // Increase original price by 20%
			currentPrice = originalBasePrice + originalBasePrice * 0.2;
		}
		else if(dayLogSize == 2) { // Decrease original price by 20%
			currentPrice = originalBasePrice - originalBasePrice * 0.2;
		}
		else { // Evaluate based on previous info
			DayParkingLog[] sortedList = dayLogs.clone();
			sortByPrice(sortedList, 0, dayLogSize - 1);
			int maxIncomeIndex = getMaxIncomeIndex(sortedList, dayLogSize);
			// Peak income has not been reached for increasing price
			if(maxIncomeIndex == dayLogSize - 1) {
				currentPrice = sortedList[maxIncomeIndex].getBasePrice() + originalBasePrice * 0.2;
			}
			// Peak income has not been reached for decreasing price
			else if(maxIncomeIndex == 0) {
				// TODO set minimum for price reevaluation
				if(sortedList[0].getBasePrice() > 1) {
					currentPrice = sortedList[maxIncomeIndex].getBasePrice() - originalBasePrice * 0.2;
				}
			}
			//Peak income is between the ends of the price range
			else {
				double leftPriceDiff = Math.abs(sortedList[maxIncomeIndex].getBasePrice() - sortedList[maxIncomeIndex - 1].getBasePrice());
				double rightPriceDiff = Math.abs(sortedList[maxIncomeIndex].getBasePrice() - sortedList[maxIncomeIndex + 1].getBasePrice());
				// Left side has larger price difference
				if(leftPriceDiff > rightPriceDiff) {
					currentPrice = sortedList[maxIncomeIndex].getBasePrice() - leftPriceDiff / 2;
				}
				// Right side has larger price difference or the same
				else {
					currentPrice = sortedList[maxIncomeIndex].getBasePrice() + rightPriceDiff / 2;
				}
			}
		}
	}
	private void sortByPrice(DayParkingLog[] list, int low, int high) {
		if(low < high) {
			double pivot = list[high].getBasePrice();
			int index = low - 1;
			for(int i = low; i < high; i++) {
				if(list[i].getBasePrice() < pivot) {
					DayParkingLog temp = list[++index];
					list[index] = list[i];
					list[i] = temp;
				}
			}
			DayParkingLog temp = list[index + 1];
			list[index + 1] = list[high];
			list[high] = temp;
			int partIndex = index + 1;
			/////
			sortByPrice(list, low, partIndex - 1);
			sortByPrice(list, partIndex + 1, high);
		}
	}
	private int getMaxIncomeIndex(DayParkingLog[] list, int size) {
		if(size <= 0) {
			return -1;
		}
		int maxIndex = 0;
		for(int i = 1; i < size; i++) {
			if(list[i].getDayIncome() > list[maxIndex].getDayIncome()) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}
}
