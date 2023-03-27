package restaurant;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Boundary class to scan user input needed for TableManager to process and to create new Table
 * @author Chen Xingyu
 *
 */
public class TableUI {
	/**
	 * scanner to scan user's input
	 */
    private static Scanner sc = new Scanner(System.in);
    
    /**
     * scan the table ID provided by user
     * @return table ID
     */
    public static int scanID()
    {
        int id;
        while (true){
        	System.out.println("Enter id for the table: ");
            try {
                id = sc.nextInt();
                break;
            } catch(InputMismatchException e) {
            	System.out.println("please enter a integer");
                sc.nextLine();
            }
        }
        return id;
    }
    /**
     * scan table capacity by user
     * @return table capacity
     */
    public static int scanCapacity()
    {
        int cap;
        while (true){
        	System.out.println("Enter capacity of the table: ");
            try {
                cap = sc.nextInt();
                if (cap <= 0)
                    throw new Exception("Capacity should be larger than 0");
                break;
            } catch(InputMismatchException e) {
            	System.out.println("please enter a integer");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        return cap;
    }
    /**
     * scan number of people at table (pax) provided by user 
     * @return number of people at table
     */
    public static int scanPax()
    {
        int pax;
        while(true)
        {
        	System.out.print("Enter the number of people: ");
            try {
                pax = sc.nextInt();
                if (pax <= 0)
                    throw new Exception("pax should be a positive integer.");
                break;
            } catch(InputMismatchException e) {
            	System.out.println("please enter a integer");
            	sc.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        return pax;
    }
}
