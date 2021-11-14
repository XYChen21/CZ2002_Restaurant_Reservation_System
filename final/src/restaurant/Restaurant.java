package restaurant;

import java.util.*;
import restaurant.Item.KindofFood;
import java.io.*;
import java.time.*;

/**
 * Restaurant contains all managers i.e. Controller classes and will initiate functions that are called by App class
 * @author Jacintha Wee, Chen Xingyu, Valencia Lie, Li Siyi
 * @version 1.5
 * @since 2021-11-13
 *
 */
public class Restaurant implements Serializable
{	
	/**
	 * The object of the Reservation Controller class 
	 */
	private ReservationManager resManager;
	
	/**
	 * The object of the Table Controller class 
	 */
	private TableManager tableManager;
	
	/**
	 * The object of the Item Controller class 
	 */
	private ItemManager itemManager;
	
	/**
	 * The object of the Package Controller class 
	 */
	private PackageManager packageManager;
	
	/**
	 * The object of the Staff Controller class 
	 */
	private StaffManager staffManager;
	
	/**
	 * The object of the Member Controller class
	 */
	private MemberManager memberManager;
	
	/**
	 * The object of the Order Controller class
	 */
	private OrderManager orderManager;

	/**
	 * The Controller class of all Controller classes of this restaurant 
	 */
	public Restaurant()
	{	itemManager = new ItemManager();
		packageManager = new PackageManager();
		resManager = new ReservationManager();
		tableManager = new TableManager();
		staffManager = new StaffManager();
		memberManager = new MemberManager();
		orderManager = new OrderManager();

		int id = 1, cap = 2;
		Table t;
		while (id <= 5)
		{
			t = new Table(id, cap);
			tableManager.add(id, t);
			resManager.initTableReservation(id);
			id++; cap+=2;
		}
		Staff staff1 = new Staff("Sourav", 'M', 1060, "General Mamager");
		staffManager.addStaff(1060, staff1);
		Staff staff2 = new Staff("Gary", 'M', 2100, "Manager");
		staffManager.addStaff(2100, staff2);
		Staff staff3 = new Staff("Guohua", 'M', 2500, "Waitor");
		staffManager.addStaff(2500, staff3);
		Staff staff4 = new Staff("Ying", 'F', 2101, "Waitress");
		staffManager.addStaff(2101, staff4);
		Staff staff5 = new Staff("Ronald", 'M', 3100, "Bartender");
		staffManager.addStaff(3100, staff5);
		Staff staff6 = new Staff("Val", 'F', 1234, "Head Chef");
		staffManager.addStaff(1234, staff6);
		
		memberManager.addMember("Jac", "+6587005902");
		memberManager.addMember("XY", "+659130633");
		memberManager.addMember("lsy", "+6586163328");
	}
	/**
	 * reset all auto removal schedules for reservations in the system
	 */
	public void restoreAutoRemove()
	{
		resManager.restoreAutoRemove();
	}
	
	/**
	 * add a new Table
	 * @param id ID of the new Table
	 * @param t Table object to be added
	 */
	public void addTable(int id, Table t)
	{
		if(tableManager.haveID(id))
			System.out.println("Duplicate ID, please use another one.");
		else
		{
			tableManager.add(id, t);
			resManager.initTableReservation(id);
		}
	}
	
	/**
	 * remove a Table if the Table is present in the system
	 * @param id ID of the table to be removed
	 */
	public void removeTable(int id)
	{
		if(tableManager.haveID(id))
			tableManager.remove(id);
		else
			System.out.println("ID doesn't exist. Please try again");
	}
	
	/**
	 * ask number of people dining and assign a Table for walk-in customers
	 */
	public void DineIn()
	{
		int pax = TableUI.scanPax();
		ArrayList<Integer> availTables = resManager.checkAvail(LocalDateTime.now());
		Integer tID = tableManager.allocateTable(availTables, LocalDateTime.now(), pax);
		if (tID != null)
			tableManager.assignTable(tID);
		else
			System.out.println("No tables are available at the moment. Please wait.");
	}
	
