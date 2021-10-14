package projectoop;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Item implements Comparable<Item> {
	public enum KindofFood {
		MAIN, SIDES, BEVERAGE, DESSERT;
		
		public static KindofFood getTypeByOrdinal(int ordinal) {
	        for(KindofFood t : KindofFood.values()) {
	            if(t.ordinal() == ordinal-1) {
	                return t;
	            }
	        }

	        return null;
	    }
	
	};
	
	
	private String name;
	private String description;
	private double price;
	private int ID;
	private KindofFood type;

public Item(int ID, String name, String description, double price, KindofFood type)
{this.ID = ID;
this.name = name;
this.description = description;
this.price = price;
this.type = type;

}

public int getIndex()
{return ID;}

public KindofFood getType()
{return type;}

public String getName()
{return name;}

public String getDescription()
{return description;}

public double getPrice()
{return price;}

public void setIndex()
{this.ID = ID;}

public void setType(KindofFood type)
{this.type = type;}

public void setName(String name)
{this.name = name;}

public void setDescription(String description)
{this.description = description;}

public void setPrice(double price)
{this.price = price;}

@Override
public String toString() {
  return 
      "Index = " + ID + '\n' +
      "Name = " + name + '\n' +
      "Description = " + description + '\n' +
      "Price = " + price + '\n' +
      "Type = " + type + '\n'
      ;
}

public class Menu{
static List<Item> menu=new ArrayList<Item>();  

public static void addMenu(Item i)
{boolean duplicate = false;
for(Item food:menu)
	{if (food.getIndex() == i.getIndex())
		{duplicate = true;
		break;
		
		}}
if (duplicate == false)
{menu.add(i);}

else
{System.out.println("Duplicate index.");}
}



public static void removeMenu(int a)
{boolean removed = false;
int count = 0;

for(Item food:menu)
	{if (food.getIndex() == a)
		{
		removed = true;
		break;
		}
	count++;
}

if (removed == true)
{menu.remove(count);
System.out.println("Removed");}

if (removed == false)
{System.out.println("Nothing is removed.");}
}

public static void filterMenuByType(KindofFood k)
{List<Item> newMenu=new ArrayList<Item>(); 
for(Item food:menu)
	{if (food.getType() == k)
		{newMenu.add(food);
		
		}}

sortByDefault(newMenu);
for(Item food:newMenu)  
System.out.println(food.toString()); 
}

public static void filterMenuByPrice(Double a, Double b)
{List<Item> newMenu2=new ArrayList<Item>(); 

for(Item food:menu)
{if (food.getPrice() >= a && food.getPrice()<=b)
	{newMenu2.add(food);
	
	}}

sortByDefault(newMenu2);
for(Item food:newMenu2)  
System.out.println(food.toString()); 
}


public static void sortMenuByAlphabet() {
	menu.sort(Comparator.comparing(Item::getName));
	for(Item food:menu)  
	    System.out.println(food.toString());
}


public static void viewMenu()
{  sortByDefault(menu);
	for(Item food:menu)  
    System.out.println(food.toString()); 
}

public static void sortByDefault(List k)
{k.sort(Comparator.comparing(Item::getIndex));
}

}

@Override
public int compareTo(Item o) {
	// TODO Auto-generated method stub
	return 0;
}



}
