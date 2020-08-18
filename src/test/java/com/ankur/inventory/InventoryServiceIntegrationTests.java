package com.ankur.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class InventoryServiceIntegrationTests {

    @Test
    public void listAll(){
        final String uri = "http://localhost:7000/inventory/services/listall";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("client-id","aa");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        System.out.println(result);
    }

    @Test
    public void findById(){
        final String uri = "http://localhost:7000/inventory/services/findById";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("client-id","aa");

        String request ="{\n" +
                "  \"itemId\": 1\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        System.out.println(result);
    }
}
