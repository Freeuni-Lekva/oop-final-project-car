<%@ page import="org.example.car.Car.Model.Car" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Car Rental Home</title>

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage/CSS/style.css">

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/AI/CSS/chat.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

<div class="navbar">
  <div class="nav-left">
    <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
    <a href="${pageContext.request.contextPath}/userProfile" class="nav-btn">Profile</a>
    <a href="${pageContext.request.contextPath}/login" class="nav-btn">Log in</a>
  </div>
</div>

<section class="hero-section">
  <div class="hero-text">
    <h1>Rent more than a car — <span>rent an experience.</span></h1>
    <p>Luxury, comfort, and performance in every ride.</p>
  </div>
  <div class="hero-img">
    <img src="${pageContext.request.contextPath}/images/Home.png" alt="Luxury Car" />
  </div>
</section>

<div class="wrapper">
  <div class="glass">

    <div class="glass-header">
      <h1>Available Cars</h1>
      <h2>Find the perfect ride for your journey</h2>
    </div>

    <%
      List<Car> cars = (List<Car>) request.getAttribute("cars");
      if (cars != null && !cars.isEmpty()) {
    %>

    <div class="car-list">
      <%
        for (Car car : cars) {
      %>
      <div class="car">
        <img class="car-image" src="<%= car.getImage_url() %>" alt="<%= car.getBrand() %> <%= car.getModel() %>">
        <div class="car_info">
          <h2><%= car.getBrand() %> <%= car.getModel() %> <span class="year">(<%= car.getYear() %>)</span></h2>
          <p class="car_desc"><%= car.getDescription() %></p>
          <p class="price highlight1">$<%= car.getPrice_per_day() %>/day</p>
          <a class="btn" href="car-details?car=<%= car.getId() %>">View Details</a>

        </div>
      </div>
      <%
        }
      %>
    </div>
    <%
    }
    else {
    %>
    <p class="status3">No cars available at the moment.</p>
    <%
      }
    %>

  </div>
</div>

<%@ include file="../../AI/JSP/AIchat.jsp" %>
<script src="${pageContext.request.contextPath}/AI/JS/chat.js"></script>
</body>
</html>