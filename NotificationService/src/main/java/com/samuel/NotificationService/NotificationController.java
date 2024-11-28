package com.samuel.NotificationService;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
@CrossOrigin("*")
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final WebClient webClient;

    public NotificationController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://USER-SERVICE").build();
    }

    @GetMapping("/send/{userId}")
    public String sendNotification(@PathVariable String userId) {
        Map<String, String> user = webClient.get()
                .uri("/users/" + userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .block();

        return "Notification sent to " + user.get("name") + " (" + user.get("email") + ")";
        //return "Hello world";
    }
}
