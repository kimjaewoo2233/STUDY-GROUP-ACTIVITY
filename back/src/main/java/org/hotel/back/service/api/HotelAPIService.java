package org.hotel.back.service.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.hotel.back.data.request.HotelRequestData;
import org.hotel.back.data.response.HotelResponseData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class HotelAPIService {

    @Value("${web.api.key}")
    String key;

    public HotelResponseData getData(HotelRequestData request){
         request.setKey(key);
         var uri = UriComponentsBuilder
                .fromUriString("https://openapi.gg.go.kr/GSST")
                .queryParams(request.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                null,
                String.class
        );

        HotelResponseData data = parser(responseEntity.getBody());
        return data;
    }

    public HotelResponseData parser(String json) {
        ObjectMapper objectMapper = new JsonMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        HotelResponseData response = null;
        try {
           response = objectMapper.readValue(json, HotelResponseData.class);

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

}
