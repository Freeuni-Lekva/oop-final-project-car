<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head><title>Add Car</title></head><body>
<h2>Add a New Car</h2>

<c:if test="${not empty errorMessage}">
  <p style="color:red">${errorMessage}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/addCar" enctype="multipart/form-data">
  Brand:        <input type="text" name="brand" required><br><br>
  Model:        <input type="text" name="model" required><br><br>
  Year:         <input type="number" name="year" required><br><br>
  Price/Day:    <input type="number" step="0.01" name="price_per_day" required><br><br>
  Description:<br>
  <textarea name="description" rows="4" cols="40"></textarea><br><br>

  Image (upload):  <input type="file" name="image"><br><br>
  or Image URL:    <input type="text" name="image_url_fallback"><br><br>

  <button type="submit">Add Car</button>
</form>
</body></html>
