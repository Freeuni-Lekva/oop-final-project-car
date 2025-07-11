<%
    Object user = session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
        return;
    }
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Your Bookings</title>
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
    <div class="section-title"> Bookings</div>
    <div class="grid">
      <c:forEach var="bd"  items="${bookings}" varStatus="loop">
        <c:if test="${loop.index < 3}">
          <div class="card">
            <div class="card-content">
              <div class="card-text">
                <p><strong>Car:</strong> ${bd.car.brand} ${bd.car.model}</p>
                <p><strong>From:</strong> ${bd.booking.startDate}</p>
                <p><strong>To:</strong> ${bd.booking.endDate}</p>
              </div>
              <img src="${bd.car.image_url}" alt="Car Image" class="car-image">
            </div>

            <c:if test="${type == 'future'}">
              <form method="post" action="${pageContext.request.contextPath}/cancelBooking">
                <input type="hidden" name="bookingId" value="${bd.booking.id}" />
                <button class="btn-action" type="submit">Cancel</button>
              </form>
            </c:if>


          </div>
        </c:if>
      </c:forEach>
    </div>
  </div>
    </div>
  </div>

</body>
</html>
