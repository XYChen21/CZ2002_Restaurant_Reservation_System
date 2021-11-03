package restaurant;
import java.util.HashMap;
import java.util.Scanner;

public class Membership {
	public static HashMap<Integer, String> memberArray; // key: contact no; value: name 
	
	public Membership(){
		memberArray = new HashMap<Integer, String>();
	}
	
	public static boolean paymentMembership() {
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
				membership = isMember(contactNo, name);
				if (membership) {return true;}
				else {
					System.out.println("Invalid name or contact number entered. Please re-enter.");
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
						membership = addMember(contactNo, name);
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
	
	public static boolean addMember(int contactNo, String name) {
		if (memberArray.get(contactNo) != null) { // contactNo exists in system 
			if (memberArray.get(contactNo) == name) {
				System.out.println("Member " + name + " already exists in system.");
				return true;
			}
			else {
				System.out.println("There is an existing member " + memberArray.get(contactNo) + " with phone number " + contactNo);
				System.out.println("Please try again and input correct phone number.");
				return false;
			}
		}
		else { // contactNo does not exist in system -> add member 
			memberArray.put(contactNo, name);
			System.out.println("Successfully added " + name + " as member.");
			return true;
		}
	}
	
	public static void removeMember(int contactNo, String name) {
		if (memberArray.get(contactNo) == name) {
			memberArray.remove(contactNo);
			System.out.println("Successfully removed " + name + ", contact no. " + contactNo + " from membership.");
		}
		else if (memberArray.get(contactNo) == null){
			System.out.println("No such member with contact no. " + contactNo + " exists");
		}
		else if(memberArray.get(contactNo) != name){
			System.out.println("Wrong contact number entered. Please re-enter again.");
		}
	}
	
	public static boolean isMember(int contactNo, String name) {
		if (memberArray.get(contactNo) == name) {
			return true;
		}
		return false;
	}
}
