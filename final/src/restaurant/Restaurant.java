package restaurant;

import java.util.*;

import restaurant.Item.KindofFood;

import java.io.*;
import java.time.*;

// import java.util.concurrent.ScheduledFuture;
// import java.util.concurrent.TimeUnit;
// import java.time.format.DateTimeFormatter;
// import restaurant.Item.KindofFood;

public class Restaurant implements Serializable
{	
	public ReservationManager resManager;
	public TableManager tableManager;
	public TableUI tableUI;
	public ReservationUI resUI;
	public ItemManager m;
	public PackageManager pack;
	public ItemUI itemUI;
	public PackageUI packageUI;
	public StaffUI staffui;
	public StaffManager staffmg;
	public MemberUI memui;
	public MemberManager memmg;
	public OrderUI orderui;
	public OrderManager ordermg;
	// private ArrayList<Order> ordersbyID;
	// private Menu m;
	// private PackageMenu pack;
	// private int numofOrders;
	// private HashMap<Integer, Table> tables;
	// private HashMap<String, Reservation> allReservations;
	// private StaffRoster listofStaff;
	// private Membership listofMembers;
	// private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	// private Revenue revenue;
	public Restaurant()
	{	m = new ItemManager();
		pack = new PackageManager();
		itemUI = new ItemUI();
		packageUI = new PackageUI();
		resManager = new ReservationManager();
		tableManager = new TableManager();
		tableUI = new TableUI();
		resUI = new ReservationUI();
		staffui = new StaffUI();
		staffmg = new StaffManager();
		memui = new MemberUI();
		memmg = new MemberManager();
		orderui = new OrderUI();
		ordermg = new OrderManager();

		int id = 1, cap = 2;
		Table t;
		while (id <= 10)
		{
			t = new Table(id, cap);
			tableManager.add(id, t);
			resManager.initTableReservation(id);
			id++;
			t = new Table(id, cap);
			tableManager.add(id, t);
			resManager.initTableReservation(id);
			id++; cap+=2;
		}
		Staff staff1 = new Staff("Sourav", 'M', 1060, "General Mamager");
		staffmg.addStaff(1060, staff1);
		Staff staff2 = new Staff("Gary", 'M', 2100, "Manager");
		staffmg.addStaff(2100, staff2);
		Staff staff3 = new Staff("Guohua", 'M', 2500, "Waitor");
		staffmg.addStaff(2500, staff3);
		Staff staff4 = new Staff("Ying", 'F', 2101, "Waitress");
		staffmg.addStaff(2101, staff4);
		Staff staff5 = new Staff("Ronald", 'M', 3100, "Bartender");
		staffmg.addStaff(3100, staff5);
		Staff staff6 = new Staff("Val", 'F', 1234, "Head Chef");
		staffmg.addStaff(1234, staff6);
		
		memmg.addMember("Jac", "+6587005902");
		memmg.addMember("XY", "+659130633");
		memmg.addMember("lsy", "+6586163328");
	}
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
	public void removeTable(int id)
	{
		if(tableManager.haveID(id))
			tableManager.remove(id);
		else
			System.out.println("ID doesn't exist. Please try again");
	}
	public void DineIn()
	{
		int pax = tableUI.scanPax();
		ArrayList<Integer> availTables = resManager.checkAvail(LocalDateTime.now());
		Integer tID = tableManager.allocateTable(availTables, LocalDateTime.now(), pax);
		if (tID != null)
			tableManager.assignTable(tID);
		else
			System.out.println("No tables are available at the moment. Please wait.");
	}
	public void reservedDineIn()
	{
		String name = resUI.scanName();
		String contact = resUI.scanContact();
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
				int c = resUI.scanEarlyArrivalChoice();
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
	public void showRes()
	{
		String name = resUI.scanName();
		String contact = resUI.scanContact();
		LocalDateTime dateTime = resUI.scanTime();
		String key = name + contact + dateTime.toLocalDate();
		Reservation res = resManager.getRes(key);
		if (res == null)
			System.out.println("Reservation not found!");
		else
			System.out.println(res.toString());
	}
	public void showAllRes()
	{
		resManager.showAllRes();
	}
	public void makeReservation()
	{
		String name = resUI.scanName();
		String contact = resUI.scanContact();
		LocalDateTime dateTime = resUI.scanTime();
		int pax = resUI.scanPax();
		ArrayList<Integer> availTables = resManager.checkAvail(LocalDateTime.now());
		Integer tID = tableManager.allocateTable(availTables, LocalDateTime.now(), pax);
		String key = name + contact + dateTime.toLocalDate();
//		System.out.println("key: "+key);
		if (resManager.haveRes(key))
			System.out.println("You've already made a reservation on that day, cannot make another reservation.");
		else
		{
			if (tID != null)
			{
				Reservation res = new Reservation(dateTime, name, contact, pax, tID);
				resManager.setAutoRemove(res);
				resManager.addRes(tID, key, res);
				System.out.println("Reservation made successfully!");
				Notification.sendSMS(res.toStringCust(), contact);
			}
			else
				System.out.println("Cannot make reservation for given date and time");
		}
	}
	public void removeReservation()
	{
		String name = resUI.scanName();
		String contact = resUI.scanContact();
//		String date = resUI.scanTime().toLocalDate();
		String key = name + contact + resUI.scanTime().toLocalDate();
//		System.out.println("key: " + key);
		resManager.removeRes(key);
	}
	public void listAvail()
	{
		tableManager.listAllAvail();
	}
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
	public void addMenuItem()
	{
		int index = itemUI.getIndexItemUI();
		if (m.checkDuplicate(index) == false)
		{
			String name = itemUI.getNameItemUI();
			String description = itemUI.getDescItemUI();
			double price = itemUI.getPriceItemUI();
			KindofFood type = itemUI.getTypeItemUI();
			Item i = new Item(index, name, description, price, type);
			m.addMenu(index, i);
		}
		else
		{
			System.out.println("Duplicate index in this menu.");
		}
	}
	public void updateItem()
	{
		int index = itemUI.getIndexItemUI();
		if (m.checkDuplicate(index))
		{
			int opt = itemUI.updateMenuChoice();
			switch(opt) {
			case 1:
				int newIndex = itemUI.getNewIndexItemUI();
				if (m.checkDuplicate(newIndex) == false) {
					m.updateIndex(index, newIndex);
				}
				else {
					System.out.println("Duplicate index in this menu.");
				}
				break;
			case 2:
				String name = itemUI.getNameItemUI();
				m.updateName(index, name);
				break;
			case 3:
				String description = itemUI.getDescItemUI();
				m.updateDesc(index, description);
				break;
			case 4:
				double price = itemUI.getPriceItemUI();
				m.updatePrice(index, price);
				Item i = m.getItem(index);
				ArrayList<Package> packToUpdate = pack.itemInPackage(i);
				updateItemInPackage(packToUpdate);
				break;
			case 5:
				KindofFood type = itemUI.getTypeItemUI();
				m.updateType(index, type);
				break;
			}
		}
		else
		{
			System.out.println("Item with this index does not exist.");
		}
	}
	public void removeMenuItem()
	{
		int index = itemUI.getIndexItemUI();
		if (m.checkDuplicate(index))
			m.removeMenu(index);
		else
			System.out.println("Item with this index does not exist.");
	}
	public void createPackage()
	{
		if (m.isNull())
		{
			System.out.println("There are no items in the menu, cannot create a package");
			return;
		}
		int index = packageUI.getIndexPackageUI();
		if (pack.checkDuplicatePackage(index) == false)
		{
			String name = packageUI.getNamePackageUI();
			Package e = new Package(name, index);
			addPackageItem(e);
			double price = packageUI.getPricePackageUI(e.getOriPrice());
			e.setPrice(price);
			pack.addPackageMenu(index, e);
		}
		else
			System.out.println("Duplicate index of packages in this menu");
	}
	public void updatePackage()
	{
		int packageIndex = packageUI.getIndexPackageUI();
		if (pack.checkDuplicatePackage(packageIndex))
		{
			int opt = packageUI.updatePackageChoice();
			switch(opt) {
			case 1:
				int newIndex = packageUI.getNewIndexPackageUI();
				pack.updatePackageIndex(packageIndex, newIndex);
				break;
			case 2:
				String name = packageUI.getNamePackageUI();
				pack.updatePackageName(packageIndex, name);
			case 3:
				Package p = pack.getPackage(packageIndex);
				System.out.println("The initial discounted price of this package is " + p.getPrice());
				double price = packageUI.getPricePackageUI(p.getOriPrice());
				pack.updatePackagePrice(packageIndex, price);
				break;
			case 4:
				int itemIndex = itemUI.getIndexItemUI();
				int qty = packageUI.getQuantity();;
				Item i = m.getItem(itemIndex);
				boolean result = pack.removeItemsInPackage(packageIndex, i, qty);
				if (result) {
					Package pac = pack.getPackage(packageIndex);
					double finalPrice = packageUI.getPricePackageUI(pac.getOriPrice());
					pac.setPrice(finalPrice);
				}
				break;
			case 5:
				itemIndex = itemUI.getIndexItemUI();
				qty = packageUI.getQuantity();
				i = m.getItem(itemIndex);
				if (i != null)
				{result = pack.addItemsInPackage(packageIndex, i, qty);
				if (result) {
					Package pac = pack.getPackage(packageIndex);
					double finalPrice = packageUI.getPricePackageUI(pac.getOriPrice());
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
	public void removePackage()
	{
		int packageInd = packageUI.getIndexPackageUI();
		if (pack.checkDuplicatePackage(packageInd)) 
			pack.removePackageMenu(packageInd);
		else 
			System.out.println("There is no package with that index.");
	}
	
	
	public void viewMenus()
	{
		m.viewMenu();
		pack.viewPackageMenu();
	}
	public void addPackageItem(Package e)
	{
		char choice;
		int itemIndex;
		while (true) {
			System.out.println("Add items to package! Press c to continue, press q to stop adding items.");
			choice = packageUI.addItemChoice();
			if (choice == 'c') {
				itemIndex = itemUI.getIndexItemUI();
				if (m.checkDuplicate(itemIndex)) {
					int quantity = packageUI.getQuantity();
					Item item = m.getItem(itemIndex);
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
				int opt = packageUI.updateItemInPackage1();
				if (opt == 1)
				{
					double newP = packageUI.getPricePackageUI(oriP);
					p.setPrice(newP);
				}
			}
			else
			{
				System.out.println("This item is inside package" + p.getName()
				+ "please update the price of the package since the total price of each item in the package is higher.");
				System.out.println("The initial discounted price of this package is " + p.getPrice());
				double newP = packageUI.getPricePackageUI(oriP);
				p.setPrice(newP);
			}
		}
		
	}
	public void createOrder() {
		try {
    		int table = orderui.scanTableID();
        	boolean tableavail = tableManager.checkTableStatus(table);
        	if (tableavail) {
        		throw new Exception("This table has not been assigned yet. Please dine in first before creating order.");
        	}
			String staffName = staffui.scanStaffName();
			int staffid = staffui.scanStaffID();
			boolean staff = staffmg.isStaff(staffid, staffName);
			if (staff) {
				ordermg.createOrder(table, staffName);
			}
			else {
				System.out.println("Unable to create order as staff server particulars are wrong or does not exist.");
			}
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void viewOrder() {
		try {
    		int orderid =orderui.scanOrderID();
    		if (ordermg.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
    		ordermg.viewOrder(orderid);
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void addOrder() {
		try {
    		int orderid = orderui.scanOrderID();
    		if (ordermg.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			boolean alacarte = orderui.scanisAlaCarte();
			int foodid = orderui.scanfoodID();
			int quantity = orderui.scanQuantity();
			if (alacarte) {
				Item fooditem = m.getItem(foodid);
				if (fooditem == null) {
					throw new Exception("Item does not exist in the menu");
				}
				ordermg.addOrder(orderid, fooditem, quantity);
			}
			else {
				Package foodpack = pack.getPackage(foodid);
				if (foodpack == null) {
					throw new Exception("Package does not exist in the menu");
				}
				ordermg.addOrder(orderid, foodpack, quantity);
			}
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void removeOrder() {
		try {
    		int orderid =orderui.scanOrderID();
    		if (ordermg.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			boolean alacarte = orderui.scanisAlaCarte();
			int foodid = orderui.scanfoodID();
			int quantity = orderui.scanQuantity();
			if (alacarte) {
				Item fooditem = m.getItem(foodid);
				if (fooditem == null) {
					throw new Exception("Item does not exist in the menu");
				}
				ordermg.removeOrder(orderid, fooditem, quantity);
			}
			else {
				Package foodpack = pack.getPackage(foodid);
				if (foodpack == null) {
					throw new Exception("Package does not exist in the menu");
				}
				ordermg.removeOrder(orderid, foodpack, quantity);
			}
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void checkout() {
		try {
    		System.out.println("Checkout in progress ...");
    		int orderid = orderui.scanOrderID();
			if (ordermg.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			Order completeOrder = ordermg.getOrder(orderid);
			if (completeOrder == null) {
				throw new Exception("Error: order does not exist yet");
			}
			if (completeOrder.paid() == true) {
        		throw new Exception("The order has already been paid");
        	}
        	int join = memui.joinMembership();
        	String name, contact;
			if (join == 1 || join == 2) {
				name = memui.scanMemberName();
				contact = memui.scanMemberHP();
			}
			else {
				name = null;
				contact = null;
			}
			boolean membership = memmg.paymentMembership(name, contact, join);
			int releaseTable = ordermg.printInvoice(membership, orderid);
			tableManager.vacateTable(releaseTable);
			System.out.println("Thank you and see you again!");
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void printRevenue()
	 {
	  String[] period = orderui.scanTime();
	  ordermg.parseTme(period);
	  int choice = orderui.scanRevenueChoice();
	  ordermg.printRevenueReport(choice);
	 }
}
