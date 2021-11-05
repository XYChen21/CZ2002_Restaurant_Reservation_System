package projectoop;

import java.util.Scanner;

import projectoop.Item.KindofFood;

public class main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	
		
		System.out.println("1. Add item in menu"
				+ "\n" +
				"2. Remove item from menu"
				+ "\n" + 
				"3. Sort menu based on index of item" 
				+ "\n" +
				"4. View menu based type of item" 
				+ "\n" +
				"5. View menu based on price"
				+ "\n" +
				"6. Sort menu based on name of item"
				+ "\n" +
				"7. Get specific menu item based on index"
				+ "\n" +
				"8. Create a new promotional package"
				+ "\n" +
				"9. Add item into an existing promotional package"
				+ "\n" +
				"10. Remove item from an existing promotional package"
				+ "\n" + 
				"11. Remove an existing promotional package entirely"
				+ "\n" + 
				"12. Sort promotional package menu based on index of package"
				+ "\n" +
				"13. View promotional package menu based on price"
				+ "\n" +
				"14. Sort promotional package menu based on name of package"
				+ "\n" +
				"15. Get specific package based on index"
				+ "\n" +
				"16. View both menus"
				+ "\n" +
				"17. Quit"
				+ "\n"
				);
		
		System.out.println("What is your option?" + "\n");
		int e = sc.nextInt();
		Menu m = new Menu();
		PackageMenu pack = new PackageMenu();
		
		while (e != 17)
			{
			if (e == 1)
				
				{
				System.out.println("Create an index for the item you want to add.");
				int a = sc.nextInt();
				sc.nextLine();
				System.out.println("What is the name of the item you want to add in the menu?");
				String s = sc.nextLine();
				System.out.println("What is the description of the item?");
				String b = sc.nextLine();
				System.out.println("What is the price?");
				double c = sc.nextDouble();
				System.out.println("What is the type of the item?  1: Main, 2: Sides, 3: Beverage, 4: Dessert. Enter either 1,2,3 or 4.");
				int d = sc.nextInt(); // Assume userInput = 0
				KindofFood f= KindofFood.getTypeByOrdinal(d);
		
				Item i = new Item(a, s, b, c, f);
				m.addMenu(i);}
		
			else if (e == 2)
				{System.out.println("Enter the index of the item you want to remove.");
				int x = sc.nextInt();
				m.removeMenu(x);
		
				}
		
			else if (e == 3)
			{
				m.viewMenu();}
			
			else if (e == 4)
			{System.out.println("Which kind of items do you want to view? 1: Main, 2: Sides, 3: Beverage, 4: Dessert. Enter either 1,2,3 or 4.");
			int y = sc.nextInt();
			KindofFood kind= KindofFood.getTypeByOrdinal(y);
			m.filterMenuByType(kind);
		
				
			}
			
			else if (e == 5)
			{System.out.println("What is the price range of items you want to view? Enter the lower limit of price range.");
			double a = sc.nextDouble();
			System.out.println("Enter the upper limit of price range.");
			double b = sc.nextDouble();
			m.filterMenuByPrice(a, b);
				
			}
			
			else if (e == 6)
			{
				m.sortMenuByAlphabet();
			}
			
			else if (e == 7)
			{System.out.println("Which item do you want to get? Enter the item index number.");
			int index = sc.nextInt();
			if (m.getItem(index) != -1)
				{m.getItemByIndex(m.getItem(index));}
			else
				{System.out.println("There is no such item index in our menu.");}
				
			}
			
			else if (e == 8)
			{
			System.out.println("How many items are there in this new package?");
			int number = sc.nextInt();
			System.out.println("Create an index for this new package.");
			int a = sc.nextInt();
			sc.nextLine();
			System.out.println("What is the name of this new package?");
			String name = sc.nextLine();
			Package p = new Package(name, a);
			
			for (int i=0; i< number; i++)
				{System.out.println("Enter the index of the item inside this package.");
				int ind = sc.nextInt();
				sc.nextLine();
				System.out.println("What is the name of the item you want to add in this package?");
				String s = sc.nextLine();
				System.out.println("What is the description of the item?");
				String b = sc.nextLine();
				System.out.println("What is the price?");
				double c = sc.nextDouble();
				System.out.println("What is the type of the item?  1: Main, 2: Sides, 3: Beverage, 4: Dessert. Enter either 1,2,3 or 4.");
				int d = sc.nextInt(); // Assume userInput = 0
				KindofFood f= KindofFood.getTypeByOrdinal(d);
		
				Item j = new Item(ind, s, b, c, f);
				p.addPackageItem(j);
				}
			p.setPrice();
			
			pack.addPackageMenu(p);
				
			}
			
			else if (e == 9)
			{Package p = null;
			System.out.println("In which package do you want to add an item in? Enter the index of the package.");
			int addedNo = sc.nextInt();
			int result = pack.filterPackageMenuInt(addedNo);
			if (result != -1)
			{p = pack.filterPackageMenuByIndex(result);
			System.out.println("Enter the index of the item you want to add inside this package.");
			int index = sc.nextInt();
			sc.nextLine();
			System.out.println("What is the name of the item you want to add in this package?");
			String s = sc.nextLine();
			System.out.println("What is the description of the item?");
			String b = sc.nextLine();
			System.out.println("What is the price?");
			double c = sc.nextDouble();
			System.out.println("What is the type of the item? 1: Main, 2: Sides, 3: Beverage, 4: Dessert. Enter either 1,2,3 or 4.");
			int d = sc.nextInt(); // Assume userInput = 0
			KindofFood f= KindofFood.getTypeByOrdinal(d);
	
			Item j = new Item(index, s, b, c, f);
			p.addPackageItem(j);}
				
			else
			{System.out.println("There is no package with that index number.");
			}
			
			}
			
			else if (e == 10)
			{Package p = null;
			System.out.println("From which package do you want to remove an item from? Enter the index of the package.");
			int removedNo = sc.nextInt();
			int result = pack.filterPackageMenuInt(removedNo);
			if (result != -1)
			{p = pack.filterPackageMenuByIndex(result);
			System.out.println("Enter the index of the item you want to remove from this package.");
			int index = sc.nextInt();
			p.removePackageItem(index);}
			
			else
			{System.out.println("There is no package with that index number.");
			}
			}
			
			else if (e == 11)
			{System.out.println("Which package do you want to completely remove? Enter the index of the package.");
			int removed = sc.nextInt();
			pack.removePackageMenu(removed);
			}
			
			else if (e == 12)
			{pack.viewPackageMenu();
			}
			
			else if (e== 13)
			{System.out.println("What is the price range of promotional packages you want to view? Enter the lower limit of price range.");
			double a = sc.nextDouble();
			System.out.println("Enter the upper limit of price range.");
			double b = sc.nextDouble();
			pack.filterPackageMenuByPrice(a, b);
			
			}
			
			else if (e == 14)
			{pack.sortPackageMenuByAlphabet();
			}
			
			else if (e == 15)
			{System.out.println("Which package do you want to get? Enter the package index number.");
			int index = sc.nextInt();
			if (pack.getPackage(index) != -1)
				{pack.getPackageByIndex(pack.getPackage(index));}
			else
				{System.out.println("There is no such package index in our menu.");}
				
			}
			
			
			else if (e==16)
			{m.viewMenu();
			pack.viewPackageMenu();
			}
			
		
			System.out.println("What is your option?");
			e = sc.nextInt();
			}
	
	
		
		if (e == 17)
		{System.out.println("Quitting...");}
	}

}
