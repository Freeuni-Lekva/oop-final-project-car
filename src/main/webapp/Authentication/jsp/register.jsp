<%@ page import="org.example.car.User.Model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <link rel="stylesheet" href="../css/authStyles.css">
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    Boolean isLoggedIn = (user != null);
%>

<div class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
        <% if (isLoggedIn) { %>
        <a href="${pageContext.request.contextPath}/userProfile" class="nav-btn">Profile</a>
        <%if(user.isAdmin()){%>
        <a href="${pageContext.request.contextPath}/admin-dashboard" class="nav-btn">Admin Dashboard</a>
        <% } }
        else { %>
        <a href="${pageContext.request.contextPath}/login" class="nav-btn">Log In</a>
        <% } %>
    </div>
</div>

<div class="auth-wrapper">
    <div class="auth-container">
        <div class="auth-header">
            <h2>Create Account</h2>
            <form method="post" action="<%=request.getContextPath()%>/register">
                <label for="full_name">Full Name</label>
                <input type="text" id="full_name" name="full_name" required />

                <label for="password">Password</label>
                <input type="password" id="password" name="password" required />

                <button type="submit" class="btn">Register</button>

                <div class="message">
                    <% String msg = request.getParameter("msg");
                        if (msg != null) { %>
                    <%= msg %>
                    <% } %>
                </div>

            </form>
            <div class="auth-footer">
                <a href="login.jsp">Already have an account? Login</a>
            </div>
        </div>
    </div>
</div>
</body>
</html> 