package project.blobus.Backend.youth.house.service;

import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.youth.house.dto.HouseDTO;
import project.blobus.Backend.youth.house.dto.PageRequestDTO;
import project.blobus.Backend.youth.house.dto.PageResponseDTO;

@Transactional
public interface HouseService {
    PageResponseDTO<HouseDTO> getPolicyList(PageRequestDTO pageRequestDTO, String searchTerm, String filterType);   // 정책현황 - 리스트
    HouseDTO getPolicyDetail(Long policyId);    // 정책현황 - 리스트 상세

}
