package project.blobus.Backend.youth.job.service;

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
import project.blobus.Backend.youth.house.entity.HouseEntity;
import project.blobus.Backend.youth.job.dto.JobDTO;
import project.blobus.Backend.youth.job.dto.PageRequestDTO;
import project.blobus.Backend.youth.job.dto.PageResponseDTO;
import project.blobus.Backend.youth.job.entity.JobEntity;
import project.blobus.Backend.youth.job.repository.JobRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService{
    @Value("${serviceKey1}")
    private String serviceKey1; // 오픈 API 인증 키

    private final ModelMapper modelMapper;
    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;

    // 서버 시작 시 데이터 초기화
    @PostConstruct
    public void init() {
        log.info("서버 시작 - 일자리정책 오픈 API 정책 데이터 초기화 중...");
        getPolicyApi(); // 초기 데이터 로드
    }

    // 정책 오픈 API
    public void getPolicyApi() {
        // 필수 파라미터
        int display = 100;   // 출력건수 : 기본값 10, 최대 100까지 가능
        int pageIndex = 1;  // 조회할 페이지 : 기본값 1

        // 선택 파라미터
        String bizTycdSel = "023010";   // 정책유형 - 일자리 분야(023010) / 주거 분야(023020)
        String srchPolyBizSecd = "003002002,003001001,003001003,003001004,003001006,003001007,003001008,003001010,003001012,003001015,003001017,003001018,003001019,003001020,003001023,003001024,003001026,003001028,003001031,003001033,003001038,003001051,003001058,003001059";   // 지역코드 - 부산(003002002)

        List<JobEntity> entityList = new ArrayList<>();

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
                JsonNode policyList =  rootNode.path("youthPolicy");   // 'youthPolicy' 키 기준으로 데이터 추출

                log.info("일자리 정책 API 데이터 추출 =============================");
                log.info("현재 페이지: " + pageIndex + ", 총 데이터 수: " + totalCnt);
                log.info("API: " + policyList);

                // 4. 정책 데이터를 엔티티로 변환
                for(JsonNode node : policyList) {
                    String bizId = node.path("bizId").asText();

                    // 중복 체크 후 데이터 추가
                    if (!jobRepository.existsByBizId(bizId)) {
                        JobEntity jobEntity = JobEntity.builder()
                                .polyRlmCd(node.path("polyRlmCd").asText())
                                .bizId(node.path("bizId").asText())
                                .polyBizSjnm(node.path("polyBizSjnm").asText())
                                .polyItcnCn(node.path("polyItcnCn").asText())
                                .polyBizTy(node.path("polyBizTy").asText())
                                .mngtMson(node.path("mngtMson").asText())
                                .cnsgNmor(node.path("cnsgNmor").asText())
                                .sporCn(node.path("sporCn").asText())
                                .bizPrdCn(node.path("bizPrdCn").asText())
                                .prdRpttSecd(node.path("prdRpttSecd").asText())
                                .rqutPrdCn(node.path("rqutPrdCn").asText())
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
                                .build();

                        entityList.add(jobEntity);
                    }
                }
                // 다음 페이지 확인
                pageIndex++;
                int totalPages = (int) Math.ceil((double) totalCnt / display);
                hasMorePages = pageIndex <= totalPages;
            }

            // 5. 데이터저장
            jobRepository.saveAll(entityList);
            log.info("일자리정책 공공 API 정책 데이터 저장완료 =====");
        } catch (Exception e) {
            log.error("JobServiceImpl - 일자리정책 오픈 API 호출 실패 : ", e);
        }
    }

    @Override
    public PageResponseDTO<JobDTO> getPolicyList(PageRequestDTO pageRequestDTO, String searchTerm, String filterType) {
        log.info("JobServiceImpl - getPolicyList 호출 --------------------------- ");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("policyId").descending()
        );

        Page<JobEntity> result;

        // 검색어와 필터 타입이 있을 경우 조건 처리
        if (searchTerm != null && !searchTerm.isEmpty()) {
            switch (filterType) {
                case "polyBizSjnm":
                    result = jobRepository.findByPolyBizSjnmContaining(searchTerm, pageable);
                    break;
                case "polyItcnCn":
                    result = jobRepository.findByPolyItcnCnContaining(searchTerm, pageable);
                    break;
                case "both":
                    result = jobRepository.findByPolyBizSjnmContainingOrPolyItcnCnContaining(searchTerm, searchTerm, pageable);
                    break;
                default:
                    result = jobRepository.findAll(pageable);
            }
        } else {
            result = jobRepository.findAll(pageable); // 검색어가 없으면 전체 조회
        }

        List<JobDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, JobDTO.class))
                .collect(Collectors.toList());

        return project.blobus.Backend.youth.job.dto.PageResponseDTO.<JobDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }

    @Override
    public JobDTO getPolicyDetail(Long policyId) {
        Optional<JobEntity> result = jobRepository.findById(policyId);
        JobEntity jobEntity = result.orElseThrow();
        JobDTO jobDTO = modelMapper.map(jobEntity, JobDTO.class);
        return jobDTO;
    }
}
