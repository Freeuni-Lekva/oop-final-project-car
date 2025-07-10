<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 7/9/2025
  Time: 6:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Your Dashboard</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'Arial', sans-serif;
      background: url('images/background.png') no-repeat center center fixed;
      background-size: cover;
      color: #023047;
    }

    .container {
      background-color: rgba(255, 255, 255, 0.88);
      max-width: 1000px;
      margin: 2rem auto;
      padding: 2rem;
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }

    header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 2rem;
    }

    .section-title {
      font-size: 1.4rem;
      border-bottom: 2px solid #219ebc;
      padding-bottom: 0.5rem;
      margin-top: 2rem;
    }

    .profile {
      margin-top: 3rem;
    }


    .grid {
      display: flex;
      flex-wrap: wrap;
      gap: 1rem;
      margin-top: 0.5rem;
    }

    .card {
      background-color: #ffffff;
      border: 2px solid #8ecae6;
      border-radius: 10px;
      padding: 1rem;
      width: 28.5%;
      box-shadow: 0 2px 6px rgba(0,0,0,0.08);
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }

    .card-content {
      display: flex;
      gap: 1rem;
      align-items: center;
    }

    .card-text {
      flex: 1;
    }

    .car-image {
      width: 100px;
      height: auto;
      border-radius: 8px;
      object-fit: cover;
    }



    .home-link {
      display: inline-flex;
      align-items: center;
      font-weight: bold;
      color: #023047;
      text-decoration: none;
      margin-top: 1rem;
      font-size: 24px;
      margin-bottom: 2rem;
    }

    .home-icon {
      width: 24px;
      height: 24px;
      margin-right: 8px;
      transition: transform 0.2s ease;
    }

    .home-link:hover .home-icon {
      transform: scale(1.1);
    }

    .btn-action {
      background-color: #fb5800;
      color: white;
      border: none;
      padding: 0.3rem 0.6rem;
      border-radius: 4px;
      cursor: pointer;
      font-size: 0.8rem;
      position: relative;
      top: 8px;
      right: 8px;
    }

    .btn-action:hover {
      background-color: #e04a00;
    }

    form {
      display: inline;
    }

  </style>
</head>
<body>

<div class="container">
  <header>
    <div class="home-icon">
      <a href="/cars.jsp" class="home-link">
        <img src="images/home-icon.png" alt="Home" class="home-icon">
        Home
      </a>
    </div>
  </header>

  <div class="profile">
    <p>Name: ${user.full_name}</p>
    <p>ID: ${user.id}</p>
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

            <form method="post" action="/deleteReview">
              <input type="hidden" name="reviewId" value="${r.review.id}" />
              <button class="btn-action" type="submit">Delete</button>
            </form>
          </div>
        </c:if>
      </c:forEach>
    </div>
  </div>


</div>

</body>
</html>
