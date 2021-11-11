package restaurant;

import java.util.Scanner;

import restaurant.Item.KindofFood;

public class Main {

	public static void main(String[] args) {
		
		ItemManager m = new ItemManager();
		PackageManager pack = new PackageManager();
		ItemUI itemUI = new ItemUI();
		PackageUI packageUI = new PackageUI();
		
		Scanner sc = new Scanner(System.in); 
		System.out.println("(1)Create menu item");
		System.out.println("(2)Update menu item");
		System.out.println("(3)Remove menu item");
		System.out.println("(4)Create promotion");
		System.out.println("(5)Update promotion");
		System.out.println("(6)Remove promotion");
		System.out.println("(7)View menu & promotion");
		System.out.println("(8)Close");
		System.out.println("What is your option?");
		int choice = sc.nextInt();
		sc.nextLine(); // consume newline char
		while (choice <= 7) {
		switch (choice) {
		case 1:
			int index = itemUI.getIndexItemUI();
			if (m.checkDuplicate(index) == false)
			{String name = itemUI.getNameItemUI();
			String description = itemUI.getDescItemUI();
			double price = itemUI.getPriceItemUI();
			KindofFood type = itemUI.getTypeItemUI();
			
			Item i = new Item(index, name, description, price, type);
			
			m.addMenu(index, i);}
			else	
			{System.out.println("Duplicate index in this menu.");}
			
			break;
		case 2:
			index = itemUI.getIndexItemUI();
			if (m.getItem(index) != null)
			
				{System.out.println("What do you want to update? (1: Index, 2: Name, 3: Price, 4: Description, 5: Type.");
				int opt = sc.nextInt();
				switch(opt)
				{case 1:
					int newIndex = itemUI.getNewIndexItemUI();
					if (m.checkDuplicate(newIndex) == false)
					{m.updateIndex(index, newIndex);}
					
					else
					{System.out.println("Duplicate index in this menu.");}
					break;
				
				case 2:
					String name = itemUI.getNameItemUI();
					m.updateName(index, name);
					break;
			
				case 3:
					double price = itemUI.getPriceItemUI();
					boolean result = m.updatePrice(index, price);
				
					if (result == true)
						{Item a = m.getItem(index);
						Package p = pack.checkItemInPackage(a);
				
					if (pack.updatePriceItemInPackage(a) == 2)
						{System.out.println("This item is inside package" + p.getName() + "please update the price of the package since the total price of each item in the package is higher.");
						price = packageUI.getPricePackageUI(p.getOriPrice());
						p.setPrice(price);
				
						}
				
					else if (pack.updatePriceItemInPackage(a) == 1)
						{System.out.println("This item is inside package " + p.getName() + ", do you want to update price of this package as well? (1: Yes/2: No)");
						opt = packageUI.updateItemInPackage1();
						if (opt == 1)
						{price = packageUI.getPricePackageUI(p.getOriPrice());
						p.setPrice(price);}
					
						}
					
						}
				
					break;
				
				case 4:
					String description = itemUI.getDescItemUI();
					m.updateDesc(index, description);
				
					break;
				
				case 5:
					KindofFood type = itemUI.getTypeItemUI();
					m.updateType(index, type);
					
					break;}}
			else {
			System.out.println("Item with this index does not exist.");}
			
			break;
		case 3:
			index = itemUI.getIndexItemUI();
			if (m.getItem(index) != null)
			{
			m.removeMenu(index);}
			
			else
			{System.out.println("Item with this index does not exist.");}
			break;
		case 4: 
			index =packageUI.getIndexPackageUI();
			if (pack.checkDuplicatePackage(index) == false)
			{
			String name = packageUI.getNamePackageUI();
			Package e = new Package(name, index);
			System.out.println("Press -1 to stop adding items.");
			int itemIndex = itemUI.getIndexItemUI();
			while (e.isNull() == true || itemIndex != -1 )
				{
				if (m.getItem(itemIndex) != null)
					{int quantity = packageUI.getQuantity();
					Item item = m.getItem(itemIndex);
					e.addPackageItem(item, quantity);}
				
				
				else
					{System.out.println("Nothing is added into this package because there is no item with this index.");}
				

				System.out.println("Press -1 to stop adding items.");
				itemIndex = itemUI.getIndexItemUI();
				}
			
			double price = packageUI.getPricePackageUI(e.getOriPrice());
			e.setPrice(price);
			
			pack.addPackageMenu(index,e);}
			
			else
			{System.out.println("Duplicate index of packages in this menu");}
			
			break;
		case 5:
			int packageIndex = packageUI.getIndexPackageUI();
			if (pack.getPackage(packageIndex) != null)
				{System.out.println("What do you want to update? (1: Index, 2: Name, 3: Price, 4: Add item into package, 5: Remove item from package");
				int opt = sc.nextInt();
				switch (opt)
					{case 1:
						int newIndex= packageUI.getNewIndexPackageUI();
						pack.updatePackageIndex(packageIndex, newIndex);
						break;
				
					case 2:
						String name = packageUI.getNamePackageUI();
						pack.updatePackageName(packageIndex, name);
				
				
						break;
				
					case 3:
						Package b = pack.getPackage(packageIndex);
						double price = packageUI.getPricePackageUI(b.getOriPrice());
						pack.updatePackagePrice(packageIndex, price);
						break;
			
					case 4:
						int itemIndex = itemUI.getIndexItemUI();
						int qty = packageUI.getQuantity();
						Item x = m.getItem(itemIndex);
						pack.addItemsInPackage(packageIndex, x, qty);
						Package r = pack.getPackage(packageIndex);
						double finalPrice = packageUI.getPricePackageUI(r.getOriPrice());
						r.setPrice(finalPrice);
					
						break;
				
					case 5:
						itemIndex = itemUI.getIndexItemUI();
						qty = packageUI.getQuantity();
						x = m.getItem(itemIndex);
						pack.removeItemsInPackage(packageIndex, x, qty);
						r = pack.getPackage(packageIndex);
						finalPrice = packageUI.getPricePackageUI(r.getOriPrice());
						r.setPrice(finalPrice);
				
				
				
						break;}}
			else
			{
				System.out.println("There are no packages with that index.");}
			
			
			
			break;
		case 6:
			int packageInd = packageUI.getIndexPackageUI();
			if (pack.getPackage(packageInd) != null)
			{pack.removePackageMenu(packageInd);}
			
			else
			{System.out.println("There is no package with that index.");}
			
			
			break;
		case 7:
			m.viewMenu();
			pack.viewPackageMenu();
			break;}
		
		System.out.println("What is your option?");
		choice = sc.nextInt();}
		

	}

}
