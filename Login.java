package library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Login {
    String url="jdbc:mysql://localhost/library";
    String user="root";
    String pass="Dhivya";
    Connection con;
    public Login() throws SQLException{
        con=DriverManager.getConnection(url, user, pass);
    }
    public boolean login(String name,String pass) throws SQLException{
        boolean r=false;
        PreparedStatement pst=con.prepareStatement("select * from users where username=? and password=?");
        pst.setString(1, name);
        pst.setString(2, pass);
        ResultSet rs=pst.executeQuery();
        int k=0;
        while(rs.next()) {
            k++;
        }
        if(k!=0) {
            r=true;
        }
        return r;
    }
    public void project() throws Exception{
        Scanner s = new Scanner(System.in);
        DBCode db=new DBCode();
        while(true) {
            System.out.println("------DASHBOARD---------");
            System.out.print("1.ADD\n2.VIEW\n3.UPDATE\n4.REMOVE\n5.ADD MEMBER\n6.ISSUE BOOK\n7.VIEW ISSUED BOOKS (INNER JOIN)\n" +
            	    "8.VIEW ISSUED BOOKS (LEFT JOIN)\n9.COUNT Total Books\n10.SUM Total Quantity\n11.AVG Book Price\n" +
            	    "12.MAX Book Price\n13.MIN Book Price\n0.EXIT\nENTER: ");
            int ch=s.nextInt();
            if(ch==1){    
            	s.nextLine();      	
            	System.out.print("Enter Title: ");
                String title=s.next();
                System.out.print("Enter Author: ");
                String author=s.next();
                System.out.print("Enter Publisher: ");
                String publisher=s.next();
                System.out.print("Enter Price: ");
                float price=s.nextFloat();
                System.out.print("Enter Quantity: ");
                int quantity=s.nextInt();
                int res=db.add(title, author, publisher, price, quantity);
                System.out.println(res != 0 ? "Book Added!" : "Failed");
            }
            else if(ch==2) {
                System.out.print("1.LIST\n2.DETAILS\nEnter: ");
                int c=s.nextInt();
                if(c==1) {
                    db.view();
                }
                else if(c==2) {
                    System.out.print("Enter Book ID: ");
                    db.view(s.nextInt());  
                }
                else {
                    System.out.print("Wrong Option.....");
                }
            }
            else if(ch==3) {
                System.out.print("Enter Book ID to update: ");
                int bookId = s.nextInt();
                System.out.print("Enter New Price: ");
                float price = s.nextFloat();
                System.out.print("Enter New Quantity: ");
                int qty = s.nextInt();
                System.out.println(db.edit(bookId, price, qty) != 0 ? "Updated!" : "Failed");
            } 
            else if(ch==4) {
                System.out.print("Enter Book ID to delete: ");
                int bookId = s.nextInt();
                System.out.println(db.remove(bookId) != 0 ? "Deleted!" : "Failed");
            }
            else if (ch==5) {
            	s.nextLine();
                System.out.print("Enter Member Name: ");
                String mname = s.nextLine();
                System.out.print("Enter Email: ");
                String e = s.nextLine();
                System.out.print("Enter Phone: ");
                String ph=s.nextLine();
                System.out.print("Enter Address: ");
                String a=s.nextLine();
                System.out.println(db.addMember(mname,e, ph,a) != 0 ? "Member Added!" : "Failed");
            }
            else if (ch == 6) {
                System.out.print("Enter Issue ID: ");
                int iid = s.nextInt();
                System.out.print("Enter Book ID: ");
                int bid = s.nextInt();
                System.out.print("Enter Member ID: ");
                int memid = s.nextInt();
                System.out.print("Enter Issue Date(YYYY-MM-DD)");
                String idate=s.next();
                System.out.println(db.issueBook(iid, bid, memid,idate) != 0 ? "Book Issued!" : "Failed");
            }
            else if (ch == 7) {  
                db.viewIssuedDetailsInnerJoin();
            }
            else if (ch == 8) {  
                db.viewIssuedDetailsLeftJoin();
            }
            else if(ch == 9) {
            	db.countBooks();
            }
            else if(ch == 10) {
            	db.totalBookQuantity();
            }
            else if(ch == 11) {
            	db.averageBookPrice();
            }
            else if(ch == 12) {
            	db.maxBookPrice();
            }
            else if(ch == 13) {
            	db.minBookPrice();
            }
            else if(ch==0){
                System.out.println("Application closed");
                break;
            }
            else {
                System.out.println("Invalid Choice");
            }
        }
        s.close();
    }
}
