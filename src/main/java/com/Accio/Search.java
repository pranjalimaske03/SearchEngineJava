package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String keyword = request.getParameter("keyword");
        System.out.println(keyword);
        try {
            //Establish/get connection to database
            Connection connection = DatabaseConnection.getConnection();
            //Save keyword and link associated into history table
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?)");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngineJava/Search?keyword="+keyword);
            preparedStatement.executeUpdate();
            //Executing a query related to keyword and get the result
            ResultSet resultSet = connection.createStatement().executeQuery("select pagetitle, pagelink, (length(lower(pagetext)) - length(replace(lower(pagetext), '"+keyword+"', ''))) / length('"+keyword+"')as countoccurence from pages order by countoccurence desc limit 30");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            //iterate through resultset and save all the elements in the result arraylist
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                //get page title
                searchResult.setPageTitle(resultSet.getString("pageTitle"));
                //get pagelink
                searchResult.setPageLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
            //display result in console
            for (SearchResult result:results) {
                System.out.println(result.getPageLink() + " " + result.getPageTitle()+"\n");
            }
            //set attribute of request with result arraylist
            request.setAttribute("results",results);
            //forward request to search.jsp
            request.getRequestDispatcher("/search.jsp").forward(request,response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }
        catch (SQLException | ServletException | IOException sqlException){
            sqlException.printStackTrace();
        }
    }
}
