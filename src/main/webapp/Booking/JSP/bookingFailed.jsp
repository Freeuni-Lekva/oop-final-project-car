<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Booking Failed</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/Booking/CSS/bookingStyles.css">
</head>
<body>

<div class="wrapper">
  <div class = "glass">
    <div class = "glass_header">
      <div class = "status3">We're sorry, ${userName}</div>
      <div class = "status3">There has been problem in databse, The car ${carName} is not available from ${startDate} to ${endDate} </div>
</div>
  </div>
</div>
</body>
</html>
