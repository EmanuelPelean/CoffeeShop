package com.gc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// Step 1 import sql
import java.sql.*;
import java.util.ArrayList;

/*
 * author: Emanuel Pelean
 *
 */

@Controller
public class HomeController {

	// take data from a form and just add it to a page
	@RequestMapping(value = "/submitform", method = RequestMethod.POST)
	public String submitForm(Model model, @RequestParam("firstName") String firstN, @RequestParam("lastName") String lastN,
			@RequestParam("email") String userEmail, @RequestParam("userPassword") String password, @RequestParam("phone") String userPhone) {

		model.addAttribute("showUserInfo", firstN + " " + lastN);
		return "newPage";
	}
	

	@RequestMapping("/welcome")
	public ModelAndView helloWorld() throws ClassNotFoundException, SQLException {

		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/coffeeshopdb";
		//Fill in userName and password
		String userName = "guest";
		String password = "treehouse111";
		String query = "select * from customers";

		// Step # 2: Load and register the driver
		Class.forName("com.mysql.jdbc.Driver");

		// Step # 3 : Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);

		// Step 4 : Create Statment
		Statement st = con.createStatement();

		// Step 5 : Retrieve results
		ResultSet rs = st.executeQuery(query);

		ArrayList<String> list = new ArrayList<String>();
		// Step 6: Process Results (optional)
		while (rs.next()) {

			String userId = rs.getString(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			String email = rs.getString(4);
			String userPassword = rs.getString(5);
			String phone = rs.getString(6);

			list.add(userId + " " + firstName + " " + lastName + " " + email + " " + userPassword + " " + phone);

		}

		return new ModelAndView("welcome", "message", list);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView test(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email,
			@RequestParam("userPassword") String userPassword, @RequestParam("phone") String phone)
			throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/coffeeshopdb";
		//Fill in userName and password
				String userName = "";
				String password = "";

		// Step # 2: Load and register the driver
		Class.forName("com.mysql.jdbc.Driver");

		// Step # 3 : Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);

		// instead of hardcoding these values we can use a prepared statement
		String preparedSql = "insert into customers(id, firstname, lastname, email, userPassword, phone)" + "values(?,?,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(preparedSql);
		String id = null;
		ps.setString(1, id);
		ps.setString(2, firstName);
		ps.setString(3, lastName);
		ps.setString(4, email);
		ps.setString(5, userPassword);
		ps.setString(6, phone);
		ps.execute();

		return new ModelAndView("newPage", "testing", "Just testing stuff");
	}

	@RequestMapping("/update")
	public ModelAndView dbUpdate() throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/coffeeshopdb";
		//Fill in userName and password
				String userName = "";
				String password = "";

		// Step # 2: Load and register the driver
		Class.forName("com.mysql.jdbc.Driver");

		// Step # 3 : Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);

		String sql = "update customers set id= 3456, firstname='Merc', email='Smithson@gmail.com' where lastname ='Rogers'";
		Statement st = con.createStatement();
		int rowCount = st.executeUpdate(sql);
		return new ModelAndView("test", "testing", rowCount);
	}

	@RequestMapping("/delete")
	public ModelAndView dbDelete() throws ClassNotFoundException, SQLException {
		// prep for step # 3
		String url = "jdbc:mysql://localhost:3306/coffeeshopdb";
		//Fill in userName and password
				String userName = "";
				String password = "";

		// Step # 2: Load and register the driver
		Class.forName("com.mysql.jdbc.Driver");

		// Step # 3 : Create Connection
		Connection con = DriverManager.getConnection(url, userName, password);

		String sql = "delete from customers where lastname='Rogers'";
		Statement st = con.createStatement();
		int rowCount = st.executeUpdate(sql);
		return new ModelAndView("test", "testing", rowCount);
	}
	
	
}