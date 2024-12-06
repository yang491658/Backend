package project.blobus.Backend.resource.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import project.blobus.Backend.resource.entity.ResourceCulture;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ResourceCultureRepositoryTest {
    @Autowired
    private ResourceCultureRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test() {
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
                String title = (String) ((Map<String, Object>) item.get("title")).get("_text");
                String place = (String) ((Map<String, Object>) item.get("place_nm")).get("_text");

                String address = null;
                switch (place) {
                    case "어댑터씨어터":
                        address = "부산광역시-수영구";
                        break;
                    case "신세계센텀시티 9층 문화홀":
                        address = getAddressFromPlace("신세계센텀시티");
                        break;
                    case "아트센트":
                        address = "부산광역시-영도구";
                        break;
                    case "나다소극장":
                        address = "부산광역시-남구";
                        break;
                    default:
                        address = getAddressFromPlace(place);
                        break;
                }

                LocalDateTime createdAt = LocalDateTime.now();

                ResourceCulture res = ResourceCulture.builder()
                        .title(title)
                        .address(address)
                        .category(category)
                        .subcategory(subcategory)
                        .createdAt(createdAt)
                        .build();

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