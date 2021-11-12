package restaurant;
import java.util.Scanner;

import restaurant.Item.KindofFood;

//import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class App {
 public static void main(String[] args)
	{
		Restaurant r = null;
		Scanner sc = new Scanner(System.in);
		r = new Restaurant();
		int choice;
		
		boolean membership = false;
		Staff staff1 = new Staff("Sourav", 'M', 1060, "Manager");
		r.staffmg.addStaff(1060, staff1);
		// if (have serial file)
		// System.out.println("Do you want to 1. restore previous Resetaurant object or 2. create a new one?");
		// int choice;
		// while (true) {
		// 	try {
		// 		choice = sc.nextInt();
		// 		sc.nextLine();
		// 		if (choice != 1 && choice != 2)
		// 			throw new Exception("Invalid choice, please put 1 and 2 only");
		// 		break;
		// 	} catch (Exception e) {
		// 		System.out.println(e.getMessage());
		// 	}
		// }
		// if (choice == 1)
		// {
		// 	try {
		// 		FileInputStream fs = new FileInputStream("restaurant.ser");
		// 		ObjectInputStream is = new ObjectInputStream(fs);
		// 		r = (Restaurant) is.readObject(); // need cast because we'll get back type Object
		// 		is.close();
		// 	} catch (Exception ex) {
		// 		ex.printStackTrace();
		// 	}
		// }
		// else r = new Restaurant();
		// do {
		// 	while (true) {
		// 		System.out.println("Setting up restaurant... Enter your chioce:");
		// 		System.out.println("(1)Add table");
		// 		System.out.println("(2)Remove table");
		// 		System.out.println("(3)Add staff");
		// 		System.out.println("(4)Remove staff");
		// 		System.out.println("(5)Add member");
		// 		System.out.println("(6)Finish and run restaurant");
		// 		try {
		// 			choice = sc.nextInt();
		// 			sc.nextLine();
		// 			break;
		// 		} catch (Exception e) {
		// 			System.out.println(e.getMessage());
		// 		}
		// 	}
		// 	switch (choice) {
		// 		case 1:
		// 			int id = r.tableUI.scanID();
		// 			int cap = r.tableUI.scanCapacity();
		// 			Table t = new Table(id, cap);
		// 			r.addTable(id, t);
     //             break;
		// 		case 2:
		// 			id = r.tableUI.scanID();
		// 			r.removeTable(id);
		// 			break;
		// 		case 3:
		// 			// code
		// 			break;
		// 		case 4:
		// 			// code
		// 			break;
		// 		case 5:
		// 			// code
		// 			break;
		// 		case 6:
		// 			System.out.println("Restaurant is ready to run!");
		// 			break;
		// 		default:
		// 			System.out.println("Invalid choice!");
		// 			break;
		// 	}
		// } while (choice < 6);	
		do {
			// while (true) {
			// 	System.out.println("(1)Dine in (without reservation)"); 
			// 	System.out.println("(2)Dine in (with reservation)"); 
			// 	System.out.println("(3)Make a reservation"); 
			// 	System.out.println("(4)Remove a reservation"); 
			// 	System.out.println("(5)Find a reservation"); 
			// 	System.out.println("(6)Show all reservations"); 
			// 	System.out.println("(7)Close"); 
			// 	System.out.print("Enter the number of your choice: "); 
			// 	try {
			// 		choice = sc.nextInt();
			// 		sc.nextLine();
			// 		break;
			// 	} catch (Exception e) {
			// 		System.out.println(e.getMessage());
			// 	}
			// }
			System.out.println("(1)Dine in (without reservation)"); 
			System.out.println("(2)Dine in (with reservation)"); 
			System.out.println("(3)Make a reservation"); 
			System.out.println("(4)Remove a reservation"); 
			System.out.println("(5)Find a reservation"); 
			System.out.println("(6)Show all reservations"); 
			System.out.println("(7)List all table availability"); 
			System.out.println("(8)Create menu item");
			System.out.println("(9)Update menu item");
			System.out.println("(10)Remove menu item");
			System.out.println("(11)Create promotion");
			System.out.println("(12)Update promotion");
			System.out.println("(13)Remove promotion");
			System.out.println("(14)View menu & promotion");
			System.out.println("(15)Create order");
			System.out.println("(16)View order");
			System.out.println("(17)Add to order");
			System.out.println("(18)Remove from order");
			System.out.println("(19)Checkout and print invoice");
			System.out.println("(20)Close");
			System.out.print("Enter the number of your choice: "); 
			
			choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
				case 1:
					int pax = r.tableUI.scanPax();
					r.DineIn(pax);
					break;
				case 2: 
					String name = r.resUI.scanName();
					String contact = r.resUI.scanContact();
                 int re = r.reservedDineIn(name, contact);
                 switch(re){
                 case 0: // res not found
                     pax = r.tableUI.scanPax();
                     r.DineIn(pax);
                     break;
                 case 1: // edge case: early arrival
                     int c = r.resUI.scanEarlyArrivalChoice();
                     if (c == 1)
                         if (r.earlyReservedDineIn(name, contact)) break;
                     pax = r.tableUI.scanPax();
                     r.DineIn(pax);
                     break;
                 case 2: // successfully dine in
                     break;
                 }
					break;
				case 3: 
                 name = r.resUI.scanName();
                 contact = r.resUI.scanContact();
                 LocalDateTime dateTime = r.resUI.scanTime();
                 pax = r.resUI.scanPax();
					r.makeReservation(dateTime, name, contact, pax);
					break;
				case 4: 
                 name = r.resUI.scanName();
                 contact = r.resUI.scanContact();
                 String key = name + contact + LocalDate.now();
					r.removeReservation(key);
					break;
				case 5: 
                 name = r.resUI.scanName();
                 contact = r.resUI.scanContact();
                 LocalDate date = r.resUI.scanTime().toLocalDate();
                 key = name + contact + date;
                 r.showRes(key);
					break;
				case 6: 
					r.showAllRes();
					break;
	            case 7:
	                 r.listAvail();
	                 break;
	            case 8:
	            	int index = r.itemUI.getIndexItemUI();
	    			if (r.m.checkDuplicate(index) == false)
	    			{name = r.itemUI.getNameItemUI();
	    			String description = r.itemUI.getDescItemUI();
	    			double price = r.itemUI.getPriceItemUI();
	    			KindofFood type = r.itemUI.getTypeItemUI();
	    			
	    			Item i = new Item(index, name, description, price, type);
	    			
	    			r.m.addMenu(index, i);}
	    			else	
	    			{System.out.println("Duplicate index in this menu.");}
	    			
	    			break;
	    			
	            case 9:
	            	index = r.itemUI.getIndexItemUI();
	    			if (r.m.getItem(index) != null)
	    			
	    				{System.out.println("What do you want to update? (1: Index, 2: Name, 3: Price, 4: Description, 5: Type.");
	    				int opt = sc.nextInt();
	    				switch(opt)
	    				{case 1:
	    					int newIndex = r.itemUI.getNewIndexItemUI();
	    					if (r.m.checkDuplicate(newIndex) == false)
	    					{r.m.updateIndex(index, newIndex);}
	    					
	    					else
	    					{System.out.println("Duplicate index in this menu.");}
	    					break;
	    				
	    				case 2:
	    					name = r.itemUI.getNameItemUI();
	    					r.m.updateName(index, name);
	    					break;
	    			
	    				case 3:
	    					System.out.println("The original price of this item is " + r.m.getItem(index).getPrice());
	    					double price = r.itemUI.getPriceItemUI();
	    					boolean result = r.m.updatePrice(index, price);
	    				
	    					if (result == true)
	    						{Item a = r.m.getItem(index);
	    						Package p = r.pack.checkItemInPackage(a);
	    				
	    					if (r.pack.updatePriceItemInPackage(a) == 2)
	    						{System.out.println("This item is inside package" + p.getName() + "please update the price of the package since the total price of each item in the package is higher.");
	    						System.out.println("The original price of this package is " + p.getPrice());
	    						price = r.packageUI.getPricePackageUI(p.getOriPrice());
	    						p.setPrice(price);
	    				
	    						}
	    				
	    					else if (r.pack.updatePriceItemInPackage(a) == 1)
	    						{System.out.println("This item is inside package " + p.getName() + ", do you want to update price of this package as well? (1: Yes/2: No)");
	    						System.out.println("The original price of this package is " + p.getPrice());
	    						opt = r.packageUI.updateItemInPackage1();
	    						if (opt == 1)
	    						{price = r.packageUI.getPricePackageUI(p.getOriPrice());
	    						p.setPrice(price);}
	    					
	    						}
	    					
	    						}
	    				
	    					break;
	    				
	    				case 4:
	    					String description = r.itemUI.getDescItemUI();
	    					r.m.updateDesc(index, description);
	    				
	    					break;
	    				
	    				case 5:
	    					KindofFood type = r.itemUI.getTypeItemUI();
	    					r.m.updateType(index, type);
	    					
	    					break;}}
	    			else {
	    			System.out.println("Item with this index does not exist.");}
	    			
	    			break;
	            case 10:
	            	index = r.itemUI.getIndexItemUI();
	    			if (r.m.getItem(index) != null)
	    			{
	    			r.m.removeMenu(index);}
	    			
	    			else
	    			{System.out.println("Item with this index does not exist.");}
	    			break;
	    			
	            case 11:
	            	index =r.packageUI.getIndexPackageUI();
	    			if (r.pack.checkDuplicatePackage(index) == false)
	    			{
	    			name = r.packageUI.getNamePackageUI();
	    			Package e = new Package(name, index);
	    			System.out.println("Press -1 to stop adding items.");
	    			int itemIndex = r.itemUI.getIndexItemUI();
	    			while (e.isNull() == true || itemIndex != -1 )
	    				{
	    				if (r.m.getItem(itemIndex) != null)
	    					{int quantity = r.packageUI.getQuantity();
	    					Item item = r.m.getItem(itemIndex);
	    					e.addPackageItem(item, quantity);}
	    				
	    				
	    				else
	    					{System.out.println("Nothing is added into this package because there is no item with this index.");}
	    				System.out.println("Press -1 to stop adding items.");
	    				itemIndex = r.itemUI.getIndexItemUI();
	    				}
	    			
	    			double price = r.packageUI.getPricePackageUI(e.getOriPrice());
	    			e.setPrice(price);
	    			
	    			r.pack.addPackageMenu(index,e);}
	    			
	    			else
	    			{System.out.println("Duplicate index of packages in this menu");}
	    			
	    			break;
	            case 12:
	    			int packageIndex = r.packageUI.getIndexPackageUI();
	    			if (r.pack.getPackage(packageIndex) != null)
	    				{System.out.println("What do you want to update? (1: Index, 2: Name, 3: Price, 4: Add item into package, 5: Remove item from package");
	    				int opt = sc.nextInt();
	    				sc.nextLine();
	    				switch (opt)
	    					{case 1:
	    						int newIndex= r.packageUI.getNewIndexPackageUI();
	    						r.pack.updatePackageIndex(packageIndex, newIndex);
	    						break;
	    				
	    					case 2:
	    						name = r.packageUI.getNamePackageUI();
	    						r.pack.updatePackageName(packageIndex, name);
	    				
	    				
	    						break;
	    				
	    					case 3:
	    						Package b = r.pack.getPackage(packageIndex);
	    						System.out.println("The original price of this package is " + b.getPrice());
	    						double price = r.packageUI.getPricePackageUI(b.getOriPrice());
	    						r.pack.updatePackagePrice(packageIndex, price);
	    						break;
	    			
	    					case 4:
	    						int itemIndex = r.itemUI.getIndexItemUI();
	    						int qty = r.packageUI.getQuantity();
	    						Item x = r.m.getItem(itemIndex);
	    						r.pack.addItemsInPackage(packageIndex, x, qty);
	    						Package o = r.pack.getPackage(packageIndex);
	    						double finalPrice = r.packageUI.getPricePackageUI(o.getOriPrice());
	    						o.setPrice(finalPrice);
	    					
	    						break;
	    				
	    					case 5:
	    						itemIndex = r.itemUI.getIndexItemUI();
	    						qty = r.packageUI.getQuantity();
	    						x = r.m.getItem(itemIndex);
	    						r.pack.removeItemsInPackage(packageIndex, x, qty);
	    						o = r.pack.getPackage(packageIndex);
	    						finalPrice = r.packageUI.getPricePackageUI(o.getOriPrice());
	    						o.setPrice(finalPrice);
	    				
	    				
	    				
	    						break;}}
	    			else
	    			{
	    				System.out.println("There are no packages with that index.");}
	    			
	    			
	    			
	    			break;
	            case 13:
	            	int packageInd = r.packageUI.getIndexPackageUI();
	    			if (r.pack.getPackage(packageInd) != null)
	    			{r.pack.removePackageMenu(packageInd);}
	    			
	    			else
	    			{System.out.println("There is no package with that index.");}
	    			
	    			
	    			break;
	            case 14:
	            	r.m.viewMenu();
	    			r.pack.viewPackageMenu();
	            	break;
	            case 15:
	            	try {
	            		int table = r.orderui.scanTableID();
		            	boolean tableavail = r.tableManager.checkTableStatus(table);
		            	if (tableavail) {
		            		throw new Exception("This table has not been assigned yet. Please dine in first before creating order.");
		            	}
		    			String staffName = r.staffui.scanStaffName();
		    			int staffid = r.staffui.scanStaffID();
		    			boolean staff = r.staffmg.isStaff(staffid, staffName);
		    			if (staff) {
		    				r.ordermg.createOrder(table, staffName);
		    			}
		    			else {
		    				System.out.println("Unable to create order as staff server particulars are wrong or does not exist.");
		    			}
	            	} catch (Exception e) {
	    				System.out.println(e.getMessage());
	    			}
	            	
	            	break;
	            case 16:
	            	try {
	            		int orderid = r.orderui.scanOrderID();
	            		if (r.ordermg.checkValidOrderID(orderid) == false) {
	            			throw new IndexOutOfBoundsException("Invalid orderID");
	            		}
	            		r.ordermg.viewOrder(orderid);
	            	} catch (Exception e) {
	    				System.out.println(e.getMessage());
	    			}
	            	break;
	            case 17:
	            	try {
	            		int orderid = r.orderui.scanOrderID();
	            		if (r.ordermg.checkValidOrderID(orderid) == false) {
	            			throw new IndexOutOfBoundsException("Invalid orderID");
	            		}
		    			boolean alacarte = r.orderui.scanisAlaCarte();
		    			int foodid = r.orderui.scanfoodID();
		    			int quantity = r.orderui.scanQuantity();
		    			if (alacarte) {
		    				Item fooditem = r.m.getItem(foodid);
		    				if (fooditem == null) {
		    					throw new Exception("Item does not exist in the menu");
		    				}
		    				r.ordermg.addOrder(orderid, fooditem, quantity);
		    			}
		    			else {
		    				Package foodpack = r.pack.getPackage(foodid);
		    				if (foodpack == null) {
		    					throw new Exception("Package does not exist in the menu");
		    				}
		    				r.ordermg.addOrder(orderid, foodpack, quantity);
		    			}
	            	} catch (Exception e) {
	    				System.out.println(e.getMessage());
	    			}
	            	break;
	            case 18:
	            	try {
	            		int orderid = r.orderui.scanOrderID();
	            		if (r.ordermg.checkValidOrderID(orderid) == false) {
	            			throw new IndexOutOfBoundsException("Invalid orderID");
	            		}
		    			boolean alacarte = r.orderui.scanisAlaCarte();
		    			int foodid = r.orderui.scanfoodID();
		    			int quantity = r.orderui.scanQuantity();
		    			if (alacarte) {
		    				Item fooditem = r.m.getItem(foodid);
		    				if (fooditem == null) {
		    					throw new Exception("Item does not exist in the menu");
		    				}
		    				r.ordermg.removeOrder(orderid, fooditem, quantity);
		    			}
		    			else {
		    				Package foodpack = r.pack.getPackage(foodid);
		    				if (foodpack == null) {
		    					throw new Exception("Package does not exist in the menu");
		    				}
		    				r.ordermg.removeOrder(orderid, foodpack, quantity);
		    			}
	            	} catch (Exception e) {
	    				System.out.println(e.getMessage());
	    			}
	    			break;
	            case 19:
	            	try {
	            		System.out.println("Checkout in progress ...");
	            		int orderid = r.orderui.scanOrderID();
	    				if (r.ordermg.checkValidOrderID(orderid) == false) {
	            			throw new IndexOutOfBoundsException("Invalid orderID");
	            		}
	    				Order completeOrder = r.ordermg.getOrder(orderid);
	    				if (completeOrder == null) {
	    					throw new Exception("Error: order does not exist yet");
	    				}
	    				if (completeOrder.paid() == true) {
		            		throw new Exception("The order has already been paid");
		            	}
		            	int join = r.memui.joinMembership();
		    			if (join == 1 || join == 2) {
		    				name = r.memui.scanMemberName();
		    				contact = r.memui.scanMemberHP();
		    			}
		    			else {
		    				name = null;
		    				contact = null;
		    			}
		    			membership = r.memmg.paymentMembership(name, contact, join);
		    			int releaseTable = r.ordermg.printInvoice(membership, orderid);
						r.tableManager.vacateTable(releaseTable);
						System.out.println("Thank you and see you again!");
	            	} catch (Exception e) {
	    				System.out.println(e.getMessage());
	    			}
	    			break;
			case 20: 
				System.out.println("Program terminating ....");
//				r.close();
				break;
//				default:
//					System.out.println("Invalid choice!");
//					break;
			}
			System.out.println(""); 
		} while (choice < 20);
     sc.close();
	}
}
