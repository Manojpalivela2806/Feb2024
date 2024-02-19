package com.manny.bus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/mannylogin")
public class MannyLogiin extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		
		String url="jdbc:mysql://localhost:3306/webproject?user=root&password=12345";
		String select="select * from mannyuser where USER_EMAIL_ID=? and USER_PASSWORD=?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement pst = connection.prepareStatement(select);
			pst.setString(1, email);
			pst.setString(2, pwd);
			ResultSet set = pst.executeQuery();
			// It is Used to get Session Object Which is Used To Store User Information
			HttpSession session = request.getSession();
			if (set.next()) {
				int id = set.getInt("USER_ID");
				String name = set.getString("USER_NAME");
				// To set the values in session Object
				session.setAttribute("userId",id);
				session.setAttribute("useRnamE", name);
				session.setAttribute("emailbususer",email );
				
				RequestDispatcher dp = request.getRequestDispatcher("MannyBusSelection.html");
				dp.forward(request, response);
			} else {
				RequestDispatcher dp = request.getRequestDispatcher("BusLogin.html");
				dp.include(request, response);
				writer.println("<center><h2>Try Again</h2></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writer.println("<center><h2>Invalid credentials</h2></center>");
		}
	}
}
