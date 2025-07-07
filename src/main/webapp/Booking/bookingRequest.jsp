<%@ page import="java.util.List" %>
<%@ page import="org.example.car.Booking" %>
<%@ page import="org.example.car.BookingSystem.Repository.BookingRepository" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  int carId = 1;
  String carName = "Toyota";
  Double pricePerDay = 50.0;
  int userId = 1;

  List<Booking> bookings = BookingRepository.getCarBookings(carId);
  List<String> bookedDates = new ArrayList<>();

  for(Booking b : bookings){
    LocalDate start = b.getStartDate().toLocalDate();
    LocalDate end = b.getEndDate().toLocalDate();

    while(!start.isAfter(end)){
      bookedDates.add(start.toString());
      start = start.plusDays(1);
    }
  }
%>

<html>
  <head>
    <title>Booking</title>
  </head>
  <body>

  <h2>Book <%= carName %></h2>

  <form action="${pageContext.request.contextPath}/BookingController" method="post" id="bookingForm">

    <input type = "hidden" name = "carId" value = "<%= carId%>">
    <input type = "hidden" name = "carName" value = "<%= carName%>">
    <input type = "hidden" name = "pricePerDay" value = "<%= pricePerDay%>">
    <input type = "hidden" name = "userId" value = "<%= userId%>">

    <label for = "from">Pick-Up Date</label>
    <input type = "text" id = "from" name = "startDate" readonly required><br>

    <label for = "to">Return Date:</label>
    <input type = "text" id = "to" name = "endDate" readonly required><br><br>

    <div>
      Days: <span id = "totalDays">0</span><br>
      Total Price: <span id="totalPrice">$0.00</span>
    </div><br>

    <button type = "submit">Book</button>
  </form>

  </body>
</html>
