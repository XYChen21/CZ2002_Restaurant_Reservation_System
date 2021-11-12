package restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Package extends Food implements Serializable {
//	private List<Item> menuItemPackage;
	private HashMap<Item, Integer> menuItemPackage;
	private String name;
	private double finalPrice;
	private double oriPrice;
	private int index;

	public Package(String name, int index) {
//		menuItemPackage = new ArrayList<Item>();
		menuItemPackage = new HashMap<Item, Integer>();
		this.name = name;
		this.index = index;
	}

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

	public boolean removePackageItem(Item i, int quantity) { // ensure item i is not null before pass in
		Integer quan = menuItemPackage.get(i);
		if (quan == null){
			System.out.println("Item does not exist in this package. Nothing is removed.");
			return false;
		}
		else {
			if (quan < quantity) {
				System.out.println("Not enough items insides. Nothing is removed.");
				return false;
			}
			else {
				menuItemPackage.replace(i, quan-quantity);
				return true;
			}
			
		}
	}
	public String getName() {
		return name;
	}

	public double getPrice() {
		return finalPrice;
	}

	public double getOriPrice() {
		oriPrice = 0;
		for (Item i : menuItemPackage.keySet())
		{
			int quantity = menuItemPackage.get(i);
			oriPrice += i.getPrice() * quantity;
		}
		return oriPrice;

	}

	public int getIndex() {
		return index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPrice(double price) {
		this.finalPrice =price;
	}

	public boolean isNull() {
		if (menuItemPackage.isEmpty())
			return true;
		else
			return false;
	}

	public void toStringCustom() {
		System.out.println("Package Index = " + index + '\n' + "Package Name = " + name + '\n'
				+ "Original Package Price = " + oriPrice + '\n' + "Discounted Package Price = " + finalPrice + '\n');
		for (Item food : menuItemPackage.keySet())
			System.out.println("quantity: " + menuItemPackage.get(food) + "\n" + food.toString());
		System.out.println("*************************************");
	}

//	public void sort() {
//		menuItemPackage.sort(Comparator.comparing(Item::getIndex));
//	}

	public boolean checkItem(Item i) {
		Integer quan = menuItemPackage.get(i);
		if (quan == null)
			return false;
		else
			return true;
	}

}