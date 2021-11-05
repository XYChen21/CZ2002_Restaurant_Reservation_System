package restaurant;
import java.io.*;
import java.util.Random;

class Staff implements java.io.Serializable{
	private String name;
	private char gender;
	private int employeeID;
	private String jobPosition; 
	
	public Staff(String name, char gender, int employeeID, String jobPosition) {
		this.name = name;
		this.gender = gender;
		this.employeeID = employeeID;
		this.jobPosition = jobPosition;
	}
	
	public String getStaffName() {return name;}
	
	public char getStaffGender() {return gender;}
	
	public int getStaffID() {return employeeID;}
	
	public String getStaffPos() {return jobPosition;}
	
	public void printStaff() {
		System.out.println("Particulars of staff: ");
		System.out.println("Staff: " + getStaffName());
		System.out.println("ID: " + getStaffID());
		System.out.println("Gender: " + getStaffGender());
		System.out.println("Position: " + getStaffPos());
	}
}
