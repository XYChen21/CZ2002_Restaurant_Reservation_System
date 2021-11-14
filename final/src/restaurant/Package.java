package restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Represents promotional package containing item objects
 * @author Valencia Lie
 * @version 1.0
 * @since 2021-11-12
 */
public class Package extends Food implements Serializable{
	/**
	 * Represents a HashMap of item object and its quantity inside the package
	 */
	private HashMap<Item, Integer> menuItemPackage;
	/**
	 * Name of the package
	 */
	private String name;
	/**
	 * Price of the package that is lower than the total price of of all the items inside
	 */
	private double finalPrice;
	/** 
	 * Total price of all the items inside
	 */
	private double oriPrice;
	/**
	 * The index of the package
	 */
	private int index;

	/**
	 * Constructor of the package object
	 * @param name Name of the package object
	 * @param index Index of the package object
	 */
	public Package(String name, int index) {
//		menuItemPackage = new ArrayList<Item>();
		menuItemPackage = new HashMap<Item, Integer>();
		this.name = name;
		this.index = index;
	}

	/** 
	 * Method to add items inside the package
	 * @param i Item object to be added
	 * @param quantity The quantity of the item objects to be added in 
	 * @return true if addition is successful
	 */
	public boolean addPackageItem(Item i, int quantity) {
		Integer quan = menuItemPackage.get(i);
		
		if (quan != null)
		{
			System.out.println("Item already inside. Quantity is incremented.");
			menuItemPackage.replace(i, quan+quantity);
		}
		menuItemPackage.put(i, quantity);
		return true;
	}
	
	/**
	 * Method to remove items from a package object
	 * @param i Item object to be removed
	 * @param quantity The quantity of the item objects to be removed
	 * @return true if removal is successful, false if removal is unsuccessful
	 */

	public boolean removePackageItem(Item i, int quantity) { // ensure item i is not null before pass in
		Integer quan = menuItemPackage.get(i);
		if (quan == null){
			System.out.println("Item does not exist in this package. Nothing is removed.");
			return false;
		}
		else {
			if (quan < quantity) {
				System.out.println("Not enough items inside. Nothing is removed.");
				return false;
			}
			else {
				menuItemPackage.replace(i, quan-quantity);
				return true;
			}
			
		}
	}
	/**
	 * Method to get the name of the package
	 * @return the name of the package
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to get the price of the package
	 * @return the price of the package
	 */

	public double getPrice() {
		return finalPrice;
	}

	/**
	 * Method to get the total price of the items inside the package
	 * @return the total price of the items inside the package
	 */
	public double getOriPrice() {
		oriPrice = 0;
		for (Item i : menuItemPackage.keySet())
		{
			int quantity = menuItemPackage.get(i);
			oriPrice += i.getPrice() * quantity;
		}
		return oriPrice;

	}
	
	/**
	 * Method to get the index of the package
	 * @return the index of the package
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Method to set the name of the package
	 * @param name The name of the package
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method to set the index of the package
	 * @param index The index of the package
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Method to set the price of the package
	 * @param price The price of the package
	 */
	public void setPrice(double price) {
		this.finalPrice =price;
	}
	
	/**
	 * Method to check if the package has any items inside
	 * @return true if package is empty, false otherwise
	 */
	public boolean isNull() {
		if (menuItemPackage.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * Method to print the details of the package
	 */
	public void toStringCustom() {
		System.out.println("Package Index = " + index + '\n' + "Package Name = " + name + '\n'
				+ "Original Package Price = " + oriPrice + '\n' + "Discounted Package Price = " + finalPrice + '\n');
		for (Item food : menuItemPackage.keySet())
			System.out.println("quantity: " + menuItemPackage.get(food) + "\n" + food.toString());
		System.out.println("*************************************");
	}


	/**
	 * Method to check if item is in package or not
	 * @param i Item object to be checked
	 * @return true if item object is in package, false otherwise
	 */
	public boolean checkItem(Item i) {
		Integer quan = menuItemPackage.get(i);
		if (quan == null)
			return false;
		else
			return true;
	}
}
