<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Booking Confirmed</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Booking/CSS/success.css">
</head>
<body>

<div class = "glass">

  <div class = "glass_header">
    <div class = "status">Booking Confirmed</div>
    <h1> Congratulations ${userName}!</h1>
    <p>Your journey begins soon with <span class="highlight1">${carName}</span></p>
  </div>


  <div class = "car">
    <img src="${pageContext.request.contextPath}/${carPic}" alt="${carName}" class="car-image">
    <div class = "car_info">
        <h2>${carName}</h2>
        <p class = "car_desc">${carDetails}</p>
        <div class = "dates">
          <p><span class="highlight2">Pick-up: ${startDate} </span></p>
          <p><span class="highlight2">Return: ${endDate} </span></p>
        </div>
    </div>
  </div>

  <div class="glass-footer">
    <a class="home-btn" href="${pageContext.request.contextPath}/index.jsp">Return to Homepage</a>
  </div>

</div>

</body>
</html>
