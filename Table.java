package restaurant;

import java.io.*;
import java.util.*;
import java.time.*;

public class Table implements Comparable<Table>, Serializable
{
	protected ArrayList<Reservation> reservations; // sort by time order
	protected int capacity;
	protected int id;
	protected boolean isAvail; // availability
	protected LocalDateTime nextAvailTime; // this is current time + 2h
	public Table(int id, int capacity)
	{
		this.id = id;
		this.capacity = capacity;
		this.reservations = new ArrayList<Reservation>(); 
		this.isAvail = true;
	}
	public void addReservation(Reservation res)
	{
		int i;
		for (i = 0; i < reservations.size(); i++)
			if (res.dateTime.isBefore(reservations.get(i).dateTime)) break;
		reservations.add(i, res);
	}
	public void removeReservation(Reservation res)
	{
		reservations.remove(res);
	}
	public void assign()
	{
		isAvail = false;
		nextAvailTime = LocalDateTime.now().plusHours(2);
	}
	public void vacate()
	{
		isAvail = true;
		nextAvailTime = null;
	}
	@Override
	public int compareTo(Table t) 
	{
		Integer thisCap = (Integer) this.capacity;
		Integer compCap = (Integer) t.capacity;
		return thisCap.compareTo(compCap);
	}
}
