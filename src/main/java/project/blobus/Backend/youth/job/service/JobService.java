package project.blobus.Backend.youth.job.service;

import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.youth.job.dto.JobDTO;
import project.blobus.Backend.youth.job.dto.PageRequestDTO;
import project.blobus.Backend.youth.job.dto.PageResponseDTO;

@Transactional
public interface JobService {
    // BLOBUS > 청년관 > 일자리
    // 1.정책현황
    // 정책현황 - 생성

    // 정책현황 - 리스트
    PageResponseDTO<JobDTO> getPolicyList(PageRequestDTO pageRequestDTO, String policyStsType, String searchTerm, String filterType);
    // 정책현황 - 리스트 상세
    JobDTO getPolicyDetail(Long policyId);
    // 정책현황 - 수정
    void policyModify(JobDTO jobDTO);
    // 정책현황 - 삭제
    void policyRemove(Long policyId);

    // 2.일자리정보
    // 일자리정보 - 생성
    // 일자리정보 - 리스트
    // 일자리정보 - 리스트 상세
    // 일자리정보 - 수정
    // 일자리정보 - 삭제
}
