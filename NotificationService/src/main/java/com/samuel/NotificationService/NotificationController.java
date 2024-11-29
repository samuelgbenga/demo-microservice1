package com.samuel.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
@CrossOrigin("*")
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final WebClient webClient;

    @Autowired
    public NotificationController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/send/{userId}")
    public String sendNotification(@PathVariable String userId) {
        Map<String, String> user = webClient.get()
                .uri("http://USER-SERVICE/users/" + userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .block();

        return "Notification sent to " + user.get("name") + " (" + user.get("email") + ")";
        //return "Hello world";
    }


//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public NotificationController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @GetMapping("/send/{userId}")
//    public String sendNotification(@PathVariable String userId) {
//        // Make a REST call to fetch user details
//        String url = "http://USER-SERVICE/users/" + userId;
//        Map<String, String> user = restTemplate.getForObject(url, Map.class);
//
//        // Return the notification message
//        return "Notification sent to " + user.get("name") + " (" + user.get("email") + ")";
//    }
}
