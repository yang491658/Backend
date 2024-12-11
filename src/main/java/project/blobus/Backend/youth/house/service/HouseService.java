package project.blobus.Backend.youth.house.service;

import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.youth.house.dto.HouseDTO;
import project.blobus.Backend.youth.house.dto.PageRequestDTO;
import project.blobus.Backend.youth.house.dto.PageResponseDTO;

@Transactional
public interface HouseService {
    // BLOBUS > 청년관 > 주거
    // 1.정책현황
    // 정책현황 - 생성

    // 정책현황 - 리스트
    PageResponseDTO<HouseDTO> getPolicyList(PageRequestDTO pageRequestDTO, String policyStsType, String searchTerm, String filterType);
    // 정책현황 - 리스트 상세
    HouseDTO getPolicyDetail(Long policyId);
    // 정책현황 - 수정
    void policyModify(HouseDTO houseDTO);
    // 정책현황 - 삭제
    void policyRemove(Long policyId);
}
