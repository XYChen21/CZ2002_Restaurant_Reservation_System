package restaurant;
import java.util.*;
import java.io.*;

public class MemberManager implements Serializable {
	private HashMap<String, Member> memberArray; // Key: name+contact; Value: Member object 
	
	public MemberManager() {
		memberArray = new HashMap<String, Member>();
	}
	
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
