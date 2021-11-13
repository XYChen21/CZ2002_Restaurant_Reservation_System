package restaurant;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Boundary class to scan user input needed for ReservationManager to process and to create new Reservation
 * @author Chen Xingyu
 *
 */
public class ReservationUI 
{
	/**
	 * scanner to scan user's input
	 */
    private static Scanner sc = new Scanner(System.in);
    
    /**
     * scan the reservation name provided by the user
     * @return reservation name
     */
    public static String scanName()
    {
       
        String name;
        while(true)
        {
        	 System.out.print("Enter name: ");
            try {
                name = sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return name;
    }
    
    /**
     * scan the reservation time provided by the user
     * @return reservation date and time as a LocalDateTime object
     */
    public static LocalDateTime scanTime()
    {
        LocalDateTime dateTime;
        while (true)
        {
        	System.out.print("Enter data and time of reservation(dd/MM/yyyy HH:mm): ");
            try {
                String strDateTime = sc.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                dateTime = LocalDateTime.parse(strDateTime, formatter);
                if (dateTime.isBefore(LocalDateTime.now()))
                    throw new Exception("Reservation time cannot be earlier than current time.");
                break;
            } 
            catch(DateTimeParseException e) {
            	System.out.println("Please enter the correct format for date time (dd/MM/yyyy HH:mm)");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return dateTime;
    }
    
    /**
     * scan the number of people dining provided by the user
     * @return number of peeople dining
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
        sc.nextLine();
        return pax;
    }
    
    /**
     * scan the contact number provided by the user
     * @return user's contact number as a string in format +65xxxxxxxx
     */
    public static String scanContact()
    {
        String contact;
        while (true)
        {
        	System.out.print("Enter contact number(+65xxxxxxxx): ");
            try {
                contact = sc.nextLine();
                if (contact.matches("^\\+65[0-9]{8}") == false)
                    throw new Exception("Please enter correct format of contact number (eg +6512345678)");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return contact;
    }
    
    /**
     * scan the choice from user about whether they want to assign reserved table of assign a new table when customers arrive before the reservation time
     * @return user's choice of assigning reserved table or new table when customers arrive early
     */
    public static int scanEarlyArrivalChoice()
    {
        int choice;
        System.out.print("1.Assign reserved table? OR 2.Assign new table? (1/2) ");
        while(true)
        {
            try {
                choice = sc.nextInt();
                if (choice != 1 && choice != 2)
                    throw new Exception("Invalid choice: please input 1 or 2 only.");
                break;
            } catch(InputMismatchException e) {
            	System.out.println("please enter a integer");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        return choice;
    }
}