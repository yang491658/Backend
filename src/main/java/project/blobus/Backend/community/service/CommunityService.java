package project.blobus.Backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.community.dto.CommnuntiyDTO;
import project.blobus.Backend.community.entity.Community;
import project.blobus.Backend.community.repository.CommunityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
    @Autowired
    private final CommunityRepository repository;

    // 커뮤니티 목록 조회
    public PageResponseDTO<CommnuntiyDTO> getList(PageRequestDTO pageRequestDTO,
                                                  String boardType,
                                                  String category,
                                                  String keyward) {
        log.info("Communtiy Get List");

        List<CommnuntiyDTO> boardList = repository.findAll().stream()
                .filter(data -> (boardType == "" || data.getBoadrType().equalsIgnoreCase(boardType)) &&
                        (category == "" || data.getCategory().equalsIgnoreCase(category))
                        && (keyward == "" || searchByKeyward(data, keyward)))
                .map(this::toDTO)
                .collect(Collectors.toList());

        boardList = boardList.stream()
                .sorted((b1, b2) -> b2.getId().compareTo(b1.getId()))
                .collect(Collectors.toList());

        int totalCount = boardList.size();

        int startIndex = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        int endIndex = Math.min(startIndex + pageRequestDTO.getSize(), totalCount);
        List<CommnuntiyDTO> dtoList = boardList.subList(startIndex, endIndex);

        return PageResponseDTO.<CommnuntiyDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    // 검색 함수
    private boolean searchByKeyward(Object data, String keyword) {
        if (data == null || keyword == null || keyword.isEmpty()) {
            return false;
        }
        try {
            for (var field : data.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(data);
                if (value != null && value.toString().contains(keyword)) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error :" + e);
        }
        return false;
    }

    // 커뮤니티 게시글 상세 조회
    public CommnuntiyDTO get(Long id) {
        log.info("Commnuntiy Get One");

        Community community = repository.findById(id).orElseThrow();
        return toDTO(community);
    }

    private CommnuntiyDTO toDTO(Community community) {
        return CommnuntiyDTO.builder()
                .id(community.getId())
                .boadrType(community.getBoadrType())
                .category(community.getCategory())
                .title(community.getTitle())
                .content(community.getContent())
                .author(community.getAuthor())
                .authorId(community.getAuthorId())
                .visibility(community.isVisibility())
                .createdAt(community.getCreatedAt())
                .updatedAt(community.getUpdatedAt())
                .build();
    }

    // 커뮤니티 게시글 생성
    public Long register(CommnuntiyDTO commnuntiyDTO) {
        log.info("Commnuntiy Register");

        Community community = toEntity(commnuntiyDTO);
        community.setCreatedAt(LocalDateTime.now());
        community.setUpdatedAt(LocalDateTime.now());
        Community result = repository.save(community);

        return result.getId();
    }

    private Community toEntity(CommnuntiyDTO communityDTO) {
        return Community.builder()
                .boadrType(communityDTO.getBoadrType())
                .category(communityDTO.getCategory())
                .title(communityDTO.getTitle())
                .content(communityDTO.getContent())
                .author(communityDTO.getAuthor())
                .authorId(communityDTO.getAuthorId())
                .visibility(communityDTO.isVisibility())
                .build();
    }

    // 커뮤니티 게시글 수정
    public void modify(CommnuntiyDTO dto) {
        log.info("Commnuntiy Modify");

        Community community = repository.findById(dto.getId()).orElseThrow();

        community.setId(dto.getId());
        community.setTitle(dto.getTitle());
        community.setContent(dto.getContent());
        community.setVisibility(dto.isVisibility());
        community.setUpdatedAt(LocalDateTime.now());

        repository.save(community);
    }

    // 커뮤니티 게시글 삭제
    public void remove(Long id) {
        log.info("Commnuntiy Remove");

        repository.deleteById(id);
    }
}
