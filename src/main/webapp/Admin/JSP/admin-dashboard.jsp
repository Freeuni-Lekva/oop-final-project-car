<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Admin/CSS/adminDashboardStyles.css">
</head>
<body>

<div class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/HPcontroller" class="nav-btn">Home</a>
    </div>
    <a href="${pageContext.request.contextPath}/logout" class="btn logout-btn">Logout</a>
</div>

<div class="wrapper">
    <div class="glass">
        
        <div class="profile">
            <p><strong>Welcome, Admin ${admin.full_name}</strong></p>
            <p class="summary">Total Users: ${users.size()}</p>
        </div>

        <form action="${pageContext.request.contextPath}/HPcontroller" method="get">
            <button type="submit" class="car-collection-btn">See Car Collection</button>
        </form>

        <div>
            <div class="section-title">All Users</div>
            <div class="grid">
                <c:forEach var="u" items="${users}">
                    <div class="card">
                        <div class="card-content">
                            <div class="user-avatar">
                                ${u.full_name.charAt(0)}
                            </div>
                            <div class="card-text">
                                <p><strong>ID:</strong> ${u.id}</p>
                                <p><strong>Name:</strong> ${u.full_name}</p>
                            </div>
                        </div>
                        
                        <div class="card-footer">
                            <form action="${pageContext.request.contextPath}/userProfile" method="get" style="display:inline;">
                                <input type="hidden" name="userId" value="${u.id}" />
                                <button type="submit" class="btn-view">See More</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/deleteUser" method="post" style="display:inline;">
                                <input type="hidden" name="userId" value="${u.id}" />
                                <button type="submit" class="btn-delete" onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</body>
</html>
