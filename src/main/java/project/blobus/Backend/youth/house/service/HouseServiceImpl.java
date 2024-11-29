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
    public PageResponseDTO getPolicyList(PageRequestDTO pageRequestDTO,
                                         String searchTerm,
                                         String filterType) {
        log.info("HouseServiceImpl - getPolicyList --------------------------- ");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("policyId").descending()
        );

        Page<HouseEntity> result;

        // 검색어와 필터 타입이 있을 경우 조건 처리
        if (searchTerm != null && !searchTerm.isEmpty()) {
            switch (filterType) {
                case "polyBizSjnm":
                    result = houseRepository.findByPolyBizSjnmContaining(searchTerm, pageable);
                    break;
                case "polyItcnCn":
                    result = houseRepository.findByPolyItcnCnContaining(searchTerm, pageable);
                    break;
                case "both":
                    result = houseRepository.findByPolyBizSjnmContainingOrPolyItcnCnContaining(searchTerm, searchTerm, pageable);
                    break;
                default:
                    result = houseRepository.findAll(pageable);
            }
        } else {
            result = houseRepository.findAll(pageable); // 검색어가 없으면 전체 조회
        }

        List<HouseDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity, HouseDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<HouseDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
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
