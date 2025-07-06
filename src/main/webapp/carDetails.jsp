<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 7/6/2025
  Time: 2:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.car.Car.Model.Car" %>
<html>
<head>
  <title>Car Details</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      padding: 40px;
    }
    .car-container {
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 20px;
      max-width: 700px;
      margin: auto;
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }
    .car-container img {
      max-width: 100%;
      border-radius: 8px;
    }
  </style>
</head>
<body>

<%
  Car car = (Car) request.getAttribute("car");

  if (car != null) {
%>
<div class="car-container">
  <h1><%= car.getBrand() %> <%= car.getModel() %> (<%= car.getYear() %>)</h1>
  <p><strong>Price per day:</strong> $<%= car.getPrice_per_day() %></p>
  <p><strong>Description:</strong> <%= car.getDescription() %></p>

  <% if (car.getImage_url() != null && !car.getImage_url().isEmpty()) { %>
  <img src="<%= car.getImage_url() %>" alt="Car image">
  <% } else { %>
  <p><em>No image available.</em></p>
  <% } %>
</div>
<%
} else {
%>
<p>Car not found.</p>
<%
  }
%>

</body>
</html>

