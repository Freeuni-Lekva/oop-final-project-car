<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Occurred</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .error-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #d9534f;
        }
        .error-message {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid #ebccd1;
            border-radius: 4px;
            color: #a94442;
            background-color: #f2dede;
        }
        .btn {
            display: inline-block;
            padding: 8px 12px;
            background: #337ab7;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .btn:hover {
            background: #286090;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h1>Error Occurred</h1>

    <div class="error-message">
        <c:choose>
            <c:when test="${not empty errorMessage}">
                ${errorMessage}
            </c:when>
            <c:otherwise>
                An unexpected error has occurred.
            </c:otherwise>
        </c:choose>
    </div>

    <c:choose>
        <c:when test="${not empty pageContext.request.userPrincipal}">
            <a href="${pageContext.request.contextPath}/userProfile" class="btn">Back to My Profile</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login" class="btn">Go to Login Page</a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>