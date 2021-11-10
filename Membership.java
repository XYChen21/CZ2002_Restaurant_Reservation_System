package restaurant;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
public class Membership implements Serializable{
	private HashMap<String, Member> memberArray; // Key: name+contact; Value: Member object 
	
	public Membership(){
		memberArray = new HashMap<String, Member>();
	}
	
	public boolean paymentMembership() {
		Scanner sc = new Scanner(System.in);
		String contactNo;
		String name;
		boolean membership;
		System.out.println("Welcome to payment!");
		
		while (true) {
			System.out.println("Are you a member? (Y/N)");
			char c = sc.next().charAt(0);
			if (c == 'Y' || c == 'y') { // Verify membership to apply membership discount
				System.out.println("We would like to verify your membership. May we have your contact number please?");
				contactNo = sc.next();
				System.out.println("May we have your name please?");
				name = sc.next();
				membership = isMember(name, contactNo);
				if (membership) {return true;}
				else {
					System.out.println("Invalid name or contact number entered. Please try again.");
					continue;
				}
			}
			else if (c == 'N' || c == 'n'){ // Customer is not a member -> ask if customer wants to join as a member
				System.out.println("Would you like to join us as a member? (Y/N)");
				while (true) {
					char ans = sc.next().charAt(0);
					if (ans == 'Y' || ans == 'y') {
						System.out.println("May we have your contact number please?");
						contactNo = sc.next();
						System.out.println("May we have your name please?");
						name = sc.next();
						membership = addMember(name, contactNo);
						if (membership) {return true;}
						else {
							System.out.println("Enter 'Y' to re-enter your particulars. Enter 'N' to quit.");
							continue;
						}
					}
					else if (ans == 'N' || ans == 'n') {
						System.out.println("We will proceed to payment now.");
						return false;
					}
					else {
						System.out.println("Invalid response given. Please input (Y/N) only.");
						continue;
					}
				}
			}
			else {
				System.out.println("Invalid response given. Please input (Y/N) only.");
				continue;
			}
		}
	} 
	
	public boolean addMember(String name, String contact) {
		Member m = memberArray.get(name+contact);
		if (m != null) { // Member already exists in membership, error
			System.out.println("Member already exists in membership.");
			m.printMember();
			System.out.println("Please check if you have made a mistake when entering member particulars and try again.");
			return false;
		}
		else { // Member does not exist in membership, can add
			m = new Member(contact, name);
			memberArray.put(name+contact, m);
			System.out.println("Successfully added " + m.getMemberName() + " HP: " + m.getMemberHP() + " to membership.");
			return true;
		}
	}
	
//	public void removeMember(Member m) {
//		if (memberArray.get(m) != null) { // Member exists in membership, can proceed to confirm and remove
//			System.out.println("Here are the particulars of the member you wish to remove from membership:");
//			m.printMember();
//			System.out.println("To proceed with removal of membership, enter 1");
//			System.out.println("To cancel removal of membership, enter 2");
//			Scanner scan = new Scanner(System.in);
//			boolean exit=false;
//			while (!exit) {
//				int choice = scan.nextInt();
//				if (choice == 1) { // Remove Member from Membership
//					memberArray.remove(m);
//					System.out.println("Successfully removed member from membership.");
//					exit=true;
//				}
//				else if (choice == 2) { // Cancel removal of membership
//					System.out.println("Cancelled removal of membership.");
//					exit=true;
//				}
//				else { // Invalid choice given
//					System.out.println("Please enter a valid choice of 1 (proceed with removal of membership) or 2 (cancel removal of membership).");
//				}
//			}
//		}
//		else { // Member does not exist in membership, error
//			System.out.println("The member you have chosen to remove from membership does not exist.");
//			m.printMember();
//			System.out.println("Please check if you have chosen the correct member to remove from membership and try again.");
//		}
//	}
	
	public boolean isMember(String name, String contact) {
		Member m = memberArray.get(name+contact);
		if (m != null) {
			return true;
		}
		else {
			System.out.println("Member does not exist. Try again.");
			return false;
		}
	}
}
