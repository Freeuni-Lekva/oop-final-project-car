<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 7/7/2025
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Page</title>
</head>
<body>
    <h2>Current Bookings</h2>
    <c:forEach var="b" items="${currentBookings}">
        <p>Car ID: ${b.carId} | From: ${b.startDate} To: ${b.endDate}</p>
    </c:forEach>

    <h2>Past Bookings</h2>
    <c:forEach var="b" items="${pastBookings}">
        <p>Car ID: ${b.carId} | From: ${b.startDate} To: ${b.endDate}</p>
    </c:forEach>

    <h2>Future Bookings</h2>
    <c:forEach var="b" items="${futureBookings}">
        <p>Car ID: ${b.carId} | From: ${b.startDate} To: ${b.endDate}</p>
    </c:forEach>

    <h2>Your Reviews</h2>
    <c:forEach var="r" items="${userReviews}">
        <p>Car ID: ${r.carId} | Rating: ${r.rating} | Comment: ${r.comment}</p>
    </c:forEach>


</body>
</html>
