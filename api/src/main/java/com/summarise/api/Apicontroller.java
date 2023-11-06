package com.summarise.api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Apicontroller {
    @PostMapping("/summarize")
    public String summarize(@RequestBody String textToSummarize) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = ("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"); // Retrieve your API Key
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> messagesList = new ArrayList<>();
        Map<String, String> systemMessage = new HashMap<>();

        systemMessage.put("role", "system");
        systemMessage.put("content", "You must summarize the text in as little words as possible.");
        messagesList.add(systemMessage);

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", "Summarize: " + textToSummarize);
        messagesList.add(userMessage);
        map.put("messages", messagesList);
        String jsonString = gson.toJson(map);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();
    
        try (Response response = client.newCall(request).execute()) {
          return response.body().string();
        } catch (IOException e) {
          e.printStackTrace();
          return "An error occurred";
        }
      }
    
}
