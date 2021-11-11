package restaurant;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class PackageManager implements Serializable {
//	private List<Package> menuPackage;
	private HashMap<Integer, Package> menuPackage;

	public PackageManager() {
		this.menuPackage = new HashMap<Integer, Package>();
	}

	public void addPackageMenu(int index, Package p) {
	
			menuPackage.put(index, p);
			System.out.println("Added package successfully.");
	}

	public void removePackageMenu(int ind) {
			menuPackage.remove(ind);
			System.out.println("Removed package successfully.");
		
	}

	public void viewPackageMenu() {
		System.out.println("******** PROMOTIONAL MENU **********" + '\n');
		for (int index : menuPackage.keySet())
			menuPackage.get(index).toStringCustom();
	}



	public Package getPackage(int index) {
		return menuPackage.get(index);
	}

//
//	public void updateItemInPackage(Item i) {
//		Scanner sc = new Scanner(System.in);
//		int choice;
//		for (int index : menuPackage.keySet()) {
//			Package p = menuPackage.get(index);
//			if (p.checkItem(i)) {
//				if (p.getPrice() < p.getOriPrice()) {
//					System.out.println("This item is inside package " + p.getName()
//							+ ", do you want to update price of this package as well? (1: Yes/2: No)");
//					choice = sc.nextInt();
//					if (choice == 1) {
//						System.out.println("what is the new price for this package?");
//						double price = sc.nextDouble();
//						p.setPrice(price);
//					}
//				}
//				else {
//					System.out.println("This item is inside package " + p.getName()
//							+ ", please update the price of the package since the total price of each item in the package is higher.");
//					System.out.println("what is the new price for this package?");
//					double price = sc.nextDouble();
//					p.setPrice(price);
//				}
//			}
//		}
//	}
	
	public Package checkItemInPackage(Item i)
	{
		for (int index : menuPackage.keySet()) {
		Package p = menuPackage.get(index);
		if (p.checkItem(i) == true) 
			return p;}
		
		return null;
		
	}
	
	public int updatePriceItemInPackage(Item i)
	{if (checkItemInPackage(i) != null)
		{Package p = checkItemInPackage(i);
		if(p.getPrice() < p.getOriPrice())
		{return 1;}
		else
		{return 2;}
		
		
		
		}
	return -1;
		
	}
	

	public void updatePackageIndex(int oldIndex, int newIndex) {
		Package p = getPackage(oldIndex);
		
		if (p != null)
		{boolean duplicate = false;
		if (menuPackage.get(newIndex) != null)
			duplicate = true;
			
		if (duplicate == false) {
			p.setIndex(newIndex);
			Package a = menuPackage.get(oldIndex);
			menuPackage.remove(oldIndex);
			menuPackage.put(newIndex, a);
			System.out.println("Updated successfully");
			}
		else {
			System.out.println("Duplicate index of items in this menu.");}}
		
		else
			{System.out.println("Package with that index does not exist.");}}
	
	public void updatePackageName(int oldIndex, String newName)
		{Package p = getPackage(oldIndex);
		
		if (p != null)
		{
		p.setName(newName);
		System.out.println("Updated successfully");}
		
		else
		{System.out.println("Package with that index does not exist.");}
		
		
		}
			
	
	public void updatePackagePrice(int oldIndex, double newPrice)
		{Package p = getPackage(oldIndex);
		
		if (p != null)
		{System.out.println(
				"Make sure it's lower than the total price of the items inside this package.");
		p.setPrice(newPrice);}
		
		else
		{System.out.println("Package with that index does not exist.");}
		}

	
	public void addItemsInPackage(int oldIndex, Item i, int quantity)
	{Package p = getPackage(oldIndex);
	if (p != null) {
	p.addPackageItem(i, quantity);}
	else
	{System.out.println("Package with that index does not exist.");}
	
	
	
	}
	
	
	public void removeItemsInPackage(int oldIndex, Item i, int quantity)
	{Package p = getPackage(oldIndex);
	if (p != null) {
	
	p.removePackageItem(i, quantity);}
	
	else
	{System.out.println("Package with that index does not exist.");}
		
	}
	
	public boolean checkDuplicatePackage(int index)
	{if (menuPackage.containsKey(index))
		{return true;}
	else
		return false;}


			
	



}