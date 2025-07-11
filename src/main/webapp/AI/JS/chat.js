function toggleChat() {
    const chatWindow = document.getElementById('chatWindow');
    chatWindow.classList.toggle('active');

    if (chatWindow.classList.contains('active')) {
        localStorage.setItem('chatOpen', 'true');
    }

    else {
        localStorage.setItem('chatOpen', 'false');
    }

    if (chatWindow.classList.contains('active')) {
        const messages = document.getElementById('chatMessages');
        messages.scrollTop = messages.scrollHeight;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const chatWindow = document.getElementById('chatWindow');
    const chatMessages = document.getElementById('chatMessages');

    if (localStorage.getItem('chatOpen') === 'true') {
        chatWindow.classList.add('active');
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
});
