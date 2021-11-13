package restaurant;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
	private String staffServer; // Restaurant serialize a Staff object then here we get it back through deserialize
	private LocalDateTime orderDateTime; // for sale revenue
	private int tableID;
	private int orderID; // for sale revenue
	private double Total;// Total var -> sale revenue then minus off taxes
	private HashMap<Food, Integer> ordersFood; // Food object-> Item or Package. Integer-> quantity
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

		// Assign a staff to serve the customer (manual input)
		staffServer = name;
		paid = false;
		Total = 0;
	}
	
	public boolean paid() {return paid;}
	
	public void checkout(boolean paid) {this.paid = paid;}
	
	public boolean haveOrder() {
		if (ordersFood.isEmpty()) {
			return false;
		} 
		else {
			return true;
		}
	}

	public double getTotal() {return Total;}
	
	public void setTotal(double Total) {this.Total = Total;}

	public int getorderID() {return orderID;}

	public LocalDateTime getTime() {return orderDateTime;}

	public int gettableID() {return tableID;}
	
	public String getStaffServer() {return staffServer;}
	
	public HashMap<Food, Integer> getOrders() {return ordersFood;}
	
}
