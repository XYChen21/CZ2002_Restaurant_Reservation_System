package projectoop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import projectoop.Item.KindofFood;

public class PackageMenu {
	public List<Package> menuPackage;
	
	public PackageMenu()
	{this.menuPackage=new ArrayList<Package>();}
	
	public void sortPackageMenuByAlphabet() {
		menuPackage.sort(Comparator.comparing(Package::getName));
		printPackageMenu(menuPackage);
	}
	
	public void addPackageMenu(Package p)
	{boolean duplicate = false;
	for(Package pack:menuPackage)
	{if (pack.getIndex() == p.getIndex())
		{duplicate = true;
		break;
		
		}}
	if (duplicate == false)
		{if (p.isNull() == true)
			{System.out.println("No package is created since no valid item is added.");}
		else
		{menuPackage.add(p);}}

	else
		{System.out.println("Duplicate index of packages.");}
		
	}
	
	public void removePackageMenu(int a)
	{boolean removed = false;
	int count = 0;

	for(Package pack:menuPackage)
		{if (pack.getIndex() == a)
			{
			removed = true;
			break;
			}
		count++;
	}

	if (removed == true)
	{menuPackage.remove(count);
	System.out.println("Package is removed.");}

	if (removed == false)
	{System.out.println("Package with that index does not exist. Nothing is removed.");}
	}


	public void viewPackageMenu()
	{sortByDefaultPackage(menuPackage);
	System.out.println("******** PROMOTIONAL MENU **********" + '\n');
	for(Package pack:menuPackage)  
		pack.toStringCustom(); 
	}

	private void sortByDefaultPackage(List<Package> k)
	{sort();
	
	k.sort(Comparator.comparing(Package::getIndex));
	}

	private void printPackageMenu(List<Package>a)
	{System.out.println("******** PROMOTIONAL MENU **********" + '\n');
	for(Package pack:a)  
	   pack.toStringCustom();}
	
	public void filterPackageMenuByPrice(double a, double b)
	{List<Package> menuPackage2 =new ArrayList<Package>(); 
	boolean empty = true;

	for(Package pack:menuPackage)
	{if (pack.getPrice() >= a && pack.getPrice()<=b)
		{menuPackage2.add(pack);
		empty = false;
		}}

	sortByDefaultPackage(menuPackage2);
	printPackageMenu(menuPackage2);

	if (empty == true)
	{System.out.println("There are no packages within that price range.");}
	}
	
	public int filterPackageMenuInt(int a)
	{int count = 0;
	for(Package pack:menuPackage)
	{if (pack.getIndex() == a)
		{return count;
		}
	count++;
	}
	return -1;
	
	}
	
	
	public Package filterPackageMenuByIndex(int a)
	{return menuPackage.get(a);
	}
	
	
	public int getPackage(int index)
	{int a = 0;
	for(Package pack:menuPackage)
	{if (pack.getIndex() == index)
		{return a;}
		a++;}

	return -1;

	}
	

	public Package getPackageByIndex(int a) {
//	{menuPackage.get(a).toStringCustom();
	return menuPackage.get(a);
	
		
		
	}
	
	public void sort()
	{for (Package pack:menuPackage)
	{pack.sort();}
		
	}
	
	public void setAllPackagesPrice()
	{for (Package pack:menuPackage)
		pack.setPrice();}
	

	public void updatePackage()
	{Scanner sc = new Scanner(System.in);
	System.out.println("Which package do you want to update? Enter the index of the package.");
	int index = sc.nextInt();
	int result = getPackage(index);
	if (result != -1)
		{Package a = getPackageByIndex(result);
		System.out.println("What do you want to update? Enter 1 for Index, 2 for Name, 3 for Price.");
		int scan = sc.nextInt();
		
		if (scan == 1)
			{System.out.println("Enter the new index you want to update the item with.");
			int b = sc.nextInt();
			a.setIndex(b);
			System.out.println("Updated successfully");
			
			}
		
		else if (scan == 2)
			{System.out.println("Enter the new name you want to update the item with.");
			sc.nextLine();
			String b = sc.nextLine();
			a.setName(b);
			System.out.println("Updated successfully");
			}
		
		else if (scan == 3)
			{System.out.println("Enter the new price you want to update the item with. Make sure it's lower than the total price of the items inside this package.");
			double b = sc.nextDouble();
			if (b< a.getOriPrice())
				{a.setPrice(b);
				System.out.println("Updated successfully");}
			
			else
			{System.out.println("The package price should be lower than the total price of the items inside this package.");}
			}}
		
	
	else
	{System.out.println("There is no package with such index.");}
		
		
		
		
		
	}
	
	
	


}