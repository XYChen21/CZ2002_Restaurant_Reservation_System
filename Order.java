package restaurant;
import java.time.LocalDateTime; 
import java.util.Scanner;
import java.time.format.DateTimeFormatter; 
import java.util.ArrayList;
import java.util.HashMap;

public class Order {
	private String staffServer; // Restaurant serialize a Staff object then here we get it back through deserialize
	private LocalDateTime orderDateTime; // for sale revenue
	private int tableID; 
	private int orderID; // int orderID for sale revenue
	private double Total;// Total var -> sale revenue then minus off taxes
	public HashMap<Item, Integer> ordersAC;// each order contains foodIndex and corresponding quantity
	public HashMap<Package, Integer> ordersP;
	
	
	public Order(int tableID, int orderID, String name) {
		/* Parameters
		 * tableID : assign a table to the customer
		 * orderID : generate an order number for customer 
		 * name : from main class, pass in the name of staff to serve the customer (StaffRoster whoToServe() method) as argument
		*/
		
		ordersAC =  new HashMap<Item, Integer>(); // HashMap of orders for ala carte ie Items
		ordersP = new HashMap<Package, Integer>(); //Hashmap of orders for promotional set ie Package
		
		this.tableID = tableID; // A table is assigned to the customer
		this.orderID = orderID; // Obtain the order number for current customer

		// Obtain time when order was created
		orderDateTime = LocalDateTime.now();
		
		// Assign a staff to serve the customer (StaffArray whoToServe() method)
		staffServer = name;
	}
	
	// addOrder method
	public void addOrder(int foodIndex, boolean isAlaCarte, int quantity) 
	{
		/* Parameters
		 * foodIndex : each food on the menu has a corresponding index to be indicated when ordering
		 * isAlaCarte : check if food ordered is ala carte; true means ala carte, false means promotional set. 
		 * 				This is impt as an ala carte Item or a set Package may have the same foodIndex, so we use this var to differentiate.
		 * quantity : quantity of the food ordered eg 1, 2, ...
		 * */
		
		// Our main control is isAlaCarte to decide which HashMap to access. (ordersAC for Items, ordersP for Package)
		// Check if Item/Package is already in customer's order. If it is, we add the corresponding quantity assuming the customer wants to order more.
		// Else, we create a new key for the Item/Package
		
		if (isAlaCarte == true) { // Get Item
			Item orderedItem = getItemByIndex(foodIndex); // orderedItem is an Item object
			if (ordersAC.get(orderedItem)) { // Item exists in the order
				ordersAC.put(orderedItem, ordersAC.get(orderedItem)+quantity); 
			}
			else { // Item does not exist in the order yet
				ordersAC.put(orderedItem, quantity); // Add Item object (key) and quantity (value) to HashMap ordersAC
			}
			System.out.println("You have ordered " + ordersAC.get(orderedItem) + " " + orderedItem.getName()); 
		}
		else { // Get Package
			Package orderedPackage = getPackageByIndex(foodIndex); // orderedPackage is a Package object
			if (ordersP.get(orderedPackage)) { // Package exists in the order
				ordersP.put(orderedPackage,  ordersP.get(orderedPackage)+quantity);
			}
			else { // Package does not exist in the order yet
				ordersP.put(orderedPackage, quantity); // Add Package object (key) and quantity (value) to HashMap ordersP
			}
			
			System.out.println("You have ordered " + ordersP.get(orderedPackage) + " " + orderedPackage.getName()); 
		}
	}
	
