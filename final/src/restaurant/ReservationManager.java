package restaurant;
import java.util.*;
import java.time.*;
import java.util.concurrent.*;
import java.io.*;
public class ReservationManager implements Serializable{
    private HashMap<Integer, ArrayList<Reservation>> resByTable;
    private HashMap<String, Reservation> allReservations;
    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ReservationManager()
    {
        allReservations = new HashMap<String, Reservation>();
        resByTable = new HashMap<Integer, ArrayList<Reservation>>();
    }
    public void initTableReservation(int tableID)
    {
        resByTable.put(tableID, new ArrayList<Reservation>());
    }
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
    public boolean haveRes(String key)
    {
        if (allReservations.get(key) != null)
            return true;
        else
            return false;
    }
    public void addRes(int tableID, String key, Reservation res)
    {
        resByTable.get(tableID).add(res);
        allReservations.put(key, res);
    }
    public void showAllRes()
	{
		allReservations.forEach((k,v) -> System.out.println(v.toString()));
	}
    public Reservation getRes(String key)
	{
		return allReservations.get(key);
	}
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
    public void setAutoRemove(Reservation res)
    {
        LocalDateTime reservationTime = res.getTime();
		long delay = Duration.between(LocalDateTime.now(), reservationTime.plusMinutes(1)).toSeconds();
        Runnable task = new AutoCancelTask(res);
		ScheduledFuture<?> s = scheduler.schedule(task, delay, TimeUnit.SECONDS);
		res.setSchedule(s);
    }
    public void close()
    {
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
                String key = res.getName() + res.getContact() + res.getTime().toLocalDate();
				int tID = res.getTableID();
                ArrayList<Reservation> resAtTable = resByTable.get(tID);
                resAtTable.remove(res);
                allReservations.remove(key);
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
