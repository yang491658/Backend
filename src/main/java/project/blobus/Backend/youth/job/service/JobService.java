package project.blobus.Backend.youth.job.service;

import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.youth.job.dto.JobDTO;
import project.blobus.Backend.youth.job.dto.PageRequestDTO;
import project.blobus.Backend.youth.job.dto.PageResponseDTO;

@Transactional
public interface JobService {
    PageResponseDTO<JobDTO> getPolicyList(PageRequestDTO pageRequestDTO, String searchTerm, String filterType); // 정책현황 - 리스트
    JobDTO getPolicyDetail(Long policyId);  // 정책현황 - 리스트 상세
}
