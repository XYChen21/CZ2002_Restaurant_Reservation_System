package restaurant;
import java.io.*;
import java.util.HashMap;
import java.util.Random;

class Staff implements java.io.Serializable{
	private String name;
	private char gender;
	private int employeeID;
	private String jobPosition; // fixed some positions then we just take 
	
	public static HashMap<Integer, String> staffArray = new HashMap<Integer, String>(); //key: employeeID ; value: name
	
	public Staff(String n, char g, int e, String pos) {
		name = n;
		gender = g;
		employeeID = e;
		jobPosition = pos;
		if (staffArray.get(employeeID) != null) {
			if (staffArray.get(employeeID) != name) {
				System.out.printf("Wrong employeeID entered, there is an existing staff with employeeID ", employeeID,"\n");
			}
			else{
				System.out.println("Staff " + name + " already exists in staff roster.");
			}
		}
		else {
			staffArray.put(employeeID, name); // Add employee to HashMap
			System.out.println("Succesffuly added " + name + " to staff roster.");
		}
		
	}
	
	// Return name of staff
	public String getStaffName() {return name;}
	
	// Remove current staff
	public void removeStaff(int ID, String n) {
		if (staffArray.get(ID) == n) {
			staffArray.remove(ID);
			System.out.printf("Successfully removed " + n + ", employeeID " + ID + " from staff roster.");
		}
		else if (staffArray.get(ID) == null){
			System.out.printf("No such employeeID ", ID, " exists.\n");
		}
		else if (staffArray.get(ID) != n){
			System.out.println("Wrong employeeID entered. Please re-enter again.");
		}
	}
	
	// Generate random number (employeeID) to decide which staff to serve the customer
	public static String whoToServe() {
		int randomemployeeID = new Random().nextInt(staffArray.size());
		return staffArray.get(randomemployeeID);
	}
	
}
