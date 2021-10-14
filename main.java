package projectoop;

import java.util.Scanner;

import projectoop.Item.KindofFood;
import projectoop.Item.Menu;

public class main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	
		
		System.out.println("1. Add Menu"
				+ "\n"
				+ "2. Remove Menu"
				+ "\n" + "3. View Normal Menu" 
				+ "\n" +
				"4. View Menu based Type" 
				+ "\n" +
				"5. View Menu based on Price"
				+ "\n" +
				"6. View Menu based on Alphabet"
				+ "\n" +
				"7. Add Promotional Package"
				+ "\n" +
				"8. Remove Promotional Package"
				+ "\n"
				+ "9. View Promotional Menu"
				+ "\n" +
				"10. View Promotional Menu based on Price"
				+ "\n" +
				"11. View all menu"
				+ "\n" +
				"12. Quit"
				);
		
		System.out.println("What is your option?");
		int e = sc.nextInt();
		
		while (e != 12)
			{if (e == 1)
				{
				System.out.println("What is the index?");
				int a = sc.nextInt();
				System.out.println("What item do you want to add in the menu?");
				String s = sc.next();
				sc.nextLine();
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
		
			System.out.println("What is your option?");
			e = sc.nextInt();
			}
	
		
		if (e == 12)
		{System.out.println("Quitting...");}
	}

}
