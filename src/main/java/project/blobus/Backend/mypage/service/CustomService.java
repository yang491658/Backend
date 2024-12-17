package project.blobus.Backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.member.role.general.entity.GeneralMember;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;
import project.blobus.Backend.mypage.dto.CustomDTO;
import project.blobus.Backend.resource.entity.ResourceCulture;
import project.blobus.Backend.resource.repository.ResourceCultureRepository;
import project.blobus.Backend.youth.education.EducationEntity;
import project.blobus.Backend.youth.education.EducationRepository;
import project.blobus.Backend.youth.house.entity.HouseEntity;
import project.blobus.Backend.youth.house.repository.HouseRepository;
import project.blobus.Backend.youth.job.entity.JobEntity;
import project.blobus.Backend.youth.job.repository.JobRepository;
import project.blobus.Backend.youth.welfare.WelfareEntity;
import project.blobus.Backend.youth.welfare.WelfareRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class CustomService {
    private final GeneralRepository generalRepository;

    private final JobRepository jobRepository;
    private final HouseRepository houseRepository;
    private final WelfareRepository welfareRepository;
    private final EducationRepository educationRepository;
    private final ResourceCultureRepository cultureRepository;

    // 커스텀 설정 불러오기
    public Map<String, String> loadSetting(String userId) {
        log.info("Custom Load Setting");

        GeneralMember member = generalRepository.findByUserId(userId).orElseThrow();

        Map<String, String> result = new HashMap<>();

        if (member.getCustomSetting() != null) {
            String[] setting = member.getCustomSetting().split("-");

            setSetting(result, setting, "청년", 0);
            setSetting(result, setting, "지역", 1);
            setSetting(result, setting, "키워드", 2);
        }

        return result;
    }

    // 설정 함수
    private void setSetting(Map<String, String> result,
                            String[] setting,
                            String name,
                            int num) {
        if (setting[num].split(":").length == 1)
            result.put(name, null);
        else
            result.put(name, setting[num].split(":")[1]);
    }

    // 커스텀 설정 저장
    public void saveSetting(String userId,
                            String yListStr,
                            String rListStr,
                            String kListStr) {
        log.info("Custom Save Setting");

        GeneralMember member = generalRepository.findByUserId(userId).orElseThrow();

        String setting
                = "청년:" + yListStr
                + "-지역:" + rListStr
                + "-키워드:" + kListStr;
        member.setCustomSetting(setting);
        generalRepository.save(member);
    }

    // 커스텀 정보 조회
    public PageResponseDTO<CustomDTO> getList(PageRequestDTO pageRequestDTO,
                                              String address,
                                              String yListStr,
                                              String rListStr,
                                              String kListStr) {
        log.info("Custom Get List");

        List<CustomDTO> customList = filterDTO(address, yListStr, rListStr, null);

        List<String> kList = Arrays.asList(kListStr.split("/"));
        kList.forEach(keyward ->
                customList.addAll(filterDTO(address, null, null, keyward)));

        List<CustomDTO> newCustomList = customList.stream()
                .distinct()
                .sorted(Comparator.comparing(CustomDTO::getTitle))
                .collect(Collectors.toList());

        int totalCount = newCustomList.size();

        int startIndex = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        int endIndex = Math.min(startIndex + pageRequestDTO.getSize(), totalCount);
        List<CustomDTO> dtoList = newCustomList.subList(startIndex, endIndex);

        return PageResponseDTO.<CustomDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    // 필터 함수
    private List<CustomDTO> filterDTO(String address,
                                      String yListStr,
                                      String rListStr,
                                      String keyward) {
        List<CustomDTO> customList = new ArrayList<>();

        String region = address.split("-")[0];
        String city = address.split("-")[1];

        if (yListStr == null || yListStr.contains("일자리")) {
            customList.addAll(jobRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산")
                            || (searchByKeyward(data, "부산") && searchByKeyward(data, city)))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            data,
                            null,
                            null,
                            null,
                            null))
                    .toList());
        }

        if (yListStr == null || yListStr.contains("주거")) {
            customList.addAll(houseRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산")
                            || (searchByKeyward(data, "부산")))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            data,
                            null,
                            null,
                            null))
                    .toList());
        }

        if (yListStr == null || yListStr.contains("복지")) {
            customList.addAll(welfareRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산")
                            || (searchByKeyward(data, "부산") && searchByKeyward(data, city)))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            data,
                            null,
                            null))
                    .toList());
        }

        if (yListStr == null || yListStr.contains("교육")) {
            customList.addAll(educationRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산")
                            || (searchByKeyward(data, "부산") && searchByKeyward(data, city)))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            null,
                            data,
                            null))
                    .toList());
        }

        if (rListStr == null || rListStr.contains("문화")) {
            customList.addAll(cultureRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산")
                            || (searchByKeyward(data, "부산") && searchByKeyward(data, city)))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            null,
                            null,
                            data))
                    .toList());
        }

        return customList;
    }

    // 키워드 검색 함수
    private Boolean searchByKeyward(Object data, String keyword) {
        if (data == null || keyword == null || keyword.isEmpty()) {
            return false;
        }
        try {
            for (var field : data.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(data);
                if (value != null && value.toString().contains(keyword)) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error :" + e);
        }
        return false;
    }

    // 매핑 함수
    private CustomDTO toDTO(
            JobEntity jobPolicy,
            HouseEntity housePolicy,
            WelfareEntity welfarePolicy,
            EducationEntity educationPolicy,
            ResourceCulture culture) {

        List<String> regionList = new ArrayList<>(Arrays.asList(
                "강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구",
                "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"
        ));

        Long targetId = null;
        String mainCategory = null;
        String subCategory = null;
        String title = null;
        String content = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        String place = null;
        String link = null;

        if (jobPolicy != null) {
            targetId = jobPolicy.getPolicyId();
            mainCategory = "청년";
            subCategory = "일자리";
            title = jobPolicy.getPolyBizSjnm();
            content = jobPolicy.getPolyItcnCn();
            String[] dueDate = jobPolicy.getRqutPrdCn().split("\n")[0].split("~");
            if (dueDate.length > 1) {
                startDate = LocalDate.parse(dueDate[0].trim());
                endDate = LocalDate.parse(dueDate[dueDate.length - 1].trim());
            }
            place = jobPolicy.getMngtMson().replace("□ ","");
//            createdAt = jobPolicy.getCreatedAt();
//            updatedAt = jobPolicy.getUpdatedAt();
            link = "/youth/job/policyRead/" + targetId;
        } else if (housePolicy != null) {
            targetId = housePolicy.getPolicyId();
            mainCategory = "청년";
            subCategory = "주거";
            title = housePolicy.getPolyBizSjnm();
            content = housePolicy.getPolyItcnCn();
            String[] dueDate = housePolicy.getRqutPrdCn().split("\n")[0].split("~");
            if (dueDate.length > 1) {
                startDate = LocalDate.parse(dueDate[0].trim());
                endDate = LocalDate.parse(dueDate[dueDate.length - 1].trim());
            }
            place = housePolicy.getMngtMson().replace("□ ","");
//            createdAt = housePolicy.getCreatedAt();
//            updatedAt = housePolicy.getUpdatedAt();
            link = "/youth/house/policyRead/" + targetId;
        } else if (welfarePolicy != null) {
            targetId = (long) welfarePolicy.getPolicyId();
            mainCategory = "청년";
            subCategory = "복지";
            title = welfarePolicy.getPolicyName();
            content = welfarePolicy.getPolicyOverview();
            startDate = welfarePolicy.getPolicyApplicationStartPeriod();
            endDate = welfarePolicy.getPolicyApplicationEndPeriod();
            place = welfarePolicy.getOperatingAgency().replace("□ ","");
//            createdAt = welfarePolicy.getCreatedAt();
//            updatedAt = welfarePolicy.getUpdatedAt();
            link = "/youth/welfare/" + targetId;
        } else if (educationPolicy != null) {
            targetId = (long) educationPolicy.getPolicyId();
            mainCategory = "청년";
            subCategory = "교육";
            title = educationPolicy.getPolicyName();
            content = educationPolicy.getPolicyOverview();
            startDate = educationPolicy.getPolicyApplicationStartPeriod();
            endDate = educationPolicy.getPolicyApplicationEndPeriod();
            place = educationPolicy.getOperatingAgency().replace("□ ","");
//            createdAt = educationPolicy.getCreatedAt();
//            updatedAt = educationPolicy.getUpdatedAt();
            link = "/youth/education/" + targetId;
        } else if (culture != null) {
            targetId = culture.getId();
            mainCategory = "지역";
            subCategory = "문화";
            title = culture.getTitle();
            startDate = culture.getStartDate();
            endDate = culture.getEndDate();
            place = culture.getPlace();
            createdAt = culture.getCreatedAt();
//            updatedAt = culture.getUpdatedAt();
            link = culture.getLink();
        }

        return CustomDTO.builder()
                .targetId(targetId)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .place(place)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .link(link)
                .build();
    }
}
