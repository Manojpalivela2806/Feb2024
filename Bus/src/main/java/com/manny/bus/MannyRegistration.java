package com.manny.bus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
@WebServlet("/mannyBusRegistration")
public class MannyRegistration extends GenericServlet{

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String url="jdbc:mysql://localhost:3306/webproject?user=root&password=12345";
		String Insert="insert into mannyuser(USER_NAME, USER_EMAIL_ID, USER_PASSWORD, USER_ADDRESS, USER_MOBILE_NUM) values(?,?,?,?,?)";
		
		String userName = request.getParameter("buname");
		String userEmailId = request.getParameter("buemail");
		String userPassword = request.getParameter("bupassword");
		String userAddress = request.getParameter("buaddress");
		String userMobileNumber = request.getParameter("bumobile");
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps1 = connection.prepareStatement(Insert);
			ps1.setString(1, userName);
			ps1.setString(2, userEmailId);
			ps1.setString(3, userPassword);
			ps1.setString(4, userAddress);
			ps1.setString(5, userMobileNumber);
			int ex = ps1.executeUpdate();
			if (ex!=0) {
				RequestDispatcher rd = request.getRequestDispatcher("BusLogin.html");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("BusRegister.html");
				rd.include(request, response);
				writer.println("<center><h2>Invalid Details try again</h2></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writer.println("<center><h2>ProvideAccurate Details</h2></center>");
		}
		
	}

}
