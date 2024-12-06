package project.blobus.Backend.resource.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.resource.repository.ResourceCultureRepository;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ResourceCultureService {
    private final ResourceCultureRepository repository;

    @PostConstruct
    public void init() {
        log.info("서버 시작 - 주거정책 오픈 API 정책 데이터 초기화 중...");
        getResource(); // 초기 데이터 로드
    }

    public void getResource() {
    }
}
