package projectoop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
	{if (pack.getIndex() ==p.getIndex())
		{duplicate = true;
		break;
		
		}}
	if (duplicate == false)
		{menuPackage.add(p);}

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
	

	public Package getPackageByIndex(int a)
	{menuPackage.get(a).toStringCustom();
	return menuPackage.get(a);
	
		
		
	}
	
	public void sort()
	{for (Package pack:menuPackage)
	{pack.sort();}
		
	}
	
}