<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>


  <%
    if(session != null){
      List<String> chat = (List<String>) session.getAttribute("chat");
      if(chat != null){
  %>
  <% for(String s: chat) {%>

  <p> <%= s %> </p>

  <%
        }
      }
    }
  %>


  <form action = "AIServlet" method="post">
    <input type="text" name="message" required/>
    <button type="submit"> send </button>
  </form>

  </body>
</html>
