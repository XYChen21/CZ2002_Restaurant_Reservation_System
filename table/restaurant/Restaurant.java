package restaurant;

import java.util.*;
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
	public void DineIn(int pax)
	{
		ArrayList<Integer> availTables = resManager.checkAvail(LocalDateTime.now());
		Integer tID = tableManager.allocateTable(availTables, LocalDateTime.now(), pax);
		if (tID != null)
			tableManager.assignTable(tID);
		else
			System.out.println("No tables are available at the moment. Please wait.");
	}
	public int reservedDineIn(String name, String contact)
	{
		String key = name + contact + LocalDate.now();
		Reservation res = resManager.getRes(key);
		if(res == null)
		{
			System.out.println("Reservation not found. Try assigning new table...");
			return 0;
		}
		else
		{
			int tableID = res.getTableID();
			LocalDateTime reservedTime = res.getTime();
			if (LocalDateTime.now().isBefore(reservedTime))
			{
				System.out.println("A later reservation at " + reservedTime + " was found");
				return 1;
			}
			else
			{
				tableManager.assignTable(tableID);
				System.out.println("Reservation found: assign table " + tableID);
				resManager.removeRes(key);
				return 2;
			}
		}
	}
	public boolean earlyReservedDineIn(String name, String contact)
	{
			String key = name + contact + LocalDate.now();
			Reservation res = resManager.getRes(key);
			int tableID = res.getTableID();
			boolean result = tableManager.assignTable(tableID);
			if (result)
				resManager.removeRes(key);
			else
				System.out.println("Try assigning new table...");
			return result;
	}
	public void showRes(String key)
	{
		Reservation res = resManager.getRes(key);
		if (res == null)
			System.out.println("Reservation not found!");
		else
			res.toString();
	}
	public void showAllRes()
	{
		resManager.showAllRes();
	}
	public void makeReservation(LocalDateTime dateTime, String name, String contact, int pax)
	{
		ArrayList<Integer> availTables = resManager.checkAvail(LocalDateTime.now());
		Integer tID = tableManager.allocateTable(availTables, LocalDateTime.now(), pax);
		String key = name + contact + dateTime.toLocalDate();
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
			}
			else
				System.out.println("Cannot make reservation for given date and time");
		}
	}
	public void removeReservation(String key)
	{
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
}