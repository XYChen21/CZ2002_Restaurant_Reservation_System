package restaurant;
import java.util.*;
import java.time.*;
import java.util.concurrent.*;
import java.io.*;

/**
 * Controller class for Reservation objects that holds collections of reservations
 * @author Chen Xingyu
 *
 */
public class ReservationManager implements Serializable{
	/**
	 * a collection of all reservations using HashMap. The HashMap stores table id and an ArrayList of reservations at that table
	 */
    private HashMap<Integer, ArrayList<Reservation>> resByTable;
    
    /**
     * a collection of all reservations using HashMap. The HashMap stores reservation key and Reservation object pair.
     * The reservation key is a concatenated string of name + contact number + reservation date of the reservation
     */
    private HashMap<String, Reservation> allReservations;
    
    /**
     * a scheduler to schedule auto remove events to remove reservations upon expiry (15 minutes after reservation time)
     */
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * create a new Reservation Manager with empty collections of reservations
     */
    public ReservationManager()
    {
        allReservations = new HashMap<String, Reservation>();
        resByTable = new HashMap<Integer, ArrayList<Reservation>>();
    }
    
    /**
     * Restore all the auto cancel events. All auto cancel events of reservations are not stored since the delay time passed to scheduler to schedule the event depends on runtime.
     * Hence, this is to be called when the restaurant is restored. It also clears all reservations that expired when the system is closed
     */
    public void restoreAutoRemove()
    {
    	for(String key : allReservations.keySet())
    	{
    		Reservation res = allReservations.get(key);
    		if (res.getTime().isBefore(LocalDateTime.now()))
    		{
    			int tID = res.getTableID();
    	        ArrayList<Reservation> resAtTable = resByTable.get(tID);
    	        resAtTable.remove(res);
    	        allReservations.remove(key);
    		}
    		else
    			setAutoRemove(res);
    	}
    }
    
    /**
     * Initialise a new entry of table ID and an ArrayList to store all reservations made at this table in HashMap resByTable.
     * It is called when a new Table is created
     * @param tableID new table ID
     */
    public void initTableReservation(int tableID)
    {
        resByTable.put(tableID, new ArrayList<Reservation>());
    }
    
    /**
     * Check each table's availability at given time by checking reservations at that table, return an ArrayList of table IDs that can be allocated after checking reservations <br>
     * The condition is there are no reservations are 2 hours before and after the the given time, since we assume 
	 * the table will be vacated 2 hours after the customers start dining <br>
	 * - e.g. if the given time is 5pm, then the condition is satisfied if there are no reservations from 3pm to 7pm 
     * @param time time given to check table's availability
     * @return an ArrayList of available table IDs
     */
    public ArrayList<Integer> checkAvail(LocalDateTime time)
    {
        ArrayList<Integer> availTables = new ArrayList<Integer>();
        ArrayList<Reservation> reservations;
        for(int id:resByTable.keySet())
        {
            reservations = resByTable.get(id);
            if (reservations.size() == 0)
            	availTables.add(id);
            else
            {
            	int i;
            	for (i = 0; i < reservations.size(); i++)
            	    if (time.isBefore(reservations.get(i).getTime())) break;
            	if (i == reservations.size())
            	{
            	    if (time.minusHours(2).isAfter(reservations.get(i-1).getTime()))
            	    	availTables.add(id);
            	}
            	else if (i == 0)
            	{
            	    if (time.plusHours(2).isBefore(reservations.get(i).getTime()))
            	    	availTables.add(id);
            	}
            	else 
            	{
            	    if (time.plusHours(2).isBefore(reservations.get(i).getTime()) 
            	    	&& time.minusHours(2).isAfter(reservations.get(i-1).getTime()))
            	    	availTables.add(id);
            	}
            }
        }
        return availTables;
    }
    
