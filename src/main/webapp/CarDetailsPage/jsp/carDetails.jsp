<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.car.Review.Review" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.car.User.Model.User" %>
<html>
<head>
  <title>Car Details</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Booking/CSS/variables.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CarDetailsPage/css/carDetailStyling.css">
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
    <a href="${pageContext.request.contextPath}/login?prevPage=home" class="nav-btn">Log In</a>
    <% } %>
  </div>
</div>


<div class="wrapper">
  <div class="glass">

    <div class="glass-header">
      <img class="car-image" src="${car.image_url}" alt="Car Image">
      <h1>${car.brand} ${car.model} (${car.year})</h1>
      <p class="car_desc">${car.description}</p>
      <p class="price">Price per day: <span class="highlight1">$${car.price_per_day}</span></p>


      <form action="${pageContext.request.contextPath}/BookingRequestController" method="get">
        <input type="hidden" name="carId" value="${car.id}">
        <input type="hidden" name="returnUrl" value="${pageContext.request.contextPath}/car-details?car=${car.id}">
        <button type="submit" class="btn">Book Now</button>
      </form>

      <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
        <!-- manqanis daditeba mxolod admini -->
        <form action="${pageContext.request.contextPath}/editCar" method="get" style="display:inline;">
          <input type="hidden" name="carId" value="${car.id}">
          <button type="submit" class="btn2">Edit Car</button>
        </form>

        <!-- manqanis washla mxolod admini -->
        <form action="${pageContext.request.contextPath}/deleteCar" method="post" style="display:inline;"
              onsubmit="return confirm('Delete this car?');">
          <input type="hidden" name="carId" value="${car.id}">
          <button type="submit" class="btn2">Delete Car</button>
        </form>
      </c:if>

    </div>


    <div class="review-section">
      <h2 class="review-title">Write your review</h2>
      <form class="review-form" method="post" action="submitReview">
        <input type="hidden" name="carId" value="${car.id}">
        <input type="hidden" name="returnUrl" value="${pageContext.request.contextPath}/car-details?car=${car.id}">

        <textarea name="comment" placeholder="Add a comment..." required></textarea>
        <div class="star-rating">
          <input type="radio" id="star5" name="rating" value="5" required />
          <label for="star5">★</label>
          <input type="radio" id="star4" name="rating" value="4" />
          <label for="star4">★</label>
          <input type="radio" id="star3" name="rating" value="3" />
          <label for="star3">★</label>
          <input type="radio" id="star2" name="rating" value="2" />
          <label for="star2">★</label>
          <input type="radio" id="star1" name="rating" value="1" />
          <label for="star1">★</label>
        </div>



        <button type="submit" class="btn">Submit</button>
      </form>
    </div>


    <div class="reviews">
      <h2 class="review-title">What others say</h2>
      <c:forEach var="review" items="${reviews}">
        <div class="review-card">
          <div class="review-user">${review.userName}</div>
          <div class="review-rating">⭐ ${review.review.rating}/5</div>
          <div class="review-comment">${review.review.comment}</div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>
</body>
</html>
