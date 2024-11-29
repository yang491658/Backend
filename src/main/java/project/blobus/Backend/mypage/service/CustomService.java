package project.blobus.Backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.member.role.general.entity.GeneralMember;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;
import project.blobus.Backend.mypage.dto.CustomDTO;
import project.blobus.Backend.temp.entity.*;
import project.blobus.Backend.temp.repository.*;

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

    private final YouthEmploymentPolicyRepository youthEmploymentPolicyRepository;
    private final YouthJobPostingRepository youthJobPostingRepository;
    private final YouthHousingPolicyRepository youthHousingPolicyRepository;
    private final YouthFinancialPolicyRepository youthFinancialPolicyRepository;
    private final YouthEducationPolicyRepository youthEducationPolicyRepository;
    private final YouthStartupPolicyRepository youthStartupPolicyRepository;
    private final ResourceCultureRepository resourceCultureRepository;
    private final ResourceSupportRepository resourceSupportRepository;

    // 커스텀 설정 불러오기
    public Map<String, String> loadSetting(String userId) {
        log.info("Custom Load Setting");

        GeneralMember member = generalRepository.findByUserId(userId).orElseThrow();

        Map<String, String> result = new HashMap<>();

        if (member.getCustomSetting() != null) {
            String[] setting = member.getCustomSetting().split("-");

            setSetting(result, setting, "청년", 0);
            setSetting(result, setting, "기업", 1);
            setSetting(result, setting, "지역", 2);
            setSetting(result, setting, "키워드", 3);
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
                            String eListStr,
                            String rListStr,
                            String kListStr) {
        log.info("Custom Save Setting");

        GeneralMember member = generalRepository.findByUserId(userId).orElseThrow();

        String setting
                = "청년:" + yListStr
                + "-기업:" + eListStr
                + "-지역:" + rListStr
                + "-키워드:" + kListStr;
        member.setCustomSetting(setting);
        generalRepository.save(member);
    }

    // 커스텀 정보 조회
    public PageResponseDTO<CustomDTO> getList(PageRequestDTO pageRequestDTO,
                                              String address,
                                              String yListStr,
                                              String eListStr,
                                              String rListStr,
                                              String kListStr) {
        log.info("Custom Get List");

        List<CustomDTO> customList = filterDTO(address, yListStr, eListStr, rListStr, null);

        List<String> kList = Arrays.asList(kListStr.split("/"));
        kList.forEach(keyward ->
                customList.addAll(filterDTO(address, null, null, null, keyward)));

        List<CustomDTO> newCustomList = customList.stream()
                .distinct()
                .sorted((dto1, dto2) -> dto2.getCreatedAt().compareTo(dto1.getCreatedAt()))
                .collect(Collectors.toList());

        int totalCount = newCustomList.size();

        int startIndex = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        int endIndex = Math.min(startIndex + pageRequestDTO.getSize(), totalCount);
        List<CustomDTO> dtoList = newCustomList.subList(startIndex, endIndex);

        dtoList.forEach(data -> System.out.println(data));

        return PageResponseDTO.<CustomDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    // 필터 함수
    private List<CustomDTO> filterDTO(String address,
                                      String yListStr,
                                      String eListStr,
                                      String rListStr,
                                      String keyward) {
        List<CustomDTO> customList = new ArrayList<>();

        if (yListStr == null || yListStr.contains("일자리")) {
            customList.addAll(youthEmploymentPolicyRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getRegion().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            data,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null))
                    .collect(Collectors.toList()));
        }

        if (yListStr == null || yListStr.contains("구인")) {
            customList.addAll(youthJobPostingRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getLocation().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            data,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null))
                    .collect(Collectors.toList()));
        }

        if (yListStr == null || yListStr.contains("주거")) {
            customList.addAll(youthHousingPolicyRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getRegion().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            data,
                            null,
                            null,
                            null,
                            null,
                            null))
                    .collect(Collectors.toList()));
        }

        if (yListStr == null || yListStr.contains("금융")) {
            customList.addAll(youthFinancialPolicyRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getResidenceRequirement().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            null,
                            data,
                            null,
                            null,
                            null,
                            null))
                    .collect(Collectors.toList()));
        }

        if (yListStr == null || yListStr.contains("교육")) {
            customList.addAll(youthEducationPolicyRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getLocation().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            null,
                            null,
                            data,
                            null,
                            null,
                            null))
                    .collect(Collectors.toList()));
        }

        if (yListStr == null || yListStr.contains("창업")) {
            customList.addAll(youthStartupPolicyRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getLocationRequirement().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            null,
                            null,
                            null,
                            data,
                            null,
                            null))
                    .collect(Collectors.toList()));
        }

        if (rListStr == null || rListStr.contains("문화")) {
            customList.addAll(resourceCultureRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getAddress().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            data,
                            null))
                    .collect(Collectors.toList()));
        }

        if (rListStr == null || rListStr.contains("지원")) {
            customList.addAll(resourceSupportRepository.findAll().stream()
                    .filter(data -> (!address.contains("부산") || data.getAddress().equalsIgnoreCase(address))
                            && (keyward == null || searchByKeyward(data, keyward)))
                    .map(data -> toDTO(
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            data))
                    .collect(Collectors.toList()));
        }

        return customList;
    }

    // 키워드 검색 함수
    private boolean searchByKeyward(Object data, String keyword) {
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
            TempYouthEmploymentPolicy tempYouthEmploymentPolicy,
            TempYouthJobPosting tempYouthJobPosting,
            TempYouthHousingPolicy tempYouthHousingPolicy,
            TempYouthFinancialPolicy tempYouthFinancialPolicy,
            TempYouthEducationPolicy tempYouthEducationPolicy,
            TempYouthStartupPolicy tempYouthStartupPolicy,
            TempResourceCulture tempResourceCulture,
            TempResourceSupport tempResourceSupport) {

        String title = null;
        String content = null;
        String address = null;
        String mainCategory = null;
        String subCategory = null;
        Long targetId = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        if (tempYouthEmploymentPolicy != null) {
            title = tempYouthEmploymentPolicy.getTitle();
            content = tempYouthEmploymentPolicy.getDescription();
            address = tempYouthEmploymentPolicy.getRegion();
            mainCategory = "청년";
            subCategory = "일자리";
            targetId = tempYouthEmploymentPolicy.getPolicyId();
            startDate = tempYouthEmploymentPolicy.getStartDate();
            endDate = tempYouthEmploymentPolicy.getEndDate();
            createdAt = tempYouthEmploymentPolicy.getCreatedAt();
            updatedAt = tempYouthEmploymentPolicy.getUpdatedAt();
        } else if (tempYouthJobPosting != null) {
            title = tempYouthJobPosting.getCompanyName() + " 구인";
            content = tempYouthJobPosting.getJobTitle() + " [" + tempYouthJobPosting.getJobType() + "]";
            mainCategory = "청년";
            subCategory = "구인";
            targetId = tempYouthJobPosting.getPolicyId();
            address = tempYouthJobPosting.getLocation();
            endDate = tempYouthJobPosting.getApplicationDeadline();
            createdAt = tempYouthJobPosting.getCreatedAt();
            updatedAt = tempYouthJobPosting.getUpdatedAt();
        } else if (tempYouthHousingPolicy != null) {
            title = tempYouthHousingPolicy.getTitle();
            content = tempYouthHousingPolicy.getDescription();
            address = tempYouthHousingPolicy.getRegion();
            mainCategory = "청년";
            subCategory = "주거";
            targetId = tempYouthHousingPolicy.getPolicyId();
            startDate = tempYouthHousingPolicy.getStartDate();
            endDate = tempYouthHousingPolicy.getEndDate();
            createdAt = tempYouthHousingPolicy.getCreatedAt();
            updatedAt = tempYouthHousingPolicy.getUpdatedAt();
        } else if (tempYouthFinancialPolicy != null) {
            title = tempYouthFinancialPolicy.getTitle();
            content = tempYouthFinancialPolicy.getOverview() + " [" + tempYouthFinancialPolicy.getBenefitType() + "]";
            address = tempYouthFinancialPolicy.getResidenceRequirement();
            mainCategory = "청년";
            subCategory = "금융";
            targetId = tempYouthFinancialPolicy.getPolicyId();
            startDate = tempYouthFinancialPolicy.getApplicationPeriodStart();
            endDate = tempYouthFinancialPolicy.getApplicationPeriodEnd();
            createdAt = tempYouthFinancialPolicy.getCreatedAt();
            updatedAt = tempYouthFinancialPolicy.getUpdatedAt();
        } else if (tempYouthEducationPolicy != null) {
            title = tempYouthEducationPolicy.getProgramName();
            content = tempYouthEducationPolicy.getOverview() + " [" + tempYouthEducationPolicy.getSupportType() + "]";
            address = tempYouthEducationPolicy.getLocation();
            mainCategory = "청년";
            subCategory = "금융";
            targetId = tempYouthEducationPolicy.getProgramId();
            startDate = tempYouthEducationPolicy.getApplicationPeriodStart();
            endDate = tempYouthEducationPolicy.getApplicationPeriodEnd();
            createdAt = tempYouthEducationPolicy.getCreatedAt();
            updatedAt = tempYouthEducationPolicy.getUpdatedAt();
        } else if (tempYouthStartupPolicy != null) {
            title = tempYouthStartupPolicy.getProgramName();
            content = tempYouthStartupPolicy.getOverview() + " [" + tempYouthStartupPolicy.getSupportType() + "]";
            address = tempYouthStartupPolicy.getLocationRequirement();
            mainCategory = "청년";
            subCategory = "창업";
            targetId = tempYouthStartupPolicy.getProgramId();
            startDate = tempYouthStartupPolicy.getApplicationPeriodStart();
            endDate = tempYouthStartupPolicy.getApplicationPeriodEnd();
            createdAt = tempYouthStartupPolicy.getCreatedAt();
            updatedAt = tempYouthStartupPolicy.getUpdatedAt();
        } else if (tempResourceCulture != null) {
            title = tempResourceCulture.getTitle();
            content = tempResourceCulture.getContent();
            address = tempResourceCulture.getAddress();
            mainCategory = "지역";
            subCategory = "문화";
            targetId = tempResourceCulture.getId();
            createdAt = tempResourceCulture.getCreatedAt();
            updatedAt = tempResourceCulture.getUpdatedAt();
        } else if (tempResourceSupport != null) {
            title = tempResourceSupport.getTitle();
            content = tempResourceSupport.getContent();
            address = tempResourceSupport.getAddress();
            mainCategory = "지역";
            subCategory = "지원";
            targetId = tempResourceSupport.getId();
            createdAt = tempResourceSupport.getCreatedAt();
            updatedAt = tempResourceSupport.getUpdatedAt();
        }

        return CustomDTO.builder()
                .title(title)
                .content(content)
                .address(address)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .targetId(targetId)
                .startDate(startDate)
                .endDate(endDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
