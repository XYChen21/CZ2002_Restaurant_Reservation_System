package restaurant;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class StaffUI {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static String scanStaffName() {
		String name;
		while(true) {
			System.out.println("Enter staff's name: ");
			try {
				name = sc.nextLine();
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return name;
	}
	
	public static char scanStaffGender() {
		char gender = 'F';
		while (true) {
			try {
				System.out.println("Enter staff's gender (F/M): ");
				gender = sc.nextLine().charAt(0);
				if (gender == 'F' || gender == 'f') {gender = 'F'; break;}
				else if (gender == 'M' || gender == 'm') {gender = 'M'; break;}
				else {
					throw new Exception ("Error: enter gender as either F or M");
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Please enter a character");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
		return gender;
	}
	
	public static int scanStaffID() { // need to check ID against staffArray
		int id = 0;
		while (true) {
			try {
				System.out.println("Enter staff's ID: ");
				id = sc.nextInt();
				if (id < 0) {
					throw new Exception("Error: invalid staff ID");
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer");
				sc.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		return id;
	}
	
	public static String scanStaffPos() {
		System.out.println("Enter staff's job position: ");
		String pos;
		while (true) {
			try {
				pos = sc.nextLine();
				break;
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		return pos;
	}
}
