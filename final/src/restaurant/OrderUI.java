package restaurant;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class OrderUI {

	private static Scanner sc = new Scanner(System.in);

	// scan tableID
	public static int scanTableID() {
		int tableID = 0;
		while (true) {
			try {
				System.out.println("Enter table number: ");
				tableID = sc.nextInt();
				if (tableID < 1 || tableID > 10) {
					throw new Exception("Error: table number can only be a number between 1 to 10 inclusive");
				}
				break;
			} catch(InputMismatchException e) {
				System.out.println("Please input an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return tableID;
	}

	// scan orderID
	public static int scanOrderID() {
		int orderID = 0;
		while (true) {
			try {
				System.out.println("Enter orderID given during order creation: ");
				orderID = sc.nextInt();
				if (orderID < 0) {
					throw new Exception("Error: orderID is greater than or equal to 0");
				}
				break;
			} catch(InputMismatchException e) {
				System.out.println("Please input an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return orderID;
	}

	// scan isAlaCarte
	public static boolean scanisAlaCarte() {
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
			} catch(InputMismatchException e) {
				System.out.println("Please input an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		if (choice == 1) {
			return true;
		} else {
			return false;
		}
	}

	// scan foodID
	public static int scanfoodID() {
		int foodID = 0;
		while (true) {
			try {
				System.out.println("Enter corresponding index of food you wish to order: ");
				foodID = sc.nextInt();
				if (foodID < 0) {
					throw new Exception("Error: foodID is greater than or equal to 0");
				}
				break;
			} catch(InputMismatchException e) {
				System.out.println("Please input an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return foodID;
	}

	// scan quantity
	public static int scanQuantity() {

		int quantity = 0;
		while (true) {
			try {
				System.out.println("Enter quantity of food item: ");
				quantity = sc.nextInt();
				if (quantity <= 0) {
					throw new Exception("Error: please enter quantity as a positive integer");
				}
				break;
			} catch(InputMismatchException e) {
				System.out.println("Please input an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return quantity;
	}

	// scan staffName
	public static String scanstaffName() {
		String staffServer;
		while (true) {
			System.out.println("Enter name of staff server: ");
			try {
				staffServer = sc.nextLine();
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return staffServer;
	}

	public static String[] scanTime() {
		System.out.println("Specify a period (d/M/y; input null for all orders) - start:");
		String s = sc.nextLine();
		if (s == "")
			return new String[] { s };
		System.out.println("Specify a period (d/M/y; input null for all orders) - end:");
		String e = sc.nextLine();
		return new String[] { s, e };
	}

	public static int scanRevenueChoice() {
		int choice;
		while (true) {
			try {
				System.out.println("Please enter a choice to view revenue report: 0-by item amount; 1-by order ID; 2-by order amount");
				choice = sc.nextInt();
				if (choice < 0 || choice > 2)
					throw new Exception("Invalid choice! Please put 0/1/2 only");
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please put an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return choice;
	}
}
