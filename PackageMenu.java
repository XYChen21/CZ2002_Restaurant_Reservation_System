package projectoop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PackageMenu {
	public static Package p;
	static List<Package> menuPackage=new ArrayList<Package>();
	
	public static void sortPackageMenuByAlphabet() {
		menuPackage.sort(Comparator.comparing(Package::getPackageName));
		printPackageMenu(menuPackage);
	}
	
	public static void addPackageMenu(Package p)
	{boolean duplicate = false;
	for(Package pack:menuPackage)
	{if (pack.getPackageIndex() ==p.getPackageIndex())
		{duplicate = true;
		break;
		
		}}
	if (duplicate == false)
		{menuPackage.add(p);}

	else
		{System.out.println("Duplicate index of packages.");}
		
	}
	
	public static void removePackageMenu(int a)
	{boolean removed = false;
	int count = 0;

	for(Package pack:menuPackage)
		{if (pack.getPackageIndex() == a)
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


	public static void viewPackageMenu()
	{sortByDefaultPackage(menuPackage);
	System.out.println("******** PROMOTIONAL MENU **********" + '\n');
	for(Package pack:menuPackage)  
		pack.toStringCustom(); 
	}

	private static void sortByDefaultPackage(List<Package> k)
	{k.sort(Comparator.comparing(Package::getPackageIndex));
	}

	private static void printPackageMenu(List<Package>a)
	{System.out.println("******** PROMOTIONAL MENU **********" + '\n');
	for(Package pack:a)  
	   pack.toStringCustom();}
	
	public static void filterPackageMenuByPrice(Double a, Double b)
	{List<Package> menuPackage2 =new ArrayList<Package>(); 
	boolean empty = true;

	for(Package pack:menuPackage)
	{if (pack.getPackagePrice() >= a && pack.getPackagePrice()<=b)
		{menuPackage2.add(pack);
		empty = false;
		}}

	sortByDefaultPackage(menuPackage2);
	printPackageMenu(menuPackage2);

	if (empty == true)
	{System.out.println("There are no packages within that price range.");}
	}
	
	public static Package filterPackageMenuByIndex(int a)
	{
	boolean empty = true;
	for(Package pack:menuPackage)
	{if (pack.getPackageIndex() == a)
		{p = pack;
		empty = false;
		}}
	
	return p;
	

	}
	
	public static int getPackage(int index)
	{int a = 0;
	for(Package pack:menuPackage)
	{if (pack.getPackageIndex() == index)
		{return a;}
		a++;}

	return -1;

	}
	

	public static Package getPackageByIndex(int a)
	{menuPackage.get(a).toStringCustom();
	return menuPackage.get(a);
	
		
		
	}
	
}