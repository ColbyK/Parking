import java.util.Calendar;

// Class for the log of each day with each parking ticket recorded
public class DayParkingLog {
	// Ticket log for that day
	private ParkingTicket[] ticketLog;
	// Current size of the ticket log
	private int dayLogSize;
	// Timestamp of the day
	private long day;
	// Base price for the lot at this day
	private double basePrice;
	// Maximum size of the ticket log
	private static final int MAX_LOG_SIZE = 10000;
	
	public DayParkingLog(long dayMarker, double price) {
		day = dayMarker;
		ticketLog = new ParkingTicket[MAX_LOG_SIZE];
		dayLogSize = 0;
		basePrice = price;
	}
	public void addTicket(ParkingTicket ticket) {
		if(dayLogSize < MAX_LOG_SIZE) {
			ticketLog[dayLogSize++] = ticket;
		}
		else {
			System.out.println("Max ticket log size reached. Cannot log anymore tickets");
		}
	}
	public int getDay() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(day*1000);
		return c.get(Calendar.DATE);
	}
	public int getMonth() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(day*1000);
		return c.get(Calendar.MONTH);
	}
	public String getStringMonth() {
		switch(getMonth()) {
			case 0:
				return "January";
			case 1:
				return "February";
			case 2:
				return "March";
			case 3:
				return "April";
			case 4:
				return "May";
			case 5:
				return "June";
			case 6:
				return "July";
			case 7:
				return "August";
			case 8:
				return "September";
			case 9:
				return "October";
			case 10:
				return "November";
			case 11:
				return "December";
			default:
				return "MONTH ERROR";
		}
	}
	public double getDayIncome() {
		double money = 0;
		for(int i = 0; i < dayLogSize; i++) {
			money += ticketLog[i].getPrice();
		}
		return money;
	}
	// Used for rull report
	public String toString() {
		return getFullReport();
	}
	public String getFullReport() {
		String report = getGeneralReport();
		for(int i = 0 ; i < dayLogSize; i++) {
			report += "" + ticketLog[i] + '\n';
		}
		return report;
	}
	public String getGeneralReport() {
		String report = "";
		report += "======================================\n";
		report += "Day :\t\t\t\t" + getStringMonth() + " " + getDay() + '\n';
		report += "Number of Tickets :\t" + dayLogSize + '\n';
		report += "Income :\t\t\t\t$ " + getDayIncome() + '\n';
		report += "Base Price :\t\t\t$ " + basePrice + "\n";
		return report;
	}
	public String getGeneralReportForR() {
		return basePrice + " " + getDayIncome() + '\n';
	}
	public double getBasePrice() {
		return basePrice;
	}
}
