package project.blobus.Backend.youth.education.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//온통청년 API를 사용해서 지역 : 부산, 카테고리 : 교육으로 데이터 가져오는 로직
@RestController
@RequiredArgsConstructor
@RequestMapping("/education1")
public class EducationDataApiController {

    @Value("${serviceKey1}")
    private String serviceKey1;

    private final RestTemplate restTemplate;
    private final EducationDataApiService educationDataApiService;
    private static final String POLICY_URL = "https://www.youthcenter.go.kr/opi/youthPlcyList.do";

    @GetMapping("/policies")
    public ResponseEntity<String> getFinancePolicy(
            @RequestParam(defaultValue = "100") int display, // 출력 건수, 기본값 10
            @RequestParam(defaultValue = "1") int pageIndex, // 페이지 번호, 기본값 1
            @RequestParam(defaultValue = "023030") String bizTycdSel, // 교육 분야
            @RequestParam(defaultValue = "003002002") String srchPolyBizSecd // 부산지역
    ) {
        try {
            // URL 생성
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(POLICY_URL)
                    .queryParam("openApiVlak", serviceKey1)
                    .queryParam("display", display)
                    .queryParam("pageIndex", pageIndex)
                    .queryParam("srchPolyBizSecd", srchPolyBizSecd)
                    .queryParam("bizTycdSel", bizTycdSel);

            String url = builder.toUriString();
            System.out.println(url);

            // 외부 API 호출
            String response = restTemplate.getForObject(url, String.class);

            // XML 응답을 JSON으로 변환
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(response.getBytes());

            // JSON 데이터를 DTO로 변환하고 서비스 계층을 통해 저장
            // 저장되는 로직 추가는 했는데 아직 안돌려봄.
            // 즉, 코드는 짰는데 동작은 안시켜봄. 나중에 동작해봐야함
            // 꼭 주석풀고 해봐야됨!!!!
//            EducationDataApiDTO educationDataApiDTO = new ObjectMapper().treeToValue(jsonNode, EducationDataApiDTO.class);
//            educationDataApiService.savePolicy(educationDataApiDTO);

            // youthPolicy 배열 추출
            JsonNode youthPolicyArray = jsonNode.get("youthPolicy");

            // 각 항목을 DTO로 변환하고 저장
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode policyNode : youthPolicyArray) {
                EducationDataApiDTO educationDataApiDTO = objectMapper.treeToValue(policyNode, EducationDataApiDTO.class);
                educationDataApiService.savePolicy(educationDataApiDTO);
            }

            // 성공적인 응답 반환
            return ResponseEntity.ok(jsonNode.toString());

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 에러 메시지 반환
            return ResponseEntity.status(500).body("Error fetching finance policy data: " + e.getMessage());
        }
    }
}