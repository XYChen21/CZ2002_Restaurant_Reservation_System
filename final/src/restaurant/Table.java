package restaurant;

import java.io.*;
import java.time.*;
/**
 * Represents a table in the restaurant.
 * @author Chen Xingyu
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
	 * @return return true if the table is available, false if the table is occupied by customers
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
	
	/**
	 * check whether the table can be assigned to the given number of customers for dining in or making reservations based on two conditions <br>
	 * <li> the capacity of the table is larger than the number of people dining </li>
	 * <li> no customers are using the table now or the table is expected to be vacated before the given time < li>
	 * @param time the given time to check availability
	 * @param pax number of people dining
	 * @return return true if both conditions are satisfied, false otherwise
	 */
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
	 * assign a table for customers to dine in
	 */
	public void assign()
	{
		isAvail = false;
		nextAvailTime = LocalDateTime.now().plusHours(2);
	}
	
	/**
	 * vacate the table when the customers have checked out
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
