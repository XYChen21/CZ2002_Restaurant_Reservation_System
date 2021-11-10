package restaurant;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import restaurant.Item.KindofFood;

public class Restaurant implements Serializable
{	
	private ArrayList<Order> ordersbyID;
	private Menu m;
	private PackageMenu pack;
	private int numofOrders;
	private HashMap<Integer, Table> tables;
	private HashMap<String, Reservation> allReservations;
	private StaffRoster listofStaff;
	private Membership listofMembers;
	private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private Revenue revenue;
	public Restaurant()
	{	
		ordersbyID = new ArrayList<Order>();
		numofOrders = 0;
		tables = new HashMap<Integer, Table>();
		allReservations = new HashMap<String, Reservation>();
		listofStaff = new StaffRoster();
		listofMembers = new Membership();
		int id = 1, cap = 2;
		Table t;
		while (id <= 10)
		{
			t = new Table(id, cap);
			tables.put(id, t);
			id++;
			t = new Table(id, cap);
			tables.put(id, t);
			id++; cap+=2;
		}
		
		m = new Menu();
		pack = new PackageMenu();
		
		Staff staff1 = new Staff("Gary", 'M', 2100, "Waitor");
		Staff staff2 = new Staff("Guohua", 'M', 2500, "Waitor");
		Staff staff3 = new Staff("Yingying", 'F', 2101, "Waitress");
		Staff staff4 = new Staff("Sourav", 'M', 1060, "Manager");
		Staff staff5 = new Staff("Fang", 'F', 2002, "General Manager");
		listofStaff.addStaff(staff1);
		listofStaff.addStaff(staff2);
		listofStaff.addStaff(staff3);
		listofStaff.addStaff(staff4);
		listofStaff.addStaff(staff5);
		
		listofMembers.addMember("Jac", "+6587005902");
		listofMembers.addMember("Val", "+6584084110");
		listofMembers.addMember("lsy", "+6586163328");
		listofMembers.addMember("XY", "+6591307633");
		

		revenue = new Revenue(ordersbyID, new HashMap<Food, Integer>());
		
	}
//	public ScheduledFuture<?> scheduleCancel(LocalDateTime scheduleTime, Runnable task)
//	{
//		long delay = Duration.between(LocalDateTime.now(), scheduleTime).toSeconds();
//		return scheduler.schedule(task, delay, TimeUnit.SECONDS);
//	}
	public void showAllRes()
	{
		allReservations.forEach((k,v) -> System.out.println(v.toString()));
	}
	public Reservation findRes(String name, String contact)
	{
		return allReservations.get(name+contact);
	}
	public boolean reservedDineIn(String name, String contact)
	{
		Scanner sc = new Scanner(System.in);
		Reservation res = findRes(name, contact);
		if (res == null)
		{
			System.out.println("Reservation not found. Try assigning new table...");
			System.out.print("How many people? ");
			int pax = sc.nextInt();
			sc.nextLine();
			return DineIn(pax);
		}
		else 
		{
			int tableID = res.getTableID();
			Table t = tables.get(tableID);
			LocalDateTime reservedTime = res.getTime();
			if (LocalDateTime.now().isBefore(reservedTime)) //early arrival
			{
				System.out.println("A later reservation at " + reservedTime + " was found");
				System.out.print("1.Assign reserved table? OR 2.Assign new table? (1/2) ");
				int option  = sc.nextInt();
				sc.nextLine();
				if (option == 1)	
				{
					if (t.getAvail())
					{
						t.assign();
						System.out.println("Assign table " + tableID);
						System.out.print("Remove this reservation?(Yes/No) ");
						if (sc.nextLine() == "Yes")
							removeReservation(res);
						return true;
					}
					else
					{
						System.out.println("Early arrival: reservation found but table is occupied");
						System.out.println("Try assigning new table...");
					}
				}
				System.out.print("How many people? ");
				int pax = sc.nextInt();
				sc.nextLine();
				boolean result = DineIn(pax);
				System.out.print("Remove this reservation?(Yes/No) ");
				if (sc.nextLine() == "Yes")
					removeReservation(res);
				return result;
			}
			else // on time or late no more than 15 mins
			{
				t.assign();
				System.out.println("Reservation found: assign table " + tableID);
				removeReservation(res);
				return true;
			}	
		}
	}
	
