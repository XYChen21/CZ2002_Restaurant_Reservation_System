package restaurant;

public class Member {
	private String memberHP;
	private String memberName;
	
	public Member(String memberHP, String memberName) {
		this.memberHP = memberHP;
		this.memberName = memberName;
	}
	
	public String getMemberHP() {return memberHP;}
	
	public String getMemberName() {return memberName;}
	
	public void printMember() {
		System.out.println("Particulars of member:");
		System.out.println("Name: " + memberName);
		System.out.println("Contact No.: " + memberHP);
	}
}
