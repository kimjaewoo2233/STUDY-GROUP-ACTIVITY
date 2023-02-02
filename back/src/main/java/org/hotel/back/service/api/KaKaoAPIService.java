package org.hotel.back.service.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.hotel.back.data.response.HotelResponseData;
import org.hotel.back.data.response.KaKaoResponseData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Service
public class KaKaoAPIService {

    @Value("${kakao.api.key}")
    String key;

    public Optional<KaKaoResponseData>  getLocationInfo(String query) throws ParseException {
        var uri = UriComponentsBuilder
                .fromUriString("https://dapi.kakao.com/v2/local/search/address.json")
                .queryParam("query",query)
                .build()
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization"," KakaoAK "+key);
        System.out.println(headers.toString());

        var httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        return parser(responseEntity.getBody());

    }
    public Optional<KaKaoResponseData> parser(String json) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        if(json != null){
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            JSONArray docuArray = (JSONArray) jsonObject.get("documents");

            JSONObject docuObject = (JSONObject) docuArray.get(0);


            return Optional.of(KaKaoResponseData.builder()
                    .longitude(docuObject.get("x").toString())
                    .latitude(docuObject.get("y").toString())
                    .build());
        }else{
            return Optional.
                    empty();
        }
    }
}
