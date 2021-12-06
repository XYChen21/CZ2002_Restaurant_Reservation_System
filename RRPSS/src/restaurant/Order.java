package restaurant;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an order made per customer in the restaurant.
 * @author Jacintha Wee
 * @version 1.1
 * @since 2021-11-14
 */
public class Order implements Serializable {
	/**
	 * The name of the staff who is assigned to serve this customer
	 */
	private String staffServer; 
	/**
	 * The exact point of time in which the Order was created
	 */
	private LocalDateTime orderDateTime; 
	/**
	 * The ID of the table assigned to this customer
	 */
	private int tableID;
	/**
	 * The ID of the order for this customer assigned when Order was created
	 */
	private int orderID; 
	/**
	 * The sale revenue (not including taxes) from this customer ie how much the restaurant has earned from this customer
	 */
	private double Total;
	/**
	 * A HashMap whereby the key is the Food object ordered and the value is the quantity of Food object ordered
	 */
	private HashMap<Food, Integer> ordersFood; 
	/**
	 * Determines whether this customer has paid for the food; true if this customer has paid and false otherwise
	 */
	private boolean paid;
	
	/**
	 * Creates a new Order with assigned table ID, order ID, and staff to serve this customer
	 * @param tableID The ID of the table assigned to this customer
	 * @param orderID The ID of the order for this customer assigned during creation of Order
	 * @param name The name of the staff assigned to serve this customer
	 */
	public Order(int tableID, int orderID, String name) {
		ordersFood = new HashMap<Food, Integer>(); 
		this.tableID = tableID; 
		this.orderID = orderID;
		orderDateTime = LocalDateTime.now();
		staffServer = name;
		paid = false;
		Total = 0;
	}
	
	/**
	 * Check if this customer has paid for the food yet
	 * @return true if this customer has paid for the food and false otherwise
	 */
	public boolean paid() {return paid;}
	
	/**
	 * Once payment has been made, set paid to true
	 */
	public void setPaid() {
		paid = true; 
		System.out.println("Payment complete");
		}
	
	/**
	 * Check if this customer has created an Order yet
	 * @return true if this customer has created an Order and false otherwise
	 */
	public boolean haveOrder() {
		if (ordersFood.isEmpty()) {
			return false;
		} 
		else {
			return true;
		}
	}

	/**
	 * Get the total amount of money the restaurant has earned from this customer
	 * @return Total The revenue made from this customer
	 */
	public double getTotal() {return Total;}
	
	/**
	 * Saves the revenue made from this customer in the Order created for this customer
	 * @param Total The revenue made from this customer
	 */
	public void setTotal(double Total) {this.Total = Total;}

	/**
	 * Get the order ID assigned to this customer
	 * @return the order ID of the Order for this customer
	 */
	public int getorderID() {return orderID;}

	/**
	 * Get the time at which Order was created for this customer
	 * @return the time of creation of Order for this customer
	 */
	public LocalDateTime getTime() {return orderDateTime;}

	/**
	 * Get the table ID of the table assigned to this customer
	 * @return the table ID of table assigned to this customer
	 */
	public int gettableID() {return tableID;}
	
	/**
	 * Get the name of the staff who served this customer
	 * @return the name of staff server
	 */
	public String getStaffServer() {return staffServer;}
	
	/**
	 * Get the HashMap of food ordered and their respective quantities
	 * @return HashMap of food ordered and quantities of each food ordered by this customer
	 */
	public HashMap<Food, Integer> getOrders() {return ordersFood;}
}
