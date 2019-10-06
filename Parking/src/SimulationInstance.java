import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;

// Class for each parking simulation instance
public class SimulationInstance {
	private ParkingLot lot;
	private PopulusDemand popDemand;
	private TimePassageSimulation timePassage;
	private ParkingInputData inputData;
	// Start time of the simulation
	private Timestamp simEnd;
	private long simEnd2;
	// End time of the simulation
	private Timestamp simStart;
	private long simStart2;
	// Default data instantiation
	public SimulationInstance() {
		inputData = new ParkingInputData();
		initData();
	}
	// Custom data instantiation
	public SimulationInstance(String fileName) {
		inputData = new ParkingInputData(fileName);
		initData();
	}
	// Instantiate objects
	private void initData() {
		simStart = getEpochTimeOfDay(Instant.now().getEpochSecond(), inputData.getOpenTime());
		simEnd = getEpochTimeOfDay(simStart.getTime() + 86400 * inputData.getSimDays(), inputData.getOpenTime());
		lot = new ParkingLot(inputData.getMaxSpots(), inputData.getBasePrice(), inputData.getOverTime());
		popDemand = new PopulusDemand(inputData.getPickyness(), inputData.getBasePrice());
		timePassage = new TimePassageSimulation(inputData.getActivityRate(), inputData.getTickTime(), simStart, inputData.getOpenTime(), inputData.getCloseTime());             
		
	}
	public void runSimulation() {
		// Keep running until end of simulation
		System.out.println(timePassage.getTime());
		System.out.println(simEnd.getTime());
		System.out.println(simStart.getTime());
		while(timePassage.getTime() < simEnd.getTime()) {
			timePassage.runCurrentTick(popDemand, lot);
			timePassage.tick();
		}
	}
	public void getGeneralReport(String fileName) {
		lot.getGeneralReport(fileName);
	}
	public void getFullReport(String fileName) {
		lot.getFullReport(fileName);
	}
	// Gets the open time for the day 
	public static Timestamp getEpochTimeOfDay(long dayInTime, long startTime) {
		Timestamp newDay = new Timestamp(dayInTime);
		System.out.println("Start: " + newDay.getTime());
		newDay.setHours(0);
		newDay.setMinutes(0);
		newDay.setSeconds(0);
		newDay.setTime(newDay.getTime() + startTime);
		System.out.println("End: " + newDay.getTime());
		return newDay;
	}
	public static long getEpochTimeOfDay2(long timeToSet, long startTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeToSet * 1000);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTimeInMillis()/1000;
	}
}
