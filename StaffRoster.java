package restaurant;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class StaffRoster implements java.io.Serializable{
	private HashMap<Staff, String> staffArray;
	
	public StaffRoster() {
		staffArray = new HashMap<Staff, String>(); //Key: Staff object; Value: Staff name
	}
	
	public void addStaff(Staff s) {
		if (staffArray.get(s) != null) { // Staff already exists in staff roster, error
			System.out.println("Staff already exists in staff roster.");
			s.printStaff();
			System.out.println("Please check if you have made a mistake when entering staff particulars and try again.");
		}
		else { // Staff does not exist in staff roster, can add
			staffArray.put(s, s.getStaffName());
			System.out.println("Successfully added " + s.getStaffName() + " ID: " + s.getStaffID() + " to staff roster.");
		}
	}
	
	public void removeStaff(Staff s) {
		if (staffArray.get(s) != null) { // Staff exists in staff roster, can confirm and proceed to remove
			System.out.println("Here are the particulars of the staff you wish to remove from the staff roster: ");
			s.printStaff();
			System.out.println("To proceed with removal of staff, enter 1");
			System.out.println("To cancel removal of staff, enter 2");
			Scanner sc = new Scanner(System.in);
			boolean exit=false;
			while (!exit) {
				int choice = sc.nextInt();
				if (choice == 1) { // Remove Staff from staff roster
					staffArray.remove(s);
					System.out.println("Successfully removed staff from staff roster.");
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
			System.out.println("The staff you have chosen to remove from the staff roster does not exist.");
			s.printStaff();
			System.out.println("Please check if you have chosen the correct staff to remove from the staff roster and try again.");
		}
	}
	
	// Randomly select a staff to serve the table, returning the staff's name
	public String whoToServe() {
		ArrayList<Staff> randEmpArray = new ArrayList<Staff>();
		for (Staff s: staffArray.keySet()) {
			randEmpArray.add(s);
		}
		int randEmpIndex = new Random().nextInt(randEmpArray.size());
		Staff randEmp = randEmpArray.get(randEmpIndex);
		randEmpArray.clear();
		return staffArray.get(randEmp);
	}
}
