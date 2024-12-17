package project.blobus.Backend.resource.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.blobus.Backend.resource.entity.ResourceCulture;
import project.blobus.Backend.resource.repository.ResourceCultureRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ResourceCultureService {
    private final ResourceCultureRepository repository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        getResource();
    }

    public void getResource() {
        saveJson("json/culture-musical.json", "문화", "뮤지컬");
        saveJson("json/culture-dance.json", "문화", "무용");
        saveJson("json/culture-opera.json", "문화", "오페라");
        saveJson("json/culture-theater.json", "문화", "연극");
        saveJson("json/culture-tradition.json", "문화", "전통");
    }

    public void saveJson(String json, String category, String subcategory) {
        try {
            ClassPathResource resource = new ClassPathResource(json);

            Map<String, Object> jsonMap = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<Map<String, Object>>() {
                    }
            );

            Map<String, Object> response = (Map<String, Object>) jsonMap.get("response");
            Map<String, Object> body = (Map<String, Object>) response.get("body");
            Map<String, Object> items = (Map<String, Object>) body.get("items");
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

            itemList.forEach(item -> {
                String resNum = (String) ((Map<String, Object>) item.get("res_no")).get("_text");
                String title = (String) ((Map<String, Object>) item.get("title")).get("_text");

                String startDate = (String) ((Map<String, Object>) item.get("op_st_dt")).get("_text");
                String endDatee = (String) ((Map<String, Object>) item.get("op_ed_dt")).get("_text");

                String place = (String) ((Map<String, Object>) item.get("place_nm")).get("_text");
                String address = switch (place) {
                    case "어댑터씨어터" -> "부산광역시-수영구";
                    case "신세계센텀시티 9층 문화홀" -> getAddressFromPlace("신세계센텀시티");
                    case "아트센트" -> "부산광역시-영도구";
                    case "나다소극장" -> "부산광역시-남구";
                    default -> getAddressFromPlace(place);
                };

                String imgUrl = item.get("image") != null
                        ? (String) ((Map<String, Object>) item.get("image")).get("_url")
                        : null;

                String link = item.get("card") != null
                        ? (String) ((Map<String, Object>) item.get("card")).get("_url")
                        : null;

                LocalDateTime createdAt = LocalDateTime.now();

                ResourceCulture res = ResourceCulture.builder()
                        .resNum(Long.valueOf(resNum))
                        .title(title)
                        .startDate(LocalDate.parse(startDate))
                        .endDate(LocalDate.parse(endDatee))
                        .place(place)
                        .address(address)
                        .imgUrl(imgUrl)
                        .link(link)
                        .category(category)
                        .subcategory(subcategory)
                        .createdAt(createdAt)
                        .build();


                if (!repository.existsByResNum(Long.valueOf(resNum)))
                    repository.save(res);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAddressFromPlace(String place) {
        String key = "AIzaSyCn6BLAk8-4-UwpeSOHYmEsUESvepMwZGA";
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json";

        String url = apiUrl + "?address=" + place + "&key=" + key + "&language=ko";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            List<Map<String, Object>> results = (List<Map<String, Object>>) responseMap.get("results");

            if (!results.isEmpty()) {
                Map<String, Object> firstResult = results.get(0);
                String fullAddress = (String) firstResult.get("formatted_address");
                return fullAddress.split(" ")[1] + "-" + fullAddress.split(" ")[2];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
