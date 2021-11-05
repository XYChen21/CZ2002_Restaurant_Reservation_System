package projectoop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import projectoop.Item.KindofFood;


public class Package extends Food{
	private List<Item> menuItemPackage;
	private String name;
	private double finalPrice;
	private double oriPrice;
	private int index;
	
	public Package(String name, int index)
	{
	menuItemPackage=new ArrayList<Item>();  
	this.name = name;
	this.index = index;
	}
	
	public void addPackageItem(Item i)
	{boolean duplicate = false;
	for(Item food:menuItemPackage)
		{if (food.getIndex() == i.getIndex())
			{duplicate = true;
			break;
			
			}}
	if (duplicate == false)
	{menuItemPackage.add(i);}

	else
	{System.out.println("Duplicate index of items in this package.");}
	}
	
	public void setPrice()
	{for(Item food:menuItemPackage)
		{oriPrice += food.getPrice();}
	finalPrice = oriPrice*0.90;}
	

	
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
	
	public void toStringCustom() {
	System.out.println("Package Index = " + index + '\n' +
		      "Package Name = " + name + '\n' +
		      "Original Package Price = " + oriPrice + '\n' +
		      "Discounted Package Price (10% OFF) = " + finalPrice + '\n');
	  for(Item food:menuItemPackage)  
		    System.out.println(food.toStringCust());
	  System.out.println("*************************************");
	  }
	
	public void sort()
	{menuItemPackage.sort(Comparator.comparing(Item::getIndex));}
	
}



