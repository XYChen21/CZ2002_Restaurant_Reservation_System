package restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

/**
 * Represents the class that controls the methods pertaining to the Package class
 * @author Valencia Lie
 * @version 1.0
 * @since 2021-11-12
 */
public class PackageManager implements Serializable {
//	private List<Package> menuPackage;
	
	/**
	 * A hashmap containing a collection of indexes of the packages and the package objects themselves
	 */
	private HashMap<Integer, Package> menuPackage;

	 /** 
	  * Creates the hashmap collection of indexes of the packages and the package objects themselves
	  */
	public PackageManager() {
		this.menuPackage = new HashMap<Integer, Package>();
	}

	/**
	 * A method to add a package into the hashmap
	 * @param index Index of the package to be added
	 * @param p The package object itself
	 */
	public void addPackageMenu(int index, Package p) {

		menuPackage.put(index, p);
		System.out.println("Added package successfully.");
	}

	/**
	 * A method to remove a package from the hashmap
	 * @param ind Index of the package to be removed
	 */
	public void removePackageMenu(int ind) {
		menuPackage.remove(ind);
		System.out.println("Removed package successfully.");

	}

	/**
	 * A method to print the details of the hashmap
	 */
	public void viewPackageMenu() {
		System.out.println("******** PROMOTIONAL MENU **********" + '\n');
		for (int index : menuPackage.keySet())
			menuPackage.get(index).toStringCustom();
	}

	/**
	 * A method to get the package in the hashmap through the index as its key
	 * @param index Index of the package
	 * @return the Package object itself
	 */
	public Package getPackage(int index) {
		return menuPackage.get(index);
	}

	
	/** A method to check if a particular item is inside any package
	 * @param i Item object
	 * @return an arraylist of packages that contain that item object
	 */
	
	public ArrayList<Package> itemInPackage(Item i)
	{
		ArrayList<Package> packList = new ArrayList<Package>();
	
		for (int index : menuPackage.keySet()) 
		{
			Package p = menuPackage.get(index);
			if (p.checkItem(i))
				packList.add(p);
		}
		return packList;
	}

	/**
	 * A method to update the index of the package
	 * @param oldIndex The old index of the package
	 * @param newIndex The new index of the package
	 */
	public void updatePackageIndex(int oldIndex, int newIndex) {
		Package p = getPackage(oldIndex);

		if (p != null) {
			boolean duplicate = false;
			if (menuPackage.get(newIndex) != null)
				duplicate = true;

			if (duplicate == false) {
				p.setIndex(newIndex);
				Package a = menuPackage.get(oldIndex);
				menuPackage.remove(oldIndex);
				menuPackage.put(newIndex, a);
				System.out.println("Updated successfully");
			} else {
				System.out.println("Duplicate index of items in this menu.");
			}
		}

		else {
			System.out.println("Package with that index does not exist.");
		}
	}

	/**
	 * A method to update the name of the package
	 * @param oldIndex The index of the package
	 * @param newIndex The new name of the package
	 */
	public void updatePackageName(int oldIndex, String newName) {
		Package p = getPackage(oldIndex);

		if (p != null) {
			p.setName(newName);
			System.out.println("Updated successfully");
		}

		else {
			System.out.println("Package with that index does not exist.");
		}

	}

	/**
	 * A method to update the price of the package
	 * @param oldIndex The index of the package
	 * @param newIndex The new price of the package
	 */
	public void updatePackagePrice(int oldIndex, double newPrice) {
		Package p = getPackage(oldIndex);

		if (p != null) {
			System.out.println("Make sure it's lower than the total price of the items inside this package.");
			p.setPrice(newPrice);
		}

		else {
			System.out.println("Package with that index does not exist.");
		}
	}
	
	/**
	 * A method to add items inside a package
	 * @param oldIndex The index of the package
	 * @param i The item object to be added
	 * @param quantity The quantity of the item object to be added
	 * @return
	 */

	public boolean addItemsInPackage(int oldIndex, Item i, int quantity) {
		Package p = getPackage(oldIndex);
		if (p != null) {
			return p.addPackageItem(i, quantity);
		} else {
			System.out.println("Package with that index does not exist.");
			return false;
		}

	}

	/**
	 * A method to remove items from a package
	 * @param oldIndex The index of the package
	 * @param i The item object to be removed
	 * @param quantity The quantity of the item object to be removed
	 * @return
	 */

	public boolean removeItemsInPackage(int oldIndex, Item i, int quantity) {
		Package p = getPackage(oldIndex);
		if (p != null) {
			return p.removePackageItem(i, quantity);
		}

		else {
			System.out.println("Package with that index does not exist.");
			return false;
		}

	}

	/**
	 * A method to check if there are any other duplicates of packages with the same index
	 * @param index The index to be checked
	 * @return true if there is duplicates, false otherwise
	 */
	public boolean checkDuplicatePackage(int index) {
		if (menuPackage.containsKey(index)) {
			return true;
		} else
			return false;
	}
}