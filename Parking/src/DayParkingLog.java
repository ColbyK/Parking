import java.sql.Timestamp;

// Class for the log of each day with each parking ticket recorded
public class DayParkingLog {
	// Ticket log for that day
	private ParkingTicket[] ticketLog;
	// Current size of the ticket log
	private int dayLogSize;
	// Timestamp of the day
	private Timestamp day;
	// Maximum size of the ticket log
	private static final int MAX_LOG_SIZE = 10000;
	
	public DayParkingLog(Timestamp dayMarker) {
		day = dayMarker;
		ticketLog = new ParkingTicket[MAX_LOG_SIZE];
		dayLogSize = 0;
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
		return day.getDate();
	}
	public int getMonth() {
		return day.getMonth();
	}
	public double getDayIncome() {
		double money = 0;
		for(int i = 0; i < ticketLog.length; i++) {
			money += ticketLog[i].getPrice();
		}
		return money;
	}
}
