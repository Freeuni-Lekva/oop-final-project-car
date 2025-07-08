<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.car.BookingSystem.Booking" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="org.example.car.Review.Review" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="org.example.car.BookingSystem.Repository.BookingRepository" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 7/7/2025
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Booking> current = BookingRepository.getBookingsByUserId(1);
    List<Booking> past = BookingRepository.getBookingsByUserId(1);

    List<Booking> future = BookingRepository.getBookingsByUserId(1);

    List<Review> reviews = new ArrayList<>();
    reviews.add(new Review(1, 1, 3, 5, "Great car!"));
    reviews.add(new Review(2, 1, 1, 4, "Comfortable and clean."));

    request.setAttribute("currentBookings", current);
    request.setAttribute("pastBookings", past);
    request.setAttribute("futureBookings", future);
    request.setAttribute("userReviews", reviews);

%>
<html>
<head>
    <title>Your Dashboard</title>
    <style>
        body {
            background-color: #e8f4fa;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            color: #023047;
        }

        header {
            background-color: #219ebc;
            padding: 1rem;
            text-align: center;
            color: white;
        }

        section {
            max-width: 900px;
            margin: 2rem auto;
            padding: 1rem;
        }

        h2 {
            border-bottom: 2px solid #219ebc;
            padding-bottom: 0.3rem;
            margin-top: 2rem;
        }

        .card {
            background-color: white;
            border-left: 4px solid #219ebc;
            margin: 1rem 0;
            padding: 1rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            border-radius: 6px;
        }

        .btn {
            background-color: #fb8500;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #ffb703;
        }
    </style>
</head>
<body>

<header>
    <h1>Welcome to Your Dashboard</h1>
</header>

<section>
    <h2>Current Bookings</h2>
    <c:forEach var="b" items="${currentBookings}">
        <div class="card">
            <p><strong>Car ID:</strong> ${b.carId}</p>
            <p><strong>From:</strong> ${b.startDate} <strong>To:</strong> ${b.endDate}</p>
        </div>
    </c:forEach>

    <h2>Past Bookings</h2>
    <c:forEach var="b" items="${pastBookings}">
        <div class="card">
            <p><strong>Car ID:</strong> ${b.carId}</p>
            <p><strong>From:</strong> ${b.startDate} <strong>To:</strong> ${b.endDate}</p>
        </div>
    </c:forEach>

    <h2>Future Bookings</h2>
    <c:forEach var="b" items="${futureBookings}">
        <div class="card">
            <p><strong>Car ID:</strong> ${b.carId}</p>
            <p><strong>From:</strong> ${b.startDate} <strong>To:</strong> ${b.endDate}</p>

            <form action="/user" method="post" style="margin-top: 0.5rem;">
                <input type="hidden" name="action" value="cancelBooking" />
                <input type="hidden" name="bookingId" value="${b.id}" />
                <button class="btn" type="submit">Cancel Booking</button>
            </form>
        </div>
    </c:forEach>


    <h2>Your Reviews</h2>
    <c:forEach var="r" items="${userReviews}">
        <div class="card">
            <p><strong>Car ID:</strong> ${r.carId}</p>
            <p><strong>Rating:</strong> ${r.rating} / 5</p>
            <p><strong>Comment:</strong> ${r.comment}</p>

            <form action="/user" method="post" style="margin-top: 0.5rem;">
                <input type="hidden" name="action" value="deleteReview" />
                <input type="hidden" name="reviewId" value="${r.id}" />
                <button class="btn" type="submit">Delete Review</button>
            </form>
        </div>
    </c:forEach>

</section>

</body>
</html>