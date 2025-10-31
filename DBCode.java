package library;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
        public class DBCode {
        String url="jdbc:mysql://localhost/library";
        String user="root";
		String pass="Dhivya";
		Connection con;
		public DBCode() throws SQLException{
			con=DriverManager.getConnection(url, user, pass);
		}
		public int add(String title, String author, String publisher, float price, int quantity) throws SQLException {
		    PreparedStatement pst = con.prepareStatement("insert into books (title,author,publisher,price,quantity) values(?,?,?,?,?)");
		    pst.setString(1, title);
		    pst.setString(2, author);
		    pst.setString(3, publisher);
		    pst.setFloat(4, price);
		    pst.setInt(5, quantity);
		    int r = pst.executeUpdate();
		    pst.close();
		    return r;
		}
		public void view() throws SQLException{
			PreparedStatement pst=con.prepareStatement("select * from books");
			ResultSet rs=pst.executeQuery();
			int k=0;
			System.out.println("Book Information: ");
			while (rs.next()) {
		           System.out.println("Record " + (++k));
		           System.out.println("Book ID: " + rs.getInt("bookid"));
		           System.out.println("Title: " + rs.getString("title"));
		           System.out.println("Author: " + rs.getString("author"));
		           System.out.println("Publisher: " + rs.getString("publisher"));
		           System.out.println("Price: " + rs.getFloat("price"));
		           System.out.println("Quantity: " + rs.getInt("quantity"));
		           System.out.println("-----------------------------------");
		        }
			rs.close();
			pst.close();
		}
		public void view(int id) throws SQLException{
			PreparedStatement pst = con.prepareStatement("select * from books where bookid=?");
			pst.setInt(1, id);
			ResultSet rs=pst.executeQuery();
			int k=0;
			System.out.println("Book Details:");
	        while (rs.next()) {
	            k++;
	            System.out.println("Book ID: " + rs.getInt("bookid"));
	            System.out.println("Title: " + rs.getString("title"));
	            System.out.println("Author: " + rs.getString("author"));
	            System.out.println("Publisher: " + rs.getString("publisher"));
	            System.out.println("Price: " + rs.getFloat("price"));
	            System.out.println("Quantity: " + rs.getInt("quantity"));
	        }
				if(k==0) {
					System.out.println("Book Not Found");
				}		
				rs.close();
				pst.close();
			}
			public int edit(int id,float price,int qty) throws SQLException{
				PreparedStatement pst=con.prepareStatement("update books set price=?,quantity=? where bookid=?");
				pst.setFloat(1, price);
				pst.setInt(2, qty);
				pst.setInt(3, id);
				int r=pst.executeUpdate();
				pst.close();
				return r;
			}
			public int remove(int id)throws SQLException{
				PreparedStatement pst=con.prepareStatement("delete from books where bookid=?");
				pst.setInt(1, id);
		        int r = pst.executeUpdate();
		        pst.close();
		        return r;
		    }
			public int addMember(String name,String email, String phone,String address) throws SQLException {
		        PreparedStatement pst = con.prepareStatement("insert into members(name, email, phone, address) values(?, ?, ?, ?)");
		        pst.setString(1, name);
		        pst.setString(2,email);
		        pst.setString(3, phone);
		        pst.setString(4,address);
		        int r=pst.executeUpdate();
		        pst.close();
		        return r;
		    }
			public int issueBook(int issueid, int bookid, int memberid, String issuedate) throws SQLException {
			    PreparedStatement pst = con.prepareStatement("insert into bookissues values(?,?,?,?)");
			    pst.setInt(1, issueid);
			    pst.setInt(2, bookid);
			    pst.setInt(3, memberid);
			    pst.setString(4, issuedate);
			    int r=pst.executeUpdate();
			    pst.close();
			    return r;
			}
			public void viewIssuedDetailsInnerJoin() throws SQLException {
				String q = "select members.memberid,members.name,books.title,bookissues.issuedate from bookissues inner join members on bookissues.memberid=members.memberid inner join books on bookissues.bookid=books.bookid";
			    PreparedStatement pst = con.prepareStatement(q);
			    ResultSet rs = pst.executeQuery();
			    int k = 0;
			    System.out.println("Issued Book Details (Inner Join): ");
			    while (rs.next()) {
			        k++;
			        System.out.println("Record " + k);
			        System.out.println("Member ID: " + rs.getInt("memberid"));
			        System.out.println("Name: " + rs.getString("name"));
			        System.out.println("Book Title: " + rs.getString("title"));
			        System.out.println("Issue Date: " + rs.getString("issuedate"));
			        System.out.println("---------------------------");
			    }
			    if (k == 0) {
			        System.out.println("No records found");
			    }
			    rs.close();
			    pst.close();
			}
			public void viewIssuedDetailsLeftJoin() throws SQLException {
				String q = "select members.memberid,members.name,books.title,bookissues.issuedate from bookissues left join members on bookissues.memberid=members.memberid left join books on bookissues.bookid=books.bookid";
			    PreparedStatement pst = con.prepareStatement(q);
			    ResultSet rs = pst.executeQuery();
			    int k = 0;
			    System.out.println("Issued Book Details (Left Join): ");
			    while (rs.next()) {
			        k++;
			        System.out.println("Record " + k);
			        System.out.println("Member ID: " + rs.getInt("memberid"));
			        System.out.println("Name: " + rs.getString("name"));
			        System.out.println("Book Title: " + rs.getString("title"));
			        System.out.println("Issue Date: " + rs.getString("issuedate"));
			        System.out.println("---------------------------");
			    }
			    if (k == 0) {
			        System.out.println("No records found");
			    }
			    rs.close();
			    pst.close();
			}
			public void countBooks() throws SQLException {
			    String q = "SELECT COUNT(*) AS totalBooks FROM books";
			    PreparedStatement pst = con.prepareStatement(q);
			    ResultSet rs = pst.executeQuery();
			    if (rs.next()) {
			        System.out.println("Total Books: " + rs.getInt("totalBooks"));
			    }
			    rs.close();
			    pst.close();
			}
			public void totalBookQuantity() throws SQLException {
				String q = "SELECT SUM(price) AS totalQty FROM books";
			    PreparedStatement pst = con.prepareStatement(q);
			    ResultSet rs = pst.executeQuery();
			    if (rs.next()) {
			        System.out.println("Total Quantity of All Books: " + rs.getInt("totalQty"));
			    }
			    rs.close();
			    pst.close();
			}
			public void averageBookPrice() throws SQLException {
			    String q = "SELECT AVG(price) AS avgPrice FROM books";
			    PreparedStatement pst = con.prepareStatement(q);
			    ResultSet rs = pst.executeQuery();
			    if (rs.next()) {
			        System.out.println("Average Book Price: " + rs.getDouble("avgPrice"));
			    }
			    rs.close();
			    pst.close();
			}
			public void maxBookPrice() throws SQLException {
			    String q = "SELECT MAX(price) AS maxPrice FROM books";
			    PreparedStatement pst = con.prepareStatement(q);
			    ResultSet rs = pst.executeQuery();
			    if (rs.next()) {
			        System.out.println("Most Expensive Book Price: " + rs.getDouble("maxPrice"));
			    }
			    rs.close();
			    pst.close();
			}
			public void minBookPrice() throws SQLException {
			    String q = "SELECT MIN(price) AS minPrice FROM books";
			    PreparedStatement pst = con.prepareStatement(q);
			    ResultSet rs = pst.executeQuery();
			    if (rs.next()) {
			        System.out.println("Cheapest Book Price: " + rs.getDouble("minPrice"));
			    }
			    rs.close();
			    pst.close();
			}
}
        
        