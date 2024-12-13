package project.blobus.Backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.entity.CommunityPost;
import project.blobus.Backend.community.repository.PostRepository;
import project.blobus.Backend.community.util.CommunityMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private final PostRepository repository;
    @Autowired
    private final JavaMailSender mailSender;

    // 커뮤니티 목록 조회
    public PageResponseDTO<PostDTO> getList(PageRequestDTO pageRequestDTO,
                                            String boardType,
                                            String category,
                                            String keyward) {
        log.info("Post Get List");

        List<PostDTO> postList = repository.findAll().stream()
                .filter(data -> (boardType == "" || data.getBoardType().equalsIgnoreCase(boardType)) &&
                        (category == "" || data.getCategory().equalsIgnoreCase(category))
                        && (keyward == "" || searchByKeyward(data, keyward)))
                .map(CommunityMapper::postEntityToDto)
                .collect(Collectors.toList());

        postList = postList.stream()
                .sorted((b1, b2) -> b2.getId().compareTo(b1.getId()))
                .collect(Collectors.toList());

        int totalCount = postList.size();

        int startIndex = (pageRequestDTO.getPage() - 1) * pageRequestDTO.getSize();
        int endIndex = Math.min(startIndex + pageRequestDTO.getSize(), totalCount);
        List<PostDTO> dtoList = postList.subList(startIndex, endIndex);

        return PageResponseDTO.<PostDTO>withAll()
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
    public PostDTO get(Long id) {
        log.info("Post Get One");

        CommunityPost post = repository.findById(id).orElseThrow();

        // 이전 게시글 찾기
        CommunityPost prevPost = repository.findFirstByIdGreaterThanOrderByIdAsc(id).orElse(null);
        Long prev = prevPost != null ? prevPost.getId() : null;

        // 다음 게시글 찾기
        CommunityPost nextPost = repository.findFirstByIdLessThanOrderByIdDesc(id).orElse(null);
        Long next = nextPost != null ? nextPost.getId() : null;

        PostDTO postDTO = CommunityMapper.postEntityToDto(post);
        postDTO.setPrev(prev);
        postDTO.setNext(next);

        return postDTO;
    }

    // 커뮤니티 게시글 등록
    public Long register(PostDTO dto) {
        log.info("Post Register");

        CommunityPost post = CommunityMapper.postDtoToEntity(dto);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        CommunityPost result = repository.save(post);

        if (dto.isToEmail()) {
            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setTo("blobus051@gmail.com");
            emailMessage.setSubject("건의게시판이 새글이 등록되었습니다.");
            emailMessage.setText("작성자 ID : " + result.getAuthorId()
                    + "\n\n작성자 이름 : " + result.getAuthorName()
                    + "\n\n작성자 메일 : " + result.getAuthorEmail()
                    + "\n\n제목 : " + result.getTitle()
                    + "\n\n내용 : \n" + result.getContent());
            mailSender.send(emailMessage);
        }

        return result.getId();
    }

    // 커뮤니티 게시글 수정
    public void modify(PostDTO dto) {
        log.info("Post Modify");

        CommunityPost post = repository.findById(dto.getId()).orElseThrow();

        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setVisibility(dto.isVisibility());
        post.setUpdatedAt(LocalDateTime.now());

        repository.save(post);
    }

    // 커뮤니티 게시글 삭제
    public void remove(Long id) {
        log.info("CommunityPost Remove");

        repository.deleteById(id);
    }
}
