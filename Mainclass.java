package library;
import java.util.Scanner;
public class Mainclass {
	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		try {
			Login m=new Login();
			System.out.println("Library Login Page");
			System.out.print("Enter UserName: ");
			String name=s.next();
			System.out.print("Enter Password: ");
			String pass=s.next();
			if(m.login(name,pass))
				m.project();
			else
				System.out.println("Wrong Password/Username");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		s.close();
	}
	}
