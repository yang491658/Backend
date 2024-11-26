package project.blobus.Backend.mypage.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.mypage.bookmark.dto.BookmarkDTO;
import project.blobus.Backend.mypage.bookmark.entity.Bookmark;
import project.blobus.Backend.mypage.bookmark.repository.BookmarkRepository;
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
    private final YouthEmploymentPolicyRepository youthEmplRepository;
    private final YouthJobPostingRepository youthJobRepository;
    private final YouthHousingPolicyRepository youthHousingRepository;
    private final YouthFinancialPolicyRepository youthFinanRepository;
    private final YouthEducationPolicyRepository youthEduRepository;
    private final YouthStartupPolicyRepository youthStartupRepository;
    private final ResourceCultureRepository resourceCulRepository;
    private final ResourceSupportRepository resourceSupRepository;

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
        String title = null;
        String content = null;
        String mainCategory = bookmark.getMainCategory();
        String subCategory = bookmark.getSubCategory();

        Long targeId = bookmark.getTargetId();
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        if (mainCategory.equals("청년") && subCategory.equals("일자리")) {
            YouthEmploymentPolicy entity = youthEmplRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getDescription();
            startDate = entity.getStartDate();
            endDate = entity.getEndDate();
            createdAt = entity.getCreatedAt();
            updatedAt = entity.getUpdatedAt();
        } else if (mainCategory.equals("청년") && subCategory.equals("구인")) {
            YouthJobPosting entity = youthJobRepository.findById(targeId).orElseThrow();
            title = entity.getCompanyName() + " 구인 공고";
            content = entity.getJobTitle() + " / " + entity.getJobDescription() + " / " + entity.getJobType();
            endDate = entity.getApplicationDeadline();
            createdAt = entity.getCreatedAt();
            updatedAt = entity.getUpdatedAt();
        } else if (mainCategory.equals("청년") && subCategory.equals("주거")) {
            YouthHousingPolicy entity = youthHousingRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getDescription();
            startDate = entity.getStartDate();
            endDate = entity.getEndDate();
            createdAt = entity.getCreatedAt();
            updatedAt = entity.getUpdatedAt();
        } else if (mainCategory.equals("청년") && subCategory.equals("금융")) {
            YouthFinancialPolicy entity = youthFinanRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getOverview() + " / " + entity.getBenefitAmount() + " / " + entity.getBenefitType();
            startDate = entity.getApplicationPeriodStart();
            endDate = entity.getApplicationPeriodEnd();
            createdAt = entity.getCreatedAt();
            updatedAt = entity.getUpdatedAt();
        } else if (mainCategory.equals("청년") && subCategory.equals("교육")) {
            YouthEducationPolicy entity = youthEduRepository.findById(targeId).orElseThrow();
            title = entity.getProgramName();
            content = entity.getOverview() + " / " + entity.getSupportAmount() + " / " + entity.getSupportType();
            startDate = entity.getApplicationPeriodStart();
            endDate = entity.getApplicationPeriodEnd();
            createdAt = entity.getCreatedAt();
            updatedAt = entity.getUpdatedAt();
        } else if (mainCategory.equals("청년") && subCategory.equals("창업")) {
            YouthStartupPolicy entity = youthStartupRepository.findById(targeId).orElseThrow();
            title = entity.getProgramName();
            content = entity.getOverview() + " / " + entity.getSupportAmount() + " / " + entity.getSupportType();
            startDate = entity.getApplicationPeriodStart();
            endDate = entity.getApplicationPeriodEnd();
            createdAt = entity.getCreatedAt();
            updatedAt = entity.getUpdatedAt();
        } else if (mainCategory.equals("지역") ) {
            ResourceSupport entity = resourceSupRepository.findById(targeId).orElseThrow();
            title = entity.getTitle();
            content = entity.getContent();
            createdAt = entity.getCreatedAt();
            updatedAt = entity.getUpdatedAt();
        }

        return BookmarkDTO.builder()
                .id(bookmark.getId())
                .title(title)
                .content(content)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .startDate(startDate)
                .endDate(endDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
