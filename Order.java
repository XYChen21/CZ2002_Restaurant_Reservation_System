package restaurant;
import java.time.LocalDateTime; // Used for time stamp java time
import java.time.format.DateTimeFormatter; 
import java.util.ArrayList;
import java.util.HashMap;

public class Order {
	String staffServer; // Restaurant serialize a Staff object then here we get it back through deserialize
	String orderDateTime; // for sale revenue
	int quantity;
	int tableNo; 
	int orderID; // int orderID for sale revenue
	// Restaurant keep track of orders -> orderID = len of array of orders + 1
	double Total;// Total var -> sale revenue then minus off taxes
	
	public static ArrayList<HashMap> orderArray = new ArrayList<HashMap>(); // array of orders
	public static HashMap<Integer, Integer> orders = new HashMap<Integer, Integer>(); // each order contains foodIndex and corresponding quantity
	
	public Order(int foodIndex, int quantity) {
		tableNo = id; // id is a protected var from Table
		orderID = orderArray.size() + 1; // orderArray is an array of orders in Restaurant
		orderedFood = // get the Food object from method from Menu class containing items and packages, HashMap of food name, description etc
		// quantity per Food
		this.quantity = quantity;
		// get time of order stored in attribute Time local date time
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		orderDateTime = dt.format(format);
		// assign a staff to serve the customer
		staffServer = whoToServe();
		
	}
	
	// addOrder method
	public void addOrder() {
		orderArray.get(orderID-1).put(foodIndex, quantity);
		String food = // need a method to return the food name
		System.out.printf("Successfully added " , quantity , " " + food + "\n"); 
	}
	
	// removeOrder method
	public void removeOrder() {
		orderArray.remove(orderID-1);
	}
	
	// viewOrder method --> just show food item and quantity
	
	public void printInvoice() {
		// Print the Restaurant name and address
		System.out.println("SCSE Restaurant\n" +
		                   "Nanyang Technological University\n" +
			               "50 Nanyang Avenue\n" +
		                   "Singapore 639798\n" +
			               "Tel: (65) 6790 5786\n");
		// Print Check number -> orderID
		System.out.printf("Check #: ", orderID + "\n");
		// Print Staff name (Server)
		System.out.println("Served by: " + staffServer);
		// Print Table Number
		System.out.printf("Table: ", tableNo + "\n");
		// Print DateTime  dd/mm/yyyy hh:mm
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String invoiceDateTime = dt.format(format);
		System.out.println("Date and time: " + invoiceDateTime);
		System.out.println("----------------------------------------------------");
		// Print Quantity, Name of food, Individual Cost
		
		// Print SubTotal
		// Print Taxes -> 7% GST
		// Print Discount -> get from Package
		// Print Membership Discount -> if membership == true, apply and print
		// Print Total
		// Revenue = Total - Taxes
		
		// Method to free up availability of table by passing in table number -- find from Restaurant table array and call vacate() on that table
	}
}
