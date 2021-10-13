package restaurant;

import java.io.*;
import java.util.concurrent.ScheduledFuture;
import java.time.LocalDateTime;

public class Reservation implements Comparable<Reservation>, Serializable
{
	protected int pax;
	protected String name;
	protected String contact;
	protected LocalDateTime dateTime;
	protected Table table;
	protected transient ScheduledFuture<?> autoCancel;  // auto cancel the reservation after 15mins
	// this will not be saved because depends on runtime, 
	// and will be null when bring back. need to reassign the schedule
	// assume duration for each reservation is 2h
	public Reservation(LocalDateTime dateTime, String name, String contact, int pax, Table table)
	{
		this.dateTime = dateTime;
		this.name = name;
		this.contact = contact;
		this.pax = pax;
		this.table = table;
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
		s.append("\nTable: " + table.id);
		return s.toString();
	}
	@Override
	public int compareTo(Reservation r) 
	{
		return this.dateTime.compareTo(r.dateTime);
	}

}
