package restaurant;
import java.time.LocalDateTime; // Used for time stamp java time
import java.util.Scanner;
import java.time.format.DateTimeFormatter; 
import java.util.ArrayList;
import java.util.HashMap;

public class Order {
	String staffServer; // Restaurant serialize a Staff object then here we get it back through deserialize
	LocalDateTime orderDateTime; // for sale revenue
	int quantity;
	boolean isAlaCarte;
	int tableNo; 
	static int orderID; // int orderID for sale revenue
	// Restaurant keep track of orders -> orderID = len of array of orders + 1
	double Total;// Total var -> sale revenue then minus off taxes
	Item orderedItem; // Item object for respective food ordered
	Package orderedPackage; // Package object for respective food ordered
	Member isMem;
	
	public static HashMap<Item, Integer> ordersAC = new HashMap<Item, Integer>(); // each order contains foodIndex and corresponding quantity
	public static HashMap<Package, Integer> ordersP = new HashMap<Package, Integer>();
	
	public Order(int foodIndex, boolean isAlaCarte, int quantity, int id) {
		tableNo = id; // id is a protected var from Table
		orderID++; 
		if (isAlaCarte == true) {orderedItem = getItemByIndex(foodIndex);}
		else {orderedPackage = getPackageByIndex(foodIndex);}  // get the Food object from method from Menu class containing items and packages, HashMap of food name, description etc
		// quantity per Food
		this.quantity = quantity;
		// get time of order stored in attribute Time local date time
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		orderDateTime = dt.format(format);
		orderDateTime = LocalDateTime.now();
		// assign a staff to serve the customer
		staffServer = whoToServe();
		
	}
	
	// addOrder method
	public void addOrder() {
		if (isAlaCarte == true) {
			ordersAC.put(orderedItem.getName(), quantity);
			System.out.printf("Successfully added " , quantity , " " + orderedItem.getName() + "\n"); 
		}
		else {
			ordersP.put(orderedPackage.getPackageName(), quantity);
			System.out.printf("Successfully added " , quantity , " " + orderedPackage.getPackageName() + "\n"); 
		}
	}
	
	// removeOrder method
	public void removeOrder() {
		if (isAlaCarte == true) {
			ordersAC.remove(orderedItem.getName());
		}
		else {
			ordersP.remove(orderedPackage.getPackageName());
		}
	}
	
	// viewOrder method --> just show food item and quantity
	public void viewOrder() {
		if (!ordersAC.isEmpty()) {
			ordersAC.forEach((key, value) -> {
				System.out.println(String.format("%.2f", value) + "\t" + key.getName());
			});
		}
		if (!ordersP.isEmpty()) {
			ordersP.forEach((key, value) -> {
				System.out.println(String.format("%.2f", value) + "\t" + key.getPackageName());
			});
		}
	}
	
	public int printInvoice() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to payment! May we have your contact number please?");
		int contactNo = sc.nextInt();
		System.out.println("May we have your name please?");
		String name = sc.next();
		boolean membership = isMem.isMember(contactNo, name);
		if (!membership) {
			System.out.println("Would you like to join us as a member? (Y/N)");
			char ans = sc.next().charAt(0);
			if (ans == 'Y') {
				addMember(contactNo, name);
				membership = true;
			}
		}
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
		double subTotal=0;
		if (!ordersAC.isEmpty()) {
			ordersAC.forEach((key, value) -> {
				System.out.println(String.format("%.2f", value) + " " + key.getName() + "\t" + String.format("%.2f", key.getPrice()));
				subTotal += key.getPrice();
			});
		}
		if(!ordersP.isEmpty()) {
			ordersP.forEach((key, value) -> {
				System.out.println(String.format("%.2f", value) + " " + key.getPackageName() + "\t" + String.format("%.2f", key.getPackagePrice()));
				subTotal += key.getPackagePrice();
			});
		}
		// Print SubTotal
		System.out.println("SubTotal: " + String.format("%.2f", subTotal));
		// Print Taxes -> 7% GST
		double taxes = subTotal*0.17;
		System.out.println("GST and Service Charge: " + String.format("%.2f", taxes));
		subTotal *= 1.17;
		// Print Discount -> get from Package
		System.out.println("Discount (package): " + 10 + "%%");
		// Print Membership Discount -> if membership == true, apply and print
		if (membership) {
			System.out.println("Membership discount: " + 10 + "%%");
			subTotal *= 0.9;
		}
		// Print Total
		System.out.println("Total: $" + subTotal);
		// Revenue = Total - Taxes
		Total = subTotal - taxes; 
		// Method to free up availability of table by passing in table number -- find from Restaurant table array and call vacate() on that table
		return tableNo;
	}
}
