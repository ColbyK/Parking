import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Input data that contains both defaults and customized inputs
public class ParkingInputData {
	//TODO variables based on input
	// Maximum slots in the lot
	private int maxSpots;
	// Base price for a ticket
	private double basePrice;
	// Time allowed to be in the lot for the price of a ticket.
	private long overTime; 
	// Pickyness of the simulation populus
	private double pickyness;
	// Activity rate for the simulation time
	private double activityRate;
	// instantiation for default data
	public ParkingInputData() {
		
	}
	// instantiation for custom data
	public ParkingInputData(String fileName) {
		this();
	}
	// Reads data from fileName
	private void readData(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = reader.readLine()) != null) {
				checkInputLine(line);
			}
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
				case "Population_Pickyness":
					pickyness = Double.parseDouble(line.substring(line.indexOf('=')+1).trim());
					break;
				case "Population_ActivityRate":
					activityRate = Double.parseDouble(line.substring(line.indexOf('=')+1).trim());
					break;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
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
}
