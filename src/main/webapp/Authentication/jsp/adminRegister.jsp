<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Registration</title>
    <link rel="stylesheet" href="../css/authStyles.css">
</head>
<body>
<div class="auth-container">
    <h2>Admin Register</h2>
    <form method="post" action="<%=request.getContextPath()%>/admin-register">
        <label for="full_name">Full Name</label>
        <input type="text" id="full_name" name="full_name" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />

        <label for="invitation_code">Invitation Code</label>
        <input type="text" id="invitation_code" name="invitation_code" required />

        <input type="hidden" name="is_admin" value="true" />

        <button type="submit">Register as Admin</button>
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