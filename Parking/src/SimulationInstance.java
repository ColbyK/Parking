import java.time.Instant;
import java.util.Calendar;

// Class for each parking simulation instance
public class SimulationInstance {
	private ParkingLot lot;
	private PopulusDemand popDemand;
	private TimePassageSimulation timePassage;
	private ParkingInputData inputData;
	// Start time of the simulation
	private long simEnd;
	// End time of the simulation
	private long simStart;
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
		simEnd = getEpochTimeOfDay(simStart + 86400 * inputData.getSimDays(), inputData.getOpenTime());
		lot = new ParkingLot(inputData.getMaxSpots(), inputData.getBasePrice(), inputData.getOverTime());
		popDemand = new PopulusDemand(inputData.getPickyness(), inputData.getBasePrice());
		timePassage = new TimePassageSimulation(inputData.getActivityRate() * inputData.getMaxSpots(), inputData.getTickTime(), simStart, inputData.getOpenTime(), inputData.getCloseTime());             
		
	}
	public void runSimulation() {
		// Keep running until end of simulation
		System.out.println(timePassage.getTime());
		System.out.println(simEnd);
		System.out.println(simStart);
		while(timePassage.getTime() < simEnd) {
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
	public static long getEpochTimeOfDay(long timeToSet, long startTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeToSet * 1000);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTimeInMillis()/1000;
	}
}
