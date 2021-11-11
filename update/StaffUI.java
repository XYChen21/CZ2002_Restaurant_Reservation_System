package restaurant;
import java.util.Scanner;

public class StaffUI {
	
	private Scanner sc = new Scanner(System.in);
	
	public String scanStaffName() {
		System.out.println("Enter staff's name: ");
		String name = sc.next();
		return name;
	}
	
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
	
	public String scanStaffPos() {
		System.out.println("Enter staff's job position: ");
		String pos = sc.next();
		return pos;
	}
}
