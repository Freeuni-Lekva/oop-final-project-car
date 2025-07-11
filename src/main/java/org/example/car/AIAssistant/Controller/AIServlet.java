package org.example.car.AIAssistant.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.car.AIAssistant.Service.GeminiAI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AIServlet")
public class AIServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");

        GeminiAI ai = new GeminiAI();
        String AIresponse = "123";
        //AIresponse = ai.ask(message);

        System.out.println(AIresponse);

        HttpSession session = request.getSession();

        List<String> chat = (List<String>) session.getAttribute("chat");
        if (chat == null) {
            chat = new ArrayList<>();
        }

        chat.add("you: " + message);
        chat.add("AI: " + AIresponse);

        session.setAttribute("chat", chat);

        response.sendRedirect("/car_war_exploded/HPcontroller");


    }
}
