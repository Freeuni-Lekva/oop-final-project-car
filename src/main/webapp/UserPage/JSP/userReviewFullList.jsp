<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Your Reviews</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/UserPage/CSS/userPageStyles.css">
</head>
<body>

<div class="navbar">
  <div class="nav-left">
    <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
  </div>
  <a href="${pageContext.request.contextPath}/logout" class="btn logout-btn">Logout</a>
</div>

<div class="wrapper">
  <div class="glass">
    <div class="profile">
      <p>Name: <c:choose>
        <c:when test="${not empty targetUser}">${targetUser.full_name}</c:when>
        <c:otherwise>${user.full_name}</c:otherwise>
      </c:choose></p>
    </div>
    <div>
      <div class="section-title">Your Reviews</div>
      <div class="grid">
        <c:forEach var="r" items="${userReviews}" varStatus="loop">
          <div class="card">
            <div class="card-content">
              <div class="card-text">
                <p><strong>Car:</strong> ${r.car.brand} ${r.car.model}</p>
                <p><strong>Rating:</strong> ${r.review.rating} / 5</p>
                <p><strong>Comment:</strong> ${r.review.comment}</p>
              </div>
              <img src="${r.car.image_url}" alt="Car Image" class="car-image">
            </div>
            <div class="card-footer">
              <form method="post" action="${pageContext.request.contextPath}/deleteReview">
                <input type="hidden" name="reviewId" value="${r.review.id}" />
                <button class="btn btn-delete" type="submit">Delete</button>
              </form>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
</div>

</body>
</html>
