package com.serverless.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.model.RequestValue;
import com.serverless.model.StromPriceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class FetchDataService {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String URL = "https://norway-power.ffail.win/?zone={0}&date={1}";
    private static final TypeReference<Map<String, RequestValue>> TYPE_REF = new TypeReference<Map<String, RequestValue>>() {};

    @Autowired
    private RestTemplate restTemplate;

    public List<StromPriceInfo> getData(final String zone) throws JsonProcessingException {
        final HttpEntity<String> entity = getEntity();
        final String formattedUrl = getFormattedUrl(zone, getTodayDate());
        final ResponseEntity<JsonNode> response = restTemplate.exchange(formattedUrl,
                HttpMethod.GET,
                entity,
                JsonNode.class);
        return jsonToJavaObject(response.getBody());
    }

    public List<StromPriceInfo> jsonToJavaObject(final JsonNode body) throws JsonProcessingException {
        final Map<String, RequestValue> mapData = new ObjectMapper().readValue(body.toString(), TYPE_REF);
        final String todayDate = getTodayDate();

        final List<StromPriceInfo> data = new ArrayList<>();
        mapData.entrySet().forEach(entrySet -> {
            final RequestValue value = entrySet.getValue();
            try {
                data.add(new StromPriceInfo(SIMPLE_FORMAT.parse(todayDate),value.getNokPrice(), value.getValidFrom(),
                        value.getValidTo()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return data;
    }

    private HttpEntity<String> getEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    public String getTodayDate() {
        final LocalDateTime todayDate = LocalDateTime.now();
        return FORMAT.format(todayDate);
    }

    public String getFormattedUrl(final String zone, final String date) {
        return MessageFormat.format(URL,zone, date);
    }
}
