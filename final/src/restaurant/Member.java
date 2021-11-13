package restaurant;
import java.io.Serializable;

/**
 * Represents a member of this restaurant. 
 * Members are entitled to 10% discount off their payment.
 * @author Jacintha Wee
 * @version 1.0
 * @since 2021-11-12
 */

public class Member implements Serializable{
	/**
	 * The contact number of this Member
	 */
	private String memberHP;
	/**
	 * The name of this Member
	 */
	private String memberName;
	
	/**
	 * Creates a new Member with the given contact number and name.
	 * @param memberHP The contact number of this Member
	 * @param memberName The name of this Member
	 */
	public Member(String memberHP, String memberName) {
		this.memberHP = memberHP;
		this.memberName = memberName;
	}
	
	/**
	 * Gets the contact number of this Member
	 * @return this Member's contact number
	 */
	public String getMemberHP() {
        return memberHP;
    }
	
	/**
	 * Gets the name of this Member
	 * @return this Member's name
	 */
	public String getMemberName() {
        return memberName;
    }
	
	/**
	 * Prints the name and contact number of this Member
	 */
	public void printMember() {
		System.out.println("Particulars of member:");
		System.out.println("Name: " + memberName);
		System.out.println("Contact No.: " + memberHP);
	}
}