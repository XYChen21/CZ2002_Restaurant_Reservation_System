package restaurant;

import java.util.Scanner;

public class PackageUI {
	
	Scanner sc = new Scanner(System.in);
	
public String getNamePackageUI()
	{System.out.println("What is the name of the package?");
	String name;
	while (true)
	{try {
		name = sc.nextLine();
		break;
//		if (name < 0)
//			throw new Exception("Invalid input, price cannot be smaller than zero.");
	}
	catch(Exception e) 
	{System.out.println(e.getMessage());}}
	
	return name;
	}

public int getIndexPackageUI()
	{System.out.println("What is the index of the package?");
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

public int getNewIndexPackageUI()
{System.out.println("What is the new index of the package?");
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


public int getQuantity()
{System.out.println("How much quantity of this item?");
int no;
while (true)
	{try {
no = sc.nextInt();
sc.nextLine();
if (no <= 0)
	throw new Exception("Invalid input, quantity cannot be smaller than or equals to 0. Please enter again.");
break;
}

catch(Exception e)
{System.out.println(e.getMessage());}}


return no;
}



public double getPricePackageUI(double oriPrice)
{System.out.println("What is the price of the package?");
double price;
while (true)
{
	try {
		price = sc.nextDouble();
		if (price>=oriPrice || price<=0)
			throw new Exception ("Invalid input. Price cannot be smaller than 0 or higher than the total price of individual items inside the package. Please enter again.");
			
		break;}
	
	catch(Exception e)
	{System.out.println(e.getMessage());}


}

return price;
}

public int updatePackageChoice()
{System.out.println(
		"What do you want to update? Enter 1 for Index, 2 for Name, 3 for Price, 4 for Item inside.");
int choice;

while (true)
{
	try {
		choice = sc.nextInt();
		sc.nextLine();
		if (choice<1 || choice>4)
			throw new Exception ("Invalid input. Please enter either 1,2,3, 4 or 5.");
		break;}
	
	catch(Exception e)
	{System.out.println(e.getMessage());}
	
}
return choice;

}

public int updateItemInPackage1()
{

int choice;

while (true)
{
	try {
		choice = sc.nextInt();
		sc.nextLine();
		if (choice<1 || choice>2)
			throw new Exception ("Invalid input. Please enter either 1 or 2.");
		break;}
	
	catch(Exception e)
	{System.out.println(e.getMessage());}
	
}
return choice;

}



}
