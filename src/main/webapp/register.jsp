<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; }
        .register-container {
            width: 350px;
            margin: 60px auto;
            padding: 30px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .register-container h2 { text-align: center; margin-bottom: 20px; }
        .register-container label { display: block; margin-top: 10px; }
        .register-container input[type="text"],
        .register-container input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .register-container button {
            width: 100%;
            padding: 10px;
            margin-top: 18px;
            background: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .register-container button:hover { background: #0056b3; }
        .message { margin-top: 15px; text-align: center; color: red; }
        .success { color: green; }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Register</h2>
    <form method="post" action="register">
        <label for="full_name">Full Name</label>
        <input type="text" id="full_name" name="full_name" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />

        <button type="submit">Register</button>
    </form>
    <%-- Placeholders for messages --%>
    <div class="message">
        <% String msg = request.getParameter("msg");
           if (msg != null) { %>
            <%= msg %>
        <% } %>
    </div>
</div>
</body>
</html> 