package restaurant;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

/**
 * Controller class for Table objects that holds a collection of tables by table ID
 * @author Chen Xingyu
 *
 */
public class TableManager implements Serializable{
	/**
	 * a collection of all tables using HashMap. The HashMap stores table id and table object pairs
	 */
    private HashMap<Integer, Table> tables;
    
    /**
     * create a new Table Manager with an empty collection of tables
     */
    public TableManager()
    {
        tables = new HashMap<Integer, Table>();
    }
    
    /**
     * add a table to table manager's collection
     * @param id table ID
     * @param t the Table object to be added
     */
    public void add(int id, Table t)
    {
        tables.put(id, t);
    }
    
    /**
     * remove a table from table manager's collection
     * @param id table ID
     */
    public void remove(int id)
    {
        tables.remove(id);
    }
    
    /**
     * check whether the ID provided is present in the collection of tables
     * @param id table ID
     * @return return true if the ID is inside the collection of tables, false otherwise
     */
    public boolean haveID(int id)
    {
        if (tables.get(id) != null)
            return true;
        else
            return false;
    }
    
    /**
     * find a table to allocate for dining in or making reservations
     * @param availTables an ArrayList of table IDs that are available after being checked by ReservationManager about the reservations it has
     * @param time Table Manager will check each table's availability at given time
     * @param pax number of people dining
     * @return return ID of the table being allocated, return null if no tables are available at the given time
     */
    public Integer allocateTable(ArrayList<Integer> availTables, LocalDateTime time, int pax)
    {
        TreeSet<Table> canAssign = new TreeSet<Table>();
        for(int id : availTables)
        {
            Table t = tables.get(id);
            if (t.checkAvail(time, pax))
                canAssign.add(t);
        }
        if (canAssign.isEmpty())
            return null;
        else
        {
            Table allocT = canAssign.first();
            return allocT.getID();
        }
    }
    
    /**
     * Assign the table with given ID by calling Table's assign() method if the table is available. Return false if the table is occupied now
     * @param id table ID
     * @return return true if the table is available and is successfully assigned, false if the table is occupied
     */
    public boolean assignTable(int id)
    {
        Table t = tables.get(id);
        if (t.getAvail())
        {
            t.assign();
            System.out.println("Assign table " + id);
            return true;
        }
        else
        {
            System.out.println("Table is occupied now.");
            return false;
        }
    }
    
    /**
     * vacate the table after the customers have checked out by calling Table's vacate() method
     * @param id table ID
     */
    public void vacateTable(int id)
    {
    	Table t = tables.get(id);
    	t.vacate();
    }
    
    /**
     * show status of all tables at the moment
     */
    public void listAllStatus()
    {
        for (Integer id : tables.keySet())
		{
			Table t = tables.get(id);
			if (t.getAvail())
				System.out.println("Table ID " + t.getID() + " is available");
			else
				System.out.println("Table ID " + t.getID() + " is occupied");
		}
    }
    
    /**
     * check whether the table with given id is available
     * @param id table ID
     * @return return false if the table is occupied at the moment, true if the table is available
     */
    public boolean checkTableStatus(int id)
    {
    	Table t = tables.get(id);
    	return t.getAvail(); // false if table is occupied
    }
}
