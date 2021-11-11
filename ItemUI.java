package restaurant;

import java.util.Scanner;

import restaurant.Item.KindofFood;


public class ItemUI {
	
Scanner sc = new Scanner(System.in);



public String getNameItemUI()
	{System.out.println("What is the name of the item?");
	String name;
	while (true)
	{try {
		
		name = sc.nextLine();
		
		break;
	}
	catch(Exception e) 
	{System.out.println(e.getMessage());}}
	
	return name;
	}

	public int getIndexItemUI() {
		System.out.println("What is the index of the item?");
		int index;
		while (true){
			try {
				index = sc.nextInt();
				sc.nextLine();
				break;
			}

			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return index;
	}

public int getNewIndexItemUI()
{System.out.println("What is the new index of the item?");
int index;
while (true)
{try {
	index = sc.nextInt();
	sc.nextLine();
	break;
}

catch(Exception e)
{System.out.println(e.getMessage());}}


return index;
}

public String getDescItemUI()
	{System.out.println("What is the description of the item?");
	String desc;
	while (true)
		{
		try {
			desc = sc.nextLine();
			break;
		}
		
		catch(Exception e)
		{System.out.println(e.getMessage());}
		
		}
	return desc;
	}


public double getPriceItemUI()
	{System.out.println("What is the price of the item?");
	double price;
	while (true)
	{
		try {
			price = sc.nextDouble();
			if (price<0)
				throw new Exception ("Invalid input. Price cannot be smaller than 0. Please enter again.");
				
			break;}
		
		catch(Exception e)
		{System.out.println(e.getMessage());}


	}
	
	return price;
	}

public KindofFood getTypeItemUI()
	{
	System.out.println("What is the type of the item?  1: Main, 2: Sides, 3: Beverage, 4: Dessert. Enter either 1,2,3 or 4.");
	int d;
	while (true)
	{
		try {
			d = sc.nextInt();
			sc.nextLine();
			if (d<1 || d>4)
				{throw new Exception ("Invalid input. Please enter either 1,2,3 or 4.");}
			break;}
		
		catch(Exception e)
		{System.out.println(e.getMessage());}
		
	}
	KindofFood f = KindofFood.getTypeByOrdinal(d);
	return f;
	}

public int updateMenuChoice()
	{System.out.println(
			"What do you want to update? Enter 1 for Index, 2 for Name, 3 for Description, 4 for Price and 5 for Type of food.");
	int choice;
	
	while (true)
	{
		try {
			choice = sc.nextInt();
			sc.nextLine();
			if (choice<1 || choice>5)
				throw new Exception ("Invalid input. Please enter either 1,2,3, 4 or 5.");
			break;}
		
		catch(Exception e)
		{System.out.println(e.getMessage());}
		
	}
	return choice;
	
	}

}


