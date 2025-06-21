<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 6/21/2025
  Time: 12:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>review</title>
</head>
<body>
    <h1>Write your review:</h1>
    <form action="submitReview" method="post">
        <input type="hidden" name="carId" value="${car.id}">
        <input type="hidden" name="userId" value="${user.id}">

        <label>Rating:</label>
        <input type="number" name="rating" min="1" max="5" required><br>

        <label>Comment:</label><br>
        <textarea name="comment" rows="4" cols="30"></textarea><br>

        <input type="submit" value="Submit Review">
    </form>
</body>
</html>
