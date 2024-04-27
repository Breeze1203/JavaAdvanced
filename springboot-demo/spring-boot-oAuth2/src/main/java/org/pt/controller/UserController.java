package org.pt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class UserController {
    @Value("${github.clientId}")
    private String clientId;

    @Value("${github.clientSecret}")
    private String clientSecret;


    @GetMapping("/authorize/redirect")
    public String githubCallback(@RequestParam("code") String code) {
        String tokenUrl = "https://github.com/login/oauth/access_token";
        String requestBody = "client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            // Extract access token from response body
            String[] parts = response.getBody().split("&");
            for (String part : parts) {
                System.out.println(part);
                if (part.startsWith("access_token")) {
                    String[] split = part.split("=");
                    return split[1];
                }
            }
        }
        return null;
    }

}
