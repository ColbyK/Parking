// Main class used to run each simulation instance
public class ParkingSimulation {
	public static void main(String[] args) {
		SimulationInstance sim1 = new SimulationInstance("sim1_Input.txt");
		sim1.runSimulation___();
		sim1.getGeneralReport("report_1_General");
		//sim1.getFullReport("report_1_Full");
		sim1.getGeneralReportForR("report_1_R");
	}
}
