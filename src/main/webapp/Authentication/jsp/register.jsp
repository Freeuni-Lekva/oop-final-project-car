<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <link rel="stylesheet" href="../css/authStyles.css">
</head>
<body>
<div class="auth-container">
    <h2>Register</h2>
    <form method="post" action="<%=request.getContextPath()%>/register">
        <label for="full_name">Full Name</label>
        <input type="text" id="full_name" name="full_name" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />

        <button type="submit">Register</button>
    </form>
    <div class="message">
        <% String msg = request.getParameter("msg");
           if (msg != null) { %>
            <%= msg %>
        <% } %>
    </div>
    <div style="text-align:center; margin-top:18px;">
        <a href="login.jsp" style="color:var(--accent-gold);text-decoration:none;">Already have an account? Login</a>
    </div>
</div>
</body>
</html> 