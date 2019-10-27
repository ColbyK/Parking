import java.util.ArrayList;
import java.util.Calendar;

// Uses various input to simulate the passage of time for customers arriving and leaving the parking lot
public class TimePassageSimulation {
	// The rate of activity for the simulation
	private double generalActivity;
	// Current time of the simulation
	private long time;
	// Time increment value
	private long tickTime;
	// Active people in the simulation. In other words, those who have tickets
	private ArrayList<Customer> activePeople;

	public TimePassageSimulation(double activityRate, long tick, long curTime) {
		generalActivity = activityRate;
		activePeople = new ArrayList<Customer>();
		tickTime = tick;
		time = curTime;	
	}
	public void tick() {
		time += tickTime;
	}
	// Checks if new day for lot to reevaluate
	public boolean isReevaluate(long startOfDay, long endOfDay) {
		return !isOpenTimePrevTick(startOfDay, endOfDay) && isOpenTime(startOfDay, endOfDay);
	}
	// Runs the current time tick for all lots
	public void runCurrentTick(PopulusDemand demand, ParkingLot[] lots) {
		long dayMidnight;
		ArrayList<ParkingLot> currentlyOpen = new ArrayList<ParkingLot>();
		// Always run leaving customers
		runLeavingCustomers();
		// Get currently opened lots at this tick time
		for(int i = 0; i < lots.length; i++) {
			dayMidnight = SimulationInstance.getEpochTimeOfDay(time, lots[i].getStartDayTime());
			if(time < dayMidnight + lots[i].getCloseDayTime() && time > dayMidnight + lots[i].getStartDayTime()) {
				currentlyOpen.add(lots[i]);
			}
		}
		ParkingLot[] arr = new ParkingLot[currentlyOpen.size()];
		// Only run new customers for lots that are currently open
		runNewCustomers(demand, currentlyOpen.toArray(arr));
	}
	// Runs leaving customers section
	private void runLeavingCustomers() {
		for(int i = 0; i < activePeople.size(); i++) {
			if(activePeople.get(i).readyToLeave(time)) {
				// Gets the respective lot from the ticket each leaving customer has
				activePeople.get(i).getTicket().getLot().returnTicket(activePeople.get(i).getTicket(), time);
				activePeople.remove(i--);
			}
		}
	}
	// Runs new customers section
	private void runNewCustomers(PopulusDemand demand, ParkingLot[] lots) {
		int newCustomers = (int)(generalActivity * Math.random() * lots.length);
		// Get ordered lots by price in ascending order
		ParkingLot[] orderedLots = orderByPrice(lots);
		for(int i = 0; i < newCustomers; i++) {
			// For every new customer, go through all lots
			for(int k = 0; k < orderedLots.length; k++) {
				// If this lot if full go to next cheapest lot
				if(!orderedLots[k].isFull()) {
					// If this lot if not full and is cheapest, check populus demand for customer agreement on price. If customer does NOT agree on price break so they are not added.
					if(demand.acceptInstance(orderedLots[k].getPrice())) {
						activePeople.add(new Customer(orderedLots[k].requestTicket(time), orderedLots[k].getTimeAllowed()));
					}
					break;
				}
			}
		}
	}
	// Orders lots by base price in ascending order
	private ParkingLot[] orderByPrice(ParkingLot[] lots) {
		ParkingLot[] ordered = lots.clone();
		for(int i = 0; i < ordered.length; i++) {
			for(int k = 0; k < ordered.length - i - 1; k++) {
				if(ordered[k].getPrice() > ordered[k + 1].getPrice()) {
					ParkingLot temp = ordered[k];
					ordered[k] = ordered[k + 1];
					ordered[k + 1] = temp;
				}
			}
		}
		return ordered;
	}
	public long getTime() {
		return time;
	}
	// Checks if time is between start and end for the day
	public boolean isOpenTime(long startOfDay, long endOfDay) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time * 1000);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (c.getTimeInMillis()/1000 + startOfDay <= time && c.getTimeInMillis()/1000 + endOfDay >= time);
	}
	// Checks if previous tick is between start and end of the day (Used for checking if flip of day for price reevaluation)
	public boolean isOpenTimePrevTick(long startOfDay, long endOfDay) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((time - tickTime) * 1000);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (c.getTimeInMillis()/1000 + startOfDay <= (time - tickTime) && c.getTimeInMillis()/1000 + endOfDay >= (time - tickTime));
	}
}
