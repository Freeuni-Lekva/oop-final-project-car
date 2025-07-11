<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Car</title>
</head>
<body>

<h1>Edit Car</h1>

<form method="post"
      action="${pageContext.request.contextPath}/updateCar"
      enctype="multipart/form-data">

    <input type="hidden" name="carId" value="${car.id}">

    <label>Brand:</label><br>
    <input type="text" name="brand" value="${car.brand}" required><br><br>

    <label>Model:</label><br>
    <input type="text" name="model" value="${car.model}" required><br><br>

    <label>Year:</label><br>
    <input type="number" name="year" value="${car.year}" required><br><br>

    <label>Price per Day:</label><br>
    <input type="number" step="0.01" name="price_per_day" value="${car.price_per_day}" required><br><br>

    <label>Description:</label><br>
    <textarea name="description" rows="5" cols="40">${car.description}</textarea><br><br>

    Image (upload):  <input type="file" name="image"><br><br>
    or Image URL:    <input type="text" name="image_url_fallback"
                            value="${car.image_url}"><br><br>

    <button type="submit">Save Changes</button>
</form>


</body>
</html>
