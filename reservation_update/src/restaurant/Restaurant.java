package restaurant;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Restaurant implements Serializable
{	
	private HashMap<Integer, Table> tables;
	private HashMap<String, Reservation> allReservations;
	private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	public Restaurant()
	{
		// put codes to read in serialisation file
		
		tables = new HashMap<Integer, Table>();
		allReservations = new HashMap<String, Reservation>();
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
	}
	public ScheduledFuture<?> scheduleCancel(LocalDateTime scheduleTime, Runnable task)
	{
		long delay = Duration.between(LocalDateTime.now(), scheduleTime).toSeconds();
		return scheduler.schedule(task, delay, TimeUnit.SECONDS);
	}
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
			if (LocalDateTime.now().isBefore(res.getTime())) //early arrival
			{
				System.out.println("A later reservation at " + res.getTime() + " was found");
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
			ScheduledFuture<?> s = scheduleCancel(dateTime.plusMinutes(15), new AutoCancelTask(res));
			res.setSchedule(s);
			t.addReservation(res);
			allReservations.put(name+contact, res);
			
			Notification.sendSMS(res.toString(), contact);
			
			return true;
		}
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
		Restaurant r = new Restaurant();
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
			System.out.println("(7)Close"); 
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
				System.out.println("Program terminating ....");
				r.close();
				break;
			}
			System.out.println(""); 
		} while (choice < 7);
		
	}
}
