package restaurant;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

/**
 * Main driver for restaurant app
 * @author Chen Xingyu, Valencia Lie, Jacintha Wee, Li Siyi
 * @since 2021-11-13
 */
public class App {
	
	/**
	 * main app
	 * @param args arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Restaurant r = null;
		int choice;
		while (true) {
			System.out.println("Do you want to 1. restore previous Restaurant object or 2. create a new one?");
			while (true) {
				try {
					 choice = sc.nextInt();
					 if (choice != 1 && choice != 2)
						 throw new Exception("Invalid choice, please put 1 and 2 only");
					 break;
				} catch (InputMismatchException e) {
					System.out.println("please input integer 1 or 2");
					 sc.nextLine();
				} catch (Exception e) {
					 System.out.println(e.getMessage());
					 sc.nextLine();
				}
			}
			sc.nextLine();
			if (choice == 1) {
				System.out.println("Enter directory of serialization file: ");
				String path = sc.nextLine();
				File f = new File(path);
				if(f.exists() && !f.isDirectory()) {
					try {
						FileInputStream fs = new FileInputStream(f);
						ObjectInputStream is = new ObjectInputStream(fs);
						r = (Restaurant) is.readObject(); // need cast because we'll get back type Object
						r.restoreAutoRemove();
						is.close();
						System.out.println("Resetaurant restored!");
						break;
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
						continue;
					}
				} else {
					System.out.println("File doesn't exist!");
					continue;
				}
			}
			else if (choice == 2) {
				System.out.println("New restaurant created!");
				r = new Restaurant();
				break;
			}
		}
		do {
			System.out.println("(1)Dine in (without reservation)");
			System.out.println("(2)Dine in (with reservation)");
			System.out.println("(3)Make a reservation");
			System.out.println("(4)Remove a reservation");
			System.out.println("(5)Find a reservation");
			System.out.println("(6)Show all reservations");
			System.out.println("(7)List all table availability");
			System.out.println("(8)Create menu item");
			System.out.println("(9)Update menu item");
			System.out.println("(10)Remove menu item");
			System.out.println("(11)Create promotion");
			System.out.println("(12)Update promotion");
			System.out.println("(13)Remove promotion");
			System.out.println("(14)View menu & promotion");
			System.out.println("(15)Create order");
			System.out.println("(16)View order");
			System.out.println("(17)Add to order");
			System.out.println("(18)Remove from order");
			System.out.println("(19)Checkout and print invoice");
			System.out.println("(20)Sale revenue");
			System.out.println("(21)Save and close");
			while(true) {
				System.out.print("Enter the number of your choice: ");
				try {
					choice = sc.nextInt();
					if (choice < 0 || choice > 21) 
						throw new Exception("Invalid choice!");
					break;
				} catch(InputMismatchException e) {
					System.out.println("Please input an integer");
					sc.nextLine();
				} catch (Exception e){
					System.out.println(e.getMessage());
					sc.nextLine();
				}
			}
			sc.nextLine();
			switch (choice) {
			case 1:
				r.DineIn();
				break;
			case 2:
				r.reservedDineIn();
				break;
			case 3:
				r.makeReservation();
				break;
			case 4:
				r.removeReservation();
				break;
			case 5:
				r.showRes();
				break;
			case 6:
				r.showAllRes();
				break;
			case 7:
				r.listTableStatus();
				break;
			case 8:
				r.addMenuItem();
				break;
			case 9:
				r.updateItem();
				break;
			case 10:
				r.removeMenuItem();
				break;
			case 11:
				r.createPackage();
				break;
			case 12:
				r.updatePackage();
				break;
			case 13:
				r.removePackage();
				break;
			case 14:
				r.viewMenus();
				break;
			case 15:
				r.createOrder();
				break;
			case 16:
				r.viewOrder();
				break;
			case 17:
				r.addOrder();
				break;
			case 18:
				r.removeOrder();
				break;
			case 19:
				r.checkout();
				break;
			case 20:
			    r.printRevenue();
			    break;
			case 21:
				System.out.println("Program terminating ....");
				r.close();
				break;
			}
			System.out.println("");
		} while (choice < 21);
		sc.close();
	}
}
