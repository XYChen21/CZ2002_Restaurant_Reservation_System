package restaurant;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

/**
 * Interface to input information regarding order made by this customer.
 * @author Jacintha
 * @version 1.0
 * @since 2021-11-12
 */
public class OrderUI {
	
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * Input the tableID of the table assigned to this customer
	 * @return the tableID of this customer
	 */
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
	
	/**
	 * Input the orderID assigned to this customer during order creation
	 * @return the orderID of this customer
	 */
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
	
	/**
	 * Input if food item is Item(ala carte) or Package(promotional set)
	 * @return true if food item is Item and false if food item is Package
	 */
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
	
	/**
	 * Input the corresponding foodID of food item to be ordered by this customer
	 * @return the foodID of food item of choice
	 */
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
	
	/**
	 * Input the quantity of food item to be ordered by this customer
	 * @return the quantity of food item to be added/removed to order of this customer
	 */
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
	
	/**
	 * Input the name of staff to serve this customer
	 * @return the name of staff server
	 */
	public String scanstaffName() {
		System.out.println("Enter name of staff server: ");
		String staffServer = sc.next();
		return staffServer;
	}
	
	public String[] scanTime(){
		System.out.println("Specify a period (d/M/y; input null for all orders) - start:");
		String s = sc.nextLine();
		if(s == "") return new String[]{s};
		System.out.println("Specify a period (d/M/y; input null for all orders) - end:");
		String e = sc.nextLine();
		return new String[]{s, e};
	}
	
	public int scanRevenueChoice()
	 {
	  int choice;
	  while(true)
	  {
	   try {
	    choice = sc.nextInt();
	    if (choice < 0 || choice > 2)
	     throw new Exception("Invalid choice! Please put 0/1/2 only");
	    break;
	   } catch (InputMismatchException e) {
	    System.out.println("Invalid input, please put an integer");
	    sc.nextLine();
	   } catch(Exception e){
	    System.out.println(e.getMessage());
	    sc.nextLine();
	   }
	  }
	  sc.nextLine();
	  return choice;
	 }
}
