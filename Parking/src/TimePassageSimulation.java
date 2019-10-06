import java.util.ArrayList;

// Uses various input to simulate the passage of time for customers arriving and leaving the parking lot
public class TimePassageSimulation {
	// The rate of activity for the simulation
	private double generalActivity;
	// Current time of the simulation
	private long time;
	// Time increment value
	private long tickTime;
	// Seconds from start of the day the lot opens
	private long simStartDay;
	// Seconds from start of the day the lot closes
	private long simEndDay;
	// Active people in the simulation. In other words, those who have tickets
	private ArrayList<Customer> activePeople;
	public TimePassageSimulation(double activityRate, long tick, long curTime, long startDay, long endDay) {
		generalActivity = activityRate;
		activePeople = new ArrayList<Customer>();
		tickTime = tick;
		time = curTime;
		simStartDay = startDay;
		simEndDay = endDay;
		
	}
	public void tick() {
		time += tickTime;
		//System.out.println(time);
	}
	public void runCurrentTick(PopulusDemand demand, ParkingLot lot) {
		long dayMidnight = SimulationInstance.getEpochTimeOfDay(time, simStartDay);
		runLeavingCustomers(lot);
		if(time < dayMidnight + simEndDay && time > dayMidnight + simStartDay) {
			runNewCustomers(demand, lot);
		}
	}
	// Runs leaving customers section
	private void runLeavingCustomers(ParkingLot lot) {
		for(int i = 0; i < activePeople.size(); i++) {
			if(activePeople.get(i).readyToLeave(time)) {
				lot.returnTicket(activePeople.get(i).getTicket(), time);
				activePeople.remove(i++);
			}
		}
	}
	// Runs new customers section
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
	public long getTime() {
		return time;
	}
}
