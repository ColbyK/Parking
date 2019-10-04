// Class for each parking simulation instance
public class SimulationInstance {
	private ParkingLot lot;
	private PopulusDemand popDemand;
	private TimePassageSimulation timePassage;
	private ParkingInputData inputData;
	
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
	public void initData() {
		lot = new ParkingLot(inputData.getMaxSpots(), inputData.getBasePrice(), inputData.getOverTime());
		popDemand = new PopulusDemand(inputData.getPickyness(), inputData.getBasePrice());
		timePassage = new TimePassageSimulation(inputData.getActivityRate());
	}
	public void runSimulation() {
		
	}
}
