// Class for each parking simulation instance
public class SimulationInstance {
	private ParkingLot lot;
	private PopulusDemand popDemand;
	private TimePassageSimulation timePassage;
	private ParkingInputData inputData;
	
	// default data instantiation
	public SimulationInstance() {
		inputData = new ParkingInputData();
	}
	// custom data instantiation
	public SimulationInstance(String fileName) {
		inputData = new ParkingInputData(fileName);
	}
}