	// removeOrder method
	public void removeOrder(int foodIndex, boolean isAlaCarte, int quantity) {
		// Logic is similar to addOrder method
		// However, for removeOrder quantity refers to the quantity of Item/Package to be removed from the order.
		// If resulting quantity of the Item/Package goes to 0, remove the key from the HashMap ie totally cancel that Item/Package ordered.
		
		if (isAlaCarte == true) { // Get Item
			Item removeItem = getItemByIndex(foodIndex); 
			if (ordersAC.get(removeItem)) { // Item exists in the order
				if (ordersAC.get(removeItem) - quantity == 0) { // Customer wants to completely remove Item from order
					ordersAC.remove(removeItem);
					System.out.println(removeItem.getName() + " is removed from your order.");
				}
				else if (ordersAC.get(removeItem) - quantity > 0){ // Customer wants a reduced quantity of Item ordered
					ordersAC.put(removeItem, ordersAC.get(removeItem)-quantity);
					System.out.println("You have ordered " + ordersAC.get(removeItem) + " " + removeItem.getName());
				}
				else { // Customer has entered an invalid quantity to be removed
					System.out.println("Please re-enter a valid quantity to remove from current quantity of " + ordersAC.get(removeItem) + " " + removeItem.getName());
				}
			}
			else { // Item does not exist in the order -- error message
				System.out.println(removeItem.getName() + " does not exist in your order yet.");
			}
		}
		else { // Get Package 
			Package removePackage = getPackageByIndex(foodIndex);
			if (ordersP.get(removePackage)) { // Package exists in the order
				if (ordersP.get(removePackage) - quantity == 0) { // Customer wants to completely remove Package from order
					ordersP.remove(removePackage);
					System.out.println(removePackage.getName() + " is removed from your order.");
				}
				else if (ordersP.get(removePackage) - quantity > 0){ // Customer wants a reduced quantity of Package ordered
					ordersP.put(removePackage, ordersP.get(removePackage)-quantity);
					System.out.println("You have ordered " + ordersP.get(removePackage) + " " + removePackage.getName());
				}
				else { // Customer has entered an invalid quantity to be removed
					System.out.println("Please re-enter a valid quantity to remove from current quantity of " + ordersP.get(removePackage) + " " + removePackage.getName());
				}
			}
			else { // Package does not exist in the order -- error message
				System.out.println(removePackage.getName() + " does not exist in your order yet.");
			}
			
		}
	}
	
	// viewOrder method --> just show food item and quantity
	public void viewOrder() {
		if (!ordersAC.isEmpty()) {
			ordersAC.forEach((key, value) -> {
				System.out.println(value + " " + key.getName());
			});
		}
		if (!ordersP.isEmpty()) {
			ordersP.forEach((key, value) -> {
				System.out.println(value + " " + key.getName());
			});
		}
		if (ordersAC.isEmpty() && ordersP.isEmpty()) {
			System.out.println("You have not ordered any food yet.");
		}
	}
	
	// 'Get' methods for Revenue class
	public double getTotal() {return Total;}
	public int getorderID() {return orderID;}
	public LocalDateTime getTime() {return orderDateTime;}
	
	public int printInvoice(boolean membership) {
		/* return type int -> tableNo (free up the table)
		  Parameter: membership -> Membership class: first call paymentMembership() method to see if there are any changes to membership 
		  						   ie customer signs up to be a member on the spot
		                           isMember() method will return true if customer is a member and false otherwise.
		 */
		
		// Remember to call paymentMembership() method (from Membership class) in main before calling printInvoice(membership) method (from Order class)
		
		// Print the Restaurant name and address
		System.out.println("SCSE Restaurant\n" +
		                   "Nanyang Technological University\n" +
			               "50 Nanyang Avenue\n" +
		                   "Singapore 639798\n" +
			               "Tel: (65) 6790 5786\n");
		// Print Check number -> orderID
		System.out.println("Check #: "+ orderID);
		// Print Staff name (Server)
		System.out.println("Served by: " + staffServer);
		// Print Table Number
		System.out.println("Table: "+ tableID);
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
				System.out.println(value + " " + key.getName() + "\t" + String.format("%.2f", key.getPrice()));
				subTotal += key.getPrice();
			});
		}
		if(!ordersP.isEmpty()) {
			ordersP.forEach((key, value) -> {
				System.out.println(value + " " + key.getName() + "\t" + String.format("%.2f", key.getPrice()));
				subTotal += key.getPrice();
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
		System.out.println("Total: $" + String.format("%.2f", subTotal));
		// Revenue = Total - Taxes
		Total = subTotal - taxes; 
		// Method to free up availability of table by passing in table number -- find from Restaurant table array and call vacate() on that table
		return tableNo;
		}
	}
}
