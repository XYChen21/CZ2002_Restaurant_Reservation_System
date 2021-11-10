package restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import restaurant.Item.KindofFood;

public class PackageMenu implements Serializable {
//	private List<Package> menuPackage;
	private HashMap<Integer, Package> menuPackage;

	public PackageMenu() {
		this.menuPackage = new HashMap<Integer, Package>();
	}

//	public void sortPackageMenuByAlphabet() {
//		menuPackage.sort(Comparator.comparing(Package::getName));
//		printPackageMenu(menuPackage);
//	}

	public void addPackageMenu(int index, Package p) {
		
//		boolean duplicate = menuPackage.containsKey(index);
//		
//		if (duplicate == false) {
//			if (p.isNull() == true) {
//				System.out.println("No package is created since no valid item is added.");
//			} else {
//				menuPackage.put(index, p);
//			}
//		}
//		else {
//			System.out.println("Duplicate index of packages.");
//		}
		
		
		if (menuPackage.containsKey(index)){
			System.out.println("Duplicate index of packages. Please try again");
		}
		else{
			menuPackage.put(index, p);
		}

	}

	public void removePackageMenu(int ind) {
		if (menuPackage.containsKey(ind)) {
			menuPackage.remove(ind);
			System.out.println("Package is removed.");
		}
		else {
			System.out.println("Package with that index does not exist. Nothing is removed.");
		}
	}

	public void viewPackageMenu() {
//		sortByDefaultPackage(menuPackage);
		System.out.println("******** PROMOTIONAL MENU **********" + '\n');
		for (int index : menuPackage.keySet())
			menuPackage.get(index).toStringCustom();
	}



//	private void printPackageMenu(List<Package>a)
//	{System.out.println("******** PROMOTIONAL MENU **********" + '\n');
//	for(Package pack:a)  
//	   pack.toStringCustom();}

//	public void filterPackageMenuByPrice(double a, double b)
//	{List<Package> menuPackage2 =new ArrayList<Package>(); 
//	boolean empty = true;
//
//	for(Package pack:menuPackage)
//	{if (pack.getPrice() >= a && pack.getPrice()<=b)
//		{menuPackage2.add(pack);
//		empty = false;
//		}}
//
//	sortByDefaultPackage(menuPackage2);
//	printPackageMenu(menuPackage2);
//
//	if (empty == true)
//	{System.out.println("There are no packages within that price range.");}
//	}


	public Package getPackage(int index) {
		return menuPackage.get(index);
	}


//	public void setAllPackagesPrice()
//	{for (Package pack:menuPackage)
//		pack.setPrice();}

	public void updateItemInPackage(Item i) {
		Scanner sc = new Scanner(System.in);
		int choice;
		for (int index : menuPackage.keySet()) {
			Package p = menuPackage.get(index);
			if (p.checkItem(i)) {
				if (p.getPrice() < p.getOriPrice()) {
					System.out.println("This item is inside package " + p.getName()
							+ ", do you want to update price of this package as well? (1: Yes/2: No)");
					choice = sc.nextInt();
					if (choice == 1) {
						System.out.println("what is the new price for this package?");
						double price = sc.nextDouble();
						p.setPrice(price);
					}
				}
				else {
					System.out.println("This item is inside package " + p.getName()
							+ ", please update the price of the package since the total price of each item in the package is higher.");
					System.out.println("what is the new price for this package?");
					double price = sc.nextDouble();
					p.setPrice(price);
				}
			}
		}
	}
	

	public void updatePackage() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Which package do you want to update? Enter the index of the package.");
		int index = sc.nextInt();
		if (getPackage(index) != null) {
			Package p = getPackage(index);

//		if (a != null)
			System.out.println("What do you want to update? Enter 1 for Index, 2 for Name, 3 for Price, 4 for Item inside");
			int scan = sc.nextInt();

			if (scan == 1) {
				System.out.println("Enter the new index you want to update the item with.");
				int b = sc.nextInt();
				p.setIndex(b);
				System.out.println("Updated successfully");

			}

			else if (scan == 2) {
				System.out.println("Enter the new name you want to update the item with.");
				sc.nextLine();
				String b = sc.nextLine();
				p.setName(b);
				System.out.println("Updated successfully");
			}

			else if (scan == 3) {
				System.out.println(
						"Enter the new price you want to update the item with. Make sure it's lower than the total price of the items inside this package.");
				double b = sc.nextDouble();
				p.setPrice(b);
			}
			else if (scan == 4) {
				// call package.add/removePackageItem
				// reset price
				
			}

			else {
				System.out.println("There is no package with such index.");
			}

		}
	}

}
