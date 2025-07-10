<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.car.Car.Model.Car" %>
<html>
<head>
  <title>Car Details</title>
  <!-- Tailwind CDN -->
  <script src="https://cdn.tailwindcss.com"></script>
  <script>
    tailwind.config = {
      theme: {
        extend: {
          colors: {
            sky_blue: '#8ecae6',
            blue_green: '#219ebc',
            prussian_blue: '#023047',
            selective_yellow: '#ffb703',
            ut_orange: '#fb8500',
          }
        }
      }
    }
  </script>
</head>
<body class="bg-sky_blue-100 font-sans p-6">

<%
  Car car = (Car) request.getAttribute("car");
  Integer userId = (Integer) request.getAttribute("user");
%>

<% if (car != null) { %>
<div class="max-w-5xl mx-auto bg-white rounded-2xl shadow-lg p-8 flex flex-col md:flex-row items-start gap-8">
  <div class="md:w-2/3">
    <h1 class="text-3xl font-bold text-prussian_blue mb-4"><%= car.getBrand() %> <%= car.getModel() %> (<%= car.getYear() %>)</h1>
    <img src="<%= car.getImage_url() %>" alt="Car image" class="w-full rounded-xl mb-4 shadow">
    <p class="text-lg text-gray-700 mb-2"><span class="font-semibold text-ut_orange">Price per day:</span> $<%= car.getPrice_per_day() %></p>
    <p class="text-gray-600"><%= car.getDescription() %></p>
  </div>

  <div class="md:w-1/3 flex justify-center items-center">
    <% if (userId != null) { %>
    <form action="booking.jsp" method="get">

      <br>
      // ES  NAWILIA BOOKINGI eseigi Tipi aris adloginebuli da misi userID gvaq.//
      <br>
      <input type="hidden" name="userId" value="<%= userId %>">
      <input type="hidden" name="carId" value="<%= car.getId() %>">
      <button
              type="submit"
              class="bg-ut_orange hover:bg-selective_yellow text-white font-semibold py-3 px-6 rounded-xl shadow transition-all duration-300 text-lg"
      >
        BOOK NOW
      </button>
    </form>
    <% } else { %>
    <a
            href="${pageContext.request.contextPath}/signUp?car=<%= car.getId() %>"
            class="bg-ut_orange hover:bg-selective_yellow text-white font-semibold py-3 px-6 rounded-xl shadow transition-all duration-300 text-lg"
    >
      SIGN UP TO BOOK
    </a>
    <% } %>
  </div>
</div>
<% } else { %>
<p class="text-center text-red-500 font-semibold">Car not found.</p>
<% } %>

</body>
</html>
