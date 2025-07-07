<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 7/7/2025
  Time: 8:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String status = (String) session.getAttribute("status");
    if (status == null || !"admin".equals(status)) {
        response.sendRedirect("/login.jsp");
        return;
    }
%>

<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
<h1>Admin Dashboard</h1>

<h2>Users</h2>
<c:forEach var="user" items="${users}">
    <p>${user.name} (${user.email})
    <form method="post" action="/admin" style="display:inline">
        <input type="hidden"  name="action" value="deleteUser" />
        <input type="hidden" name="id"  value="${user.id}" />
        <button type="submit">Delete</button>
    </form>
    </p>
</c:forEach>

<h2>Reviews</h2>
<c:forEach var="review" items="${reviews}">
    <p>${review.comment} (Rating: ${review.rating}) for car ${review.carId}
    <form method="post" action="/admin" style="display:inline">
        <input type="hidden" name="action" value="deleteReview" />
        <input type="hidden"  name="id" value="${review.id}" />
        <button type="submit">Delete</button>
    </form>
    </p>
</c:forEach>

<h2>Bookings</h2>
<c:forEach var="booking" items="${bookings}">
    <p>User ${booking.userId} booked Car ${booking.carId} from ${booking.startDate} to ${booking.endDate}
    <form method="post" action="/admin" style="display:inline">
        <input type="hidden"  name="action" value="deleteBooking" />
        <input type="hidden" name="id" value="${booking.id}" />
        <button type="submit">Delete</button>
    </form>
    </p>
</c:forEach>

</body>
</html>

