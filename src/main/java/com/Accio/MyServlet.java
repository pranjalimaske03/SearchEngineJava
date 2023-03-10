package com.Accio;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>This is my Servlet</h3>");
    }

    public static class DatabaseConnection {
        static Connection connection = null;
        public static Connection getConnection(){
            if(connection!=null){
                return connection;
            }
            String db = "searchenginejava";
            String user = "root";
            String pwd = "mauli@Shivnagar";
            return getConnection(db, user, pwd);

        }
        private static Connection getConnection(String db,String user,String pwd){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
            }
            catch(ClassNotFoundException | SQLException classNotFoundException){
                classNotFoundException.printStackTrace();
            }
            return connection;
        }
    }
}