    /**
     * Check whether a reservation with given key exist in the collection
     * @param key reservation key which is a concatenated string of name + contact number + dateTime of the reservation
     * @return return true if the key is present in collections, false otherwise
     */
    public boolean haveRes(String key)
    {
        if (allReservations.get(key) != null)
            return true;
        else
            return false;
    }
    
    /**
     * add a reservation to Reservation Manager's collections
     * @param tableID ID of the table reserved
     * @param key reservation key which is a concatenated string of name + contact number + dateTime of the reservation
     * @param res the Reservation object to be added
     */
    public void addRes(int tableID, String key, Reservation res)
    {
    	ArrayList<Reservation> resAtTable = resByTable.get(tableID);
    	int i;
		for (i = 0; i < resAtTable.size(); i++)
			if (res.compareTo(resAtTable.get(i)) < 0) break;
        resAtTable.add(i, res);
        allReservations.put(key, res);
    }
    
    /**
     * show all reservations made
     */
    public void showAllRes()
	{
    	if (allReservations.isEmpty())
    		System.out.println("No reservations are in the system.");
    	else
    		allReservations.forEach((k,v) -> System.out.println(v.toString()));
	}
    
    /**
     * Get the reservation with given key
     * @param key reservation key which is a concatenated string of name + contact number + dateTime of the reservation
     * @return Reservation object with the given key. Return null if reservation with the given key does not exist
     */
    public Reservation getRes(String key)
	{
		return allReservations.get(key);
	}
    
    /**
     * cancel the auto remove schedule and remove the reservation from reservation manager's collection
     * @param key reservation key which is a concatenated string of name + contact number + dateTime of the reservation
     */
    public void removeRes(String key)
    {
        Reservation res = allReservations.get(key);
        if (res == null)
        {
            System.out.println("Reservation not found!");
            return;
        }
		res.cancelSchedule();
		int tID = res.getTableID();
        ArrayList<Reservation> resAtTable = resByTable.get(tID);
        resAtTable.remove(res);
        allReservations.remove(key);
        System.out.println("Reservation successfully removed!");
    }
    
    /**
     * schedule auto removal of a reservation. This is to be called after each new Reservation object is created. The expiry time is defined to be 15 minutes after reservation timee
     * @param res the newly created Reservation
     */
    public void setAutoRemove(Reservation res)
    {
        LocalDateTime reservationTime = res.getTime();
        Duration timeToExpiry = Duration.between(LocalDateTime.now(), reservationTime.plusMinutes(1));
		long delay = timeToExpiry.toSeconds();
        Runnable task = new AutoCancelTask(res);
		ScheduledFuture<?> s = scheduler.schedule(task, delay, TimeUnit.SECONDS);
		res.setSchedule(s);
    }
    
    /**
     * shut down the auto remove scheduler
     */
    public void close()
    {
        scheduler.shutdownNow();
    }
    
    
    /**
     * Inner class of Reservation Manager which defines a task to remove a reservation upon expiry automatically
     * It will be passed to scheduler to be scheduled to run at the time of expiry to remove that reservation
     * @author Chen Xingyu
     *
     */
    class AutoCancelTask implements Runnable
	{
    	/**
    	 * the reservation to be removed automatically
    	 */
		private Reservation res;
		
		/**
		 * constructor of AutoCancelTask
		 * @param res the reservation to be removed
		 */
		public AutoCancelTask(Reservation res)
		{
			this.res = res;
		}
		
		/**
		 * the task to be executed by the scheduler at given time. It removes the reservation from the Reservation Manager.
		 */
		@Override
		public void run() 
		{
      			String name = res.getName();
			String contact = res.getContact();
			LocalDateTime dateTime = res.getTime();
			LocalDate date = dateTime.toLocalDate();
            		allReservations.remove(name + contact + date);
			int tID = res.getTableID();
            		ArrayList<Reservation> resAtTable = resByTable.get(tID);
            		resAtTable.remove(res);
		}
	}
}
