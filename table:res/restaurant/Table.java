package restaurant;

import java.io.*;
import java.time.*;
/**
 * Represents a table in the restaurant.
 * @author Chen Xingyu
 * @version 1.0
 * @since 2021-11-5
 *
 */
public class Table implements Comparable<Table>, Serializable
{
	/**
	 * capacity of the table
	 */
	private int capacity;
	
	/**
	 * id for the table
	 */
	private int id;
	
	/**
	 * indication of whether there are customers dining at the table
	 */
	private boolean isAvail; 
	
	/**
	 * next available time for the table after it is assigned, assuming the table will be vacated 2 hours later
	 */
	private LocalDateTime nextAvailTime;
	
	/**
	 * create a new table with given id and capacity
	 * @param id id of the table
	 * @param capacity capacity of the table
	 */
	public Table(int id, int capacity)
	{
		this.id = id;
		this.capacity = capacity;
		this.isAvail = true;
	}
	
	/**
	 * get whether there are customers dining at the table
	 * @return boolean indicating whether there are customers dining at the table
	 */
	public boolean getAvail()
	{
		return isAvail;
	}
	
	/**
	 * get the id of the table
	 * @return id of the table
	 */
	public int getID()
	{
		return id;
	}
	public boolean checkAvail(LocalDateTime time, int pax)
    {
        if (capacity >= pax)
		{
		    if (isAvail || nextAvailTime.isBefore(time))
                return true;
        }
        return false;
    }
	/**
	 * check whether the table can be assigned to the given number of customers for dining in or making reservations <br>
	 * three conditions are checked: <br>
	 * 1. the capacity of the table is larger than the number of people dining <br>
	 * 2. no customers are using the table now or the table is expected to be vacated before the given time <br>
	 * 3. no reservations are 2 hours before and after the the given time
	 * duration between the given time and the closest reservations at the table is longer than 2 hours, since we assume 
	 * the table will be vacated 2 hours after the customers start dining <br>
	 * e.g. if the given time is 5pm, then the third condition is satisfied if there are no reservations from 3pm to 7pm <br>
	 * @param time the given time to check availability
	 * @param pax number of people dining
	 * @return availability of the table at given time and number of customers dining
	 */
	// public boolean checkAvail(LocalDateTime time, int pax)
	// {
	// 	if (capacity >= pax)
	// 	{
	// 	    if (isAvail || nextAvailTime.isBefore(time))
	// 	    {
	// 	    	if (reservations.size() == 0)
	// 	    		return true;
	// 	    	else
	// 	    	{
	// 	    		int i;
	// 	    		for (i = 0; i < reservations.size(); i++)
	// 	    			if (time.isBefore(reservations.get(i).getTime())) break;
	// 	    		if (i == reservations.size())
	// 	    		{
	// 	    			if (time.minusHours(2).isAfter(reservations.get(i-1).getTime()))
	// 	    				return true;
	// 	    		}
	// 	    		else if (i == 0)
	// 	    		{
	// 	    			if (time.plusHours(2).isBefore(reservations.get(i).getTime()))
	// 	    				return true;
	// 	    		}
	// 	    		else 
	// 	    		{
	// 	    			if (time.plusHours(2).isBefore(reservations.get(i).getTime()) 
	// 	    				&& time.minusHours(2).isAfter(reservations.get(i-1).getTime()))
	// 	    				return true;
	// 	    		}
	// 	    	}
	// 	    }
	// 	}
	// 	return false;
	// }
	
// 	/**
// 	 * add reservation to the table
// 	 * @param res the reservation object to add to table
// 	 */
// 	public void addReservation(Reservation res)
// 	{
// 		int i;
// 		for (i = 0; i < reservations.size(); i++)
// 			if (res.compareTo(reservations.get(i)) < 0) break;
// //			if (res.dateTime.isBefore(reservations.get(i).dateTime)) break;
// 		reservations.add(i, res);
// 	}
	
// 	/**
// 	 * remove reservation from the table
// 	 * @param res the reservation object to remove from table
// 	 */
// 	public void removeReservation(Reservation res)
// 	{
// 		reservations.remove(res);
// 	}
	
	/**
	 * assign a table for customers to dine in
	 */
	public void assign()
	{
		isAvail = false;
		nextAvailTime = LocalDateTime.now().plusHours(2);
	}
	
	/**
	 * vacate the table when the customers have checked out, turning isAvail to true and next available time to null
	 */
	public void vacate()
	{
		isAvail = true;
		nextAvailTime = null;
	}
	/**
	 * compare the capacity of tables
	 */
	@Override
	public int compareTo(Table t) 
	{
		Integer thisCap = (Integer) this.capacity;
		Integer compCap = (Integer) t.capacity;
		return thisCap.compareTo(compCap);
	}
}
