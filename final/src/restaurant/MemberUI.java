package restaurant;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class MemberUI implements Serializable{

	Scanner sc = new Scanner(System.in);
	
	public String scanMemberName() {
		System.out.println("Enter your name: ");
		String name = sc.nextLine();
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
		while (true) {
			try {
				System.out.println("Are you a member? (1) Yes (2) No");
				ans = sc.nextInt();
				if (ans < 1 || ans > 2) {
					throw new Exception("Invalid input. Enter integers only (1) Yes (2) No");
				}
				if (ans == 1) {
					join = 1;
					break;
				}
				else {
					System.out.println("Would you like to become a member? (1) Yes (2) No");
					ans = sc.nextInt();
					if (ans < 1 || ans > 2) {
						throw new Exception("Invalid input. Enter integers only (1) Yes (2) No");
					}
					if (ans == 1) {
						join = 2;
						break;
					}
					else {
						join = 3;
						break;
					}
				}
				
			} catch(InputMismatchException e) {
				System.out.println("Please input an integer");
				sc.nextLine();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		sc.nextLine();
		
		return join;		
	}
}
