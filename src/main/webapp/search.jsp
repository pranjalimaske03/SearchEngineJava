<%@page import = "java.util.ArrayList"%>
<%@page import = "com.Accio.SearchResult"%>
<html>
<head>
<link rel = "stylesheet" type = "text/css" href = "style.css">
</head>
<body>
<form action = "Search">
    <input type = "text" name = "keyword">
    <button type = "submit">Search</button>
</form>
<div class = "resultTable">
<table border = 2>
    <tr>
        <td>Title</td>
        <td>Link</td>
    </tr>
    <%
         //get results from search servlet
          ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
         //iterate for every data present in results array
         for(SearchResult result:results){
    %>
         <tr>
             <td><%out.println(result.getPageTitle());%></td>
             <td><a href = "<%out.println(result.getPageLink());%>"><%out.println(result.getPageLink());%></a></td>
         </tr>
    <%
         }
    %>
</table>
</div>
</body>
</html>