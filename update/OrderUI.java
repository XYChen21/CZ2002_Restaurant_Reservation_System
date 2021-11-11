package restaurant;
import java.util.Scanner;
import java.io.*;

public class OrderUI implements Serializable {
	
	private Scanner sc = new Scanner(System.in);
	
	// scan tableID
	public int scanTableID() {
		int tableID = 0;
		while (true) {
			try {
				System.out.println("Enter table number: ");
				tableID = sc.nextInt();
				if (tableID < 1 || tableID > 10) {
					throw new Exception("Error: table number can only be a number between 1 to 10 inclusive");
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return tableID;
	}
	
	// scan orderID
	public int scanOrderID() {
		int orderID = 0;
		while (true) {
			try {
				System.out.println("Enter orderID given during order creation: ");
				orderID = sc.nextInt();
				if (orderID < 0) {
					throw new Exception("Error: orderID is greater than or equal to 0");
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return orderID;
	}
	
	// scan isAlaCarte
	public boolean scanisAlaCarte() {
		int choice = 0;
		boolean type = true;
		while (true) {
			try {
				System.out.println("Select (1) Ala carte (2) Promotional set");
				choice = sc.nextInt();
				if (choice < 1 || choice > 2) {
					throw new Exception("Error: only select (1) for ala carte or (2) for promotional set");
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		if (choice == 1) {return true;}
		else {return false;}
	}
	
	// scan foodID
	public int scanfoodID() {
		int foodID = 0;
		while (true) {
			try {
				System.out.println("Enter corresponding index of food you wish to order: ");
				foodID = sc.nextInt();
				if (foodID < 0) {
					throw new Exception("Error: foodID is greater than or equal to 0");
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return foodID;
	}
	
	// scan quantity
	public int scanQuantity() {
		
		int quantity = 0;
		while (true) {
			try {
				System.out.println("Enter quantity of food item: ");
				quantity = sc.nextInt();
				if (quantity <= 0) {
					throw new Exception("Error: please enter quantity as a positive integer");
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return quantity;
	}
	
	// scan staffName
	public String scanstaffName() {
		System.out.println("Enter name of staff server: ");
		String staffServer = sc.next();
		return staffServer;
	}
	
}
