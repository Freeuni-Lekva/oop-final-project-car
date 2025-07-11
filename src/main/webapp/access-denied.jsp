<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e8f4fa;
            color: #023047;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: white;
            border: 2px solid #219ebc;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(2, 48, 71, 0.1);
            text-align: center;
        }

        h1 {
            font-size: 36px;
            color: #fb8500;
            margin-bottom: 10px;
        }

        p {
            font-size: 18px;
            margin-bottom: 30px;
        }

        a.button {
            background-color: #219ebc;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.2s ease-in-out;
        }

        a.button:hover {
            background-color: #023047;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>🚫 Access Denied</h1>
    <p>You don’t have permission to access this page.</p>
    <a class="button" href="${pageContext.request.contextPath}/home.jsp">Go to Homepage</a>
</div>
</body>
</html>
