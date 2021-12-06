package restaurant;
import java.io.Serializable;

/**
 * Represents a staff of this restaurant.
 * @author Jacintha Wee
 * @version 1.0
 * @since 2021-11-12
 */
public class Staff implements Serializable{
	/**
	 * The name of this staff
	 */
	private String name;
	/**
	 * The gender of this staff
	 */
	private char gender;
	/**
	 * The employee ID of this staff
	 */
	private int employeeID;
	/**
	 * The job position of this staff
	 */
	private String jobPosition; 
	
	/**
	 * Creates a new Staff with his/her particulars
	 * @param name The name of this staff
	 * @param gender The gender of this staff
	 * @param employeeID The employee ID of this staff
	 * @param jobPosition The job position of this staff
	 */
	public Staff(String name, char gender, int employeeID, String jobPosition) {
		this.name = name;
		this.gender = gender;
		this.employeeID = employeeID;
		this.jobPosition = jobPosition;
	}
	
	/**
	 * Get the name of this staff
	 * @return the name of this staff
	 */
	public String getStaffName() {return name;}
	
	/**
	 * Get the gender of this staff
	 * @return the gender of this staff
	 */
	public char getStaffGender() {return gender;}
	
	/**
	 * Get the ID of this staff
	 * @return the employeeID of this staff
	 */
	public int getStaffID() {return employeeID;}
	
	/**
	 * Get the job position of this staff
	 * @return the job position of this staff
	 */
	public String getStaffPos() {return jobPosition;}
	
	/**
	 * Print the particulars of this staff
	 */
	public void printStaff() {
		System.out.println("Particulars of staff: ");
		System.out.println("Staff: " + getStaffName());
		System.out.println("ID: " + getStaffID());
		System.out.println("Gender: " + getStaffGender());
		System.out.println("Position: " + getStaffPos());
	}
}
