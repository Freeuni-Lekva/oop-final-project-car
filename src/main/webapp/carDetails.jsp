<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.car.Car.Model.Car" %>
<html>
<head>
  <title>Car Details</title>
  <link rel="stylesheet" href="Booking/CSS/variables.css">
  <link rel="stylesheet" href="Booking/CSS/bookingStyles.css">
</head>
<body>

<%
  Car car = (Car) request.getAttribute("car");
  Integer userId = (Integer) request.getAttribute("user");
%>

<div class="wrapper">
  <% if (car != null) { %>
  <div class="glass">
    <div class="car">
      <img src="<%= car.getImage_url() %>" alt="Car image" class="car-details-image" >

      <div class="car_info">
        <div class="glass-header">
          <h1><%= car.getBrand() %> <%= car.getModel() %> <span class="year">(<%= car.getYear() %>)</span></h1>
          <p class="price">$<%= car.getPrice_per_day() %> / day</p>
        </div>
        <p class="car_desc" style="text-align: center"><%= car.getDescription() %></p>

        <div class="form_group" style="text-align: center">
          <% if (userId != null) { %>
          <form action="booking.jsp" method="get">
            <input type="hidden" name="userId" value="<%= userId %>">
            <input type="hidden" name="carId" value="<%= car.getId() %>">
            <button type="submit" class="btn">BOOK NOW</button>
          </form>
          <% } else { %>
          <a href="${pageContext.request.contextPath}/signUp?car=<%= car.getId() %>" class="btn">SIGN UP TO BOOK</a>
          <% } %>
        </div>
      </div>
    </div>
  </div>
  <% } else { %>
  <p class="status3">Car not found.</p>
  <% } %>
</div>

</body>
</html>
