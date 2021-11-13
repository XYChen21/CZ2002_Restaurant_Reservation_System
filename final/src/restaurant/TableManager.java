package restaurant;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class TableManager implements Serializable {
    private HashMap<Integer, Table> tables;
    private TableUI tableUI;
    public TableManager()
    {
        tables = new HashMap<Integer, Table>();
        tableUI = new TableUI();
    }
    public void add(int id, Table t)
    {
        tables.put(id, t);
    }
    public void remove(int id)
    {
        tables.remove(id);
    }
    public boolean haveID(int id)
    {
        if (tables.get(id) != null)
            return true;
        else
            return false;
    }
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
    public void vacateTable(int id)
    {
    	Table t = tables.get(id);
    	t.vacate();
    }
    public void listAllAvail()
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
    public boolean checkTableStatus(int id)
    {
    	Table t = tables.get(id);
    	return t.getAvail(); // false if table is occupied
    }
}
