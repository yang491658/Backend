package project.blobus.Backend.youth.education.api;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.blobus.Backend.youth.education.EducationEntity;
import project.blobus.Backend.youth.education.EducationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EducationDataApiService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    public void savePolicy(EducationDataApiDTO educationDataApiDTO) {
        // 중복 여부 확인
        EducationEntity existingEntity = educationRepository.findByBizId(educationDataApiDTO.getBizId());
        if (existingEntity == null) {
            EducationEntity educationEntity = modelMapper.map(educationDataApiDTO, EducationEntity.class);
            educationEntity.setPolicyApplicationPeriod(educationDataApiDTO.getPolicyApplicationPeriod());
            String period = educationDataApiDTO.getPolicyApplicationPeriod();
            String[] results = extractDates(period);
            if(results != null) {
                LocalDate startDate = LocalDate.parse(results[0]);
                LocalDate endDate = LocalDate.parse(results[1]);
                educationEntity.setPolicyApplicationStartPeriod(startDate);
                educationEntity.setPolicyApplicationEndPeriod(endDate);
            }
            educationRepository.save(educationEntity);
        } else {
            // 중복된 데이터가 있을 경우 처리 로직 (필요시)
            System.out.println("중복된 biz_id가 존재합니다 : " + educationDataApiDTO.getBizId());
        }
    }

    public static String[] extractDates(String input) {
        // 정규식 패턴: "YYYY-MM-DD~YYYY-MM-DD" 형태의 날짜 범위를 찾음
        String regex = "(\\d{4}-\\d{2}-\\d{2})~(\\d{4}-\\d{2}-\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            System.out.println(matcher.group(1));
            // 매칭된 두 날짜 반환
            return new String[]{matcher.group(1), matcher.group(2)};

        }
        System.out.println("null");
        return null;  // 날짜 범위를 찾지 못하면 null 반환
    }

}