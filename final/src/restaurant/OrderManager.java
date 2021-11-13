package restaurant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;

/**
 * Manages the methods pertaining to Order class.
 * @author Jacintha Wee
 * @version 2.0
 * @since 2021-11-13
 */
public class OrderManager implements Serializable{
	/**
	 * An ArrayList of Orders which orderID's correspond to the index at which they were stored in the ArrayList
	 */
	private ArrayList<Order> ordersbyID; 
	
	/**
	 * The ID of the order created for this customer
	 */
	private int orderID;
	
	/**
	 * the start date for printing revenue report
	 */
	private LocalDate _start;
	
	/**
	 * the end date for printing revenue report
	 */
	private LocalDate _end;
	
	/**
	 * variable storing total revenue
	 */
	private double totalRevenue;
	
	/**
	 * Creates a new OrderManager with ArrayList to store orders in, initialized orderID to be incremented and initialized totalRevenue to be incremented
	 */
	public OrderManager() {
		ordersbyID = new ArrayList<Order>();
		orderID = -1;
		totalRevenue = 0.0;
	}
	
	/**
	 * Check if orderID entered is valid which corresponds to a valid created order for this customer
	 * @param id The ID of the order entered for this customer
	 * @return true if orderID is valid and false otherwise
	 */
	public boolean checkValidOrderID(int id) {
		if (id >= 0 && id < orderID) {return true;}
		else {return false;}
	}
	
	/**
	 * Upon creation of order, increment orderID to generate a new orderID for the customer
	 */
	public void createOrder() {
		orderID++;
	}
	
	/**
	 * Add the newly created order to the ArrayList of all orders
	 * @param newOrder The Order object for this customer
	 * @param tableID The ID of the table assigned to this customer upon dine in 
	 */
	public void addOrdertoArrayList(Order newOrder, int tableID) {
		ordersbyID.add(newOrder);
		System.out.println("New order for table " + tableID + " with orderID " + orderID + " has been successfully created.");
	}
	
	/**
	 * Get the orderID of this customer
	 * @return The orderID of this customer
	 */
	public int getorderID() {
		return orderID;
	}
	
	/**
	 * Get the order made by this customer
	 * @param orderid The orderID of this customer
	 * @return Order of this customer
	 */
	public Order getOrder(int orderid) {
		return ordersbyID.get(orderid);
	}
	
	/**
	 * View food currently ordered by this customer
	 * @param orderID The orderID of this customer
	 */
	public void viewOrder(int orderID) {
		if (ordersbyID.get(orderID).haveOrder()) {
			HashMap<Food, Integer> orders = ordersbyID.get(orderID).getOrders();
			System.out.println("======== Current order summary ========");
			orders.forEach((key, value) -> {
				System.out.println(value + " " + key.getName());
			});
		} 
		else {
			System.out.println("You have not ordered any food yet.");
		}
	}
	
	/**
	 * Add to this customer's order the food item of choice and its corresponding quantity
	 * @param orderID The orderID of this customer
	 * @param food The Item/Package indicated by this customer
	 * @param quantity The amount of Item/Package to be added to this customer's order
	 */
	public void addOrder(int orderID, Food food, int quantity) {
		
		// Check if order exists -> exists, increment. If not, create new
		if (ordersbyID.get(orderID).getOrders().get(food) != null) { // Food exists in the order
			int currentQuantity = ordersbyID.get(orderID).getOrders().get(food);
			ordersbyID.get(orderID).getOrders().put(food, currentQuantity + quantity);
		} 
		else { // Food does not exist in the order yet
			ordersbyID.get(orderID).getOrders().put(food, quantity);
		}
		System.out.println("You have ordered " + ordersbyID.get(orderID).getOrders().get(food) + " " + food.getName());
	}
	
	/**
	 * Remove from this customer's order the food item of choice and its corresponding quantity
	 * @param orderID The orderID of this customer
	 * @param food The Item/Package indicated by this customer
	 * @param quantity The amount of Item/Package to be removed from this customer's order
	 */
	public void removeOrder(int orderID, Food food, int quantity) {
		
		if (ordersbyID.get(orderID).haveOrder()) { 
			if (ordersbyID.get(orderID).getOrders().get(food) != null) { // Food exists in the order
				int currentQuantity = ordersbyID.get(orderID).getOrders().get(food);
				if (currentQuantity - quantity == 0) { // Customer wants to completely remove food from the order
					ordersbyID.get(orderID).getOrders().remove(food);
					System.out.println(food.getName() + " is removed from your order.");
				} 
				else if (currentQuantity - quantity > 0) { // Customer wants a reduced quantity of current food ordered
					ordersbyID.get(orderID).getOrders().put(food, currentQuantity - quantity);
					System.out.println("You have ordered " + ordersbyID.get(orderID).getOrders().get(food) + " " + food.getName());
				} 
				else { // Invalid quantity entered -- error
					System.out.println("Please re-enter a valid quantity to remove from current order of " + currentQuantity + " " + food.getName());
				}
			}
			else { // Food does not exist in the order, error
				System.out.println(food.getName() + " does not exist in your order yet. Please input a valid food to remove from order.");
			}
		} 
		else { 
			System.out.println("You have not created order yet");
		}
	}
	
