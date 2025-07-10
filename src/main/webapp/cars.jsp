<%--
  Created by IntelliJ IDEA.
  User: Gigi
  Date: 6/22/2025
  Time: 1:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, org.example.car.Car.Model.Car" %>
<html>
<head>
    <title>Cars Rental</title>
</head>
<body>
<h1>Available Cars</h1>
<form action="cars" method="get" style="margin-bottom: 20px;">
    <label for="sortBy">Sort by:</label>
    <select name="sortBy" id="sortBy">
        <option value="brand">Brand</option>
        <option value="price_per_day">Price</option>
        <option value="year">Year</option>
    </select>

    <input type="hidden" name="order" id="order" value="asc" />

    <button type="submit" onclick="setOrder('ASC')">
        <img src="images/arrow_up.jpg" alt="Ascending" width="20"/>
    </button>

    <button type="submit" onclick="setOrder('DESC')">
        <img src="images/arrow_down.jpg" alt="Descending" width="20"/>
    </button>
</form>

<script>
    function setOrder(order) {
        document.getElementById("order").value = order;
    }
</script>

<%
    List<Car> cars = (List<Car>) request.getAttribute("cars");
    if(!cars.isEmpty()){
        for(Car car : cars){



%>
            <div>
            <h2>
                <a href="${pageContext.request.contextPath}/cars?id=<%=car.getId()%>">
                    ${car.brand} ${car.model} (${car.year})
                </a>
            </h2>
            <br>
                <a href="${pageContext.request.contextPath}/cars?id=<%=car.getId()%>">
            <img src="${car.image_url}" width="200" alt="car">
                </a>
            <br>
            <p>Price: $${car.getPrice_per_day()} per day</p>
            </div>
<%
        }
    }else{
%>
    <p>We Have No Cars For Now :((</p>
<%
    }
%>

</body>
</html>
