<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Admin/CSS/adminFormStyles.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
        <a href="${pageContext.request.contextPath}/admin-dashboard" class="home-btn" style="top: 20px; left: 180px;">Admin Dashboard</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/" class="home-btn">Home</a>
    <div class="form-wrapper">
        <div class="form-container">
            <div class="form-header">
                <h1>Edit Car</h1>
                <p>Update the car details below</p>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/updateCar" enctype="multipart/form-data">
                <input type="hidden" name="carId" value="${car.id}">

                <div class="form-group">
                    <label for="brand">Brand:</label>
                    <input type="text" id="brand" name="brand" value="${car.brand}" required>
                </div>

                <div class="form-group">
                    <label for="model">Model:</label>
                    <input type="text" id="model" name="model" value="${car.model}" required>
                </div>

                <div class="form-group">
                    <label for="year">Year:</label>
                    <input type="number" id="year" name="year" value="${car.year}" required>
                </div>

                <div class="form-group">
                    <label for="price_per_day">Price per Day:</label>
                    <input type="number" id="price_per_day" step="0.01" name="price_per_day" value="${car.price_per_day}" required>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" rows="5">${car.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="image">Image (upload):</label>
                    <input type="file" id="image" name="image" accept="image/*">
                </div>

                <div class="form-group">
                    <label for="image_url_fallback">or Image URL:</label>
                    <input type="text" id="image_url_fallback" name="image_url_fallback" value="${car.image_url}" placeholder="https://example.com/image.jpg">
                </div>

                <button type="submit" class="btn">Save Changes</button>
            </form>
        </div>
    </div>
</body>
</html>
