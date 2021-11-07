package projectoop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class Package extends Food{
	private List<Item> menuItemPackage;
	private String name;
	private double finalPrice = Double.MAX_VALUE;
	private double oriPrice;
	private int index;
	
	public Package(String name, int index)
	{
	menuItemPackage=new ArrayList<Item>();  
	
	this.name = name;
	this.index = index;
	}
	
	public void addPackageItem(Item i)
	{
		menuItemPackage.add(i);}


	
	
	
	public void setPrice()
	{oriPrice = 0;
	for(Item food:menuItemPackage)
	{oriPrice += food.getPrice();}
		
	if (finalPrice>=oriPrice)
	{
	finalPrice = oriPrice*0.90;}
	
	}
	

	
	public void removePackageItem(int a)
	{boolean removed = false;
	int count = 0;

	for(Item food:menuItemPackage)
		{if (food.getIndex() == a)
			{
			removed = true;
			break;
			}
		count++;
	}

	if (removed == true)
	{menuItemPackage.remove(count);
	System.out.println("Item in package is removed.");}

	if (removed == false)
	{System.out.println("Item with that index does not exist in this package. Nothing is removed.");}
	}
	
	
	public String getName()
	{return name;}
	
	public double getPrice()
	{return finalPrice;}
	
	public double getOriPrice()
	{return oriPrice;}
	
	public int getIndex()
	{return index;}
	
	public void setName(String name)
	{this.name = name;}
	
	public void setIndex(int index)
	{this.index = index;}
	
	public void setPrice(double price)
	{this.finalPrice = price;}
	
	public int getItemPackage(int index)
	{int a = 0;
	for(Item food:menuItemPackage)
	{if (food.getIndex() == index)
		{return a;}
		a++;}

	return -1;

	}
	
	public boolean isNull()
	{int a = 0;
	for(Item food:menuItemPackage)
		{a++;}
	if (a == 0)
		return true;
	else
		return false;}
	

	public Item getItemPackageByIndex(int a) {
//	{
//	System.out.println(menuItemPackage.get(a).toString());
	return menuItemPackage.get(a);
		
	}
	
	public void toStringCustom() {
	System.out.println("Package Index = " + index + '\n' +
		      "Package Name = " + name + '\n' +
		      "Original Package Price = " + oriPrice + '\n' +
		      "Discounted Package Price = " + finalPrice + '\n');
	  for(Item food:menuItemPackage)  
		    System.out.println(food.toString());
	  System.out.println("*************************************");
	  }
	
	public void sort()
	{menuItemPackage.sort(Comparator.comparing(Item::getIndex));}
	
}



