<%@ page import="org.example.car.Car.Model.Car" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.car.User.Model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
  User user = (User) session.getAttribute("user");
  Boolean isLoggedIn = (user != null);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Car Rental Home</title>

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage/CSS/style.css">

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/AI/CSS/chat.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
  <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
</head>
<body>

<div class="navbar">
  <div class="nav-left">
    <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
    <% if (isLoggedIn) { %>
    <a href="${pageContext.request.contextPath}/userProfile" class="nav-btn">Profile</a>
    <%if(user.isAdmin()){%>
    <a href="${pageContext.request.contextPath}/admin-dashboard" class="nav-btn">Admin Dashboard</a>
    <% } }
    else { %>
    <a href="${pageContext.request.contextPath}/login?prevPage=home" class="nav-btn">Log In</a>
    <% } %>
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


    <div class="horizontal-filter-container">
      <form action="HPcontroller" method="GET">
      <div class="horizontal-filter">
        <h3 class="filter-title">PRICE RANGE</h3>
        <div class="filter-inputs">
          <input
                  type="number"
                  id="priceFrom"
                  name="priceFrom"
                  min="0"
                  placeholder="Min"
                  value="<%= request.getAttribute("from") != null ? request.getAttribute("from") : "0" %>"
          >
          <span class="filter-separator">to</span>
          <input
                  type="number"
                  id="priceTo"
                  name="priceTo"
                  min="0"
                  placeholder="Max"
                  value="<%= request.getAttribute("to") != null ? request.getAttribute("to") : "" %>"
          >
        </div>
        <div class="filter-buttons">
          <button type="submit" class="filter-button">APPLY</button>
          <button type="button" class="remove-filter-btn" onclick="removePriceFilter()">REMOVE</button>
        </div>
      </div>
      </form>
    </div>


      <div class="horizontal-filter-container">
        <form action="HPcontroller" method="GET">
        <div class="horizontal-filter">
          <h3 class="filter-title">Brand</h3>
          <div class="filter-inputs">
            <input type="text"
                   id="brand"
                   name="brand"
                   placeholder=<%= request.getAttribute("brand") %>>

          </div>
          <div class="filter-buttons">
            <button type="submit" class="filter-button">APPLY</button>
            <button type="button" class="remove-filter-btn" onclick="removeBrandFilter()">REMOVE</button>
          </div>
        </div>
        </form>
      </div>

      <div class="horizontal-filter-container">
        <form action="HPcontroller" method="GET">
        <div class="horizontal-filter">
          <h3 class="filter-title">DATE RANGE</h3>
          <div class="filter-inputs">
            <input
                    type="text"
                    id="startDate"
                    name="startDate"
                    placeholder="Start Date"
                    readonly
                    value="<%= request.getAttribute("startDate") != null ? request.getAttribute("startDate") : "" %>"
            >
            <span class="filter-separator">to</span>
            <input
                    type="text"
                    id="endDate"
                    name="endDate"
                    placeholder="End Date"
                    readonly
                    value="<%= request.getAttribute("endDate") != null ? request.getAttribute("endDate") : "" %>"
            >
          </div>
          <div class="filter-buttons">
            <button type="submit" class="filter-button">APPLY</button>
            <button type="button" class="remove-filter-btn" onclick="removeDateFilter()">REMOVE</button>
          </div>
        </div>
        </form>
      </div>





    <div class="glass-header">
      <h1>Find the perfect ride for your journey</h1>
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

  <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
    <form action="${pageContext.request.contextPath}/addCar" method="get" style="margin:1rem 0;">
      <button class="btn">➕ Add a Car</button>
    </form>
  </c:if>

  </body>
<%@ include file="../../AI/JSP/AIchat.jsp" %>
<script src="${pageContext.request.contextPath}/AI/JS/chat.js"></script>

<script>
$(function() {
    $('#startDate').datepicker({
        dateFormat: 'yy-mm-dd',
        minDate: 0,
        onSelect: function() {
            const startDate = $(this).datepicker('getDate');
            const minDate = new Date(startDate);
            minDate.setDate(minDate.getDate() + 1);
            
            $('#endDate').val('').datepicker('option', {
                minDate: minDate
            });
        }
    });

    $('#endDate').datepicker({
        dateFormat: 'yy-mm-dd',
        minDate: 1
    });
});

function removePriceFilter() {
    $('#priceFrom').val('0');
    $('#priceTo').val('');
    window.location.href = 'HPcontroller?priceFrom=0&priceTo=';
}

function removeBrandFilter() {
    $('#brand').val('');
    window.location.href = 'HPcontroller?brand=';
}

function removeDateFilter() {
    $('#startDate').val('');
    $('#endDate').val('');
    window.location.href = 'HPcontroller?startDate=&endDate=';
}





</script>

</body>
</html>