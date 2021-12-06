package restaurant;
import java.util.*;
import java.io.*;

/**
 * Manages methods pertaining to Member class.
 * @author Jacintha Wee
 * @version 1.0
 * @since 2021-11-12
 */
public class MemberManager implements Serializable {
	/**
	 * HashMap whereby key is name+contact of member and value is the Member object
	 */
	private HashMap<String, Member> memberArray;  
	
	/**
	 * Creates a new HashMap
	 */
	public MemberManager() {
		memberArray = new HashMap<String, Member>();
	}
	
	/**
	 * Before printing the invoice, check if customer is a member or wants to become a member to decide whether to apply membership discount in invoice
	 * @param name The name of the member
	 * @param contact The contact number of the member
	 * @param join Determines membership status of customer: 1 to check if customer is member if customer claims to be a member, 2 is customer is not a member and wants to become a member, 3 if customer is not a member and does not want to be a member
	 * @return true if customer is a member or becomes a member and false if customer is not a member
	 */
	public boolean paymentMembership(String name, String contact, int join) {
		System.out.println("Welcome to payment!");
		boolean membership = false;
		if (join == 1) { // Customer claims to be member
			membership = isMember(name, contact);
		}
		else if (join == 2){ // Customer is not a member and wants to join
			membership = addMember(name, contact);
			if (membership == false) {membership = isMember(name, contact);	}
		}
		else { // Customer is not a member and does not want to join	
			membership = false;
		}
		return membership;
	}
	
	/**
	 * Method to add a customer to membership of this restaurant
	 * @param name The name of the member
	 * @param contact The contact number of the member
	 * @return true if membership application was successful, and false otherwise
	 */
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
	
	/**
	 * Check if customer is a member of this restaurant
	 * @param name The name of the member
	 * @param contact The contact number of the member
	 * @return true if customer is a member and false otherwise
	 */
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
