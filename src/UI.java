import java.sql.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.text.SimpleDateFormat;

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
	public UI(String url, String user, String passwd, String driver) {
		this.url = url;
		this.user = user;
		this.passwd = passwd;
		this.driver = driver;
	}
		
	/**
	 * Starts the UI
	 */
	private void start() {
		System.out.println("Query options: ")
		Scanner in = new Scanner(System.in);
		System.out.print("Enter 1 to place new order or 2 to view current order (q to quit): ");
		String input = in.nextLine();
		while (!input.equals("q")) {
			
			
			System.out.print("Enter 1 to place new order or 2 to view current order (q to quit): ");
			input = in.nextLine();
		}
		in.close();
	}
	
	/**
	 * Displays the orders of the specified customer
	 * 
	 * @param email email of the customer
	 */
	private void displayOrders(String email) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("SELECT OC.OrderId, OC.ProductId, P.Name, P.Price, OC.ItemCount "
					+ "FROM PRODUCT as P, ORDER_CONTAINS as OC, ORDER as O "
					+ "WHERE O.CEmail=?, OC.OrderId=O.OrderId, OC.ProductId=P.ProductId");
			stmt.setString(1,  email);

			// Step 4: Make a query
			rs = stmt.executeQuery();
			
			// Step 5: Display
			System.out.printf("%-27s %32s %17s %5.2f", "OrderId", "ProductId", "Name", "Price");
			while (rs.next()) {
				String orderId = rs.getString("OC.OrderId");
				String productId = rs.getString("OC.ProductId");
				String name = rs.getString("P.Name");
				float price = rs.getFloat("P.Price") * rs.getFloat("OC.ItemCount");
				System.out.printf("%-27s %32s %17s %5.2f", orderId, productId, name, price);
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
	 * Gets the products name of the corresponding productId
	 * 
	 * @param productId the id of the product
	 * @return the name of the product or an empty string if the product does not exist
	 */
	private String getProductName(String productId) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		String name = "";
		
		try {
			// Step 1: Load the JDBC driver
			Class.forName(driver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(url, user, passwd);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("SELECT Name "
					+ "FROM PRODUCT as P "
					+ "WHERE P.ProductId=?");
			stmt.setString(1,  productId);

			// Step 4: Make a query
			rs = stmt.executeQuery();
			
			// Step 5: Display
			if (rs.next()) {
				name = rs.getString("Name");
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			close(rs, stmt, conn);
		}
		
		return name;
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
	
	/**
	 * Closes the DB resources
	 * 
	 * @param rs ResultSet to be closed
	 * @param stmt Statement to be closed
	 * @param conn Connection to be closed
	 */
	private void close(ResultSet rs, Statement stmt, Connection conn) {
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
	
	/**
	 * Class represent an individual order (intended to be used to add orders to the database)
	 */
	private class Order {
		private int orderId;
		private String date;
		private String email;
		private Map<String, Integer> items;
		
		/**
		 * Constructor for the Order class
		 * 
		 * Sets the data member variable to a string representing the current day.
		 * 
		 * @param email email of the customer placing the order
		 */
		public Order(String email) {
			this.email = email;
			items = new HashMap<String, Integer>();
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.format(today);
		}
		
		/**
		 * Adds a new item to the order
		 * 
		 * @param productId productId of the product
		 * @param quantity quantity of the product
		 */
		public void addItem(String productId, int quantity) {
			items.put(productId, quantity);
		}

		/**
		 * Getter for the orderId
		 * @return orderId
		 */
		public int getOrderId() {
			return orderId;
		}

		/**
		 * Getter for the date
		 * @return the date the order was placed
		 */
		public String getDate() {
			return date;
		}

		/**
		 * Getter for the email of the customer
		 * @return email of the customer
		 */
		public String getEmail() {
			return email;
		}
		
		/**
		 * Gets the map of items, productId as the key and quantity as the value
		 * @return the map of items
		 */
		public Map<String, Integer> getItems() {
			return items;
		}
	}
}
