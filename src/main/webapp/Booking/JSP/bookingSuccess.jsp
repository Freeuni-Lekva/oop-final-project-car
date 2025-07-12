<%@ page import="org.example.car.User.Model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Booking Confirmed</title>
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

<div class ="wrapper">
<div class = "glass">
  <div class = "glass_header">
    <div class = "status2">Booking Confirmed</div>
    <h1> Congratulations ${userName}!</h1>
    <p>Your journey begins soon with <span class="highlight1">${carName}</span></p>
  </div>


  <div class = "car">
    <img src="${carPic}" alt="${carName}" class="car-image">
    <div class = "car_info">
        <h2>${carName}</h2>
        <p class = "car_desc">${carDetails}</p>
        <div class = "dates">
          <p><span class="highlight2">Pick-up: ${startDate} </span></p>
          <p><span class="highlight2">Return: ${endDate} </span></p>
        </div>
    </div>
  </div>

  <div class="glass-footer">
    <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Return to Homepage</a>
  </div>

</div>
</div>

</body>
</html>
