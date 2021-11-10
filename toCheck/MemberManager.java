package restaurant;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
public class MemberManager implements Serializable{
	private HashMap<String, Member> memberMap; // Key: contact; Value: member
	
	public MemberManager(){
		memberMap = new HashMap<String, Member>();
	}
	
	public boolean paymentMembership() {
		Scanner sc = new Scanner(System.in);
		String contactNo;
		String name;
		boolean membership;
		System.out.println("Welcome to payment!");
		
		while (true) {
			System.out.println("Are you a member? (Y/N)");
			char c;
			try {
				c = sc.nextLine().charAt(0);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
			if (c == 'Y' || c == 'y') { // Verify membership to apply membership discount
				while(true){
					System.out.println("We would like to verify your membership. May we have your contact number please?");
					try {
						contactNo = sc.nextLine();
						if (verifyContact(contactNo) == false)
							throw new Exception("Invalid contact number, please try again.");
						break;
					} catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}
				}
				// System.out.println("May we have your name please?");
				// name = sc.next();
				membership = isMember(contactNo);
				if (membership) return true;
				else continue;
			}
			else if (c == 'N' || c == 'n'){ // Customer is not a member -> ask if customer wants to join as a member
				while (true) {
					System.out.println("Would you like to join us as a member? (Y/N)");
					char ans;
					try {
						ans = sc.nextLine().charAt(0);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}
					if (ans == 'Y' || ans == 'y') {
						while (true) {
							System.out.println("May we have your name please?");
							try {
								name = sc.nextLine();
								break;
							} catch (Exception e) {
								System.out.println(e.getMessage());
								continue;
							}
						}
						while (true) {
							System.out.println("May we have your contact number please?");
							try {
								contactNo = sc.nextLine();
								if (verifyContact(contactNo) == false)
									throw new Exception("Invalid contact number, please try again.");
								break;
							} catch (Exception e) {
								System.out.println(e.getMessage());
								continue;
							}
						}
						Member newMember = new Member(contactNo, name);
						membership = addMember(contactNo, newMember);
						if (membership) return true;
						else continue;
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
	
	public boolean addMember(String contact, Member newMember) {
		Member m = memberMap.get(contact);
		if (m != null) { // Member already exists in membership, error
			System.out.println("Member already exists in membership.");
			m.printMember();
			System.out.println("Please check if you have made a mistake when entering member particulars and try again.");
			return false;
		}
		else { // Member does not exist in membership, can add

			memberMap.put(contact, newMember);
			System.out.println("Successfully added " + newMember.getMemberName() + " HP: " + newMember.getMemberHP() + " to membership.");
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
	
	public boolean isMember(String contact) {
		Member m = memberMap.get(contact);
		if (m != null) {
			return true;
//			if (memberArray.get(name) == contact) {return true;}
//			else {
//				System.out.println("Name does not match contact number.");
//				return false;
//			}
		}
		else {
			System.out.println("Member does not exist. Please try again.");
			return false;
		}
	}
	public boolean verifyContact(String contact)
	{
		return contact.matches("^\\+65[0-9]{8}");
	}
	public static void main(String[] args)
	{
		MemberManager m = new MemberManager();
		m.paymentMembership();
	}
}