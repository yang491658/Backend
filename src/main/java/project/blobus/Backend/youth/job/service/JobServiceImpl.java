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
import project.blobus.Backend.youth.job.dto.JobDTO;
import project.blobus.Backend.youth.job.dto.PageRequestDTO;
import project.blobus.Backend.youth.job.dto.PageResponseDTO;
import project.blobus.Backend.youth.job.entity.JobEntity;
import project.blobus.Backend.youth.job.repository.JobRepository;

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
public class JobServiceImpl implements JobService{
    @Value("${serviceKey1}")
    private String serviceKey1; // 오픈 API 인증 키

    private final ModelMapper modelMapper;
    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;

    // BLOBUS > 청년관 > 일자리
    // 1.정책현황

    // 서버 시작 시 데이터 초기화
    @PostConstruct
    public void init() {
        log.info("서버 시작 - 일자리정책 오픈 API 정책 데이터 초기화 중...");
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
        String bizTycdSel = "023010";
        //   2)지역코드
        //      - 부산(003002002)
        String srchPolyBizSecd = "003002002,003001001,003001003,003001004,003001006,003001007,003001008,003001010,003001012,003001015,003001017,003001018,003001019,003001020,003001023,003001024,003001026,003001028,003001031,003001033,003001038,003001051,003001058,003001059";

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

                // 4. 정책 데이터를 엔티티로 변환
                for(JsonNode node : policyList) {
                    String bizId = node.path("bizId").asText();

                    // 중복 체크 후 데이터 추가
                    if (!jobRepository.existsByBizId(bizId)) {
                        String[] results = extractDates(node.path("rqutPrdCn").asText());
                        // 날짜가 null이 아니고 유효한 경우만 처리
                        LocalDate rqutPrdStart = (results != null && results.length > 0 && results[0] != null) ? LocalDate.parse(results[0]) : null;
                        LocalDate rqutPrdEnd = (results != null && results.length > 1 && results[1] != null) ? LocalDate.parse(results[1]) : null;

                        JobEntity jobEntity = JobEntity.builder()
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

    // 정책기간 Start / End 분리시키기
    public static String[] extractDates(String input) {
        // 정규식 패턴: "YYYY-MM-DD~YYYY-MM-DD" 형태의 날짜 범위를 찾음
        String regex = "(\\d{4}-\\d{2}-\\d{2})~(\\d{4}-\\d{2}-\\d{2})";
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
    public PageResponseDTO<JobDTO> getPolicyList(PageRequestDTO pageRequestDTO,
                                                 String policyStsType,
                                                 String searchTerm,
                                                 String filterType) {
        log.info("JobServiceImpl - getPolicyList 호출 --------------------------- ");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("policyId").descending()
        );

        Page<JobEntity> result = jobRepository.findByFilterTypeAndPolicyStsType(policyStsType, searchTerm, filterType, pageable); // 전체 조회

        List<JobDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, JobDTO.class))
                .collect(Collectors.toList());

        return project.blobus.Backend.youth.job.dto.PageResponseDTO.<JobDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }

    // 정책현황 - 리스트 상세
    @Override
    public JobDTO getPolicyDetail(Long policyId) {
        Optional<JobEntity> result = jobRepository.findById(policyId);
        JobEntity jobEntity = result.orElseThrow();
        JobDTO jobDTO = modelMapper.map(jobEntity, JobDTO.class);
        return jobDTO;
    }

    // 정책현황 - 수정
    @Override
    public void policyModify(JobDTO jobDTO) {
        Optional<JobEntity> result = jobRepository.findById(jobDTO.getPolicyId());
        JobEntity jobEntity = result.orElseThrow();

        // 날짜가 null이 아니고 유효한 경우만 처리
        String[] results = extractDates(jobDTO.getRqutPrdCn());
        LocalDate rqutPrdStart = (results != null && results.length > 0 && results[0] != null) ? LocalDate.parse(results[0]) : null;
        LocalDate rqutPrdEnd = (results != null && results.length > 1 && results[1] != null) ? LocalDate.parse(results[1]) : null;

        // 정책명
        jobEntity.setPolyBizSjnm(jobDTO.getPolyBizSjnm());          // 정책명
        jobEntity.setPolyItcnCn(jobDTO.getPolyItcnCn());            // 정책소개(부제목)
        // 정책설명
        jobEntity.setSporCn(jobDTO.getSporCn());                    // 지원내용
        jobEntity.setRqutPrdCn(jobDTO.getRqutPrdCn());              // 사업신청기간
        jobEntity.setRqutPrdStart(rqutPrdStart);                    // 사업신청 시작일
        jobEntity.setRqutPrdEnd(rqutPrdEnd);                        // 사업신청 종료일
        jobEntity.setSporScvl(jobDTO.getSporScvl());                // 지원규모
        // 지원대상
        jobEntity.setAgeInfo(jobDTO.getAgeInfo());                  // 연령
        jobEntity.setPrcpCn(jobDTO.getPrcpCn());                    // 거주지 및 소득
        jobEntity.setAccrRqisCn(jobDTO.getAccrRqisCn());            // 학력요건
        jobEntity.setMajrRqisCn(jobDTO.getMajrRqisCn());            // 전공요건
        jobEntity.setEmpmSttsCn(jobDTO.getEmpmSttsCn());            // 취업상태
        jobEntity.setAditRscn(jobDTO.getAditRscn());                // 추가 단서사항
        jobEntity.setPrcpLmttTrgtCn(jobDTO.getPrcpLmttTrgtCn());    // 참여제한대상
        // 신청방법
        jobEntity.setRqutProcCn(jobDTO.getRqutProcCn());            // 신청절차
        jobEntity.setJdgnPresCn(jobDTO.getJdgnPresCn());            // 신청 및 발표
        jobEntity.setRqutUrla(jobDTO.getRqutUrla());                // 신청사이트 주소
        jobEntity.setPstnPaprCn(jobDTO.getPstnPaprCn());            // 제출서류
        // 기타
        jobEntity.setMngtMson(jobDTO.getMngtMson());                // 주관기관
        jobEntity.setCherCtpcCn(jobDTO.getCherCtpcCn());            // 주관기관 담당자 연락처
        jobEntity.setCnsgNmor(jobDTO.getCnsgNmor());                // 운영기관
        jobEntity.setTintCherCtpcCn(jobDTO.getTintCherCtpcCn());    // 운영기관 담당자 연락처
        jobEntity.setRfcSiteUrla1(jobDTO.getRfcSiteUrla1());        // 참고사이트 url1
        jobEntity.setRfcSiteUrla2(jobDTO.getRfcSiteUrla2());        // 참고사이트 url2

        jobRepository.save(jobEntity);
    }

    // 정책현황 - 삭제
    @Override
    public void policyRemove(Long policyId) {
        jobRepository.delFlagById(policyId);
    }
}