	/**
	 * assign a Table for customers who made reservation before
	 */
	public void reservedDineIn()
	{
		String name = ReservationUI.scanName();
		String contact = ReservationUI.scanContact();
		String key = name + contact + LocalDate.now();
		Reservation res = resManager.getRes(key);
		if(res == null)
		{
			System.out.println("Reservation not found. Try assigning new table...");
			DineIn();
		}
		else
		{
			int tableID = res.getTableID();
			LocalDateTime reservedTime = res.getTime();
			if (LocalDateTime.now().isBefore(reservedTime))
			{
				System.out.println("A later reservation at " + reservedTime + " was found");
				int c = ReservationUI.scanEarlyArrivalChoice();
				if (c == 1)
				{
					boolean result = tableManager.assignTable(tableID);
					if (result)
					{
						resManager.removeRes(key);
						return;
					}
				}
				DineIn();
			}
			else
			{
				tableManager.assignTable(tableID);
				System.out.println("Reservation found: assign table " + tableID);
				resManager.removeRes(key);
			}
		}
	}
	
	/**
	  * find a reservation by providing reservation name, contact number of reservation date and time
	  */
	 public void showRes()
	 {
	  String name = ReservationUI.scanName();
	  String contact = ReservationUI.scanContact();
	  LocalDate date = ReservationUI.scanDate();
	  String key = name + contact + date;
	  Reservation res = resManager.getRes(key);
	  if (res == null)
	   System.out.println("Reservation not found!");
	  else
	   System.out.println(res.toString());
	 }
	
	/**
	 * show all reservations in the system
	 */
	public void showAllRes()
	{
		resManager.showAllRes();
	}
	
	/**
	 * make a reservation (the customers are not allowed to make another reservation on the same day)
	 */
	public void makeReservation()
	{
		String name = ReservationUI.scanName();
		String contact = ReservationUI.scanContact();
		LocalDateTime dateTime = ReservationUI.scanTime();
		int pax = ReservationUI.scanPax();
		String key = name + contact + dateTime.toLocalDate();
		if (resManager.haveRes(key)) {
			System.out.println("You've already made a reservation on that day, cannot make another reservation.");
			return;
		}
		ArrayList<Integer> availTables = resManager.checkAvail(LocalDateTime.now());
		Integer tID = tableManager.allocateTable(availTables, LocalDateTime.now(), pax);
		if (tID != null)
		{
			Reservation res = new Reservation(dateTime, name, contact, pax, tID);
			resManager.setAutoRemove(res);
			resManager.addRes(tID, key, res);
			System.out.println("Reservation made successfully!");
			Notification.sendSMS(res.toStringCust(), contact);
		}
		else
			System.out.println("Cannot make reservation for given date and time since all tables are reserved!");
	}
	
	/**
	 * remove a reservation from the system manually when the reservations are not expired the customers want to cancel their reservations
	 */
	public void removeReservation()
	{
		String name = ReservationUI.scanName();
		String contact = ReservationUI.scanContact();
//		String date = resUI.scanTime().toLocalDate();
		String key = name + contact + ReservationUI.scanTime().toLocalDate();
//		System.out.println("key: " + key);
		resManager.removeRes(key);
	}
	
	/**
	 * list all tables status (occupied/available) at the moment
	 */
	public void listTableStatus()
	{
		tableManager.listAllStatus();
	}
	
