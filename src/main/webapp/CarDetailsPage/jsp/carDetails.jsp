<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.car.Review.Review" %>
<%@ page import="java.util.List" %>
<html>
<head>
  <title>Car Details</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Booking/CSS/variables.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CarDetailsPage/css/carDetailStyling.css">
</head>
<body>
<div class="wrapper">
  <div class="glass">
    <!-- Car Info -->
    <div class="glass-header">
      <img class="car-image" src="${car.image_url}" alt="Car Image">
      <h1>${car.brand} ${car.model}</h1>
      <p class="car_desc">${car.description}</p>
    </div>

    <!-- Review Form -->
    <div class="review-section">
      <h2 class="review-title">Write your review</h2>
      <form class="review-form" method="post" action="submitReview">
        <input type="hidden" name="carId" value="${car.id}">
        <input type="hidden" name="userId" value="${user.id}">

        <textarea name="comment" placeholder="Add a comment..." required></textarea>
        <select name="rating" required>
          <option value="">Rate the car</option>
          <c:forEach var="i" begin="1" end="5">
            <option value="${i}">${i} Star${i > 1 ? 's' : ''}</option>
          </c:forEach>
        </select>

        <button type="submit" class="btn">Submit</button>
      </form>
    </div>

    <!-- Display Reviews -->
    <div class="reviews">
      <h2 class="review-title">What others say</h2>
      <c:forEach var="review" items="${reviews}">
        <div class="review-card">
          <div class="review-user">User #${review.userId}</div>
          <div class="review-rating">⭐ ${review.rating}/5</div>
          <div class="review-comment">${review.comment}</div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>
</body>
</html>
