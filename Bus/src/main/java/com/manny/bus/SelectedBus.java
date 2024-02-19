package com.manny.bus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/selectedBus")
public class SelectedBus extends HttpServlet {
	BusDetails selectedBus;
	int selectedBusId;
	 String url = "jdbc:mysql://localhost:3306/webproject?user=root&password=12345";
	 String Insert="insert into selectedbusdetails(USER_EMAIL_ID, BUS_NO, BUS_BOOKING_FROM, BUS_BOOKING_TO, BUS_TIME, TICKET_PRICE) values(?,?,?,?,?,?)";
	  @Override
	  protected void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {
		  	response.setContentType("text/html");
		    PrintWriter writer = response.getWriter();
		    HttpSession session = request.getSession();
		    BusDetails selectedBus = null;
		    int selectedBusId = -1; // Initialize with a default value

		    try {
		    	String busSelectParameter = request.getParameter("busSelect");
		    	System.out.println("busSelectParameter: " + busSelectParameter);
		    	if (busSelectParameter != null) {
		    	    selectedBusId = Integer.parseInt(busSelectParameter);
		    	} else {
		    	    writer.println("<center><h2>Error: Please select a bus.</h2></center>");
		    	    writer.println("<center><a href='MannyBusSelection.html'>Go back to select a bus</a></center>");
		    	    return; 
		    	}
		    } catch (NumberFormatException e) {
		        // Handle the case where the selected bus ID is not a valid integer
		    }

//		    List<BusDetails> MannyBuses = (List<BusDetails>) request.getAttribute("buses");
		    List<BusDetails> MannyBuses = (List<BusDetails>) request.getSession().getAttribute("buses");
		    String emaiId = (String) session.getAttribute("emailbususer");
		    

		    for (BusDetails bus : MannyBuses) {
		    	if (bus != null && bus.getBUS_ID() == selectedBusId) {
		            selectedBus = bus;
		            break;
		        }
		    }

		    if (selectedBus != null) {
		        writer.println("<center><h2>Selected Bus Details</h2></center>");
		        writer.println("<center><p>Bus Service No: " + selectedBus.getBUS_ID() + "</p></center>");
		        writer.println("<center><p>Bus Booking From: " + selectedBus.getBOOKING_FROM() + "</p></center>");
		        writer.println("<center><p>Bus Destination To: " + selectedBus.getBOOKING_TO() + "</p></center>");
		        writer.println("<center><p>Bus Timing: " + selectedBus.getTIME() + "</p></center>");
		        writer.println("<center><p>Ticket Price: " + selectedBus.getPRICE() + "</p></center>");
		    } else {
		        writer.println("<center><h2>Selected bus not found.</h2></center>");
		    }
		    try {
		    	Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(Insert);
				ps.setString(1, emaiId);
				ps.setInt(2, selectedBus.getBUS_ID());
				ps.setString(3,selectedBus.getBOOKING_FROM() );
				ps.setString(4, selectedBus.getBOOKING_TO());
				ps.setString(5, selectedBus.getTIME());
				ps.setDouble(6, selectedBus.getPRICE());
				int result = ps.executeUpdate();
				if (result!=0) {
					writer.println("<center>Sucessfull</center>");
				} else {
					writer.println("<center><h2>error</h2></center>");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    writer.println("<style>");
		 	writer.println("html,body {");
	        writer.println("height: 100%;\r\n"
	        				+ "margin: 0;\r\n"
	        				+ "padding: 0;");
	        writer.println(" background-image: linear-gradient(#56ab2f, #a8e063);");
	        writer.println("}");
	        writer.println("</style>");
		}
	

}
