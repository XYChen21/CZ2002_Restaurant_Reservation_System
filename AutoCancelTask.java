package restaurant;
import java.util.HashMap;
public class AutoCancelTask implements Runnable 
{
	private Reservation res;
	private HashMap<String, Reservation> allReservations;
	public AutoCancelTask(Reservation res, HashMap<String, Reservation> allReservations)
	{
		this.res = res;
		this.allReservations = allReservations;
	}
	@Override
	public void run() 
	{
		try 
		{
			allReservations.remove(res.name+res.contact);
			res.table.removeReservation(res);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
