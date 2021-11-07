package restaurant;
import java.io.*;

public class Member implements java.io.Serializable{
	private int memberHP;
	private String memberName;
	
	public Member(int memberHP, String memberName) {
		this.memberHP = memberHP;
		this.memberName = memberName;
	}
	
	public int getMemberHP() {return memberHP;}
	
	public String getMemberName() {return memberName;}
	
	public void printMember() {
		System.out.println("Particulars of member:");
		System.out.println("Name: " + memberName);
		System.out.println("Contact No.: " + memberHP);
	}
}
