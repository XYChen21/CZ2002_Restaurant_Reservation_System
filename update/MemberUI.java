package restaurant;
import java.util.Scanner;

public class MemberUI {

	private Scanner sc = new Scanner(System.in);
	
	public String scanMemberName() {
		System.out.println("Enter your name: ");
		String name = sc.next();
		return name;
	}
	
	public String scanMemberHP() {
		String contact;
        while (true)
        {
            try {
            	System.out.println("Enter your contact number: ");
                contact = sc.nextLine();
                if (contact.matches("^\\+65[0-9]{8}") == false)
                    throw new Exception("Please enter correct format of contact number (eg +6512345678)");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return contact;
	}
	
	public int joinMembership() {
		int ans = 0, count = 0, join = 0;
		System.out.println("Are you a member? (1) Yes (2) No");
		try {
			while (count <= 1) {
				ans = sc.nextInt();
				if (ans == 1) {
					if (count == 0) {join = 1; break;}
					else {join = 2; break;}
				}
				else if (ans == 2) {
					if (count == 0) {
						System.out.println("Would you like to become a member? (1) Yes (2) No");
						count++;
						continue;
					}
					else {join = 3; break;}
				}
				else {
					throw new Exception("Invalid input. Enter (1) Yes (2) No");
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return join;		
	}
}
