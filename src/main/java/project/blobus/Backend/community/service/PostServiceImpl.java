package project.blobus.Backend.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.community.dto.CommentDTO;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.dto.PostListDTO;
import project.blobus.Backend.community.entity.Post;
import project.blobus.Backend.community.repository.PostRepository;
import project.blobus.Backend.community.service.PostService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        if (postDTO == null) {
            throw new IllegalArgumentException("게시글은 null값이 되면 안됩니다.");
        }

        // 임시 이메일 설정 (나중에 로그인 시 변경될 부분)
        String temporaryEmail = "testuser@example.com";  // 임시 이메일

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .boardType(postDTO.getBoardType())
                .userType(postDTO.getUserType())
                .createdAt(postDTO.getCreatedAt())
                .authorId(temporaryEmail)
                .build();

        Post savedPost = postRepository.save(post);

        return PostDTO.builder()
                .id(savedPost.getId())
                .content(savedPost.getContent())
                .title(savedPost.getTitle())
                .boardType(savedPost.getBoardType())
                .userType(savedPost.getUserType())
                .createdAt(savedPost.getCreatedAt())
                .authorId(savedPost.getAuthorId())
                .build();
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. ID: " + postId));
        return PostDTO.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .title(post.getTitle())
                .content(post.getContent())
                .userType(post.getUserType())
                .boardType(post.getBoardType())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

//    @Override
//    public List<PostListDTO> getAllPosts(int page, int size, String boardType, String userType) {
//        // Pageable 객체 생성 (page는 0부터 시작하므로 -1)
//        Pageable pageable = PageRequest.of(page - 1, size);
//
//        // 필터링 조건을 위한 Specification 생성
//        Specification<Post> spec = Specification.where(null);
//
//        // boardType이 있을 경우 필터링 추가
//        if (boardType != null && !boardType.isEmpty()) {
//            spec = spec.and((root, query, builder) -> builder.equal(root.get("boardType"), boardType));
//        }
//
//        // userType이 있을 경우 필터링 추가
//        if (userType != null && !userType.isEmpty()) {
//            spec = spec.and((root, query, builder) -> builder.equal(root.get("userType"), userType));
//        }
//
//        // 페이지네이션과 필터링을 적용하여 데이터를 조회
//        Page<PostL> postPage = postRepository.findAll(spec, pageable);
//
//        // PostListDTO로 변환 후 반환
//        return postPage.stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<PostListDTO> getAllPosts(int page, int size, Post.BoardType boardType, Post.UserType userType) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // boardType과 userType을 사용하여 PostListDTO를 조회
        Page<PostListDTO> postPage = postRepository.findPostListDtos(boardType, userType, pageable);

        // 반환된 Page에서 PostListDTO만 추출하여 리스트로 반환
        return postPage.getContent();
    }


    @Override
    public List<PostListDTO> getPostsByBoardType(String boardType) {
        List<Post> posts = postRepository.findByBoardType(boardType);
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean remove(Long postId) {
        if (postId == null) {
            throw new IllegalArgumentException("Post ID cannot be null");
        }

        if (this.postRepository.existsById(postId)) {
            this.postRepository.deleteById(postId);
            return true; // 삭제 성공
        }

        return false; // 삭제 실패
    }

    // 엔티티 -> PostDTO 변환 메서드
    private PostDTO toDTOWithComments(Post post) {
        List<CommentDTO> commentDTOs = post.getComments().stream()
                .map(comment -> CommentDTO.builder()
                        .id(comment.getId())
                        .authorId(comment.getAuthorId())
                        .content(comment.getContent())
                        .createdAt(LocalDateTime.from(comment.getCreatedAt()))
                        .updatedAt(LocalDateTime.from(comment.getUpdatedAt()))
                        .build())
                .collect(Collectors.toList());

        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
//                .comments(commentDTOs)  // CommentDTO로 변환된 댓글 리스트
                .createdAt(LocalDateTime.from(post.getCreatedAt()))
                .build();
    }

    // 엔티티 -> PostListDTO 변환 메서드
    private PostListDTO toDTO(Post post) {
        // LocalDateTime을 String으로 변환
        String createdAt = post.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return new PostListDTO(
                post.getId(),
                post.getTitle(),
                post.getCreatedAt(),  // LocalDateTime을 String으로 변환
                post.getAuthorId()
        );
    }
}
