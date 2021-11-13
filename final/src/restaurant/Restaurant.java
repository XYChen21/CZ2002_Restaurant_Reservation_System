package restaurant;

import java.util.*;
import restaurant.Item.KindofFood;
import java.io.*;
import java.time.*;

public class Restaurant implements Serializable
{	
	private ReservationManager resManager;
	private TableManager tableManager;
	private ItemManager m;
	private PackageManager pack;
	private StaffManager staffmg;
	private MemberManager memmg;
	private OrderManager ordermg;

	public Restaurant()
	{	m = new ItemManager();
		pack = new PackageManager();
		resManager = new ReservationManager();
		tableManager = new TableManager();
		staffmg = new StaffManager();
		memmg = new MemberManager();
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
	public void restoreAutoRemove()
	{
		resManager.restoreAutoRemove();
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
		int pax = TableUI.scanPax();
		ArrayList<Integer> availTables = resManager.checkAvail(LocalDateTime.now());
		Integer tID = tableManager.allocateTable(availTables, LocalDateTime.now(), pax);
		if (tID != null)
			tableManager.assignTable(tID);
		else
			System.out.println("No tables are available at the moment. Please wait.");
	}
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
	public void showRes()
	{
		String name = ReservationUI.scanName();
		String contact = ReservationUI.scanContact();
		LocalDateTime dateTime = ReservationUI.scanTime();
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
		String name = ReservationUI.scanName();
		String contact = ReservationUI.scanContact();
		LocalDateTime dateTime = ReservationUI.scanTime();
		int pax = ReservationUI.scanPax();
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
		String name = ReservationUI.scanName();
		String contact = ReservationUI.scanContact();
//		String date = resUI.scanTime().toLocalDate();
		String key = name + contact + ReservationUI.scanTime().toLocalDate();
//		System.out.println("key: " + key);
		resManager.removeRes(key);
	}
	public void listTableStatus()
	{
		tableManager.listAllStatus();
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
		int index = ItemUI.getIndexItemUI();
		if (m.checkDuplicate(index) == false)
		{
			String name = ItemUI.getNameItemUI();
			String description = ItemUI.getDescItemUI();
			double price = ItemUI.getPriceItemUI();
			KindofFood type = ItemUI.getTypeItemUI();
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
		int index = ItemUI.getIndexItemUI();
		if (m.checkDuplicate(index))
		{
			int opt = ItemUI.updateMenuChoice();
			switch(opt) {
			case 1:
				int newIndex = ItemUI.getNewIndexItemUI();
				if (m.checkDuplicate(newIndex) == false) {
					m.updateIndex(index, newIndex);
				}
				else {
					System.out.println("Duplicate index in this menu.");
				}
				break;
			case 2:
				String name = ItemUI.getNameItemUI();
				m.updateName(index, name);
				break;
			case 3:
				String description = ItemUI.getDescItemUI();
				m.updateDesc(index, description);
				break;
			case 4:
				double price = ItemUI.getPriceItemUI();
				m.updatePrice(index, price);
				Item i = m.getItem(index);
				ArrayList<Package> packToUpdate = pack.itemInPackage(i);
				updateItemInPackage(packToUpdate);
				break;
			case 5:
				KindofFood type = ItemUI.getTypeItemUI();
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
		int index = ItemUI.getIndexItemUI();
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
		int index = PackageUI.getIndexPackageUI();
		if (pack.checkDuplicatePackage(index) == false)
		{
			String name = PackageUI.getNamePackageUI();
			Package e = new Package(name, index);
			addPackageItem(e);
			double price = PackageUI.getPricePackageUI(e.getOriPrice());
			e.setPrice(price);
			pack.addPackageMenu(index, e);
		}
		else
			System.out.println("Duplicate index of packages in this menu");
	}
	public void updatePackage()
	{
		int packageIndex = PackageUI.getIndexPackageUI();
		if (pack.checkDuplicatePackage(packageIndex))
		{
			int opt = PackageUI.updatePackageChoice();
			switch(opt) {
			case 1:
				int newIndex = PackageUI.getNewIndexPackageUI();
				pack.updatePackageIndex(packageIndex, newIndex);
				break;
			case 2:
				String name = PackageUI.getNamePackageUI();
				pack.updatePackageName(packageIndex, name);
			case 3:
				Package p = pack.getPackage(packageIndex);
				System.out.println("The initial discounted price of this package is " + p.getPrice());
				double price = PackageUI.getPricePackageUI(p.getOriPrice());
				pack.updatePackagePrice(packageIndex, price);
				break;
			case 4:
				int itemIndex = ItemUI.getIndexItemUI();
				int qty = PackageUI.getQuantity();;
				Item i = m.getItem(itemIndex);
				boolean result = pack.removeItemsInPackage(packageIndex, i, qty);
				if (result) {
					Package pac = pack.getPackage(packageIndex);
					double finalPrice = PackageUI.getPricePackageUI(pac.getOriPrice());
					pac.setPrice(finalPrice);
				}
				break;
			case 5:
				itemIndex = ItemUI.getIndexItemUI();
				qty = PackageUI.getQuantity();
				i = m.getItem(itemIndex);
				if (i != null)
				{result = pack.addItemsInPackage(packageIndex, i, qty);
				if (result) {
					Package pac = pack.getPackage(packageIndex);
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
	public void removePackage()
	{
		int packageInd = PackageUI.getIndexPackageUI();
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
			choice = PackageUI.addItemChoice();
			if (choice == 'c') {
				itemIndex = ItemUI.getIndexItemUI();
				if (m.checkDuplicate(itemIndex)) {
					int quantity = PackageUI.getQuantity();
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
    		int orderid = OrderUI.scanOrderID();
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
    		int orderid = OrderUI.scanOrderID();
    		if (ordermg.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			boolean alacarte = OrderUI.scanisAlaCarte();
			int foodid = OrderUI.scanfoodID();
			int quantity = OrderUI.scanQuantity();
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
    		int orderid = OrderUI.scanOrderID();
    		if (ordermg.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			boolean alacarte = OrderUI.scanisAlaCarte();
			int foodid = OrderUI.scanfoodID();
			int quantity = OrderUI.scanQuantity();
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
    		int orderid = OrderUI.scanOrderID();
			if (ordermg.checkValidOrderID(orderid) == false) {
    			throw new IndexOutOfBoundsException("Invalid orderID");
    		}
			Order completeOrder = ordermg.getOrder(orderid);
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
	  String[] period = OrderUI.scanTime();
	  ordermg.parseTme(period);
	  int choice = OrderUI.scanRevenueChoice();
	  ordermg.printRevenueReport(choice);
	 }
}
