import java.sql.Timestamp;
import java.util.ArrayList;

// Uses various input to simulate the passage of time for customers arriving and leaving the parking lot
public class TimePassageSimulation {
	// The rate of activity for the simulation
	private double generalActivity;
	// Current time of the simulation
	private Timestamp time;
	// Active people in the simulation. In other words, those who have tickets
	private ArrayList<Customer> activePeople;
	public TimePassageSimulation(double activityRate) {
		generalActivity = activityRate;
		activePeople = new ArrayList<Customer>();
	}
}
