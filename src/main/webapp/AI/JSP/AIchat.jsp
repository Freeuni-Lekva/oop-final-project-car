<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="chat-popup-container">
  <div class="chat-toggle-btn" onclick="toggleChat()">
    <i class="fas fa-comment"></i>
  </div>

  <div class="chat-window" id="chatWindow">
    <div class="chat-header">
      <h3>Car Rental Assistant</h3>
      <span class="close-chat" onclick="toggleChat()">&times;</span>
    </div>

    <div class="chat-messages" id="chatMessages">
      <%
        List<String> chat = (List<String>) session.getAttribute("chat");
        if (chat != null) {
          for (String msg : chat) {
      %>
      <div class="message <%= msg.startsWith("You:") ? "user-msg" : "bot-msg" %>">
        <%= msg %>
      </div>
      <%
          }
        }
      %>
    </div>

    <form class="chat-input" action="${pageContext.request.contextPath}/AIServlet" method="post">
      <input type="text" name="message" placeholder="Ask about cars" required>
      <button type="submit"><i class="fas fa-paper-plane"></i></button>
    </form>
  </div>
</div>

<script>
  function toggleChat() {
    const chatWindow = document.getElementById('chatWindow');
    chatWindow.classList.toggle('active');
    if(chatWindow.classList.contains('active')) {
      const messages = document.getElementById('chatMessages');
      messages.scrollTop = messages.scrollHeight;
    }
  }

  document.addEventListener('DOMContentLoaded', function() {
    const messages = document.getElementById('chatMessages');
    messages.scrollTop = messages.scrollHeight;
  });
</script>