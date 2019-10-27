import java.time.Instant;
import java.util.Calendar;

// Class for each parking simulation instance
public class SimulationInstance {
	private ParkingLot lot;
	private PopulusDemand popDemand;
	private TimePassageSimulation timePassage;
	private ParkingInputData inputData;
	// /////private ParkingInputData[] inputData;
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
		// Keep running until end of simulation + offset for people leaving
		while(timePassage.getTime() < simEnd + 3*inputData.getOverTime()) {
			/////////////for(int i = 0; i < inputData.length; i++)
			if(timePassage.tick() && inputData.getAllowPriceChange()) {
				lot.reevaluatePrice();
			}
			timePassage.runCurrentTick(popDemand, lot);
			/////////////timePassage.runCurrentTick(popDemand, lot, new Customer());
		}
	}
	public void getGeneralReport(String fileName) {
		lot.getGeneralReport(fileName);
	}
	public void getFullReport(String fileName) {
		lot.getFullReport(fileName);
	}
	public void getGeneralReportForR(String fileName) {
		lot.getGeneralForR(fileName);
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