	/**
	 * Print out the order invoice of this customer upon payment 
	 * @param membership The membership status of this customer ie true if customer is a member and false otherwise
	 * @param orderid The orderID of this customer
	 * @return ID of the table that has checked out
	 */
	public int printInvoice(boolean membership, int orderid) {
		double subTotal = 0;
		// Print the Restaurant name and address
		System.out.println("SCSE Restaurant\n" + "Nanyang Technological University\n" + "50 Nanyang Avenue\n" + "Singapore 639798\n" + "Tel: (65) 6790 5786\n");
		// Print Check number -> orderID
		System.out.println("Check #: " + orderid);
		Order completeOrder = ordersbyID.get(orderid);
		// Print Staff name (Server)
		System.out.println("Served by: " + completeOrder.getStaffServer());
		// Print Table Number
		System.out.println("Table: " + completeOrder.gettableID());
		// Print DateTime dd/mm/yyyy hh:mm
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String invoiceDateTime = dt.format(format);
		System.out.println("Date and time: " + invoiceDateTime);
		System.out.println("----------------------------------------------------");
		// Print Quantity, Name of food, Individual Cost
		for (Food f : completeOrder.getOrders().keySet()) {
			int quantity = completeOrder.getOrders().get(f);
			System.out.println(quantity+ " " + f.getName() + "\t" + String.format("%.2f", f.getPrice()));
			subTotal += f.getPrice();
		}
		// Print SubTotal
		System.out.println("SubTotal: " + String.format("%.2f", subTotal));
		// Print Taxes -> 7% GST
		double taxes = subTotal * 0.17;
		System.out.println("GST and Service Charge: " + String.format("%.2f", taxes));
		subTotal *= 1.17;
		// Print Membership Discount -> if membership == true, apply and print
		if (membership) {
			System.out.println("Membership discount: " + 10 + "%");
			subTotal *= 0.9;
		}
		// Print Total
		System.out.println("Total: $" + String.format("%.2f", subTotal));
		// Revenue = Total - Taxes
		double total = subTotal - taxes;
		ordersbyID.get(orderid).setTotal(total);
				
		return completeOrder.gettableID();
	}
	
	/**
	 * parse input date time string to LocalDate
	 * @param t: string for parsing
	 */
	public void parseTme(String...t){
		try {
			this._end = LocalDate.parse(t[1], DateTimeFormatter.ofPattern("d/M/y"));
			this._start = LocalDate.parse(t[0], DateTimeFormatter.ofPattern("d/M/y"));
		} catch (DateTimeParseException _e){
			this._start = LocalDate.MIN;
			this._end = LocalDate.MAX;
			if(t[0] != "" && t[1] != "") System.out.println("Invalid Input! Printing All Paid Orders");
		} catch (IndexOutOfBoundsException _e){
			this._start = LocalDate.MIN;
			this._end = LocalDate.MAX;
		}
	}
	
	/**
	 * print a revenue report with user choice
	 * @param choice 1: by item name; choice 2: by order id; choice 3: by payment amount
	 */
	public void printRevenueReport(int choice) {
        ArrayList<Order> orders4print = this.ordersbyID.stream()
        .filter(o -> (o.getTime().toLocalDate().isAfter(this._start) && o.getTime().toLocalDate().isBefore(this._end)))
		.filter(o -> o.paid())
        .collect(Collectors.toCollection(ArrayList::new));
        double total = 0.0;
        double p = 0;

		HashMap<Food, Integer> saledItems = new HashMap<>();
		orders4print.forEach(o -> 
		{
			o.getOrders().forEach((k, v) -> 
			saledItems.put(k, v + saledItems.getOrDefault(k, 0)));
		});

		HashMap<Food, Integer> temp = saledItems;

        System.out.println("                Restaurant Name                 ");
        System.out.println("        ********************************        ");
        System.out.println();
        System.out.println("From \t" + _start.toString() + "To \t" + _end.toString());
        System.out.println();
        System.out.println("------------------------------------------------");
		switch(choice){
			case 0:
				System.out.println("Item" + "\t\t\t\t\t" + "Amount");
				System.out.println("------------------------------------------------");
				temp.entrySet().stream()
				.sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
				.forEach(k -> 
				System.out.println(k.getKey() + "\t\t\t\t\t" + k.getValue())
				);
				break;
			case 1:
				System.out.println("OrderID" + "\t\t\t\t\t" + "Amount");
				System.out.println("------------------------------------------------");
				orders4print.sort((a, b) -> a.getorderID() - b.getorderID());
				orders4print.forEach(
					o -> {
						System.out.println(o.getorderID() + "\t\t\t\t\t" + o.getTotal());
						this.totalRevenue += o.getTotal();
					}
				);
				break;
			case 2:
				System.out.println("OrderID" + "\t\t\t\t\t" + "Amount");
				System.out.println("------------------------------------------------");
				orders4print.sort((a, b) -> (int)(a.getTotal() - b.getTotal()));
				orders4print.forEach(
					o -> {
						System.out.println(o.getorderID() + "\t\t\t\t\t" + o.getTotal());
						this.totalRevenue += o.getTotal();
					}
				);
				break;
		}
		System.out.println("------------------------------------------------");
        if(total != 0.0) System.out.println("                                 Total: " + String.format("%-8.2f", this.totalRevenue));
    }
}
