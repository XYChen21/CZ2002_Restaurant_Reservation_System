package restaurant;
import java.util.Scanner;
import java.io.*;

/**
 * Interface to input information regarding staff of this restaurant.
 * @author Jacintha
 * @version 1.0
 * @since 2021-11-12
 */
public class StaffUI{
	
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * Input the name of this staff
	 * @return the name of this staff
	 */
	public String scanStaffName() {
		System.out.println("Enter staff's name: ");
		String name = sc.next();
		return name;
	}
	
	/**
	 * Input the gender of this staff
	 * @return the gender of this staff
	 */
	public char scanStaffGender() {
		char gender = 'F';
		while (true) {
			try {
				System.out.println("Enter staff's gender (F/M): ");
				gender = sc.next().charAt(0);
				if (gender == 'F' || gender == 'f') {gender = 'F'; break;}
				else if (gender == 'M' || gender == 'm') {gender = 'M'; break;}
				else {
					throw new Exception ("Error: enter gender as either F or M");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		return gender;
	}
	
	/**
	 * Input the ID of this staff
	 * @return the employeeID of this staff
	 */
	public int scanStaffID() { // need to check ID against staffArray
		int id = 0;
		while (true) {
			try {
				System.out.println("Enter staff's ID: ");
				id = sc.nextInt();
				if (id < 0) {
					throw new Exception("Error: invalid staff ID");
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return id;
	}
	
	/**
	 * Input the job position of this staff
	 * @return the job position of this staff
	 */
	public String scanStaffPos() {
		System.out.println("Enter staff's job position: ");
		String pos = sc.next();
		return pos;
	}
}
