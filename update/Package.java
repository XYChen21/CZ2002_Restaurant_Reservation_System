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

	public void addPackageItem(Item i, int quantity) {
		if (menuItemPackage.get(i) != null)
		{
			System.out.println("Item already inside");
			return;
		}
		menuItemPackage.put(i, quantity);
//		menuItemPackage.add(i);
	}

	public void removePackageItem(Item i, int quantity) { // ensure item i is not null before pass in
		Integer quan = menuItemPackage.get(i);
		if (quan == null){
			System.out.println("Item does not exist in this package. Nothing is removed.");
		}
		else {
			if (quan < quantity)
				System.out.println("Not enough items insides. Nothing is removed.");
                // remove all or don't remove?
			else
				menuItemPackage.replace(i, quantity - quan);
		}
	}
	public void updatePackageItem()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Current package:");
		this.toStringCustom();
		System.out.println("Option: 1. add new item; 2. change quantity of existing item; 3. Reduce quantity of existing item");
		int choice = sc.nextInt();
		sc.nextLine();
		if (choice == 1)
		{
			addPackageItem()
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
		Scanner sc = new Scanner(System.in);
		double newPrice = price;
		if (price < getOriPrice()) {
			this.finalPrice = price;
		}
		else {
			while (newPrice > getOriPrice()) {
				System.out.println(
						"Updated price should be lower than the total price of individual items. Please enter a new price.");
				newPrice = sc.nextDouble();
			}
			this.finalPrice = newPrice;
		}
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