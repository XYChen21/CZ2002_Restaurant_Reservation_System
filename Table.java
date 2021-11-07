package restaurant;

import java.io.*;
import java.util.*;
import java.time.*;

public class Table implements Comparable<Table>, Serializable
{
	private ArrayList<Reservation> reservations; // sort by time order
	private int capacity;
	private int id;
	private boolean isAvail; // availability
	private LocalDateTime nextAvailTime; // this is current time + 2h
	public Table(int id, int capacity)
	{
		this.id = id;
		this.capacity = capacity;
		this.reservations = new ArrayList<Reservation>(); 
		this.isAvail = true;
	}
	public boolean getAvail()
	{
		return isAvail;
	}
	public int getID()
	{
		return id;
	}
	public boolean checkAvail(LocalDateTime time, int pax)
	{
		if (capacity >= pax)
		{
		    if (isAvail || nextAvailTime.isBefore(time))
		    {
		    	if (reservations.size() == 0)
		    		return true;
		    	else
		    	{
		    		int i;
		    		for (i = 0; i < reservations.size(); i++)
		    			if (time.isBefore(reservations.get(i).getTime())) break;
		    		// System.out.println("i: "+i+" table res size "+t.reservations.size());
		    		if (i == reservations.size())
		    		{
		    			if (time.minusHours(2).isAfter(reservations.get(i-1).getTime()))
		    				return true;
		    		}
		    		else if (i == 0)
		    		{
		    			if (time.plusHours(2).isBefore(reservations.get(i).getTime()))
		    				return true;
		    		}
		    		else 
		    		{
		    			if (time.plusHours(2).isBefore(reservations.get(i).getTime()) 
		    				&& time.minusHours(2).isAfter(reservations.get(i-1).getTime()))
		    				return true;
		    		}
		    	}
		    }
		}
		return false;
	}
	public void addReservation(Reservation res)
	{
		int i;
		for (i = 0; i < reservations.size(); i++)
			if (res.compareTo(reservations.get(i)) < 0) break;
//			if (res.dateTime.isBefore(reservations.get(i).dateTime)) break;
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