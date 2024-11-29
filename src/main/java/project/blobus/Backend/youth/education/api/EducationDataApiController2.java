package project.blobus.Backend.youth.education.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;

//온통청년 API를 사용해서 지역 : 부산, 카테고리 : 교육으로 데이터 가져오는 로직
//공공데이터 사이트의 부산청년정책 API를 사용해서
@RestController
@RequiredArgsConstructor
@RequestMapping("/education2")
public class EducationDataApiController2 {

    @Value("${serviceKey2}")
    private String serviceKey2;

    private final RestTemplate restTemplate;
    private static final String POLICY_URL = "https://api.odcloud.kr/api/15114204/v1/uddi:a4c0ab77-2617-4ef5-a96e-a870e364449c";

    @GetMapping("/policies")
    public ResponseEntity<String> getFinancePolicy(
            @RequestParam(defaultValue = "2") int page, // 페이지 번호, 기본값 1
            @RequestParam(defaultValue = "100") int perPage // 출력 건수, 기본값 10
    ) {
        try {
            // URL 생성
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(POLICY_URL)
//                    .queryParam("page", page)
//                    .queryParam("perPage", perPage)
//                    .queryParam("serviceKey", serviceKey3);

//            String url = builder.toUriString();
            String url = POLICY_URL + "?page=" + page + "&perPage=" + perPage + "&serviceKey="
                    + URLEncoder.encode(serviceKey2, "utf8");
            System.out.println(url);
            // 외부 API 호출
            String response = restTemplate.getForObject(new URI(url), String.class);
//            String response = restTemplate.getForObject(
//            "https://api.odcloud.kr/api/15114204/v1/uddi:a4c0ab77-2617-4ef5-a96e-a870e364449c?page=1&perPage=10&serviceKey=2PMSGf24P4WlpQMiiZLQf3%2BEL13FVMpP5C3iNSA7ySwP5XImMga%2FF7oMoTh0hB5lXmKHuM2i0dC6Ws2eBjJhxg%3D%3D"
//            , String.class);

            // 성공적인 응답 반환
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching finance policy data: " + e.getMessage());
        }
    }
}