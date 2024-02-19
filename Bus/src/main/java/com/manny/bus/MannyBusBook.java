package com.manny.bus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/MannyBook")
public class MannyBusBook extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 String fromPoint = request.getParameter("from");
	 String toPoint = request.getParameter("to");
	 String onDate = request.getParameter("date");
	 PrintWriter writer = response.getWriter();
	 response.setContentType("text/html");
	 String url="jdbc:mysql://localhost:3306/webproject?user=root&password=12345";
	 String Insert="insert into mannybookingrecord(BOOKING_ID,USER_ID, FROM_LOCATION, TO_LOCATION, BOOKING_DATE) values(?,?,?,?,?)";
	 
	 HttpSession session = request.getSession();
	 // To get the Value from The SessionObject.
	 Integer userID = (Integer) session.getAttribute("userId");
	 String name = (String) session.getAttribute("useRnamE");
	 
	 
	 Random rd = new Random();
	 int bookingId = rd.nextInt(10000);
	 if(bookingId<1000)
	 {
		 bookingId+=1000;
	 }
	 try {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url);
		PreparedStatement ps1 = connection.prepareStatement(Insert);
		ps1.setInt(1, bookingId);
		ps1.setInt(2, userID);
		ps1.setString(3, fromPoint);
		ps1.setString(4, toPoint);
		ps1.setDate(5,Date.valueOf(onDate));
		int result = ps1.executeUpdate();
		if (result!=0) {
			writer.println("<center><h2> Ticked Booked </h2></center>");
			writer.println("<center><div> with BookingId: "+bookingId+" <div></center>");
			writer.println("<center><div> of UserName: "+name+" <div></center>");
			writer.println("<center><div> from: "+fromPoint+" <div></center>");
			writer.println("<center><div> To: "+toPoint+" <div></center>");
			writer.println("<center><div> on the Date: "+onDate+" <div></center>");
		} else {
			RequestDispatcher dp = request.getRequestDispatcher("BusBook.html");
			dp.include(request, response);
			writer.println("<center><h2>Unfortunately your ticket not booked</h2></center>");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		writer.println("<center><h2>Invalid data</h2></center>");
	}
	}
}
