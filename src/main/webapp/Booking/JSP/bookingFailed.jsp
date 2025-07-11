<%@ page import="org.example.car.User.Model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Booking Failed</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/Booking/CSS/bookingStyles.css">
</head>
<body>

<%
  User user = (User) session.getAttribute("user");
  Boolean isLoggedIn = (user != null);
%>
<div class="navbar">
  <div class="nav-left">
    <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
    <% if (isLoggedIn) { %>
    <a href="${pageContext.request.contextPath}/userProfile" class="nav-btn">Profile</a>
    <%if(user.isAdmin()){%>
    <a href="${pageContext.request.contextPath}/admin-dashboard" class="nav-btn">Admin Dashboard</a>
    <% } }
    else { %>
    <a href="${pageContext.request.contextPath}/login" class="nav-btn">Log In</a>
    <% } %>
  </div>
</div>

<div class="wrapper">
  <div class = "glass">
    <div class = "glass_header">
      <div class = "status3">We're sorry, ${userName}</div>
      <div class = "status3">There has been problem in databse, The car ${carName} is not available from ${startDate} to ${endDate} </div>
</div>
  </div>
</div>
</body>
</html>
