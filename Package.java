package projectoop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projectoop.Item.KindofFood;


public class Package {
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
	{System.out.println("Duplicate index.");}
	}
	
	public void setPackagePrice()
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
	System.out.println("Removed");}

	if (removed == false)
	{System.out.println("Nothing is removed.");}
	}
	
	
	public String getPackageName()
	{return name;}
	
	public  double getPackagePrice()
	{return finalPrice;}
	
	public int getPackageIndex()
	{return index;}
	
	public void toStringCustom() {
	System.out.println("Package Index = " + index + '\n' +
		      "Package Name = " + name + '\n' +
		      "Original Package Price = " + oriPrice + '\n' +
		      "Discounted Package Price (10% OFF) = " + finalPrice + '\n');
	  for(Item food:menuItemPackage)  
		    System.out.println(food.toStringCust());
	  System.out.println("*************************************");
	  }
	
}



