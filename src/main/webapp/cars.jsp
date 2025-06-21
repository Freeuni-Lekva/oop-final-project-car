<%--
  Created by IntelliJ IDEA.
  User: Gigi
  Date: 6/22/2025
  Time: 1:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, org.example.car.User.Model.Car" %>
<html>
<head>
    <title>Cars Rental</title>
</head>
<body>
<h1>Available Cars</h1>
<%
    List<Car> cars = (List<Car>) request.getAttribute("cars");
    if(!cars.isEmpty()){
        for(Car car : cars){
%>
            <div>
            <h2>
                <a href="cars?id=<%=car.getId()%>">
                    ${car.brand} ${car.model} (${car.year})
                </a>
            </h2>
            <br>
                <a href="cars?id=<%=car.getId()%>">
            <img src="${car.image_url}" width="200" alt="car">
                </a>
            <br>
            <p>Price: $${car.price_per_day} per day</p>
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
