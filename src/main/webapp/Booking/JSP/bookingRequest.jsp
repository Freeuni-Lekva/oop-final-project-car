<%@ page import="java.util.List" %>
<%@ page import="org.example.car.BookingSystem.Booking" %>
<%@ page import="org.example.car.BookingSystem.Repository.BookingRepository" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="org.example.car.Car.Model.Car" %>
<%@ page import="org.example.car.Car.Repository.CarRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  int carId = 1;
  int userId = 1;

  Car c = CarRepository.getCarById(carId);
  String carName = c.getModel() + " " + c.getBrand();
  Double pricePerDay = c.getPrice_per_day();
  int year = c.getYear();

  List<Booking> bookings = BookingRepository.getCarBookings(carId);
  List<String> bookedDates = new ArrayList<>();

  for (Booking b : bookings) {
    LocalDate start = b.getStartDate().toLocalDate();
    LocalDate end = b.getEndDate().toLocalDate();
    while (!start.isAfter(end)) {
      bookedDates.add(start.toString());
      start = start.plusDays(1);
    }
  }
%>

<html>
<head>
  <title>Book <%= carName %></title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/Booking/CSS/bookingStyles.css">

  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
  <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
</head>
<body>

  <div class = "glass">
    <div class ="wrapper">
    <div class = "glass-header">
      <div class = "status1">Please select booking days</div>
      <h1><%= carName %> <span class = "year">(<%= year %>)</span></h1>
      <p class = "price">$<%= pricePerDay %> / day</p>
    </div>

    <form action="${pageContext.request.contextPath}/BookingController" method="post" id="bookingForm">
      <input type="hidden" name="carId" value="<%= carId %>">
      <input type="hidden" name="carName" value="<%= carName %>">
      <input type="hidden" name="pricePerDay" value="<%= pricePerDay %>">
      <input type="hidden" name="userId" value="<%= userId %>">

      <div class="form-group">
        <label for="from">Pick-Up Date</label>
        <input type="text" id="from" name="startDate" readonly required>
      </div>

      <div class="form-group">
        <label for="to">Return Date</label>
        <input type="text" id="to" name="endDate" readonly required>
      </div>

      <div class="form-summary">
        <p>Days: <span id="totalDays">0</span></p>
        <p>Total: <span id="totalPrice">$0.00</span></p>
      </div>

        <button type="submit" class="btn">Confirm Booking</button>
      </form>
    </div>
  </div>

<script>
  bookedDates = [
    <% for (int i = 0; i < bookedDates.size(); i++) { %>
    "<%= bookedDates.get(i) %>"<%= (i < bookedDates.size() - 1 ? "," : "") %>
    <% } %>
  ];
  pricePerDay = <%= pricePerDay %>;
</script>
<script src="${pageContext.request.contextPath}/Booking/JS/booking_calendar.js"></script>

</body>
</html>
