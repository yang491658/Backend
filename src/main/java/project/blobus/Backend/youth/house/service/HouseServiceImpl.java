package project.blobus.Backend.youth.house.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.blobus.Backend.youth.house.dto.HouseDTO;
import project.blobus.Backend.youth.house.dto.PageRequestDTO;
import project.blobus.Backend.youth.house.dto.PageResponseDTO;
import project.blobus.Backend.youth.house.entity.HouseEntity;
import project.blobus.Backend.youth.house.repository.HouseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HouseServiceImpl implements HouseService{
    @Value("${serviceKey1}")
    private String serviceKey1; // 오픈 API 인증 키

    private final ModelMapper modelMapper;
    private final HouseRepository houseRepository;
    private final RestTemplate restTemplate;

    // BLOBUS > 청년관 > 주거
    // 1.정책현황

    // 서버 시작 시 데이터 초기화
    @PostConstruct
    public void init() {
        log.info("서버 시작 - 주거정책 오픈 API 정책 데이터 초기화 중...");
        getPolicyApi(); // 초기 데이터 로드
    }

    // 정책 오픈 API
    public void getPolicyApi() {
        // ● 필수 파라미터
        // 출력건수 : 기본값 10, 최대 100까지 가능
        int display = 100;
        // 조회할 페이지 : 기본값 1
        int pageIndex = 1;

        // ● 선택 파라미터
        //   1)정책유형
        //      - 일자리 분야(023010) / 주거 분야(023020)
        String bizTycdSel = "023020";
        //   2)지역코드
        //      - 부산(003002002)
        String srchPolyBizSecd = "003002002,003001001,003001003,003001004,003001007,003001016,003001017,003001018,003001022,003001053";

        List<HouseEntity> entityList = new ArrayList<>();

        try {
            boolean hasMorePages = true;

            while (hasMorePages) {
                // 1. URL 생성
                String url = UriComponentsBuilder.fromHttpUrl("https://www.youthcenter.go.kr/opi/youthPlcyList.do")
                        .queryParam("openApiVlak", serviceKey1)
                        .queryParam("display", display)
                        .queryParam("pageIndex", pageIndex)
                        .queryParam("bizTycdSel", bizTycdSel)
                        .queryParam("srchPolyBizSecd", srchPolyBizSecd)
                        .build()
                        .toUriString();

                // 2. API 호출
                String response = restTemplate.getForObject(url, String.class);

                // 3. XML 데이터를 JSON으로 변환
                XmlMapper xmlMapper = new XmlMapper();
                JsonNode rootNode = xmlMapper.readTree(response);

                int totalCnt = rootNode.path("totalCnt").asInt();       // api에서 가져온 데이터 갯수 추출
                JsonNode policyList =  rootNode.path("youthPolicy");    // 'youthPolicy' 키 기준으로 데이터 추출

                log.info("주거 정책 API 데이터 추출 =============================");

                // 4. 중복 체크 후 데이터 추가
                for(JsonNode node : policyList) {
                    String bizId = node.path("bizId").asText();

                    // 중복 체크
                    if (!houseRepository.existsByBizId(bizId)) {
                        String[] results = extractDates(node.path("rqutPrdCn").asText());
                        // 날짜가 null이 아니고 유효한 경우만 처리
                        LocalDate rqutPrdStart = (results != null && results.length > 0 && results[0] != null) ? LocalDate.parse(results[0]) : null;
                        LocalDate rqutPrdEnd = (results != null && results.length > 1 && results[1] != null) ? LocalDate.parse(results[1]) : null;

                        HouseEntity houseEntity = HouseEntity.builder()
                                .polyRlmCd(node.path("polyRlmCd").asText())
                                .bizId(node.path("bizId").asText())
                                .polyBizSjnm(node.path("polyBizSjnm").asText())
                                .polyItcnCn(node.path("polyItcnCn").asText())
                                .polyBizTy(node.path("polyBizTy").asText())
                                .mngtMson(node.path("mngtMson").asText())
                                .cherCtpcCn(node.path("cherCtpcCn").asText())
                                .cnsgNmor(node.path("cnsgNmor").asText())
                                .tintCherCtpcCn(node.path("tintCherCtpcCn").asText())
                                .sporCn(node.path("sporCn").asText())
                                .bizPrdCn(node.path("bizPrdCn").asText())
                                .prdRpttSecd(node.path("prdRpttSecd").asText())
                                .rqutPrdCn(node.path("rqutPrdCn").asText())
                                .rqutPrdStart(rqutPrdStart)
                                .rqutPrdEnd(rqutPrdEnd)
                                .sporScvl(node.path("sporScvl").asText())
                                .ageInfo(node.path("ageInfo").asText())
                                .prcpCn(node.path("prcpCn").asText())
                                .majrRqisCn(node.path("majrRqisCn").asText())
                                .empmSttsCn(node.path("empmSttsCn").asText())
                                .splzRlmRqisCn(node.path("splzRlmRqisCn").asText())
                                .accrRqisCn(node.path("accrRqisCn").asText())
                                .aditRscn(node.path("aditRscn").asText())
                                .prcpLmttTrgtCn(node.path("prcpLmttTrgtCn").asText())
                                .rqutProcCn(node.path("rqutProcCn").asText())
                                .jdgnPresCn(node.path("jdgnPresCn").asText())
                                .rqutUrla(node.path("rqutUrla").asText())
                                .rfcSiteUrla1(node.path("rfcSiteUrla1").asText())
                                .rfcSiteUrla2(node.path("rfcSiteUrla2").asText())
                                .pstnPaprCn(node.path("pstnPaprCn").asText())
                                .etct(node.path("etct").asText())
                                .delFlag(false)
                                .build();

                        entityList.add(houseEntity);
                    }
                }
                // 다음 페이지 확인
                pageIndex++;
                int totalPages = (int) Math.ceil((double) totalCnt / display);
                hasMorePages = pageIndex <= totalPages;
            }

            // 5. 데이터저장
            houseRepository.saveAll(entityList);
            log.info("주거정책 공공 API 정책 데이터 저장완료 =====");
        } catch (Exception e) {
            log.error("HouseServiceImpl - 주거정책 오픈 API 호출 실패 : ", e);
        }
    }

    // 정책기간 Start / End 분리시키기
    public static String[] extractDates(String input) {
        // 정규식 패턴: "YYYY-MM-DD~YYYY-MM-DD" 형태의 날짜 범위를 찾음
        String regex = "(\\d{4}-\\d{2}-\\d{2}).*?~\\s*(\\d{4}-\\d{2}-\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // 매칭된 날짜 반환(시작일, 종료일)
            return new String[]{matcher.group(1), matcher.group(2)};
        } else {
            // 날짜 범위를 찾지 못하면 null 반환
            return null;
        }
    }

    // 정책현황 - 생성

    // 정책현황 - 리스트
    @Override
    public PageResponseDTO<HouseDTO> getPolicyList(PageRequestDTO pageRequestDTO,
                                                   String policyStsType,
                                                   String searchTerm,
                                                   String filterType) {
        log.info("HouseServiceImpl - getPolicyList 호출 --------------------------- ");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("policyId").descending()
        );

        Page<HouseEntity> result = houseRepository.findByFilterTypeAndPolicyStsType(policyStsType, searchTerm, filterType, pageable); // 전체 조회

        List<HouseDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, HouseDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<HouseDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }

    // 정책현황 - 리스트 상세
    @Override
    public HouseDTO getPolicyDetail(Long policyId) {
        Optional<HouseEntity> result = houseRepository.findById(policyId);
        HouseEntity houseEntity = result.orElseThrow();
        HouseDTO houseDTO = modelMapper.map(houseEntity, HouseDTO.class);
        return houseDTO;
    }

    // 정책현황 - 수정
    @Override
    public void policyModify(HouseDTO houseDTO) {
        Optional<HouseEntity> result = houseRepository.findById(houseDTO.getPolicyId());
        HouseEntity houseEntity = result.orElseThrow();

        // 날짜가 null이 아니고 유효한 경우만 처리
        String[] results = extractDates(houseDTO.getRqutPrdCn());
        LocalDate rqutPrdStart = (results != null && results.length > 0 && results[0] != null) ? LocalDate.parse(results[0]) : null;
        LocalDate rqutPrdEnd = (results != null && results.length > 1 && results[1] != null) ? LocalDate.parse(results[1]) : null;

        // 정책명
        houseEntity.setPolyBizSjnm(houseDTO.getPolyBizSjnm());          // 정책명
        houseEntity.setPolyItcnCn(houseDTO.getPolyItcnCn());            // 정책소개(부제목)
        // 정책설명
        houseEntity.setSporCn(houseDTO.getSporCn());                    // 지원내용
        houseEntity.setRqutPrdCn(houseDTO.getRqutPrdCn());              // 사업신청기간
        houseEntity.setRqutPrdStart(rqutPrdStart);                      // 사업신청 시작일
        houseEntity.setRqutPrdEnd(rqutPrdEnd);                          // 사업신청 종료일
        houseEntity.setSporScvl(houseDTO.getSporScvl());                // 지원규모
        // 지원대상
        houseEntity.setAgeInfo(houseDTO.getAgeInfo());                  // 연령
        houseEntity.setPrcpCn(houseDTO.getPrcpCn());                    // 거주지 및 소득
        houseEntity.setAccrRqisCn(houseDTO.getAccrRqisCn());            // 학력
        houseEntity.setMajrRqisCn(houseDTO.getMajrRqisCn());            // 전공
        houseEntity.setEmpmSttsCn(houseDTO.getEmpmSttsCn());            // 취업상태
        houseEntity.setAditRscn(houseDTO.getAditRscn());                // 추가 세부 사항
        houseEntity.setPrcpLmttTrgtCn(houseDTO.getPrcpLmttTrgtCn());    // 참여제한대상
        // 신청방법
        houseEntity.setRqutProcCn(houseDTO.getRqutProcCn());            // 신청 절차
        houseEntity.setJdgnPresCn(houseDTO.getJdgnPresCn());            // 심사 및 발표
        houseEntity.setRqutUrla(houseDTO.getRqutUrla());                // 신청 사이트
        houseEntity.setPstnPaprCn(houseDTO.getPstnPaprCn());            // 제출서류
        // 기타
        houseEntity.setMngtMson(houseDTO.getMngtMson());                // 주관기관
        houseEntity.setCherCtpcCn(houseDTO.getCherCtpcCn());            // 주관기관 담당자 연락처
        houseEntity.setCnsgNmor(houseDTO.getCnsgNmor());                // 운영기관
        houseEntity.setTintCherCtpcCn(houseDTO.getTintCherCtpcCn());    // 운영기관 담당자 연락처
        houseEntity.setRfcSiteUrla1(houseDTO.getRfcSiteUrla1());        // 참고사이트1
        houseEntity.setRfcSiteUrla2(houseDTO.getRfcSiteUrla2());        // 참고사이트2

        houseRepository.save(houseEntity);
    }

    // 정책현황 - 삭제
    @Override
    public void policyRemove(Long policyId) {
        houseRepository.delFlagById(policyId);
    }
}
