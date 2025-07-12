package org.example.car.AIAssistant.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.car.AIAssistant.Repository.AIRepository;
import org.example.car.BookingSystem.Booking;
import org.example.car.BookingSystem.Repository.BookingRepository;
import org.example.car.Car.Model.Car;
import org.example.car.Car.Repository.CarRepository;
import org.example.car.Review.Repository.ReviewRepository;
import org.example.car.Review.Review;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GeminiAI {
    private final String KEY = "AIzaSyBVVNzNC6M_nrHBiRUg3-Kgv6f7ZvQVaOs";
    private final String AIModel = "models/gemini-1.5-flash";
    private final String URL = "https://generativelanguage.googleapis.com/v1/" + AIModel + ":generateContent?key=" + KEY;

    private final AIRepository repo;

    public GeminiAI(){
        repo = new AIRepository();

    }

    public String ask(String message) throws IOException {
        message = prepareMessage(message);
        InputStream is = sendMessage(message);
        StringBuilder output = readOut(is);
        String finalOutput = parseOutput(output);

        return finalOutput;
    }

    private InputStream sendMessage(String message) throws IOException {
        String input = String.format("""
            {
              "contents": [
                {
                  "parts": [
                    {
                      "text": "%s"
                    }
                  ]
                }
              ]
            }
            """, message);

        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(input.getBytes());
        }

        InputStream is = con.getErrorStream();
        if(con.getResponseCode() == 200){
            is = con.getInputStream();
        }

        return is;
    }

    private StringBuilder readOut(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            output.append(line);
        }
        return output;
    }

    private String parseOutput(StringBuilder output) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jn = mapper.readTree(output.toString());
        return jn
                .get("candidates")
                .get(0)
                .get("content")
                .get("parts")
                .get(0)
                .get("text")
                .asText()
                .trim();
    }

    private String prepareMessage(String message){


        StringBuilder sb = new StringBuilder();
        sb.append("you are assistant for car rental website. ONLY answer questions about this car rental website\n\n");

        sb.append(repo.getCarsAsMessage());
        sb.append(repo.getBookingsAsMessage());
        sb.append(repo.getReviewsAsMessage());

        sb.append("\nremember you ONLY answer questions about this car rental website\n\n");
        sb.append("User question: ").append(message);

        return sb.toString();
    }



}

