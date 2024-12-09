package project.blobus.Backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;
import project.blobus.Backend.mypage.dto.BookmarkDTO;
import project.blobus.Backend.mypage.entity.Bookmark;
import project.blobus.Backend.mypage.repository.BookmarkRepository;
import project.blobus.Backend.resource.entity.ResourceCulture;
import project.blobus.Backend.resource.repository.ResourceCultureRepository;
import project.blobus.Backend.temp.repository.YouthJobPostingRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final GeneralRepository generalRepository;
    private final BookmarkRepository bookmarkRepository;

    private final JobRepository jobRepository;
    private final YouthJobPostingRepository youthJobPostingRepository;
    private final HouseRepository houseRepository;
    private final WelfareRepository welfareRepository;
    private final EducationRepository educationRepository;
    private final ResourceCultureRepository cultureRepository;

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
                .map(this::toDTO)
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
        Long targetId = bookmark.getTargetId();
        String mainCategory = bookmark.getMainCategory();
        String subCategory = bookmark.getSubCategory();
        String title = "임시 제목";

        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime atTime = bookmark.getAtTime();
        String link = null;

        if (mainCategory.equals("청년") && subCategory.equals("일자리")) {
            JobEntity entity = jobRepository.findById(targetId).orElseThrow();
            title = entity.getPolyBizSjnm();
            String[] dueDate = entity.getRqutPrdCn().split("\n")[0].split("~");
            if (dueDate.length > 1) {
                startDate = LocalDate.parse(dueDate[0].trim());
                endDate = LocalDate.parse(dueDate[dueDate.length - 1].trim());
            }
            link = "/youth/job/policyRead/" + targetId;
//        } else if (mainCategory.equals("청년") && subCategory.equals("구인")) {
//            TempYouthJobPosting entity = youthJobPostingRepository.findById(targetId).orElseThrow();
//            title = entity.getCompanyName() + " 구인";
//            endDate = entity.getApplicationDeadline();
        } else if (mainCategory.equals("청년") && subCategory.equals("주거")) {
            HouseEntity entity = houseRepository.findById(targetId).orElseThrow();
            title = entity.getPolyBizSjnm();
            String[] dueDate = entity.getRqutPrdCn().split("\n")[0].split("~");
            if (dueDate.length > 1) {
                startDate = LocalDate.parse(dueDate[0].trim());
                endDate = LocalDate.parse(dueDate[dueDate.length - 1].trim());
            }
            link = "/youth/house/policyRead/" + targetId;
        } else if (mainCategory.equals("청년") && subCategory.equals("복지")) {
            WelfareEntity entity = welfareRepository.findById(Math.toIntExact(targetId)).orElseThrow();
            title = entity.getPolicyName();
            startDate = entity.getPolicyApplicationStartPeriod();
            endDate = entity.getPolicyApplicationEndPeriod();
            link = "/youth/welfare/" + targetId;
        } else if (mainCategory.equals("청년") && subCategory.equals("교육")) {
            EducationEntity entity = educationRepository.findById(Math.toIntExact(targetId)).orElseThrow();
            title = entity.getPolicyName();
            startDate = entity.getPolicyApplicationStartPeriod();
            endDate = entity.getPolicyApplicationEndPeriod();
            link = "/youth/education/" + targetId;
        } else if (mainCategory.equals("지역") && subCategory.equals("문화")) {
            ResourceCulture entity = cultureRepository.findById(targetId).orElseThrow();
            title = entity.getTitle();
            startDate = entity.getStartDate();
            endDate = entity.getEndDate();
            link = entity.getLink();
        }

        return BookmarkDTO.builder()
                .id(bookmark.getId())
                .targetId(targetId)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .atTime(atTime)
                .link(link)
                .build();
    }

    public void register(String userId,
                         String mainCategory,
                         String subCategory,
                         Long targetId) {

        Bookmark bookmark = Bookmark.builder()
                .member(generalRepository.findByUserId(userId).orElseThrow(null))
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .targetId(targetId)
                .atTime(LocalDateTime.now())
                .build();
        bookmarkRepository.save(bookmark);
    }

    public void delete(String userId,
                         String mainCategory,
                         String subCategory,
                         Long targetId) {

        List<Bookmark> entitiyList = bookmarkRepository.findAllByMember_UserId(userId);

        entitiyList.forEach(d-> System.out.println(d));
    }
}
