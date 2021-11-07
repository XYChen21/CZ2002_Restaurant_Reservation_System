package restaurant;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class Membership implements java.io.Serializable{
	private HashMap<Member, Integer> memberArray; // Key: Member object; Value: memberHP 
	
	public Membership(){
		memberArray = new HashMap<Member, Integer>();
	}
	
	public boolean paymentMembership() {
		Scanner sc = new Scanner(System.in);
		int contactNo;
		String name;
		boolean membership;
		System.out.println("Welcome to payment!");
		while (true) {
			System.out.println("Are you a member? (Y/N)");
			char c = sc.next().charAt(0);
			if (c == 'Y' || c == 'y') { // Verify membership to apply membership discount
				System.out.println("We would like to verify your membership. May we have your contact number please?");
				contactNo = sc.nextInt();
				System.out.println("May we have your name please?");
				name = sc.next();
				Member m = new Member(contactNo, name);
				membership = isMember(m);
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
						contactNo = sc.nextInt();
						System.out.println("May we have your name please?");
						name = sc.next();
						Member toAdd = new Member(contactNo, name);
						membership = addMember(toAdd);
						if (membership) {return true;}
						else {
							System.out.println("Enter 'Y' to re-enter your particulars. Enter 'N' to quit.");
							ans = sc.next().charAt(0);
							if (ans == 'Y' || ans == 'y') {continue;}
							else {return false;}
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
			
		}
	} 
	
	public boolean addMember(Member m) {
		if (memberArray.get(m) != null) { // Member already exists in membership, error
			System.out.println("Member already exists in membership.");
			m.printMember();
			System.out.println("Please check if you have made a mistake when entering member particulars and try again.");
			return false;
		}
		else { // Member does not exist in membership, can add
			memberArray.put(m, m.getMemberHP());
			System.out.println("Successfully added " + m.getMemberName() + " HP: " + m.getMemberHP() + " to membership.");
			return true;
		}
	}
	
	public void removeMember(Member m) {
		if (memberArray.get(m) != null) { // Member exists in membership, can proceed to confirm and remove
			System.out.println("Here are the particulars of the member you wish to remove from membership:");
			m.printMember();
			System.out.println("To proceed with removal of membership, enter 1");
			System.out.println("To cancel removal of membership, enter 2");
			Scanner scan = new Scanner(System.in);
			boolean exit=false;
			while (!exit) {
				int choice = scan.nextInt();
				if (choice == 1) { // Remove Member from Membership
					memberArray.remove(m);
					System.out.println("Successfully removed member from membership.");
					exit=true;
				}
				else if (choice == 2) { // Cancel removal of membership
					System.out.println("Cancelled removal of membership.");
					exit=true;
				}
				else { // Invalid choice given
					System.out.println("Please enter a valid choice of 1 (proceed with removal of membership) or 2 (cancel removal of membership).");
				}
			}
		}
		else { // Member does not exist in membership, error
			System.out.println("The member you have chosen to remove from membership does not exist.");
			m.printMember();
			System.out.println("Please check if you have chosen the correct member to remove from membership and try again.");
		}
	}
	
	public boolean isMember(Member m) {
		if (memberArray.get(m) != null) {
			return true;
		}
		return false;
	}
}
