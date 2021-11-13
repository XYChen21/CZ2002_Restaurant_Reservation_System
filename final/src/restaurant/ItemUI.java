package restaurant;

import java.util.InputMismatchException;
import java.util.Scanner;

import restaurant.Item.KindofFood;

public class ItemUI {

	private static Scanner sc = new Scanner(System.in);

	public static String getNameItemUI() {
		System.out.println("What is the name of the item?");
		String name;
		while (true) {
			try {

				name = sc.nextLine();

				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return name;
	}

	public static int getIndexItemUI() {
		
		int index;
		while (true) {
			System.out.println("What is the index of the item?");
			try {
				index = sc.nextInt();
				break;
			}
			catch(InputMismatchException e) {
				System.out.println("PLease input an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return index;
	}

	public static int getNewIndexItemUI() {
		
		int index;
		while (true) {
			System.out.println("What is the new index of the item?");
			try {
				index = sc.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.out.println("PLease input an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return index;
	}

	public static String getDescItemUI() {
		String desc;
		while (true) {
			System.out.println("What is the description of the item?");
			try {
				desc = sc.nextLine();
				break;
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		return desc;
	}

	public static double getPriceItemUI() {
		
		double price;
		while (true) {
			System.out.println("What is the price of the item?");
			try {
				price = sc.nextDouble();
				if (price < 0)
					throw new Exception("Invalid input. Price cannot be smaller than 0. Please enter again.");

				break;
			}
			catch(InputMismatchException e) {
				System.out.println("PLease input an integer");
				sc.nextLine();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

		}
		sc.nextLine();
		return price;
	}

	public static KindofFood getTypeItemUI() {
		System.out.println(
				"What is the type of the item?  1: Main, 2: Sides, 3: Beverage, 4: Dessert. Enter either 1,2,3 or 4.");
		int d;
		while (true) {
			try {
				d = sc.nextInt();
				if (d < 1 || d > 4) {
					throw new Exception("Invalid input. Please enter either 1,2,3 or 4.");
				}
				break;
			}
			catch(InputMismatchException e) {
				System.out.println("PLease input an integer");
				sc.nextLine();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

		}
		KindofFood f = KindofFood.getTypeByOrdinal(d);
		sc.nextLine();
		return f;
	}

	public static int updateMenuChoice() {
		int choice;
		while (true) {
			System.out.println(
				"What do you want to update? Enter 1 for Index, 2 for Name, 3 for Description, 4 for Price and 5 for Type of food.");
		
			try {
				choice = sc.nextInt();
				if (choice < 1 || choice > 5)
					throw new Exception("Invalid input. Please enter either 1,2,3, 4 or 5.");
				break;
			}
			catch(InputMismatchException e) {
				System.out.println("PLease input an integer");
				sc.nextLine();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

		}
		sc.nextLine();
		return choice;

	}

}
