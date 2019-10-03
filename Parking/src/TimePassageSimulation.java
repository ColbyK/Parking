import java.sql.Timestamp;
import java.util.ArrayList;

// Uses various input to simulate the passage of time for customers arriving and leaving the parking lot
public class TimePassageSimulation {
	// The rate of activity for the simulation
	private double generalActivity;
	// Current time of the simulation
	private Timestamp time;
	// Time increment value
	private long tickTime;
	// Active people in the simulation. In other words, those who have tickets
	private ArrayList<Customer> activePeople;
	public TimePassageSimulation(double activityRate) {
		generalActivity = activityRate;
		activePeople = new ArrayList<Customer>();
	}
	public void tick() {
		time.setTime(time.getTime() + tickTime);
	}
	public void runCurrentTick(PopulusDemand demand, ParkingLot lot) {
		runLeavingCustomers(lot);
		runNewCustomers(demand, lot);
	}
	private void runLeavingCustomers(ParkingLot lot) {
		for(int i = 0; i < activePeople.size(); i++) {
			
		}
	}
	private void runNewCustomers(PopulusDemand demand, ParkingLot lot) {
		int newCustomers = (int)(generalActivity * Math.random());
		for(int i = 0; i < newCustomers; i++) {
			// Checks if customer is ok with the price
			if(demand.acceptInstance(lot.getPrice())) {
				// Checks if the lot is not full
				if(!lot.isFull()) {
					activePeople.add(new Customer(lot.requestTicket(time), lot.getTimeAllowed()));
				}
			}
		}
	}
}
