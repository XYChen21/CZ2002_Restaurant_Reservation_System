package restaurant;
import java.util.Scanner;
public class TableUI {
    Scanner sc = new Scanner(System.in);
    public int scanID()
    {
        int id;
        while (true){
        	System.out.println("Enter id for the table: ");
            try {
                id = sc.nextInt();
                sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return id;
    }
    public int scanCapacity()
    {
        int cap;
        while (true){
        	System.out.println("Enter capacity of the table: ");
            try {
                cap = sc.nextInt();
                sc.nextLine();
                if (cap <= 0)
                    throw new Exception("Capacity should be larger than 0");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return cap;
    }
    public int scanPax()
    {
        int pax;
        while(true)
        {
        	System.out.print("Enter the number of people: ");
            try {
                pax = sc.nextInt();
                sc.nextLine();
                if (pax <= 0)
                    throw new Exception("pax should be a positive integer.");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return pax;
    }
}
