package restaurant;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

/**
 * Interface to input information regarding members of this restaurant.
 * @author Jacintha
 * @version 1.0
 * @since 2021-11-12
 */
public class MemberUI {

	/**
	 * a scanner to scan user's input
	 */
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Input the name of the member
	 * @return The name of the member
	 */
	public static String scanMemberName() {
		String name;
		while(true) {
			System.out.println("Enter member/customer's name: ");
			try {
				name = sc.nextLine();
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return name;
	}
	
	/**
	 * Input the contact number of the member
	 * @return The contact number of the member
	 */
	public static String scanMemberHP() {
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
	
	/**
	 * Find out if customer is a member or if customer wants to become a member before proceeding to payment
	 * @return 1 if customer claims to be a member, 2 if customer is not a member and wants to become a member, 3 if customer is not a member and does not want to become a member
	 */
	public static int joinMembership() {
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
