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
		Item i = getItem(oldIndex);
		
		if (i != null)
		{boolean duplicate = false;
		if (menu.get(newIndex) != null)
			duplicate = true;
			
		if (duplicate == false) {
			i.setIndex(newIndex);
			Item a = menu.get(oldIndex);
			menu.remove(oldIndex);
			menu.put(newIndex, a);
			System.out.println("Updated successfully");
			}
		else {
			System.out.println("Duplicate index of items in this menu.");
			}}
		
		else
		{System.out.println("Item with that index does not exist.");
			
		}
		}
	
	public void updateName(int oldIndex, String newName)
	{Item i = getItem(oldIndex);
	
			if (i != null)	
			{i.setName(newName);
			System.out.println("Updated successfully");
			}
			
			else
			{System.out.println("Item with that index does not exist.");
				
			}}
	
	public void updateDesc(int oldIndex, String newDesc)
			{Item i = getItem(oldIndex);
			
			if (i != null)
			{
			i.setDescription(newDesc);
			System.out.println("Updated successfully");}
			
			else
			{System.out.println("Item with that index does not exist.");
				
			}
				

			}

	public boolean updatePrice(int oldIndex, double newPrice)
			{Item i = getItem(oldIndex);
			
			if (i != null)
			{
			i.setPrice(newPrice);
			System.out.println("Updated successfully");
			return true;}
			
			else
			{System.out.println("Item with that index does not exist.");
			return false;
				
			}
			
			
		}

	public void updateType(int oldIndex, KindofFood newType)
			{Item i = getItem(oldIndex);
			
			if (i != null)
			{
			i.setType(newType);
			System.out.println("Updated successfully");}
			else
			{System.out.println("Item with that index does not exist.");
				
			}
			
		}

	public boolean checkDuplicate(int index)
	{if (menu.get(index) != null)
		return true;
		
	else
		{
		return false;}
		
		
	}

	public void viewMenu() {
		System.out.println("************** MENU ****************" + '\n');
		for (int index : menu.keySet())
			System.out.println(menu.get(index).toString());
	}
	

	public Item getItem(int index) {
		return menu.get(index);
	}}
