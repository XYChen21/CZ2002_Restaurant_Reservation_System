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
				"3. View normal menu" 
				+ "\n" +
				"4. View menu based type of item" 
				+ "\n" +
				"5. View menu based on price"
				+ "\n" +
				"6. View menu based on name"
				+ "\n" +
				"7. Create a new promotional package"
				+ "\n" +
				"8. Add item into an existing promotional package"
				+ "\n" +
				"9. Remove item from an existing promotional package"
				+ "\n" + 
				"10. Remove an existing promotional package entirely"
				+ "\n" + 
				"11. View promotional package menu"
				+ "\n" +
				"12. View promotional package menu based on price"
				+ "\n" +
				"13. View promotional package menu based on name"
				+ "\n" +
				"14. View all menu"
				+ "\n" +
				"15. Quit"
				+ "\n"
				);
		
		System.out.println("What is your option?" + "\n");
		int e = sc.nextInt();
		
		while (e != 15)
			{if (e == 1)
				{
				System.out.println("What is the index?");
				int a = sc.nextInt();
				sc.nextLine();
				System.out.println("What item do you want to add in the menu?");
				String s = sc.nextLine();
				System.out.println("What is the description?");
				String b = sc.nextLine();
				System.out.println("What is the price?");
				double c = sc.nextDouble();
				System.out.println("What is the type of food?  1: Main, 2: Sides, 3: Beverage, 4: Dessert");
				int d = sc.nextInt(); // Assume userInput = 0
				KindofFood f= KindofFood.getTypeByOrdinal(d);
		
				Item i = new Item(a, s, b, c, f);
				Menu.addMenu(i);}
		
			else if (e == 2)
				{System.out.println("What item do you want to remove? Enter the index.");
				int x = sc.nextInt();
				Menu.removeMenu(x);
		
				}
		
			else if (e == 3)
			{
				Menu.viewMenu();}
			
			else if (e == 4)
			{System.out.println("Which kind of item do you want to see? 1: Main, 2: Sides, 3: Beverage, 4: Dessert");
			int y = sc.nextInt();
			KindofFood kind= KindofFood.getTypeByOrdinal(y);
			Menu.filterMenuByType(kind);
		
				
			}
			
			else if (e == 5)
			{System.out.println("What is your desired price range? Enter lower limit of price range.");
			double a = sc.nextDouble();
			System.out.println("Enter upper limit of price range.");
			double b = sc.nextDouble();
			Menu.filterMenuByPrice(a, b);
				
			}
			
			else if (e == 6)
			{
				Menu.sortMenuByAlphabet();
			}
			
			else if (e == 7)
			{
			System.out.println("How many items are there in this package?");
			int number = sc.nextInt();
			System.out.println("What is the index of the package?");
			int a = sc.nextInt();
			sc.nextLine();
			System.out.println("What is the name of your package?");
			String name = sc.nextLine();
			Package p = new Package(name, a);
			
			for (int i=0; i< number; i++)
				{System.out.println("What is the index of the package item?");
				int ind = sc.nextInt();
				sc.nextLine();
				System.out.println("What item do you want to add in the menu?");
				String s = sc.nextLine();
				System.out.println("What is the description?");
				String b = sc.nextLine();
				System.out.println("What is the price?");
				double c = sc.nextDouble();
				System.out.println("What is the type of food?  1: Main, 2: Sides, 3: Beverage, 4: Dessert");
				int d = sc.nextInt(); // Assume userInput = 0
				KindofFood f= KindofFood.getTypeByOrdinal(d);
		
				Item j = new Item(ind, s, b, c, f);
				p.addPackageItem(j);
				}
			p.setPackagePrice();
			
			PackageMenu.addPackageMenu(p);
				
			}
			
			else if (e == 8)
			{System.out.println("In which package do you want to add item in? Enter a package index.");
			int addedNo = sc.nextInt();
			Package p = PackageMenu.filterPackageMenuByIndex(addedNo);
			System.out.println("What item do you want to add into this package? Enter an item index.");
			int index = sc.nextInt();
			sc.nextLine();
			System.out.println("What item do you want to add in the menu?");
			String s = sc.nextLine();
			System.out.println("What is the description?");
			String b = sc.nextLine();
			System.out.println("What is the price?");
			double c = sc.nextDouble();
			System.out.println("What is the type of food?  1: Main, 2: Sides, 3: Beverage, 4: Dessert");
			int d = sc.nextInt(); // Assume userInput = 0
			KindofFood f= KindofFood.getTypeByOrdinal(d);
	
			Item j = new Item(index, s, b, c, f);
			p.addPackageItem(j);
				
			}
			
			else if (e == 9)
			{System.out.println("From which package do you want to remove an item from? Enter a package index.");
			int removedNo = sc.nextInt();
			Package p = PackageMenu.filterPackageMenuByIndex(removedNo);
			System.out.println("What item do you want to remove from this package? Enter the item index.");
			int index = sc.nextInt();
			p.removePackageItem(index);
			}
			
			else if (e == 10)
			{System.out.println("From which package do you want to remove? Enter the index.");
			int removed = sc.nextInt();
			PackageMenu.removePackageMenu(removed);
			}
			
			else if (e == 11)
			{PackageMenu.viewPackageMenu();
			}
			
			else if (e== 12)
			{System.out.println("What is your desired price range? Enter lower limit of price range.");
			double a = sc.nextDouble();
			System.out.println("Enter upper limit of price range.");
			double b = sc.nextDouble();
			PackageMenu.filterPackageMenuByPrice(a, b);
			
			}
			
			else if (e == 13)
			{PackageMenu.sortPackageMenuByAlphabet();
			}
			
			else if (e==14)
			{Menu.viewMenu();
			PackageMenu.viewPackageMenu();
			}
			
		
			System.out.println("What is your option?");
			e = sc.nextInt();
			}
	
	
		
		if (e == 15)
		{System.out.println("Quitting...");}
	}

}