	public boolean DineIn(int pax) 
	{
		TreeSet<Table> availTables = checkAvail(LocalDateTime.now(), pax);
		if (availTables.isEmpty())
		{
			System.out.println("No free tables, please wait");
			return false;
		}
		else
		{
			Table t = availTables.first();
			t.assign();
			System.out.println("Assign table " + t.getID());
			return true;
		}
	}
	public void listAvail()
	{
		for (Integer id : tables.keySet())
		{
			Table t = tables.get(id);
			if (t.getAvail())
				System.out.println("Table ID " + t.getID() + " is available");
			else
				System.out.println("Table ID " + t.getID() + " is occupied");
		}
	}
	public TreeSet<Table> checkAvail(LocalDateTime time, int pax)
	{
		System.out.println("Checking availability...");
		TreeSet<Table> availTables = new TreeSet<Table>();
		for (Integer id : tables.keySet()) 
		{
			Table t = tables.get(id);
			if (t.checkAvail(time, pax))
				availTables.add(t); 
		}
		return availTables;
	}
	public boolean makeReservation()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter data and time for reservation(dd/MM/yyyy HH:mm): ");
		String strDateTime = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(strDateTime, formatter);
		if (dateTime.isBefore(LocalDateTime.now())) 
		{
			System.out.println("Invalid time");
			return false;
		}
		System.out.print("How many people? ");
		int pax = sc.nextInt();
		sc.nextLine();
		TreeSet<Table> availTables = checkAvail(dateTime, pax);
		if (availTables.isEmpty())
		{
			System.out.println("Cannot make reservation for given date and time");
			return false;
		}
		else
		{
			Table t = availTables.first();
			int tID = t.getID();
			System.out.print("Name: ");
			String name = sc.nextLine();
			System.out.print("Contact number(+65xxxxxxxx): ");
			String contact = sc.nextLine();
			Reservation res = new Reservation(strDateTime, name, contact, pax, tID);
//			ScheduledFuture<?> s = scheduleCancel(dateTime.plusMinutes(15), new AutoCancelTask(res));
//			res.setSchedule(s);
			autoRemove(res);
			t.addReservation(res);
			allReservations.put(name+contact, res);
			
			Notification.sendSMS(res.toString(), contact);
			
			return true;
		}
	}
	public boolean verifyContact(String contact)
	{
		return contact.matches("^\\+65[0-9]{8}");
	}
	
	public boolean removeReservation(Reservation res)
	{
		if (res == null) return false;
		res.cancelSchedule();
		int tID = res.getTableID();
		tables.get(tID).removeReservation(res);
		allReservations.remove(res.getName()+res.getContact());
		return true;
	}
	public void close()
	{
		// save all orders, menu items, table, staff, reservations etc. and close
		try {
			FileOutputStream fs = new FileOutputStream("restaurant.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(this);
			os.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		scheduler.shutdownNow();
	}
	public void autoRemove(Reservation res)
	{
		LocalDateTime reservationTime = res.getTime();
		long delay = Duration.between(LocalDateTime.now(), reservationTime.plusMinutes(15)).toSeconds();
		ScheduledFuture<?> s = scheduler.schedule(new AutoCancelTask(res), delay, TimeUnit.SECONDS);
		res.setSchedule(s);
	}
	class AutoCancelTask implements Runnable
	{
		private Reservation res;
		public AutoCancelTask(Reservation res)
		{
			this.res = res;
		}
		@Override
		public void run() 
		{
			try 
			{
				allReservations.remove(res.getName()+res.getContact());
				int tableID = res.getTableID();
				Table t = tables.get(tableID);
				t.removeReservation(res);
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
		Restaurant r = null;
		try {
			FileInputStream fs = new FileInputStream("restaurant.ser");
			ObjectInputStream is = new ObjectInputStream(fs);
			r = (Restaurant) is.readObject(); // need cast because we'll get back type Object
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (r == null)
		{
			System.out.println("null");
			return;
		}
//		Restaurant r = new Restaurant();
		Reservation res;
		int choice;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("(1)Dine in (without reservation)");
			System.out.println("(2)Dine in (with reservation)");
			System.out.println("(3)Make a reservation");
			System.out.println("(4)Remove a reservation");
			System.out.println("(5)Find a reservation");
			System.out.println("(6)Show all reservations");
			System.out.println("(7)Check table availability");
			System.out.println("(8)Create menu item");
			System.out.println("(9)Update menu item");
			System.out.println("(10)Remove menu item");
			System.out.println("(11)Create promotion");
			System.out.println("(12)Update promotion");
			System.out.println("(13)Remove promotion");
			System.out.println("(14)View menu & promotion");
			System.out.println("(15)Create order");
			System.out.println("(16)View Order");
			System.out.println("(17)Add item/s or package/s to order");
			System.out.println("(18)Remove item/s or package/s from order");
			System.out.println("(19)Print order invoice");
			System.out.println("(20)Print sale revenue report by period(eg day or month)");
			System.out.println("(21)Close");
			System.out.print("Enter the number of your choice: ");
			choice = sc.nextInt();
			sc.nextLine(); // consume newline char
			switch (choice) {
			case 1:
				System.out.print("How many people? ");
				int pax = sc.nextInt();
				sc.nextLine();
				r.DineIn(pax);
				break;
			case 2:
				System.out.print("Name: ");
				String name = sc.nextLine();
				System.out.print("Contact number: ");
				String contact = sc.nextLine();
				r.reservedDineIn(name, contact);
				break;
			case 3:
				r.makeReservation();
				break;
			case 4:
				System.out.print("Name: ");
				name = sc.nextLine();
				System.out.print("Contact number: ");
				contact = sc.nextLine();
				res = r.findRes(name, contact);
				r.removeReservation(res);
				break;
			case 5:
				System.out.print("Name: ");
				name = sc.nextLine();
				System.out.print("Contact number: ");
				contact = sc.nextLine();
				res = r.findRes(name, contact);
				if (res == null)
					System.out.println("Reservation not found!");
				else
					System.out.println(res.toString());
				break;
			case 6:
				r.showAllRes();
				break;
			case 7:
				r.listAvail();
				break;
			case 8:
				System.out.println("Create an index for the item you want to add.");
				int a = sc.nextInt();
				sc.nextLine();
				System.out.println("What is the name of the item you want to add in the menu?");
				String s = sc.nextLine();
				System.out.println("What is the description of the item?");
				String b = sc.nextLine();
				System.out.println("What is the price?");
				double c = sc.nextDouble();
				System.out.println(
						"What is the type of the item?  1: Main, 2: Sides, 3: Beverage, 4: Dessert. Enter either 1,2,3 or 4.");
				int d = sc.nextInt(); // Assume userInput = 0
				KindofFood f = KindofFood.getTypeByOrdinal(d);

				Item i = new Item(a, s, b, c, f);
				r.m.addMenu(a, i);
				break;
			case 9:
				System.out.println("Which item in the menu do you want to update? Enter the item index.");
				int indexIt = sc.nextInt();
				if (r.m.getItem(indexIt) != null) {
					Item it = r.m.getItem(indexIt);
					boolean resu = r.m.updateMenu(it);

					if (resu == true) {
						r.pack.updateItemInPackage(it);
					}

				}

				else {
					System.out.println("There is no item with such index. Nothing is changed");
				}

				break;
			case 10:
				System.out.println("Enter the index of the item you want to remove.");
				int x = sc.nextInt();
				r.m.removeMenu(x);
				break;
			case 11:
//				System.out.println("How many kinds of items are there in this new package?");
//				int number = sc.nextInt();
				System.out.println("Create an index for this new package.");
				int ind = sc.nextInt();
				sc.nextLine();
				System.out.println("What is the name of this new package?");
				String namePackage = sc.nextLine();
				Package p = new Package(namePackage, ind);
				int index;
				do {
					System.out.println(
							"Which item from the menu do you want to add into this package? Enter the index of the item, -1 to quit");
					index = sc.nextInt();
					sc.nextLine();
					Item item = r.m.getItem(index);
					if (item != null) {
						System.out.println("quantity: ");
						int quantity = sc.nextInt();
						sc.nextLine();
						if (quantity > 0)
							p.addPackageItem(item, quantity);
						else
							System.out.println("Invalid quantity, please try again.");
					} 
					else if (index != -1){
						System.out.println("There is no item from the menu with this index number.");
					}
					else if (index == -1 && p.isNull()){
						System.out.println("Package is empty, pleaese add in items");
					}

				} while (index != -1 && p.isNull() == false);
				System.out.println("What is the price of this package?");
				double price = sc.nextDouble();
				p.setPrice(price);
				r.pack.addPackageMenu(ind, p);
				break;
			case 12:
				r.pack.updatePackage();
				break;
			case 13:
				System.out.println("Which package do you want to completely remove? Enter the index of the package.");
				int removed = sc.nextInt();
				sc.nextLine();
				r.pack.removePackageMenu(removed);
				break;
			case 14:
				r.m.viewMenu();
				r.pack.viewPackageMenu();
				break;
			case 15:
				System.out.println("Creating order now...");
				System.out.println("Please enter table number allocated: ");
				int tableID = sc.nextInt();
				String randStaff = r.listofStaff.whoToServe();
				Order newOrder = new Order(tableID, r.numofOrders, randStaff);
				r.ordersbyID.add(newOrder); // index of the Order is numofOrders
				System.out.println("New order for table " + tableID + " with orderID " + r.numofOrders
						+ " has been successfully created.");
				r.numofOrders++;

				break;
			case 16:
				System.out.println("Enter orderID given during order creation: ");
				int viewOrderID = sc.nextInt();
				Order viewOrderSummary = null;
				try {
					viewOrderSummary = r.ordersbyID.get(viewOrderID);
					System.out.println("======== Current order summary ========");
					viewOrderSummary.viewOrder();
				} catch (IndexOutOfBoundsException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 17:
				System.out.println("Add to order now in service!");
				System.out.println("Enter orderID given during order creation: ");
				int addOrderID = sc.nextInt();
				Order addToOrder = r.ordersbyID.get(addOrderID);
				System.out.println("Select (1) Add to order (2) Quit");
				c = sc.nextInt();
				while (c == 1) {
					System.out.println("Enter type of meal: (1) Ala carte (2) Promotional set");
					int type = sc.nextInt();
					if (type != 1 && type != 2) {
						System.out.println(
								"Invalid selection. Enter either (1) for ala carte or (2) for promotional set. Please re-enter your selection.");
						continue;
					}
					System.out.println("Enter the corresponding food index: ");
					int foodID = sc.nextInt();
					System.out.println("Enter the quantity you wish to add: ");
					int quantity = sc.nextInt();
					if (type == 1) {
						Item addItem = r.m.getItem(foodID);
						addToOrder.addOrder(addItem, quantity);
					} else {
						Package addP = r.pack.getPackage(foodID);
						addToOrder.addOrder(addP, quantity);
					}
					System.out.println("Select (1) Add to order (2) Quit");
					c = sc.nextInt();
				}
				System.out.println("Quitting from add to order process...");
				break;
			case 18:
				System.out.println("Remove from order now in service!");
				System.out.println("Enter orderID given during order creation: ");
				int removeOrderID = sc.nextInt();
				Order removeFrOrder = r.ordersbyID.get(removeOrderID);
				System.out.println("Select (1) Remove from order (2) Quit");
				c = sc.nextInt();
				while (c == 1) {
					System.out.println("Enter type of meal: (1) Ala carte (2) Promotional set");
					int type = sc.nextInt();
					if (type != 1 && type != 2) {
						System.out.println(
								"Invalid selection. Enter either (1) for ala carte or (2) for promotional set. Please re-enter your selection.");
						continue;
					}
					System.out.println("Enter the corresponding food index: ");
					int foodID = sc.nextInt();
					System.out.println("Enter the quantity you wish to remove: ");
					int quantity = sc.nextInt();
					if (type == 1) {
						Item removeItem = r.m.getItem(foodID);
						removeFrOrder.removeOrder(removeItem, quantity);
					} else {
						Package removeP = r.pack.getPackage(foodID);
						removeFrOrder.removeOrder(removeP, quantity);
					}
					System.out.println("Select (1) Remove from order (2) Quit");
					c = sc.nextInt();
				}
				System.out.println("Quitting from remove from order process...");
				break;
			case 19:
//				boolean isMem = false;
//				String contactNo;
				System.out.println("Enter orderID given during order creation: ");
				int paymentOrderID = sc.nextInt();
				Order paymentOrder;
				try {
					paymentOrder = r.ordersbyID.get(paymentOrderID);
				} catch (IndexOutOfBoundsException e) {
					System.out.println(e.getMessage());
					break;
				}
				if (paymentOrder.haveOrder()) {
					if (paymentOrder.paid() == true)
						System.out.println("The order has already been paid");
					else {
						boolean isMem = r.listofMembers.paymentMembership();
						int releaseTable = paymentOrder.printInvoice(isMem);
						Table t = r.tables.get(releaseTable);
						r.revenue.addOrder(paymentOrder);
						t.vacate();
					}
				} else {
					System.out.println("You have no orders at the moment, unable to print invoice.");
				}
				break;
			case 20:
				System.out.println("Specify a period (d/M/y; input null for all orders) - start:");
				LocalDate start, end;
				try {
					start = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("d/M/y"));
				}catch(Exception e){
					r.revenue.printRevenueReport();
					break;
				}
				System.out.println("Specify a period (d/M/y; input null for all orders) - end:");
				try {
					end = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("d/M/y"));
				}catch(Exception e){
					r.revenue.printRevenueReport();
					break;
				}

				r.revenue.printRevenueReport(start, end);
				break;
			case 21:
				System.out.println("Program terminating ....");
				
				r.close();
				break;
			}
			System.out.println("");
		} while (choice < 21);

	}
}