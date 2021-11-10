package restaurant;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
	private String staffServer; // Restaurant serialize a Staff object then here we get it back through
								// deserialize
	private LocalDateTime orderDateTime; // for sale revenue
	private int tableID;
	private int orderID; // int orderID for sale revenue
	private double Total;// Total var -> sale revenue then minus off taxes
//	private double subTotal1;
	public HashMap<Food, Integer> ordersFood; // Food object-> Item or Package. Integer-> quantity
	private boolean paid;
	public Order(int tableID, int orderID, String name) {
		/*
		 * Parameters tableID : assign a table to the customer orderID : generate an
		 * order number for customer name : from main class, pass in the name of staff
		 * to serve the customer (StaffRoster whoToServe() method) as argument
		 */

		ordersFood = new HashMap<Food, Integer>(); // HashMap of orders for both ala carte and promotional set ie Item
													// and Package

		this.tableID = tableID; // A table is assigned to the customer
		this.orderID = orderID; // Obtain the order number for current customer

		// Obtain time when order was created
		orderDateTime = LocalDateTime.now();

		// Assign a staff to serve the customer (StaffArray whoToServe() method)
		staffServer = name;
		paid = false;
//		subTotal1 = 0;
	}
	public boolean paid()
	{
		return paid;
	}
	public boolean haveOrder() {
		if (ordersFood.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	// addOrder method
	public void addOrder(Food food, int quantity) {
		// Check if order exists -> exists, increment. If not, create new
		if (ordersFood.get(food) != null) { // Food exists in the order
			ordersFood.put(food, ordersFood.get(food) + quantity);
		} else { // Food does not exist in the order yet
			ordersFood.put(food, quantity);
		}
		System.out.println("You have ordered " + ordersFood.get(food) + " " + food.getName());
	}

	// removeOrder method
	public void removeOrder(Food food, int quantity) {
		if (ordersFood.get(food) != null) { // Food exists in the order
			if (ordersFood.get(food) - quantity == 0) { // Customer wants to completely remove food from the order
				ordersFood.remove(food);
				System.out.println(food.getName() + " is removed from your order.");
			} else if (ordersFood.get(food) - quantity > 0) { // Customer wants a reduced quantity of current food
																// ordered
				ordersFood.put(food, ordersFood.get(food) - quantity);
				System.out.println("You have ordered " + ordersFood.get(food) + " " + food.getName());
			} else { // Invalid quantity entered -- error
				System.out.println("Please re-enter a valid quantity to remove from current order of "
						+ ordersFood.get(food) + " " + food.getName());
			}
		} else { // Food does not exist in the order, error
			System.out.println(food.getName()
					+ " does not exist in your order yet. Please input a valid food to remove from order.");
		}
	}

	// viewOrder method --> just show food item and quantity
	public void viewOrder() {
		if (!ordersFood.isEmpty()) {
			ordersFood.forEach((key, value) -> {
				System.out.println(value + " " + key.getName());
			});
		} else {
			System.out.println("You have not ordered any food yet.");
		}
	}

	// 'Get' methods for Revenue class
	public double getTotal() {
		return Total; // return subtotal (real payment)
	}

	public int getorderID() {
		return orderID;
	}

	public LocalDateTime getTime() {
		return orderDateTime;
	}

	public int printInvoice(boolean membership) {
		double subTotal = 0;
		/*
		 * return type int -> tableNo (free up the table) Parameter: membership ->
		 * Membership class: first call paymentMembership() method to see if there are
		 * any changes to membership ie customer signs up to be a member on the spot
		 * isMember() method will return true if customer is a member and false
		 * otherwise.
		 */

		// Remember to call paymentMembership() method (from Membership class) in main
		// before calling printInvoice(membership) method (from Order class)

		// Print the Restaurant name and address
		System.out.println("SCSE Restaurant\n" + "Nanyang Technological University\n" + "50 Nanyang Avenue\n"
				+ "Singapore 639798\n" + "Tel: (65) 6790 5786\n");
		// Print Check number -> orderID
		System.out.println("Check #: " + orderID);
		// Print Staff name (Server)
		System.out.println("Served by: " + staffServer);
		// Print Table Number
		System.out.println("Table: " + tableID);
		// Print DateTime dd/mm/yyyy hh:mm
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String invoiceDateTime = dt.format(format);
		System.out.println("Date and time: " + invoiceDateTime);
		System.out.println("----------------------------------------------------");
		// Print Quantity, Name of food, Individual Cost

		for (Food f : ordersFood.keySet())
		{
			int quantity = ordersFood.get(f);
			System.out.println(quantity+ " " + f.getName() + "\t" + String.format("%.2f", f.getPrice()));
			subTotal += f.getPrice() * quantity;
		}
//		ordersFood.forEach((key, value) -> {
//			System.out.println(value + " " + key.getName() + "\t" + String.format("%.2f", key.getPrice()));
//			subTotal += key.getPrice();
//		});

		// Print SubTotal
		System.out.println("SubTotal: " + String.format("%.2f", subTotal));
		// Print Taxes -> 7% GST
		double taxes = subTotal * 0.17;
		System.out.println("GST and Service Charge: " + String.format("%.2f", taxes));
		subTotal *= 1.17;
		// Print Discount -> get from Package
		System.out.println("Discount (package): " + 10 + "%");
		// Print Membership Discount -> if membership == true, apply and print
		if (membership) {
			System.out.println("Membership discount: " + 10 + "%");
			subTotal *= 0.9;
		}
		// Print Total
		System.out.println("Total: $" + String.format("%.2f", subTotal));
		// Revenue = Total - Taxes
		Total = subTotal - taxes;
		this.paid = true;
		// Method to free up availability of table by passing in table number -- find
		// from Restaurant table array and call vacate() on that table
		return tableID;
	}
}