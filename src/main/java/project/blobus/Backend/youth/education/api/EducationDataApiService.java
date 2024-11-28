package project.blobus.Backend.youth.education.api;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.blobus.Backend.youth.education.EducationEntity;
import project.blobus.Backend.youth.education.EducationRepository;

@Service
@RequiredArgsConstructor
public class EducationDataApiService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    public void savePolicy(EducationDataApiDTO educationDataApiDTO) {
        EducationEntity educationEntity = modelMapper.map(educationDataApiDTO, EducationEntity.class);
        educationRepository.save(educationEntity);
    }
}