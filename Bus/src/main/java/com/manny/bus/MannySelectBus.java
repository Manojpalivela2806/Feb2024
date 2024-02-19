package com.manny.bus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seleCtbUS")
public class MannySelectBus extends HttpServlet {
    String url = "jdbc:mysql://localhost:3306/webproject?user=root&password=12345";
    String select = "SELECT * FROM mannybusdetails where BOOKING_FROM=? and BOOKING_TO=?";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fromPoint = request.getParameter("frompoint");
        String toPoint = request.getParameter("topoint");

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();

        List<BusDetails> MannyBuses = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url);
                 PreparedStatement ps = connection.prepareStatement(select)) {
                ps.setString(1, fromPoint);
                ps.setString(2, toPoint);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        BusDetails bus = new BusDetails();
                        bus.setBUS_ID(set.getInt("BUS_ID"));
                        bus.setBOOKING_FROM(set.getString("BOOKING_FROM"));
                        bus.setBOOKING_TO(set.getString("BOOKING_TO"));
                        bus.setTIME(set.getString("TIME"));
                        bus.setPRICE(set.getDouble("PRICE"));

                        MannyBuses.add(bus);                   
                    }
                }   
                if (!MannyBuses.isEmpty()) {
                   writer.println("<center><h2>Available Buses</h2></center>");
                    writer.println("<center><form action='selectedBus' method='post'>");
                    writer.println("<label for='busSelect'>Select a Bus:</label>");
                    writer.println("<select name='busSelect' id='busSelect'>");
                    for (BusDetails bus : MannyBuses) {
                        writer.println("<option value='" + bus.getBUS_ID() + "'>");
                        writer.println("ID: " + bus.getBUS_ID() + ", From: " + bus.getBOOKING_FROM() +
                                ", To: " + bus.getBOOKING_TO() + ", Time: " + bus.getTIME() +
                                ", Price: " + bus.getPRICE());
                        writer.println("</option>");
                    }
                    writer.println("</select>");
                    writer.println("<input type='submit' value='Select'>");
                    writer.println("</form></center>");
                    
                } else {
                    writer.println("<center><h2>No buses available for the selected route.</h2></center>");
                }
            }
//            request.setAttribute("buses", MannyBuses);
            session.setAttribute("buses", MannyBuses);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/selectedBus");
//            dispatcher.forward(request, response);
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
        writer.println("<style>");
        writer.println("html,body {");
        writer.println("height: 100%;\r\n"
        				+"color=#f7bb97;\r\n"
        				+ "margin: 0;\r\n"
        				+ "padding: 0;");
        writer.println(" background-image: linear-gradient(#ffecd2, #fcb69f);");
        writer.println("}");
        writer.println("</style>");
    }
}
