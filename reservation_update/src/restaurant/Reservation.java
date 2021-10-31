package restaurant;

import java.io.*;
import java.util.concurrent.ScheduledFuture;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation implements Comparable<Reservation>, Serializable
{
	private int pax;
	private String name;
	private String contact;
	// protected LocalDateTime dateTime;
	private String dateTime;
	// protected Table table;
	private int tableID;
	private transient ScheduledFuture<?> autoCancel;  // auto cancel the reservation after 15mins
	// this will not be saved because depends on runtime, 
	// and will be null when bring back. need to reassign the schedule
	// assume duration for each reservation is 2h
	public Reservation(String dateTime, String name, String contact, int pax, int tableID)
	{
		this.dateTime = dateTime;
		this.name = name;
		this.contact = contact;
		this.pax = pax;
		this.tableID = tableID;
	}
	public String getName()
	{
		return name;
	}
	public String getContact()
	{
		return contact;
	}
	public LocalDateTime getTime()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return LocalDateTime.parse(dateTime, formatter);
	}
	public int getTableID()
	{
		return tableID;
	}
	public void setSchedule(ScheduledFuture<?> s)
	{
		autoCancel = s;
	}
	public void cancelSchedule()
	{
		autoCancel.cancel(true);
	}
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
	@Override
	public int compareTo(Reservation r) 
	{
		return this.dateTime.compareTo(r.dateTime);
	}

}
