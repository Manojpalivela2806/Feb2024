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
@WebServlet("/ForgetPwd")
public class ForgetPassword extends HttpServlet {
	
	String url="jdbc:mysql://localhost:3306/webproject?user=root&password=12345";
	String select="select * from mannyuser where USER_EMAIL_ID=? and USER_MOBILE_NUM=?";
	String update ="update mannyuser set USER_PASSWORD=? where USER_EMAIL_ID=?";
			
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("pwd1");
		String confirmpassword = request.getParameter("pwd2");
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		
		if (password.equals(confirmpassword)) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement st = connection.prepareStatement(select);
				st.setString(1,email);
				st.setString(2,mobile);
				ResultSet set = st.executeQuery();
				if (set.next()) 
					{
					int id = set.getInt("USER_ID");
					String name = set.getString("USER_NAME");
					session.setAttribute("userId",id);
					session.setAttribute("useRnamE", name);
					session.setAttribute("emailbususer",email );
					PreparedStatement state = connection.prepareStatement(update);
					state.setString(1, password);
					state.setString(2, email);
					 int exe = state.executeUpdate();
					 if(exe!=0) {
						 RequestDispatcher dp = request.getRequestDispatcher("MannyBusSelection.html");
							dp.forward(request, response);
					 }
					 else {
						 RequestDispatcher dp = request.getRequestDispatcher("ForgetPassword.html");
							dp.include(request, response);
							writer.println("<center><h2>DataNotFound</h2></center>");
					 }
					}
				else {
					RequestDispatcher dp = request.getRequestDispatcher("ForgetPassword.html");
					dp.include(request, response);
					writer.println("<center><h2>DataNotFound</h2></center>");
					}
				}  
			catch (Exception e) {		
				e.printStackTrace();
			}
		} else {
			RequestDispatcher dp = request.getRequestDispatcher("ForgetPassword.html");
			dp.include(request, response);
			writer.println("<center><h2>password and confirm password are not same</h2></center>");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
