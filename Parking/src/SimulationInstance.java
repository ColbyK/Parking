import java.time.Instant;
import java.util.Calendar;

// Class for each parking simulation instance
public class SimulationInstance {
	//private ParkingLot lot; // TODO DELETE
	private ParkingLot[] lots;
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
		simStart = getEpochTimeOfDay(Instant.now().getEpochSecond(), inputData.getEarliestOpenTime());
		simEnd = getEpochTimeOfDay(simStart + 86400 * inputData.getSimDays(), inputData.getEarliestOpenTime());
		//lot = new ParkingLot(inputData.getMaxSpots(), inputData.getBasePrice(), inputData.getOverTime());
		///////
		lots = new ParkingLot[inputData.getNumLots()];
		for(int i = 0; i < lots.length; i++) {
			lots[i] = new ParkingLot(inputData.getMaxSpots___()[i], inputData.getBasePrice___()[i], inputData.getOverTime___()[i], inputData.getOpenTime___()[i], inputData.getCloseTime___()[i]);
		}
		///////
		popDemand = new PopulusDemand(inputData.getPickyness(), inputData.getStandardPrice());
		//timePassage = new TimePassageSimulation(inputData.getActivityRate() * inputData.getMaxSpots(), inputData.getTickTime(), simStart, inputData.getOpenTime(), inputData.getCloseTime());             
		timePassage = new TimePassageSimulation(inputData.getActivityRate() * inputData.getLargestMaxSpots(), inputData.getTickTime(), simStart);
	}
	/*public void runSimulation() {
		// Keep running until end of simulation + offset for people leaving
		while(timePassage.getTime() < simEnd + 3*inputData.getOverTime()) {
			/////////////for(int i = 0; i < inputData.length; i++)
			if(timePassage.tick() && inputData.getAllowPriceChange()) {
				lot.reevaluatePrice();
			}
			timePassage.runCurrentTick(popDemand, lot);
			/////////////timePassage.runCurrentTick(popDemand, lot, new Customer());
		}
	}*/
	public void runSimulation___() {
		// Keep running until end of simulation + offset for people leaving
		while(timePassage.getTime() < simEnd + 3*inputData.getLargestOverTime()) {
			timePassage.tick___();
			for(int i = 0; i < lots.length; i++) {
				if(timePassage.isReevaluate(lots[i].getStartDayTime(), lots[i].getCloseDayTime()) && inputData.getAllowPriceChange()) {
					lots[i].reevaluatePrice();
				}
				timePassage.runCurrentTick(popDemand, lots);
			}
			/////////////timePassage.runCurrentTick(popDemand, lot, new Customer());
		}
	}
	public void getGeneralReport(String fileName) {
		for(int i = 0; i < lots.length; i++) {
			lots[i].getGeneralReport(fileName, "_" + i + ".txt");
		}
	}
	public void getFullReport(String fileName) {
		for(int i = 0; i < lots.length; i++) {
			lots[i].getFullReport(fileName, "_" + i + ".txt");
		}
	}
	public void getGeneralReportForR(String fileName) {
		for(int i = 0; i < lots.length; i++) {
			lots[i].getGeneralForR(fileName, "_" + i + ".txt");
		}
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
