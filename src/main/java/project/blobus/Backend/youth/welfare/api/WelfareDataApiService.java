package project.blobus.Backend.youth.welfare.api;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.blobus.Backend.youth.education.EducationEntity;
import project.blobus.Backend.youth.education.EducationRepository;
import project.blobus.Backend.youth.welfare.WelfareEntity;
import project.blobus.Backend.youth.welfare.WelfareRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class WelfareDataApiService {

    private final WelfareRepository welfareRepository;

    public void savePolicy(WelfareDataApiDTO welfareDataApiDTO) {
        // 중복 여부 확인
        WelfareEntity existingEntity = welfareRepository.findByBizId(welfareDataApiDTO.getBizId());
        if (existingEntity == null) {
            WelfareEntity welfareEntity = welfareDataApiDTO.toEntity();
            String period = welfareDataApiDTO.getPolicyApplicationPeriod();
            String[] results = extractDates(period);
            if (results != null) {
                LocalDate startDate = LocalDate.parse(results[0]);
                LocalDate endDate;
                try {
                    endDate = LocalDate.parse(results[1]);
                } catch (DateTimeParseException e) {
                    // 11월 31일과 같은 잘못된 날짜가 들어올 경우 11월 30일로 설정
                    endDate = LocalDate.of(2024, 11, 30);
                }
                welfareEntity.setPolicyApplicationStartPeriod(startDate);
                welfareEntity.setPolicyApplicationEndPeriod(endDate);
            }
            welfareRepository.save(welfareEntity);
        } else {
            // 중복된 데이터가 있을 경우 처리 로직 (필요시)
            System.out.println("중복된 biz_id가 존재합니다 : " + welfareDataApiDTO.getBizId());
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