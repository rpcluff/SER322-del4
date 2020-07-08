import java.sql.*;

import java.util.Scanner;

/* Deliverable 4
*  Team 18
*  Jonah Cole, Ryan Cluff, Dylan Sund
*  
*  Inventory UI for running queries on the inventory database
*  To run the application, run java InventoryUI <URL> <USER> <PASSWD> <DRIVER> and follow the console prompts to run queries.
*/
public class InventoryUI {
	private String url = "";
	private String user = "";
	private String passwd = "";
	private String driver = "";
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("USAGE: java UI <url> <user> <passwd> <driver>");
			System.exit(0);
		}
		
		InventoryUI ui = new InventoryUI(args[0], args[1], args[2], args[3]);
		ui.start();
	}
	
	/**
	 * Constructor for the UI object
	 * 
	 * @param url url of the MySQL database server
	 * @param user username for accessing the database
	 * @param passwd password for accessing the database
	 * @param driver name of the driver for communicating with the database
	 */
	public InventoryUI(String url, String user, String passwd, String driver) {
		this.url = url;
		this.user = user;
		this.passwd = passwd;
		this.driver = driver;
	}
		
	/**
	 * Starts the console UI
	 */
	private void start() {
		Scanner in = new Scanner(System.in);
		printOptions();
		System.out.print("Enter query option (0 to quit): ");
		int input = in.nextInt();
		System.out.println();
		
		while (input != 0) {
			switch (input) {
			case 1:
				query1();
				break;
			case 2:
				query2();
				break;
			case 3:
				query3();
				break;
			case 4:
				query4();
				break;
			case 5:
				query5();
				break;
			}
			System.out.println();
			
			printOptions();
			System.out.print("Enter query option (0 to quit): ");
			input = in.nextInt();
			System.out.println();
		}
		in.close();
	}
	
	private void printOptions() {
		System.out.println("Query options: ");
		System.out.println("1: Display the names of all employees who work at Phoenix branch and who have" + 
				" fulfilled at least one order.");
		System.out.println("2: Display the phone number of the Flagstaff location");
		System.out.println("3: Display the price of all products that are currently in an order and the " + 
				" the number of that product being purchased is over 2.");
		System.out.println("4: Display the names of all customers who have an order placed");
		System.out.println("5: Display names of all products from Tempe that are currently in stock");
		System.out.println();
	}
	
	
	/**
	 * Display the results of the query:
	 * Select the names of all employees who work at Phoenix branch and who have fulfilled at least one order.
	 * 
	 * SELECT E.Name
	 * FROM EMPLOYEE AS E, FULFILLED_BY AS F, WORKS_AT AS W
	 * WHERE W.LocationId = 'Phoenix'
	 * AND W.eSSN = F.eSSN
	 * AND F.eSSN = E.SSN;
	 */
	private void query1() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("SELECT E.Name " + 
					"FROM EMPLOYEE AS E, FULFILLED_BY AS F, WORKS_AT AS W " + 
					"WHERE W.LocationId = 'Phoenix'" + 
					"AND W.eSSN = F.eSSN " + 
					"AND F.eSSN = E.SSN");

			// Step 4: Make a query
			rs = stmt.executeQuery();
			
			// Step 5: Display
			while (rs.next()) {
				System.out.println(rs.getString("E.Name"));
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			close(rs, stmt, conn);
		}
	}
	
	/**
	 * Display the results of the query:
	 * Select the phone number of the Anaheim location
	 * 
	 * SELECT PhoneNumber
	 * FROM LOCATION 
	 * WHERE LocationId = 'Flagstaff';
	 */
	private void query2() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("SELECT PhoneNumber " + 
					"FROM LOCATION " + 
					"WHERE LocationId = 'Flagstaff'");

			// Step 4: Make a query
			rs = stmt.executeQuery();
			
			// Step 5: Display
			while (rs.next()) {
				System.out.println(rs.getString("PhoneNumber"));
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			close(rs, stmt, conn);
		}
	}
	
	/**
	 * Display the results of the query:
	 * Select the price of all products that are currently in an order and the 
	 * the number of that product being purchased is over 2. 
	 * 
	 * SELECT P.Price 
	 * FROM PRODUCT AS P, ORDER_CONTAINS AS OC
	 * WHERE OC.ItemCount > 2
	 * AND OC.ProductId = P.ProductId;
	 */
	private void query3() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("SELECT P.Price " + 
					"FROM PRODUCT AS P, ORDER_CONTAINS AS OC " + 
					"WHERE OC.ItemCount>2 " + 
					"AND OC.ProductId=P.ProductId");

			// Step 4: Make a query
			rs = stmt.executeQuery();
			
			// Step 5: Display
			while (rs.next()) {
				System.out.printf("%.2f\n", rs.getFloat("P.Price"));
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			close(rs, stmt, conn);
		}
	}
	
	/**
	 * Display the results of the query:
	 * Select names of all customers who have an order placed.
	 * 
	 * SELECT C.Name
	 * FROM CUSTOMER AS C, CUSTOMER_ORDER AS O
	 * WHERE O.CEmail = C.Email;
	 */
	private void query4() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("SELECT C.Name " + 
					"FROM CUSTOMER AS C, CUSTOMER_ORDER AS O " + 
					"WHERE O.CEmail = C.Email");

			// Step 4: Make a query
			rs = stmt.executeQuery();
			
			// Step 5: Display
			while (rs.next()) {
				System.out.println(rs.getString("C.Name"));
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			close(rs, stmt, conn);
		}
	}
	
	/**
	 * Display the results of the query:
	 * Select names of all products from Seattle that are currently in stock.
	 * 
	 * SELECT P.Name
	 * FROM PRODUCT AS P, STORED_AT AS S
	 * WHERE S.LocationId = 'Tempe' 
	 * AND S.ItemCount > 0
	 * AND S.ProductId = P.ProductId;
	 */
	private void query5() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("SELECT P.Name " + 
					"FROM PRODUCT AS P, STORED_AT AS S " + 
					"WHERE S.LocationId = 'Tempe' " + 
					"AND S.ItemCount > 0 " + 
					"AND S.ProductId = P.ProductId");

			// Step 4: Make a query
			rs = stmt.executeQuery();
			
			// Step 5: Display
			while (rs.next()) {
				System.out.println(rs.getString("P.Name"));
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			close(rs, stmt, conn);
		}
	}
	
	/**
	 * Closes the DB resources
	 * 
	 * @param rs ResultSet to be closed
	 * @param stmt PreparedStatement to be closed
	 * @param conn Connection to be closed
	 */
	private void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
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
}
