package org.example.car.AIAssistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiAI {
    private final String KEY = "AIzaSyB3jDGkifi1jNGn9Y86vOStVECBBNOSUCE";
    private final String AIModel = "models/gemini-1.5-flash";
    private final String URL = "https://generativelanguage.googleapis.com/v1/" + AIModel + ":generateContent?key=" + KEY;

    public String ask(String message) throws IOException {
        InputStream is = sendMessage(message);
        StringBuilder output = readOut(is);
        String finalOutput = parseOutput(output);

        return finalOutput;
    }

    private InputStream sendMessage(String message) throws IOException {
        String input = """
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
            """.formatted(message);

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
}

