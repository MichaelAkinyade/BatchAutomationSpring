package com.globalcapital.database.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.globalcapital.pack.database.entity.Role;
import com.globalcapital.pack.database.entity.Users;


public class H2DatabaseLuncher {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";

	@SuppressWarnings("unchecked")
	public static Users getUserByUsername(String username) {
		Connection conn = null;
		Statement stmt = null;
		Users user = new Users();
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("connected to H2 Database database...");
			String sql = " Select * from User where username =" + "\'" + username + "\' ";
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
			ResultSet rss = stmt.executeQuery(sql);

			while (rss.next()) {
				user.setId(rss.getInt("id"));
				user.setUserName(rss.getString("username"));
				user.setFirstName(rss.getString("firstname"));
				user.setLastName(rss.getString("lastname"));
				user.setPassword(rss.getString("password"));
				user.setRole_id(rss.getInt("role_id"));
			}

			rss.close();
			System.out.println("H2 query generated");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!" + user.getFirstName());

		return user;
	}

	public List<Role> listRole(){
		
		Connection conn = null;
		Statement stmt = null;
		List<Role>  retVal = new ArrayList<>();
		
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Role role = new Role();
			// STEP 3: Execute a query
			System.out.println("connected to H2 Database database...");
			String sql = " Select * from Role";
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
			ResultSet rss = stmt.executeQuery(sql);

			while (rss.next()) {
				role.setId(rss.getInt("id"));
				role.setRoleName(rss.getString("role_name"));
				retVal.add(role);
			}

			rss.close();
			System.out.println("H2 query generated");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	
		
		return retVal;
	}
	
public static List<Users> getUsersList(){
		
		Connection conn = null;
		Statement stmt = null;
		List<Users>  retVal = new ArrayList<>();
		
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// STEP 3: Execute a query
			System.out.println("connected to H2 Database database...");
			String sql = " Select * from User";
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
			ResultSet rss = stmt.executeQuery(sql);
			
			while (rss.next()) {
				Users user = new Users();
				user.setId(rss.getInt("id"));
				user.setUserName(rss.getString("username"));
				user.setFirstName(rss.getString("firstname"));
				user.setLastName(rss.getString("lastname"));
				user.setPassword(rss.getString("password"));
				user.setRole_id(rss.getInt("role_id"));
				retVal.add(user);
			}
			rss.close();
			System.out.println("H2 query generated");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	
		
		return retVal;
	}
	
	
	
	public static void saveUser(Users user) {
		
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("connected to H2 Database database...");
			//String sql = " Select * from User where username =" + "\'" + username + "\' ";
			
			String sql = "INSERT INTO User (Firstname, lastname, username, password, role_id) " 
			         + "VALUES ("+"\'"+user.getFirstName()+"\',"+"\'"+user.getFirstName()+"\',"+"\'"+user.getUserName()+"\',"+"\'"+user.getPassword()+"\',"+"\'"+user.getRole_id()+"\')";

			
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
			
			System.out.println("user Saved");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!" + user.getFirstName());
		
	}
	
	
	
	/*
	 * public List<Role> getAllRoles() { List<Role> retVal = new ArrayList<>();
	 * String sql = " Select * from Role"; ResultSet rs =
	 * getResultSetFromQuery(sql); try {
	 * 
	 * 
	 * while (rs.next()) { Role r = new Role();
	 * 
	 * r.setId(rs.getInt("id")); r.setRoleName(rs.getString("role_name"));
	 * 
	 * retVal.add(r); }
	 *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return retVal; }
	 */
	

}
