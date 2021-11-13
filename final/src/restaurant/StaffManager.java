package restaurant;
import java.util.*;
import java.io.*;

/**
 * Manages methods pertaining to Staff class.
 * @author Jacintha
 * @version 1.1
 * @since 2021-11-12
 */
public class StaffManager implements Serializable{
	/**
	 * HashMap whereby key is the staff's ID and the value is the Staff object
	 */
	private HashMap<Integer, Staff> sMap;
	
	/**
	 * Creates a new HashMap
	 */
	public StaffManager() {
		sMap = new HashMap<Integer, Staff>(); 
	}
	
	/**
	 * Method to add a new staff to this restaurant
	 * @param id The ID of the staff to be added
	 * @param newStaff The Staff object of the staff
	 */
	public void addStaff(int id, Staff newStaff) {
		Staff s = sMap.get(id);
		if (s != null) { // Staff already exists, error
			System.out.println("Staff already exists.");
			s.printStaff();
			System.out.println("Please check if you have made a mistake when entering staff particulars and try again.");
		}
		else { // Staff does not exist, can add
			sMap.put(id, newStaff);
			System.out.println("Successfully added staff " + newStaff.getStaffName() + " ID: " + id + ".");
		}
	}
	
	/**
	 * Method to remove an existing staff from this restaurant
	 * @param id The ID of the staff to be removed
	 */
	public void removeStaff(int id) {
		Staff s = sMap.get(id);
		if (s != null) { // Staff exists in staff roster, can confirm and proceed to remove
			System.out.println("Here are the particulars of the staff you wish to remove: ");
			s.printStaff();
			System.out.println("To proceed with removal of staff, enter 1");
			System.out.println("To cancel removal of staff, enter 2");
			Scanner sc = new Scanner(System.in);
			boolean exit=false;
			while (!exit) {
				int choice;
				try {
					choice = sc.nextInt();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				sc.nextLine();
				if (choice == 1) { // Remove Staff from staff roster
					sMap.remove(id);
					System.out.println("Successfully removed staff.");
					exit=true;
				}
				else if (choice == 2) { // Cancel removal of Staff
					System.out.println("Cancelled removal of staff.");
					exit=true;
				}
				else { // Invalid choice given
					System.out.println("Please enter a valid choice of 1 (proceed with removal of staff) or 2 (cancel removal of staff).");
				}
			}
		}
		else { // Staff does not exist in staff roster, error
			System.out.println("The staff you have chosen to remove does not exist.");
			System.out.println("Please check if you have entered the correct staff ID to remove and try again.");
		}
	}
	
	/**
	 * Check if staff exists in this restaurant
	 * @param staffID The ID of the staff to be checked
	 * @param name The name of the staff to be checked
	 * @return true if the staff exists, and false otherwise
	 */
	public boolean isStaff(int staffID, String name) {
		if (sMap.get(staffID) != null) {
			if (sMap.get(staffID).getStaffName().equals(name)) {
				return true;
			}
			else {
				System.out.println("Staff with staffID " + staffID + " and name " + name + " does not exist.");
				return false;
			}
		}
		else {
			System.out.println("Invalid staffID entered");
			return false;
		}
		
	}
}