	/**
	 * save all data by writing a serialisation file and close the system
	 */
	public void close()
	{
		try {
			FileOutputStream fs = new FileOutputStream("restaurant.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(this);
			os.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		resManager.close();
	}
	
	/**
	 * A method to create an item with attributes based on user's input and add it into the object of the item controller class (like a menu or collection of item objects)
	 */
	public void addMenuItem()
	{
		int index = ItemUI.getIndexItemUI();
		if (itemManager.checkDuplicate(index) == false)
		{
			String name = ItemUI.getNameItemUI();
			String description = ItemUI.getDescItemUI();
			double price = ItemUI.getPriceItemUI();
			KindofFood type = ItemUI.getTypeItemUI();
			Item i = new Item(index, name, description, price, type);
			itemManager.addMenu(index, i);
		}
		else
		{
			System.out.println("Duplicate index in this menu.");
		}
	}
	/**
	 * A method to update an item's attribute inside the object of the item controller class (like a menu or collection of item objects) through user's input
	 */
	public void updateItem()
	{
		int index = ItemUI.getIndexItemUI();
		if (itemManager.checkDuplicate(index))
		{
			int opt = ItemUI.updateMenuChoice();
			switch(opt) {
			case 1:
				int newIndex = ItemUI.getNewIndexItemUI();
				if (itemManager.checkDuplicate(newIndex) == false) {
					itemManager.updateIndex(index, newIndex);
				}
				else {
					System.out.println("Duplicate index in this menu.");
				}
				break;
			case 2:
				String name = ItemUI.getNameItemUI();
				itemManager.updateName(index, name);
				break;
			case 3:
				String description = ItemUI.getDescItemUI();
				itemManager.updateDesc(index, description);
				break;
			case 4:
				double price = ItemUI.getPriceItemUI();
				itemManager.updatePrice(index, price);
				Item i = itemManager.getItem(index);
				ArrayList<Package> packToUpdate = packageManager.itemInPackage(i);
				updateItemInPackage(packToUpdate);
				break;
			case 5:
				KindofFood type = ItemUI.getTypeItemUI();
				itemManager.updateType(index, type);
				break;
			}
		}
		else
		{
			System.out.println("Item with this index does not exist.");
		}
	}
	
	/**
	 * A method to remove an item from the object of the item controller class (like a menu or collection of item objects) through user's input of the index of item they want to remove
	 */
	public void removeMenuItem()
	{
		int index = ItemUI.getIndexItemUI();
		if (itemManager.checkDuplicate(index))
			itemManager.removeMenu(index);
		else
			System.out.println("Item with this index does not exist.");
	}
	
	/**
	 * A method to create a package based on the user's input of its attributes and add it into the object of the package controller class (like a menu or collection of package objects)
	 */
	public void createPackage()
	{
		if (itemManager.isNull())
		{
			System.out.println("There are no items in the menu, cannot create a package");
			return;
		}
		int index = PackageUI.getIndexPackageUI();
		if (packageManager.checkDuplicatePackage(index) == false)
		{
			String name = PackageUI.getNamePackageUI();
			Package e = new Package(name, index);
			addPackageItem(e);
			double price = PackageUI.getPricePackageUI(e.getOriPrice());
			e.setPrice(price);
			packageManager.addPackageMenu(index, e);
		}
		else
			System.out.println("Duplicate index of packages in this menu");
	}
	
	/**
	 * A method to update a package's attributes inside the object of the package controller class (like a menu or collection of package objects) through user's input
	 */
	public void updatePackage()
	{
		int packageIndex = PackageUI.getIndexPackageUI();
		if (packageManager.checkDuplicatePackage(packageIndex))
		{
			int opt = PackageUI.updatePackageChoice();
			switch(opt) {
			case 1:
				int newIndex = PackageUI.getNewIndexPackageUI();
				packageManager.updatePackageIndex(packageIndex, newIndex);
				break;
			case 2:
				String name = PackageUI.getNamePackageUI();
				packageManager.updatePackageName(packageIndex, name);
			case 3:
				Package p = packageManager.getPackage(packageIndex);
				System.out.println("The initial discounted price of this package is " + p.getPrice());
				double price = PackageUI.getPricePackageUI(p.getOriPrice());
				packageManager.updatePackagePrice(packageIndex, price);
				break;
			case 4:
				int itemIndex = ItemUI.getIndexItemUI();
				int qty = PackageUI.getQuantity();;
				Item i = itemManager.getItem(itemIndex);
				boolean result = packageManager.removeItemsInPackage(packageIndex, i, qty);
				if (result) {
					Package pac = packageManager.getPackage(packageIndex);
					double finalPrice = PackageUI.getPricePackageUI(pac.getOriPrice());
					pac.setPrice(finalPrice);
				}
				break;
			case 5:
				itemIndex = ItemUI.getIndexItemUI();
				qty = PackageUI.getQuantity();
				i = itemManager.getItem(itemIndex);
				if (i != null)
				{result = packageManager.addItemsInPackage(packageIndex, i, qty);
				if (result) {
					Package pac = packageManager.getPackage(packageIndex);
					double finalPrice = PackageUI.getPricePackageUI(pac.getOriPrice());
					pac.setPrice(finalPrice);
				}}
				else
				{System.out.println("Item does not exist in menu and hence cannot be added inside package.");}
				break;
			}
		}
		else 
			System.out.println("There are no packages with that index.");
	}
	
	/**
	 * A method to remove a package from the object of the package controller class (like a menu or collection of package objects) through user's input of the index of package they want to remove
	 */
	public void removePackage()
	{
		int packageInd = PackageUI.getIndexPackageUI();
		if (packageManager.checkDuplicatePackage(packageInd)) 
			packageManager.removePackageMenu(packageInd);
		else 
			System.out.println("There is no package with that index.");
	}
	
	/**
	 * A method to print out the details of the items and packages successfully created/added
	 */
	public void viewMenus()
	{
		itemManager.viewMenu();
		packageManager.viewPackageMenu();
	}
	
	/**
	 * A method to add an item into a package based on the user's input of its attributes like index and quantity
	 * @param e The package object which we want to add items in
	 */
	public void addPackageItem(Package e)
	{
		char choice;
		int itemIndex;
		while (true) {
			System.out.println("Add items to package! Press c to continue, press q to stop adding items.");
			choice = PackageUI.addItemChoice();
			if (choice == 'c') {
				itemIndex = ItemUI.getIndexItemUI();
				if (itemManager.checkDuplicate(itemIndex)) {
					int quantity = PackageUI.getQuantity();
					Item item = itemManager.getItem(itemIndex);
					e.addPackageItem(item, quantity);
				} else {
					System.out.println("Nothing is added into this package because there is no item with this index.");
				}
			} else if (e.isNull())
				System.out.println("Package is empty, please add some items before quitting.");
			else
				break;
		}
	}
	
	/**
	 * A method to update an item's attribute in a package based on the user's input of its attributes
	 * @param packList ArrayList of packages that contains certain item and will be called when the item's price is updated in updateItem
	 */
	public void updateItemInPackage(ArrayList<Package> packList)
	{
		
		for (Package p : packList)
		{
			double oriP = p.getOriPrice();
			double oldP = p.getPrice();
			if (oldP < oriP)
			{
				System.out.println("This item is inside package " + p.getName()
				+ ", do you want to update price of this package as well? (1: Yes/2: No)");
				System.out.println("The initial discounted price of this package is " + oldP);
				int opt = PackageUI.updateItemInPackage();
				if (opt == 1)
				{
					double newP = PackageUI.getPricePackageUI(oriP);
					p.setPrice(newP);
				}
			}
			else
			{
				System.out.println("This item is inside package" + p.getName()
				+ "please update the price of the package since the total price of each item in the package is higher.");
				System.out.println("The initial discounted price of this package is " + p.getPrice());
				double newP = PackageUI.getPricePackageUI(oriP);
				p.setPrice(newP);
			}
		}
		
	}
	/**
	 * Creation of order after customer dines in
	 */
	public void createOrder() {
		try {
    		int table = OrderUI.scanTableID();
//    		if(tableManager.haveID(table) == false) {
//    			throw new Exception("Invalid table ID.");
//    		}
        	boolean tableavail = tableManager.checkTableStatus(table);
        	if (tableavail) {
        		throw new Exception("This table has not been assigned yet. Please dine in first before creating order.");
        	}
			String staffName = StaffUI.scanStaffName();
			int staffid = StaffUI.scanStaffID();
			boolean staff = staffManager.isStaff(staffid, staffName);
			if (!staff) {
				throw new Exception("Unable to create order as staff server particulars are wrong or does not exist.");
			}
			orderManager.createOrder();
			int orderid = orderManager.getorderID();
			if (orderid < 0) {
				throw new Exception("Something went wrong with orderid");
			}
			Order newOrder = new Order(table, orderid, staffName);
			orderManager.addOrdertoArrayList(newOrder, table);
    	} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
	}
	/**
	 * View all current orders that the customer has made
	 */
	public void viewOrder() {
		try {
    		int orderid = OrderUI.scanOrderID();
    		if (orderManager.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
    		orderManager.viewOrder(orderid);
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Add food item/s from the menu to the customer's order
	 */
	public void addOrder() {
		try {
    		int orderid = OrderUI.scanOrderID();
    		if (orderManager.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			boolean alacarte = OrderUI.scanisAlaCarte();
			int foodid = OrderUI.scanfoodID();
			int quantity = OrderUI.scanQuantity();
			if (alacarte) {
				Item fooditem = itemManager.getItem(foodid);
				if (fooditem == null) {
					throw new Exception("Item does not exist in the menu");
				}
				orderManager.addOrder(orderid, fooditem, quantity);
			}
			else {
				Package foodpack = packageManager.getPackage(foodid);
				if (foodpack == null) {
					throw new Exception("Package does not exist in the menu");
				}
				orderManager.addOrder(orderid, foodpack, quantity);
			}
    	} catch (Exception e) {
			System.out.println(e.getMessage()); 
		}
	}
	/**
	 * Remove food item/s from the customer's order (if they previously exist in the order)
	 */
	public void removeOrder() {
		try {
    		int orderid = OrderUI.scanOrderID();
    		if (orderManager.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			boolean alacarte = OrderUI.scanisAlaCarte();
			int foodid = OrderUI.scanfoodID();
			int quantity = OrderUI.scanQuantity();
			if (alacarte) {
				Item fooditem = itemManager.getItem(foodid);
				if (fooditem == null) {
					throw new Exception("Item does not exist in the menu");
				}
				orderManager.removeOrder(orderid, fooditem, quantity);
			}
			else {
				Package foodpack = packageManager.getPackage(foodid);
				if (foodpack == null) {
					throw new Exception("Package does not exist in the menu");
				}
				orderManager.removeOrder(orderid, foodpack, quantity);
			}
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Enquire membership status of the customer and optionally if they want to join membership before proceeding to payment and printing order invoice
	 */
	public void checkout() {
		try {
    		System.out.println("Checkout in progress ...");
    		int orderid = OrderUI.scanOrderID();
			if (orderManager.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			Order completeOrder = orderManager.getOrder(orderid);
//			if (completeOrder == null) {
//				throw new Exception("Error: order does not exist yet");
//			}
			if (completeOrder.getOrders().isEmpty()) {
				throw new Exception("You haven't ordered amything yet");
			}
			if (completeOrder.paid() == true) {
        		throw new Exception("The order has already been paid");
        	}
        	int join = MemberUI.joinMembership();
        	String name, contact;
			if (join == 1 || join == 2) {
				name = MemberUI.scanMemberName();
				contact = MemberUI.scanMemberHP();
			}
			else {
				name = null;
				contact = null;
			}
			boolean membership = memberManager.paymentMembership(name, contact, join);
			int releaseTable = orderManager.printInvoice(membership, orderid);
			tableManager.vacateTable(releaseTable);
			System.out.println("Thank you and see you again!");
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * print a revenue report given a time period in selected format
	 */
	public void printRevenue()
	 {
	  String[] period = OrderUI.scanTime();
	  orderManager.parseTme(period);
	  int choice = OrderUI.scanRevenueChoice();
	  orderManager.printRevenueReport(choice);
	 }
}
