package project.blobus.Backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.community.entity.CommunityPost;
import project.blobus.Backend.community.repository.PostRepository;
import project.blobus.Backend.mypage.dto.DocumentdDTO;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final PostRepository repository;

    // 전체 게시판 리스트 조회
    public PageResponseDTO<DocumentdDTO> getList(PageRequestDTO pageRequestDTO,
                                                 String userId,
                                                 String boardType,
                                                 String category) {
        log.info("Document Get List");

        List<DocumentdDTO> docList;
        if (boardType.isEmpty()) {
            docList = repository.findAll().stream()
                    .filter(doc
                            -> (doc.getAuthorId().equalsIgnoreCase(userId)
                            || (doc.getCommentList().stream().anyMatch(comment -> comment.getAuthorId().equalsIgnoreCase(userId))))
                            && (category.isEmpty() || doc.getCategory().equalsIgnoreCase(category)))
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } else if (boardType.equals("댓글")) {
            docList = repository.findAll().stream()
                    .filter(doc
                            -> (doc.getCommentList().stream().anyMatch(comment -> comment.getAuthorId().equalsIgnoreCase(userId)))
                            && (category.isEmpty() || doc.getCategory().equalsIgnoreCase(category)))
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } else {
            docList = repository.findAll().stream()
                    .filter(doc
                            -> (doc.getAuthorId().equalsIgnoreCase(userId))
                            && (doc.getBoardType().equalsIgnoreCase(boardType))
                            && (category.isEmpty() || doc.getCategory().equalsIgnoreCase(category)))
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        docList = docList.stream()
                .sorted((b1, b2) -> b2.getId().compareTo(b1.getId()))
                .collect(Collectors.toList());

        int totalCount = docList.size();

        int startIndex = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        int endIndex = Math.min(startIndex + pageRequestDTO.getSize(), totalCount);
        List<DocumentdDTO> dtoList = docList.subList(startIndex, endIndex);

        return PageResponseDTO.<DocumentdDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    private DocumentdDTO toDTO(CommunityPost post) {
        return DocumentdDTO.builder()
                .id(post.getId())
                .boardType(post.getBoardType())
                .category(post.getCategory())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthorId())
                .authorName(post.getAuthorName())
                .authorEmail(post.getAuthorEmail())
                .visibility(post.isVisibility())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .commentCount(post.getCommentList().size())
                .build();
    }
}
