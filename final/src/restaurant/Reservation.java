package restaurant;

import java.io.*;
import java.util.concurrent.ScheduledFuture;
import java.time.LocalDateTime;

/**
 * 
 * @author Chen Xingyu
 *
 */
public class Reservation implements Comparable<Reservation>, Serializable
{
	/**
	 * number of people dining
	 */
	private int pax;
	
	/**
	 * name of the person making reservation
	 */
	private String name;
	
	/**
	 * contact number of the person making reservation
	 */
	private String contact;
	
	/**
	 * date and time of the reservation
	 */
	private LocalDateTime dateTime;
	
	/**
	 * ID of the table reserved
	 */
	private int tableID;
	
	/**
	 * The delayed result-bearing action returned from ScheduledExecutorService after scheduling the task to auto cancel this reservation
	 */
	private transient ScheduledFuture<?> autoCancel;
	
	/**
	 * Create a new reservation with date and time, person's name, contact number, number of people dining and reserved table ID
	 * @param dateTime reservation date and time
	 * @param name name of person making reservation
	 * @param contact contact number of person making reservation
	 * @param pax number of people dining
	 * @param tableID ID of tht table reserved
	 */
	public Reservation(LocalDateTime dateTime, String name, String contact, int pax, int tableID)
	{
		this.dateTime = dateTime;
		this.name = name;
		this.contact = contact;
		this.pax = pax;
		this.tableID = tableID;
	}
	
	/**
	 * return name of the person who made the reservation
	 * @return person's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * return contact number of the person who made the reservation
	 * @return person's contact number
	 */
	public String getContact()
	{
		return contact;
	}
	
	/**
	 * return number of people dining recorded in the reservation
	 * @return number of people dining
	 */
    public int getPax()
    {
        return pax;
    }
    
    /**
     * return the date and time of the reservation
     * @return LocalDateTime object indicating the date and time of the reservation
     */
	public LocalDateTime getTime()
	{
		return dateTime;
	}
	
	/**
	 * return ID of the table being reserved
	 * @return ID of the table reserved
	 */
	public int getTableID()
	{
		return tableID;
	}
    
	/**
	 * set schedule to remove the reservation 15 minutes after the reservation time (expiry)
	 * @param s The delayed result-bearing action returned from ScheduledExecutorService after scheduling the task to auto cancel reservation
	 */
	public void setSchedule(ScheduledFuture<?> s)
	{
		autoCancel = s;
	}
	
	/**
	 * cancel the schedule to remove the reservation 15 minutes after the reservation time (expiry)
	 */
	public void cancelSchedule()
	{
		autoCancel.cancel(true);
	}
	
	/**
	 * construct a string showing information of the reservation
	 */
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("name: " + name);
		s.append("\npax: " + pax);
		s.append("\ncontact number: " + contact);
		s.append("\nreservation time: " + dateTime);
		s.append("\nTable: " + tableID);
		return s.toString();
	}
	
	/**
	 * compare the date and time of reservations
	 */
	@Override
	public int compareTo(Reservation r) 
	{
		return this.dateTime.compareTo(r.dateTime);
	}
	
	/**
	 * construct a message to be sent to customers after they successfully made a reservation
	 * @return message to be sent
	 */
	public String toStringCust()
	{
		return "Dear " + name + ",\nYou made a reservation on " + dateTime + " for " + pax + " people. \nThank you and we look forward to seeing you soon!";
	}
}
