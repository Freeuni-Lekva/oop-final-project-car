<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Your Reviews</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/UserPage/CSS/userPageStyles.css">
</head>
<body>

<div class="wrapper">
  <div class="glass">
    <div class="navbar">
      <div class="nav-left">
        <a href="${pageContext.request.contextPath}/HPcontroller" class="home-link">
          <img src="${pageContext.request.contextPath}/images/home-icon.png" alt="Home" class="home-icon">
          Home
        </a>
      </div>
    </div>

  <div class="profile">
    <p>Name: ${user.full_name}</p>
  </div>

  <div>
    <div class="section-title">Your Reviews</div>
    <div class="grid">
      <c:forEach var="r" items="${userReviews}" varStatus="loop">
        <c:if test="${loop.index < 3}">
          <div class="card">
            <div class="card-content">
              <div class="card-text">
                <p><strong>Car:</strong> ${r.car.brand} ${r.car.model}</p>
                <p><strong>Rating:</strong> ${r.review.rating} / 5</p>
                <p><strong>Comment:</strong> ${r.review.comment}</p>
              </div>
              <img src="${r.car.image_url}" alt="Car Image" class="car-image">
            </div>

            <form method="post" action="${pageContext.request.contextPath}/deleteReview">
              <input type="hidden" name="reviewId" value="${r.review.id}" />
              <button class="btn-action" type="submit">Delete</button>
            </form>
          </div>
        </c:if>
      </c:forEach>
    </div>
  </div>
    </div>
  </div>

</body>
</html>
