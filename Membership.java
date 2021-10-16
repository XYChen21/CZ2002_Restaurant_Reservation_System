package restaurant;
import java.util.HashMap;

public class Membership {
	public static HashMap<Integer, String> memberArray = new HashMap<Integer, String>(); // key: contact no; value: name 
	
	int contactNo;
	String name;
	
	public Membership(int contactNo, String name){
		this.contactNo = contactNo;
		this.name = name;
		if (memberArray.get(contactNo) != null) {
			if (memberArray.get(contactNo) != name) {
				System.out.println("Error: Contact number entered belongs to an existing member.");
			}
			else {
				System.out.println("Member " + name + " already exists in system.");
			}
		}
		else {
			memberArray.put(contactNo, name);
			System.out.println("Successfully added " + name + " as member.");
		}
	}
	
	public void removeMember(int contactNo, String name) {
		if (memberArray.get(contactNo) == name) {
			memberArray.remove(contactNo);
			System.out.printf("Successfully removed " + name + ", contact no. " + contactNo + " from membership.");
		}
		else if (memberArray.get(contactNo) == null){
			System.out.printf("No such member ", contactNo, " exists.\n");
		}
		else if(memberArray.get(contactNo) != name){
			System.out.println("Wrong contact number entered. Please re-enter again.");
		}
	}
	
	public boolean isMember(int contactNo, String name) {
		if (memberArray.get(contactNo) == name) {
			return true;
		}
		return false;
	}
}
