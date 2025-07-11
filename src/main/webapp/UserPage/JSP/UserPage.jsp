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
<%@ page import="org.example.car.User.Repository.UserRepository" %>
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
            <a href="${pageContext.request.contextPath}/logout" class="btn-action">Logout</a>
        </div>

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
</div>

</body>
</html>