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
	// Seconds from start of the day the lot opens
	private long simStartDay; // TODO DELETE
	// Seconds from start of the day the lot closes
	private long simEndDay; // TODO DELETE
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
	public TimePassageSimulation(double activityRate, long tick, long curTime) {
		generalActivity = activityRate;
		activePeople = new ArrayList<Customer>();
		tickTime = tick;
		time = curTime;	
	}
	// Returns true if the tick results in a day at opening time
	public boolean tick() {
		boolean before = isOpenTime();
		time += tickTime;
		return !before && isOpenTime();
	}
	public void tick___() {
		time += tickTime;
	}
	public boolean isReevaluate(long startOfDay, long endOfDay) {
		return !isOpenTimePrevTick(startOfDay, endOfDay) && isOpenTime(startOfDay, endOfDay);
	}
	public void runCurrentTick(PopulusDemand demand, ParkingLot lot) {
		long dayMidnight = SimulationInstance.getEpochTimeOfDay(time, simStartDay);
		runLeavingCustomers(lot);
		if(time < dayMidnight + simEndDay && time > dayMidnight + simStartDay) {
			runNewCustomers(demand, lot);
		}
	}
	public void runCurrentTick(PopulusDemand demand, ParkingLot[] lots) {
		long dayMidnight;
		ArrayList<ParkingLot> currentlyOpen = new ArrayList<ParkingLot>();
		runLeavingCustomers();
		for(int i = 0; i < lots.length; i++) {
			dayMidnight = SimulationInstance.getEpochTimeOfDay(time, lots[i].getStartDayTime());
			if(time < dayMidnight + lots[i].getCloseDayTime() && time > dayMidnight + lots[i].getStartDayTime()) {
				currentlyOpen.add(lots[i]);
			}
		}
		ParkingLot[] arr = new ParkingLot[currentlyOpen.size()];
		runNewCustomers(demand, currentlyOpen.toArray(arr));
	}
	// Runs leaving customers section
	private void runLeavingCustomers(ParkingLot lot) {
		for(int i = 0; i < activePeople.size(); i++) {
			if(activePeople.get(i).readyToLeave(time)) {
				lot.returnTicket(activePeople.get(i).getTicket(), time);
				activePeople.remove(i--);
			}
		}
	}
	private void runLeavingCustomers() {
		for(int i = 0; i < activePeople.size(); i++) {
			if(activePeople.get(i).readyToLeave(time)) {
				activePeople.get(i).getTicket().getLot().returnTicket(activePeople.get(i).getTicket(), time);
				activePeople.remove(i--);
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
	private void runNewCustomers(PopulusDemand demand, ParkingLot[] lots) {
		int newCustomers = (int)(generalActivity * Math.random() * lots.length);
		ParkingLot[] orderedLots = orderByPrice(lots);
		int checkIndex = 0;
		for(int i = 0; i < newCustomers; i++) {
			for(int k = 0; k < orderedLots.length; k++) {
				if(!orderedLots[k].isFull()) {
					if(demand.acceptInstance(orderedLots[k].getPrice())) {
						activePeople.add(new Customer(orderedLots[k].requestTicket(time), orderedLots[k].getTimeAllowed()));
					}
					break;
				}
			}
		}
	}
	// orders lots by base price in ascending order
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
	private boolean isOpenTime() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time * 1000);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (c.getTimeInMillis()/1000 + simStartDay <= time && c.getTimeInMillis()/1000 + simEndDay >= time);
	}
	public boolean isOpenTime(long startOfDay, long endOfDay) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time * 1000);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (c.getTimeInMillis()/1000 + startOfDay <= time && c.getTimeInMillis()/1000 + endOfDay >= time);
	}
	public boolean isOpenTimePrevTick(long startOfDay, long endOfDay) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((time - tickTime) * 1000);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (c.getTimeInMillis()/1000 + startOfDay <= (time - tickTime) && c.getTimeInMillis()/1000 + endOfDay >= (time - tickTime));
	}
}
