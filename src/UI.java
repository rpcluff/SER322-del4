import java.sql.*;
import java.util.Scanner;

public class UI {
	private String url = "";
	private String user = "";
	private String passwd = "";
	private String driver = "";
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("USAGE: java UI <url> <user> <passwd> <driver> <userType>");
			System.out.println("For <userType>, enter 'e' for employee or 'c' for customer.");
			System.exit(0);
		}
		
		UI ui = new UI(args[0], args[1], args[2], args[3]);
		if (args[4].equals("e")) {
			ui.employee();
		} else {
			ui.customer();
		}
	}
	
	public UI(String url, String user, String passwd, String driver) {
		this.url = url;
		this.user = user;
		this.passwd = passwd;
		this.driver = driver;
	}
	
	private void employee() {
		
	}
	
	private void customer() {
		Scanner in = new Scanner(System.in);
		String input = "";
		while (!input.equals("q")) {
			System.out.print("Enter 1 to place new order or 2 to view current order (q to quit): ");
			input = in.nextLine();
			if (input.equals("1")) {
				
			} else if (input.equals("2")) {
				
			}
		}
	}
	
	private ResultSet processQuery(String statement) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("Select dname, dnumber, mgrssn, mgrstartdate from Department WHERE dname=?");
			stmt.setString(1, statement);

			// Step 4: Make a query
			rs = stmt.executeQuery();

			// Step 5: Display the results
			while (rs.next()) {
				System.out.print(rs.getString("dname") + "\t");
				System.out.print(rs.getInt("dnumber") + "\t ");
				System.out.print(rs.getInt("mgrssn") + "\t ");
				System.out.println(rs.getDate("mgrstartdate"));
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Throwable t1) {
				System.out.println("A problem closing db resources!");
			}
			try {
				if (conn != null)
					conn.close();
			}
			catch (Throwable t2) {
				System.out.println("Oh-oh! Connection leaked!");
			}
		}
		
		return rs;
	}
}
