package project.blobus.Backend.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.mypage.dto.DocumentdDTO;
import project.blobus.Backend.temp.entity.TempCommunityFreeBoard;
import project.blobus.Backend.temp.entity.TempCommunitySuggestBoard;
import project.blobus.Backend.temp.repository.CommunityFreeBoardRepository;
import project.blobus.Backend.temp.repository.CommunitySuggestBoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final CommunityFreeBoardRepository freeBoardRepository;
    private final CommunitySuggestBoardRepository suggestBoardRepository;

    // 전체 게시판 리스트 조회
    public PageResponseDTO<DocumentdDTO> getList(PageRequestDTO pageRequestDTO,
                                                 String authorId,
                                                 String boardType,
                                                 String boardCategory) {
        log.info("Document Get List");

        List<DocumentdDTO> boardList;
        if ("FREE".equalsIgnoreCase(boardType)) {
            //  자유게시판 조회
            boardList = freeBoardRepository.findAll().stream()
                    .filter(board -> (authorId == null || board.getAuthorId().equalsIgnoreCase(authorId)) &&
                            (boardCategory == "" || board.getBoardCategory().equalsIgnoreCase(boardCategory)))
                    .map(board -> toDTO(board, null, "FREE"))
                    .collect(Collectors.toList());
        } else if ("SUGGEST".equalsIgnoreCase(boardType)) {
            // 건의게시판 조회
            boardList = suggestBoardRepository.findAll().stream()
                    .filter(board -> (authorId == null || board.getAuthorId().equalsIgnoreCase(authorId)) &&
                            (boardCategory == "" || board.getBoardCategory().equalsIgnoreCase(boardCategory)))
                    .map(board -> toDTO(null, board, "SUGGEST"))
                    .collect(Collectors.toList());
        } else {
            // 전체 조회
            List<DocumentdDTO> freeBoardList = freeBoardRepository.findAll().stream()
                    .filter(board -> (authorId == null || board.getAuthorId().equalsIgnoreCase(authorId)) &&
                            (boardCategory == "" || board.getBoardCategory().equalsIgnoreCase(boardCategory)))
                    .map(board -> toDTO(board, null, "FREE"))
                    .collect(Collectors.toList());

            List<DocumentdDTO> suggestBoardList = suggestBoardRepository.findAll().stream()
                    .filter(board -> (authorId == null || board.getAuthorId().equalsIgnoreCase(authorId)) &&
                            (boardCategory == "" || board.getBoardCategory().equalsIgnoreCase(boardCategory)))
                    .map(board -> toDTO(null, board, "SUGGEST"))
                    .collect(Collectors.toList());

            boardList = freeBoardList;
            boardList.addAll(suggestBoardList);
        }

        // 생성일자 기준 내림차순 정렬
        boardList = boardList.stream()
                .sorted((b1, b2) -> b2.getCreatedAt().compareTo(b1.getCreatedAt()))
                .collect(Collectors.toList());

        // 전체 게시글 개수
        int totalCount = boardList.size();

        // 페이징 처리
        int startIndex = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        int endIndex = Math.min(startIndex + pageRequestDTO.getSize(), totalCount);
        List<DocumentdDTO> dtoList = boardList.subList(startIndex, endIndex);

        return PageResponseDTO.<DocumentdDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    private DocumentdDTO toDTO(TempCommunityFreeBoard freeBoard,
                               TempCommunitySuggestBoard suggestBoard,
                               String boardType) {
        if (freeBoard != null) {
            return DocumentdDTO.builder()
                    .id(freeBoard.getId())
                    .title(freeBoard.getTitle())
                    .content(freeBoard.getContent())
                    .authorId(freeBoard.getAuthorId())
                    .boardType(boardType)
                    .boardCategory(freeBoard.getBoardCategory())
                    .createdAt(freeBoard.getCreatedAt())
                    .updatedAt(freeBoard.getUpdatedAt())
                    .build();
        } else if (suggestBoard != null) {
            return DocumentdDTO.builder()
                    .id(suggestBoard.getId())
                    .title(suggestBoard.getTitle())
                    .content(suggestBoard.getContent())
                    .authorId(suggestBoard.getAuthorId())
                    .boardType(boardType)
                    .boardCategory(suggestBoard.getBoardCategory())
                    .visibility(suggestBoard.getVisibility())
                    .createdAt(suggestBoard.getCreatedAt())
                    .updatedAt(suggestBoard.getUpdatedAt())
                    .build();
        }
        return null;
    }
}
