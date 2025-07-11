<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.car.BookingSystem.Booking" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="org.example.car.Review.Review" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="org.example.car.BookingSystem.Repository.BookingRepository" %>
<%@ page import="org.example.car.User.Model.User" %>
<%@ page import="org.example.car.User.Repository.UserRepository" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 7/7/2025
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Object user = session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/Authentication/jsp/login.jsp?msg=Please+login+first");
        return;
    }
%>

<%
//    User user = new User(1,"Tekla Vadakaria", "daj", false);
//    List<Booking> current = BookingRepository.getBookingsByUserId(1);
//    List<Booking> past = BookingRepository.getBookingsByUserId(1);
//
//    List<Booking> future = BookingRepository.getBookingsByUserId(1);
//
//    List<Review> reviews = new ArrayList<>();
//    reviews.add(new Review(1, 1, 3, 5, "Great car!"));
//    reviews.add(new Review(2, 1, 1, 4, "Comfortable and clean."));
//
//    request.setAttribute("currentBookings", current);
//    request.setAttribute("pastBookings", past);
//    request.setAttribute("futureBookings", future);
//    request.setAttribute("userReviews", reviews);
//    request.setAttribute("user", user);

%>
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

        .summary {
            margin: 1rem 0;
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

        .see-more {
            display: inline-block;
            margin-top: 1rem;
            padding: 0.5rem 1rem;
            background-color: #fb8500;
            color: white;
            border-radius: 6px;
            text-decoration: none;
            font-size: 0.9rem;
        }

        .see-more:hover {
            background-color: #ffb703;
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
            <a href="${pageContext.request.contextPath}/HPcontroller" class="home-link">
                <img src="images/home-icon.png" alt="Home" class="home-icon">
                Home
            </a>
        </div>
        <a href="logout" class="btn-action">Logout</a>
    </header>

    <div class="profile">
        <p>Name: ${user.full_name}</p>
        <p>ID: ${user.id}</p>
        <p class="summary">Rented Cars: ${rentedCount}</p>
        <p class="summary">Reviews Given: ${userReviews.size()}</p>
    </div>

    <div>
        <div class="section-title">Current Bookings</div>
        <div class="grid">
            <c:forEach var="bd" items="${currentBookings}" varStatus="loop">
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

                        <c:if test="${sessionScope.user.admin}">
                            <form method="post" action="${pageContext.request.contextPath}/cancelBooking"
                                  onsubmit="return confirm('Delete this booking?');">
                                <input type="hidden" name="bookingId" value="${bd.booking.id}" />
                                <button class="btn-action" type="submit">Delete</button>
                            </form>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <c:if test="${currentBookings.size() > 2}">
            <a class="see-more" href="${pageContext.request.contextPath}/userBookingsFull?type=current">See More</a>
        </c:if>
    </div>

    <div>
        <div class="section-title">Future Bookings</div>
        <div class="grid">
            <c:forEach var="bd" items="${futureBookings}" varStatus="loop">
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

                        <c:if test="${not empty bd.booking}">
                            <form method="post"  action="${pageContext.request.contextPath}/cancelBooking">
                                <input type="hidden" name="bookingId" value="${bd.booking.id}" />
                                <button class="btn-action" type="submit">Cancel</button>
                            </form>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <c:if test="${futureBookings.size() > 2}">
            <a class="see-more" href="${pageContext.request.contextPath}/userBookingsFull?type=future">See More</a>

        </c:if>
    </div>

    <div>
        <div class="section-title">Past Bookings</div>
        <div class="grid">
            <c:forEach var="bd" items="${pastBookings}" varStatus="loop">
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

                        <c:if test="${sessionScope.user.admin}">
                            <form method="post" action="${pageContext.request.contextPath}/cancelBooking"
                                  onsubmit="return confirm('Delete this booking?');">
                                <input type="hidden" name="bookingId" value="${bd.booking.id}" />
                                <button class="btn-action" type="submit">Delete</button>
                            </form>
                        </c:if>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <c:if test="${pastBookings.size() > 2}">
            <a class="see-more" href="${pageContext.request.contextPath}/userBookingsFull?type=past">See More</a>

        </c:if>
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
        <c:if test="${userReviews.size() > 2}">
            <a class="see-more" href="${pageContext.request.contextPath}/userReviewsFull">See More</a>

        </c:if>
    </div>
</div>

</body>
</html>