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

<div class="navbar">
  <div class="nav-left">
    <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
  </div>
  <a href="${pageContext.request.contextPath}/logout" class="btn logout-btn">Logout</a>
</div>

<div class="wrapper">
  <div class="glass">
    <div class="profile">
      <p>Name: ${user.full_name}</p>
    </div>
    <div>
      <div class="section-title">Bookings</div>
      <div class="grid">
        <c:forEach var="bd"  items="${bookings}" varStatus="loop">
          <div class="card">
            <div class="card-content">
              <div class="card-text">
                <p><strong>Car:</strong> ${bd.car.brand} ${bd.car.model}</p>
                <p><strong>From:</strong> ${bd.booking.startDate}</p>
                <p><strong>To:</strong> ${bd.booking.endDate}</p>
              </div>
              <img src="${bd.car.image_url}" alt="Car Image" class="car-image">
            </div>
            <c:choose>
              <c:when test="${type == 'future'}">
                <div class="card-footer">
                  <form method="post" action="${pageContext.request.contextPath}/cancelBooking">
                    <input type="hidden" name="bookingId" value="${bd.booking.id}" />
                    <button class="btn btn-delete" type="submit">
                      <c:choose>
                        <c:when test="${sessionScope.user.admin}">Delete</c:when>
                        <c:otherwise>Cancel</c:otherwise>
                      </c:choose>
                    </button>
                  </form>
                </div>
              </c:when>
              <c:when test="${type == 'current' || type == 'past'}">
                <c:if test="${sessionScope.user.admin}">
                  <div class="card-footer">
                    <form method="post" action="${pageContext.request.contextPath}/cancelBooking">
                      <input type="hidden" name="bookingId" value="${bd.booking.id}" />
                      <button class="btn btn-delete" type="submit">Delete</button>
                    </form>
                  </div>
                </c:if>
              </c:when>
            </c:choose>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
</div>

</body>
</html>
