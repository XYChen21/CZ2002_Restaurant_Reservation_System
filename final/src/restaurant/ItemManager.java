package restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import restaurant.Item.KindofFood;

public class ItemManager implements Serializable {
	private HashMap<Integer, Item> menu;
//	private List<Item> menu;

	public ItemManager() {
		this.menu = new HashMap<Integer, Item>();
	}

	public void addMenu(int index, Item i) {
		menu.put(index, i);
		System.out.println("Item is added.");
	}

	public void removeMenu(int index) {
		menu.remove(index);
		System.out.println("Item is removed.");
	}

	public void updateIndex(int oldIndex, int newIndex) {
		Item i = menu.get(oldIndex);
		i.setIndex(newIndex);
//			Item a = menu.get(oldIndex);
		menu.remove(oldIndex);
		menu.put(newIndex, i);

	}
	public boolean isNull()
	{
		return menu.isEmpty();
	}

	public void updateName(int oldIndex, String newName) {
		Item i = menu.get(oldIndex);
		i.setName(newName);
		System.out.println("Updated successfully");
	}

	public void updateDesc(int oldIndex, String newDesc) {
		Item i = menu.get(oldIndex);
		i.setDescription(newDesc);
		System.out.println("Updated successfully");

	}

	public void updatePrice(int oldIndex, double newPrice) {
		Item i = menu.get(oldIndex);
		i.setPrice(newPrice);
		System.out.println("Updated successfully");
	}

	public void updateType(int oldIndex, KindofFood newType) {
		Item i = menu.get(oldIndex);
		i.setType(newType);
		System.out.println("Updated successfully");

	}

	public boolean checkDuplicate(int index) {
		if (menu.get(index) != null)
			return true;

		else {
			return false;
		}

	}

	public void viewMenu() {
		System.out.println("************** MENU ****************" + '\n');
		for (int index : menu.keySet())
			System.out.println(menu.get(index).toString());
	}

	public Item getItem(int index) {
		return menu.get(index);
	}
}
