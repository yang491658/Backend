package project.blobus.Backend.youth.education;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class EducationDTOTest {
    private Integer policyId;
    private String title;
    private String overview;
    private String applicationPeriodStart; // Date를 String으로 변환
    private String applicationPeriodEnd;
    private String contactInfo;
    private String contactPhone;

    @Autowired
    private ModelMapper modelMapper;

    // Entity -> DTO 변환 메서드
    public EducationDTOTest fromEntity(EducationEntityTest entity) {
        return modelMapper.map(entity, EducationDTOTest.class);
    }

    // DTO -> Entity 변환 메서드
    public EducationEntityTest toEntity() {
        return modelMapper.map(this, EducationEntityTest.class);
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
