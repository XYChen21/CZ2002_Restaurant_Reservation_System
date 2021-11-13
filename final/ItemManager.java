package restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import restaurant.Item.KindofFood;

/**
 * Represents the class that controls the methods pertaining to the Item class
 * @author Valencia Lie
 * @version 1.0
 * @since 2021-11-12
 */
public class ItemManager implements Serializable {
	
	/**
	 * A HashMap collection of indexes of items and the item objects themselves
	 */
	private HashMap<Integer, Item> menu;

 /** 
  * Creates the HashMap collection of indexes of items and the item objects themselves
  */
	public ItemManager() {
		this.menu = new HashMap<Integer, Item>();
	}

/**
 * A method to add item objects into the HashMap
 * @param index Index of the item object
 * @param i Item object to be added
 */
	public void addMenu(int index, Item i) {
		menu.put(index, i);
		System.out.println("Item is added.");
	}
	
/**
 * A method to remove item objects from the HashMap
 * @param index Index of the item object
 */

	public void removeMenu(int index) {
		menu.remove(index);
		System.out.println("Item is removed.");
	}

	/**
	 * A method to update an item's index
	 * @param oldIndex The old index of the item object
	 * @param newIndex The new index of the item object
	 */
	public void updateIndex(int oldIndex, int newIndex) {
		Item i = menu.get(oldIndex);
		i.setIndex(newIndex);
//			Item a = menu.get(oldIndex);
		menu.remove(oldIndex);
		menu.put(newIndex, i);

	}
	
	/**
	 * A method to check if the HashMap of items is empty or not
	 * @return true if the HashMap is empty, false otherwise
	 */
	public boolean isNull()
	{
		return menu.isEmpty();
	}
	
	/**
	 * A method to update the name of an item object in the HashMap
	 * @param oldIndex The index of the item
	 * @param newName The name of the item
	 */
	public void updateName(int oldIndex, String newName) {
		Item i = menu.get(oldIndex);
		i.setName(newName);
		System.out.println("Updated successfully");
	}

	/**
	 * A method to update the description of an item object in the HashMap
	 * @param oldIndex The index of the item
	 * @param newDesc The description of the item
	 */
	public void updateDesc(int oldIndex, String newDesc) {
		Item i = menu.get(oldIndex);
		i.setDescription(newDesc);
		System.out.println("Updated successfully");

	}

	/**
	 * A method to update the price of an item object in the HashMap
	 * @param oldIndex The index of the item
	 * @param newPrice The description of the item
	 */
	public void updatePrice(int oldIndex, double newPrice) {
		Item i = menu.get(oldIndex);
		i.setPrice(newPrice);
		System.out.println("Updated successfully");
	}

	/**
	 * A method to update the type of an item object in the HashMap
	 * @param oldIndex The index of the item
	 * @param newType The type of the item
	 */
	public void updateType(int oldIndex, KindofFood newType) {
		Item i = menu.get(oldIndex);
		i.setType(newType);
		System.out.println("Updated successfully");

	}

	/**
	 * A method to check any duplicate of items inside the HashMap
	 * @param index The index of item to be checked
	 * @return true if there is duplicates, false otherwise
	 */
	public boolean checkDuplicate(int index) {
		if (menu.get(index) != null)
			return true;

		else {
			return false;
		}

	}
	
	/**
	 * A method to print the details of the HashMap containing a collection of item objects
	 */
	public void viewMenu() {
		System.out.println("************** MENU ****************" + '\n');
		for (int index : menu.keySet())
			System.out.println(menu.get(index).toString());
	}

	/**
	 * A method to get an item object from the HashMap
	 * @param index of the item object
	 * @return the Item object
	 */
	public Item getItem(int index) {
		return menu.get(index);
	}
}
