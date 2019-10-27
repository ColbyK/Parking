import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Input data that contains both defaults and customized inputs
public class ParkingInputData {
	// Maximum slots in the lot
	private int maxSpots; // TODO DELETE
	private int[] maxSpotsArr;
	public static final int MAX_SPOTS_DEFAULT = 1000;
	// Base price for a ticket
	private double basePrice; // TODO DELETE
	private double[] basePriceArr;
	public static final double BASE_PRICE_DEFAULT = 5.0;
	// Time allowed to be in the lot for the price of a ticket. Default 7200 = 2 hours
	private long overTime; // TODO DELETE
	private long[] overTimeArr;
	public static final long OVER_TIME_DEFAULT = 7200;
	// Pickyness of the simulation populus. Default 0.5 = Average
	private double pickyness;
	public static final double PICKYNESS_DEFAULT = 0.5;
	// Activity rate for the simulation time. Default 0.1 = 10% of max slots per tick
	private double activityRate;
	public static final double ACTIVITY_RATE_DEFAULT = 0.1;
	// Number of days to run the simulation
	private int simDays;
	public static final int SIM_DAYS_DEFAULT = 10;
	// Seconds from start of the day that the lot opens. Default 21600 = 6am
	private long openTime; // TODO DELETE
	private long[] openTimeArr;
	public static final long OPEN_TIME_DEFAULT = 21600;
	// Seconds from start of the days that the lot closes. Default 79200 = 10pm
	private long closeTime; // TODO DELETE
	private long[] closeTimeArr;
	public static final long CLOSE_TIME_DEFAULT = 79200;
	// Seconds for each tick of the simulation. Default 600 = 10 minutes
	private long tickTime;
	public static final long TICK_TIME_DEFAULT = 600;
	// Flag to allow for adjusting price. Default true
	private boolean allowPriceChange;
	public static final boolean ALLOW_PRICE_CHANGE_DEFAULT = true;
	// Number of lots for the simulation
	private int numLots;
	private static final int NUM_LOTS_DEFAULT = 1;
	
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
			formatLotRows();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Sets unfinished lot rows to default values to have all lots filled with data
	private void formatLotRows() {
		int maxLots = Math.max(numLots, Math.max(maxSpotsArr.length, 
										Math.max(basePriceArr.length, 
										Math.max(overTimeArr.length, 
										Math.max(openTimeArr.length,
												 closeTimeArr.length)))));
		int[] tempMaxSpotsArr = new int[maxLots];
		double[] tempBasePriceArr = new double[maxLots];
		long[] tempOverTime = new long[maxLots];
		long[] tempOpenTime = new long[maxLots];
		long[] tempCloseTime = new long[maxLots];
		for(int i = 0; i < maxLots; i++) {
			if(maxSpotsArr.length > i) {
				tempMaxSpotsArr[i] = maxSpotsArr[i];
			}
			else {
				tempMaxSpotsArr[i] = MAX_SPOTS_DEFAULT;
			}
			if(basePriceArr.length > i) {
				tempBasePriceArr[i] = basePriceArr[i];
			}
			else {
				tempBasePriceArr[i] = BASE_PRICE_DEFAULT;
			}
			if(overTimeArr.length > i) {
				tempOverTime[i] = overTimeArr[i];
			}
			else {
				tempOverTime[i] = OVER_TIME_DEFAULT;
			}
			if(openTimeArr.length > i) {
				tempOpenTime[i] = openTimeArr[i];
			}
			else {
				tempOpenTime[i] = OPEN_TIME_DEFAULT;
			}
			if(closeTimeArr.length > i) {
				tempCloseTime[i] = closeTimeArr[i];
			}
			else {
				tempCloseTime[i] = CLOSE_TIME_DEFAULT;
			}
		}
		maxSpotsArr = tempMaxSpotsArr;
		basePriceArr = tempBasePriceArr;
		overTimeArr = tempOverTime;
		openTimeArr = tempOpenTime;
		closeTimeArr = tempCloseTime;
	}
	private void checkInputLine(String line) {
		String lineTest = "";
		String[] strArr;
		if(line.contains("=")) {
			lineTest = line.substring(0,line.indexOf('='));
			try {
				switch (lineTest) {
					case "Lot_MaxSpots":
						strArr = line.substring(line.indexOf('=')+1).trim().split("\\s+");
						maxSpotsArr = new int[strArr.length];
						for(int i = 0; i < strArr.length; i++) {
							maxSpotsArr[i] = Integer.parseInt(strArr[i]);
						}
						maxSpots = Integer.parseInt(line.substring(line.indexOf('=')+1).trim()); // TODO DELETE
						break;
					case "Lot_BasePrice":
						strArr = line.substring(line.indexOf('=')+1).trim().split("\\s+");
						basePriceArr = new double[strArr.length];
						for(int i = 0; i < strArr.length; i++) {
							basePriceArr[i] = Double.parseDouble(strArr[i]);
						}
						basePrice = Double.parseDouble(line.substring(line.indexOf('=')+1).trim()); // TODO DELETE
						break;
					case "Lot_TimePerCharge":
						strArr = line.substring(line.indexOf('=')+1).trim().split("\\s+");
						overTimeArr = new long[strArr.length];
						for(int i = 0; i < strArr.length; i++) {
							overTimeArr[i] = Long.parseLong(strArr[i]);
						}
						overTime = Long.parseLong(line.substring(line.indexOf('=')+1).trim()); // TODO DELETE
						break;
					case "Lot_OpenTime":
						strArr = line.substring(line.indexOf('=')+1).trim().split("\\s+");
						openTimeArr = new long[strArr.length];
						for(int i = 0; i < strArr.length; i++) {
							openTimeArr[i] = Long.parseLong(strArr[i]);
						}
						openTime = Long.parseLong(line.substring(line.indexOf('=')+1).trim()); // TODO DELETE
						break;
					case "Lot_CloseTime":
						strArr = line.substring(line.indexOf('=')+1).trim().split("\\s+");
						closeTimeArr = new long[strArr.length];
						for(int i = 0; i < strArr.length; i++) {
							closeTimeArr[i] = Long.parseLong(strArr[i]);
						}
						closeTime = Long.parseLong(line.substring(line.indexOf('=')+1).trim()); // TODO DELETE
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
	}
	private void setDefaultData() {
		maxSpots = MAX_SPOTS_DEFAULT; // TODO DELETE
		maxSpotsArr = new int[] {MAX_SPOTS_DEFAULT};
		basePrice = BASE_PRICE_DEFAULT; // TODO DELETE
		basePriceArr = new double[] {BASE_PRICE_DEFAULT};
		overTime = OVER_TIME_DEFAULT; // TODO DELETE
		overTimeArr = new long[] {OVER_TIME_DEFAULT};
		pickyness = PICKYNESS_DEFAULT;
		activityRate = ACTIVITY_RATE_DEFAULT;
		simDays = SIM_DAYS_DEFAULT;
		openTime = OPEN_TIME_DEFAULT; // TODO DELETE
		openTimeArr = new long[] {OPEN_TIME_DEFAULT};
		closeTime = CLOSE_TIME_DEFAULT; // TODO DELETE
		closeTimeArr = new long[] {CLOSE_TIME_DEFAULT};
		tickTime = TICK_TIME_DEFAULT;
		allowPriceChange = ALLOW_PRICE_CHANGE_DEFAULT;
		numLots = NUM_LOTS_DEFAULT;
	}
	public int getMaxSpots() { // TODO DELETE
		return maxSpots;
	}
	public int[] getMaxSpots___() {
		return maxSpotsArr;
	}
	public double getBasePrice() { // TODO DELETE
		return basePrice;
	}
	public double[] getBasePrice___() {
		return basePriceArr;
	}
	public long getOverTime() { // TODO DELETE
		return overTime;
	}
	public long[] getOverTime___() {
		return overTimeArr;
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
	public long getOpenTime() { // TODO DELETE
		return openTime;
	}
	public long[] getOpenTime___() {
		return openTimeArr;
	}
	public long getCloseTime() { // TODO DELETE
		return closeTime;
	}
	public long[] getCloseTime___() {
		return closeTimeArr;
	}
	public long getTickTime() {
		return tickTime;
	}
	public boolean getAllowPriceChange() {
		return allowPriceChange;
	}
	public int getNumLots() {
		return numLots;
	}
}


