package restaurant;

import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Restaurant implements Serializable
{	
	private HashMap<Integer, Table> tables;
	private HashMap<String, Reservation> allReservations;
	private transient ScheduledService scheduler;
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
		scheduler = new ScheduledService();
	}
	
	public void showAllRes()
	{
		allReservations.forEach((k,v) -> System.out.println(v.toString()));
	}
	public Reservation findRes()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Name: ");
		String name = sc.nextLine();
		System.out.print("Contact number: ");
		String contact = sc.nextLine();
		return allReservations.get(name+contact);
	}
	public boolean reservedDineIn()
	{
		Scanner sc = new Scanner(System.in);
		Reservation res = findRes();
		if (res == null)
		{
			System.out.println("Reservation not found. Try assigning new table...");
			return DineIn();
		}
		else if (LocalDateTime.now().isBefore(res.dateTime)) //early arrival
		{
			System.out.println("A later reservation at " + res.dateTime + " was found");
			System.out.print("1.Assign reserved table? OR 2.Assign new table? (1/2) ");
			int option  = sc.nextInt();
			sc.nextLine();
			if (option == 1)	
			{
				if (res.table.isAvail)
				{
					res.table.assign();
					System.out.println("Assign table " + res.table.id);
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
			boolean result = DineIn();
			System.out.print("Remove this reservation?(Yes/No) ");
			if (sc.nextLine() == "Yes")
				removeReservation(res);
			return result;
			
		}
		else // on time or late no more than 15 mins
		{
			res.table.assign();
			System.out.println("Reservation found: assign table " + res.table.id);
			removeReservation(res);
			return true;
		}
	}
	
	public boolean DineIn() 
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("How many people? ");
		int pax = sc.nextInt();
		sc.nextLine();
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
			System.out.println("Assign table " + t.id);
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
		    if (t.capacity >= pax)
		    {
		    	if (t.isAvail || t.nextAvailTime.isBefore(time))
		    	{
		    		if (t.reservations.size() == 0)
		    			availTables.add(t);
		    		else
		    		{
		    			int i;
		    			for (i = 0; i < t.reservations.size(); i++)
		    				if (time.isBefore(t.reservations.get(i).dateTime)) break;
		    			System.out.println("i: "+i+" table res size "+t.reservations.size());
		    			if (i == t.reservations.size())
		    			{
		    				if (time.minusHours(2).isAfter(t.reservations.get(i-1).dateTime))
		    					availTables.add(t);
		    			}
		    			else if (i == 0)
		    			{
		    				if (time.plusHours(2).isBefore(t.reservations.get(i).dateTime))
		    					availTables.add(t);
		    			}
		    			else 
		    			{
		    				if (time.plusHours(2).isBefore(t.reservations.get(i).dateTime) 
		    					&& time.minusHours(2).isAfter(t.reservations.get(i-1).dateTime))
		    					availTables.add(t);
		    			}
		    		}
		    	}
		    }
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
		System.out.print("How many peopole? ");
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
			System.out.print("Name: ");
			String name = sc.nextLine();
			System.out.print("Contact number(+65xxxxxxxx): ");
			String contact = sc.nextLine();
			Reservation res = new Reservation(dateTime, name, contact, pax, t);
			res.autoCancel = scheduler.schedule(dateTime.plusMinutes(15), new AutoCancelTask(res, allReservations));
			t.addReservation(res);
			allReservations.put(name+contact, res);
			
//			Notification.sendSMS(res.toString(), contact);
			
			return true;
		}
	}
	public boolean removeReservation(Reservation res)
	{
		if (res == null) return false;
		res.cancelSchedule();
		res.table.removeReservation(res);
		allReservations.remove(res.name+res.contact);
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
		scheduler.executor.shutdownNow();
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
				r.DineIn();
				break;
			case 2: 
				r.reservedDineIn();
				break;
			case 3: 
				r.makeReservation();
				break;
			case 4: 
				res = r.findRes();
				r.removeReservation(res);
				break;
			case 5: 
				res = r.findRes();
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
