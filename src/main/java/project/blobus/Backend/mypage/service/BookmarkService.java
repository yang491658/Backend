package project.blobus.Backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.mypage.dto.BookmarkDTO;
import project.blobus.Backend.mypage.entity.Bookmark;
import project.blobus.Backend.mypage.repository.BookmarkRepository;
import project.blobus.Backend.temp.entity.*;
import project.blobus.Backend.temp.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final YouthEmploymentPolicyRepository youthEmploymentRepository;
    private final YouthJobPostingRepository youthJobPostingRepository;
    private final YouthHousingPolicyRepository youthHousingPolicyRepository;
    private final YouthFinancialPolicyRepository youthFinancialPolicyRepository;
    private final YouthEducationPolicyRepository youthEducationPolicyRepository;
    private final YouthStartupPolicyRepository youthStartupPolicyRepository;
    private final ResourceCultureRepository resourceCultureRepository;
    private final ResourceSupportRepository resourceSupportRepository;

    private final BookmarkRepository bookmarkRepository;

    public PageResponseDTO<BookmarkDTO> getList(PageRequestDTO pageRequestDTO,
                                                String userId,
                                                String mainCategory) {
        log.info("Boomark Get List");

        List<Bookmark> entitiyList;
        if (mainCategory == "")
            entitiyList = bookmarkRepository.findAllByMember_UserId(userId);
        else
            entitiyList = bookmarkRepository.findAllByMember_UserIdAndMainCategory(userId, mainCategory);

        List<BookmarkDTO> bookmarkList = entitiyList.stream()
                .map(bookmark -> toDTO(bookmark))
                .collect(Collectors.toList());

        bookmarkList = bookmarkList.stream()
                .sorted((b1, b2) -> b2.getAtTime().compareTo(b1.getAtTime()))
                .collect(Collectors.toList());


        int totalCount = bookmarkList.size();

        int startIndex = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        int endIndex = Math.min(startIndex + pageRequestDTO.getSize(), totalCount);
        List<BookmarkDTO> dtoList = bookmarkList.subList(startIndex, endIndex);

        return PageResponseDTO.<BookmarkDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    private BookmarkDTO toDTO(Bookmark bookmark) {
        String title = "임시 제목";
        String content = "임시 내용";
        String address = "임시 주소";
        String mainCategory = bookmark.getMainCategory();
        String subCategory = bookmark.getSubCategory();

        Long targeId = bookmark.getTargetId();
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime atTime = bookmark.getAtTime();

        if (mainCategory.equals("청년") && subCategory.equals("일자리")) {
            TempYouthEmploymentPolicy entity = youthEmploymentRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getDescription();
            address = entity.getRegion();
            startDate = entity.getStartDate();
            endDate = entity.getEndDate();
        } else if (mainCategory.equals("청년") && subCategory.equals("구인")) {
            TempYouthJobPosting entity = youthJobPostingRepository.findById(targeId).orElseThrow();
            title = entity.getCompanyName() + " 구인";
            content = entity.getJobTitle() + " [" + entity.getJobType() + "]";
            address = entity.getLocation();
            endDate = entity.getApplicationDeadline();
        } else if (mainCategory.equals("청년") && subCategory.equals("주거")) {
            TempYouthHousingPolicy entity = youthHousingPolicyRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getDescription();
            address = entity.getRegion();
            startDate = entity.getStartDate();
            endDate = entity.getEndDate();
        } else if (mainCategory.equals("청년") && subCategory.equals("금융")) {
            TempYouthFinancialPolicy entity = youthFinancialPolicyRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getOverview() + " [" + entity.getBenefitType() + "]";
            address = entity.getResidenceRequirement();
            startDate = entity.getApplicationPeriodStart();
            endDate = entity.getApplicationPeriodEnd();
        } else if (mainCategory.equals("청년") && subCategory.equals("교육")) {
            TempYouthEducationPolicy entity = youthEducationPolicyRepository.findById(targeId).orElseThrow();
            title = entity.getProgramName();
            content = entity.getOverview() + " [" + entity.getSupportType() + "]";
            address = entity.getLocation();
            startDate = entity.getApplicationPeriodStart();
            endDate = entity.getApplicationPeriodEnd();
        } else if (mainCategory.equals("청년") && subCategory.equals("창업")) {
            TempYouthStartupPolicy entity = youthStartupPolicyRepository.findById(targeId).orElseThrow();
            title = entity.getProgramName();
            content = entity.getOverview() + " [" + entity.getSupportType() + "]";
            address = entity.getLocationRequirement();
            startDate = entity.getApplicationPeriodStart();
            endDate = entity.getApplicationPeriodEnd();
        } else if (mainCategory.equals("지역") && subCategory.equals("문화")) {
            TempResourceCulture entity = resourceCultureRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getContent();
            address = entity.getAddress();
        } else if (mainCategory.equals("지역") && subCategory.equals("지원")) {
            TempResourceSupport entity = resourceSupportRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getContent();
            address = entity.getAddress();
        }

        return BookmarkDTO.builder()
                .id(bookmark.getId())
                .title(title)
                .content(content)
                .address(address)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .startDate(startDate)
                .endDate(endDate)
                .atTime(atTime)
                .build();
    }
}
