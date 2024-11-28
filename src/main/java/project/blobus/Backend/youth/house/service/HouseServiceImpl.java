package project.blobus.Backend.youth.house.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.youth.house.dto.HouseDTO;
import project.blobus.Backend.youth.house.dto.PageRequestDTO;
import project.blobus.Backend.youth.house.dto.PageResponseDTO;
import project.blobus.Backend.youth.house.entity.HouseEntity;
import project.blobus.Backend.youth.house.repository.HouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HouseServiceImpl implements HouseService{
    private final ModelMapper modelMapper;
    private final HouseRepository houseRepository;

    // 정책현황 - 리스트
    @Override
    public PageResponseDTO getPolicyList(PageRequestDTO pageRequestDTO) {
        log.info("HouseServiceImpl - getPolicyList --------------------------- ");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("policyId").descending()
        );

        Page<HouseEntity> result = houseRepository.findAll(pageable);
        List<HouseDTO> dtoList = result.getContent().stream().map(
                (houseEntity) -> modelMapper.map(houseEntity, HouseDTO.class)
        ).collect(Collectors.toList());
        HouseEntity.builder().build();
        long totalCount = result.getTotalElements();
        PageResponseDTO pageResponseDTO = PageResponseDTO.<HouseDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();

        return pageResponseDTO;
    }

    // 정책현황 - 리스트 상세
    @Override
    public HouseDTO getPolicyDetail(Long policyId) {
        Optional<HouseEntity> result = houseRepository.findById(policyId);
        HouseEntity houseEntity = result.orElseThrow();
        HouseDTO houseDTO = modelMapper.map(houseEntity, HouseDTO.class);
        return houseDTO;
    }
}
