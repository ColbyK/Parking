// Main class used to run each simulation instance
public class ParkingSimulation {
	public static void main(String[] args) {
		SimulationInstance sim1 = new SimulationInstance();
		sim1.runSimulation();
		sim1.getGeneralReport("report_1_General.txt");
		sim1.getFullReport("report_1_Full.txt");
		sim1.getGeneralReportForR("report_1_R.txt");
	}
}
