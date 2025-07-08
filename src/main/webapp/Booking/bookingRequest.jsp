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

  List<Booking> bookings = BookingRepository.getCarBookings(carId);
  List<String> bookedDates = new ArrayList<>();

  for(Booking b : bookings){
    LocalDate start = b.getStartDate().toLocalDate();
    LocalDate end = b.getEndDate().toLocalDate();

    System.out.println(start.toString());
    System.out.println(end.toString());

    while(!start.isAfter(end)){
      bookedDates.add(start.toString());
      start = start.plusDays(1);
    }
  }
%>

<html>
  <head>
    <title>Booking</title>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

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


  <script>
    bookedDates = [
      <% for (int i = 0; i < bookedDates.size(); i++) { %>
      "<%= bookedDates.get(i) %>"<%= (i < bookedDates.size() - 1 ? "," : "") %>
      <% } %>
    ];
    pricePerDay = <%= pricePerDay %>;
  </script>
  <script src="booking_calendar.js"></script>

  </body>
</html>
