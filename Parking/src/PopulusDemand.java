// Tracks the demand of the population for choosing the parking lot
public class PopulusDemand {
	private double pickyness;
	private double standardTicketPrice;
	
	public PopulusDemand(double picky, double price) {
		pickyness = picky;
		standardTicketPrice = price;
	}
	
	public boolean acceptInstance(double ticketPrice) {
		double acceptanceRate = pickyness * ticketPrice / standardTicketPrice;
		return Math.random() < acceptanceRate;
	}
}
