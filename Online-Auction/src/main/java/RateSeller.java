import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RateSeller
 */
@WebServlet("/RateSeller")
public class RateSeller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String auction = request.getParameter("aid");		
		int auctionid = Integer.parseInt(auction);
		String buyer = request.getParameter("bid");
		int buyerid = Integer.parseInt(buyer);
		int sellerid =0;
		int rating = (int) Float.parseFloat(request.getParameter("rating"));
		PrintWriter out = response.getWriter();
		
		
		String url = "jdbc:mysql://localhost:3306/own";  
	    String driver = "com.mysql.cj.jdbc.Driver";  
	    String userName = "root";  
	    String password = "java@123";
	    
	    
	   try {
	    	Class.forName(driver);
		    Connection conn= DriverManager.getConnection(url, userName, password);
	       String query = "select sellerid from auction where auctionid = ?";
	       PreparedStatement ps =conn.prepareStatement(query);
	       ps.setInt(1, auctionid);
	      ResultSet rs= ps.executeQuery();
	       if(rs.next()){
	    	  sellerid=rs.getInt(1);	    	   
	       }
	       out.println(rating);
	       String SQL = "update userrating set sellerrating= ? where userid=? ";
	       PreparedStatement pst =conn.prepareStatement(SQL);
	      pst.setInt(2,sellerid);
	       pst.setInt(1, rating);
	       pst.execute();
	       conn.close();
	       
	       out.println("Rating submitted");
	       
	    }
	    catch (SQLException | ClassNotFoundException e) {
	      
	    }		
		
	}

}