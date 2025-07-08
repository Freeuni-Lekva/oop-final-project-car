package org.example.car.AIAssistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final String KEY = "AIzaSyB3jDGkifi1jNGn9Y86vOStVECBBNOSUCE";
    private final String AIModel = "models/gemini-1.5-flash";
    private final String URL = "https://generativelanguage.googleapis.com/v1/" + AIModel + ":generateContent?key=" + KEY;

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

        List<Car> cars = CarRepository.getAllCars();
        List<Booking> bookings = BookingRepository.getBookings();
        List<Review> reviews = ReviewRepository.getReviews();

        StringBuilder sb = new StringBuilder();
        sb.append("you are assistant for car rental website. ONLY answer questions related to data below\n\n");

        sb.append("\n=== Cars ===\n");
        for(Car car: cars) {
            String s = String.format("Car ID: %d: Brand - %s; Model - %s; Year of release - %d; Price per day - %.2f; Description - %s",
                    car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getPrice_per_day(), car.getDescription());
            sb.append(s).append("\n");
        }

        sb.append("\n=== Bookings ===\n");
        for(Booking b: bookings){
            String s = String.format("Car with ID: %d is booked from %s to %s", b.getCarId(), b.getStartDate(), b.getEndDate());
            sb.append(s).append("\n");
        }

        sb.append("\n=== Reviews ===\n");
        for(Review r: reviews){
            String s = String.format("Car with ID: %d is rated %d/5", r.getCarId(), r.getRating());
            sb.append(s).append("\n");
        }


        sb.append("\nremember you ONLY answer questions about data above\n\n");
        sb.append("User question: ").append(message);

        return sb.toString();
    }



}

