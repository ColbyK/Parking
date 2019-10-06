import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Input data that contains both defaults and customized inputs
public class ParkingInputData {
	// Maximum slots in the lot
	private int maxSpots;
	public static final int MAX_SPOTS_DEFAULT = 1000;
	// Base price for a ticket
	private double basePrice;
	public static final double BASE_PRICE_DEFAULT = 5.0;
	// Time allowed to be in the lot for the price of a ticket. Default 7200 = 2 hours
	private long overTime;
	public static final long OVER_TIME_DEFAULT = 7200;
	// Pickyness of the simulation populus. Default 0.5 = Average
	private double pickyness;
	public static final double PICKYNESS_DEFAULT = 0.5;
	// Activity rate for the simulation time. Default 0.2 = 20% of max slots per tick
	private double activityRate;
	public static final double ACTIVITY_RATE_DEFAULT = 0.01;
	// Number of days to run the simulation
	private int simDays;
	public static final int SIM_DAYS_DEFAULT = 30;
	// Seconds from start of the day that the lot opens. Default 21600 = 6am
	private long openTime;
	public static final long OPEN_TIME_DEFAULT = 21600;
	// Seconds from start of the days that the lot closes. Default 79200 = 10pm
	private long closeTime;
	public static final long CLOSE_TIME_DEFAULT = 79200;
	// Seconds for each tick of the simulation. Default 600 = 10 minutes
	private long tickTime;
	public static final long TICK_TIME_DEFAULT = 600;
	// Flag to allow for adjusting price. Default true
	private boolean allowPriceChange;
	public static final boolean ALLOW_PRICE_CHANGE_DEFAULT = true;
	//TODO VARIABLES
	
	// instantiation for default data
	public ParkingInputData() {
		setDefaultData();
	}
	// instantiation for custom data
	public ParkingInputData(String fileName) {
		this();
		readData(fileName);
	}
	// Reads data from fileName
	private void readData(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = reader.readLine()) != null) {
				checkInputLine(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void checkInputLine(String line) {
		try {
			switch (line) {
				case "Lot_MaxSpots":
					maxSpots = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Lot_BasePrice":
					basePrice = Double.parseDouble(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Lot_TimePerCharge":
					overTime = Long.parseLong(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Lot_OpenTime":
					openTime = Long.parseLong(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Lot_CloseTime":
					closeTime = Long.parseLong(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Population_Pickyness":
					pickyness = Double.parseDouble(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Population_ActivityRate":
					activityRate = Double.parseDouble(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Simulation_Days":
					simDays = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Simulation_TickTime":
					tickTime = Long.parseLong(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Simulation_AllowPriceChange":
					if(line.substring(line.indexOf('=')+1).trim().toLowerCase().equals("false")) {
						allowPriceChange = false;
					}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	private void setDefaultData() {
		maxSpots = MAX_SPOTS_DEFAULT;
		basePrice = BASE_PRICE_DEFAULT;
		overTime = OVER_TIME_DEFAULT;
		pickyness = PICKYNESS_DEFAULT;
		activityRate = ACTIVITY_RATE_DEFAULT;
		simDays = SIM_DAYS_DEFAULT;
		openTime = OPEN_TIME_DEFAULT;
		closeTime = CLOSE_TIME_DEFAULT;
		tickTime = TICK_TIME_DEFAULT;
		allowPriceChange = ALLOW_PRICE_CHANGE_DEFAULT;
	}
	public int getMaxSpots() {
		return maxSpots;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public long getOverTime() {
		return overTime;
	}
	public double getPickyness() {
		return pickyness;
	}
	public double getActivityRate() {
		return activityRate;
	}
	public int getSimDays() {
		return simDays;
	}
	public long getOpenTime() {
		return openTime;
	}
	public long getCloseTime() {
		return closeTime;
	}
	public long getTickTime() {
		return tickTime;
	}
	public boolean getAllowPriceChange() {
		return allowPriceChange;
	}
}


