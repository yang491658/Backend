package project.blobus.Backend.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.community.dto.CommentDTO;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.entity.Post;
import project.blobus.Backend.community.repository.PostRepository;
import project.blobus.Backend.community.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        if (postDTO == null){
            throw new IllegalArgumentException("게시글은 null값이 되면 안됩니다.");
        }

        Post post = Post.builder()
                        .title(postDTO.getTitle())
                        .content(postDTO.getContent())
                        .boardType(postDTO.getBoardType())
                        .createdAt(postDTO.getCreatedAt())
                        .authorId(postDTO.getAuthorId())
                        .build();

        Post savedPost = postRepository.save(post);

        PostDTO savedPostDTO = PostDTO.builder()
                .id(savedPost.getId())
                .content(savedPost.getContent())
                .title(savedPost.getTitle())
                .boardType(savedPost.getBoardType())
                .createdAt(savedPost.getCreatedAt())
                .authorId(savedPost.getAuthorId())
                .build();

        return savedPostDTO;
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. ID: " + postId));
        return toDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts(int page, int size, String boardType, String userType, String searchTerm) {
        // Pageable 객체 생성 (page는 0부터 시작하므로 -1)
        Pageable pageable = PageRequest.of(page - 1, size);

        // 필터링 조건을 위한 Specification 생성
        Specification<Post> spec = Specification.where(null);

        // boardType이 있을 경우 필터링 추가
        if (boardType != null && !boardType.isEmpty()) {
            spec = spec.and((root, query, builder) -> builder.equal(root.get("boardType"), boardType));
        }

        // userType이 있을 경우 필터링 추가
        if (userType != null && !userType.isEmpty()) {
            spec = spec.and((root, query, builder) -> builder.equal(root.get("userType"), userType));
        }

        // searchTerm이 있을 경우 필터링 추가
        if (searchTerm != null && !searchTerm.isEmpty()) {
            spec = spec.and((root, query, builder) -> builder.like(root.get("title"), "%" + searchTerm + "%")
                    .in(builder.like(root.get("content"), "%" + searchTerm + "%")));
        }

        // 페이지네이션과 필터링을 적용하여 데이터를 조회
        Page<Post> postPage = postRepository.findAll(spec, pageable);

        // PostDTO로 변환 후 반환
        return postPage.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<PostDTO> getPostsByBoardType(String boardType) {
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


    // 엔티티 -> DTO 변환 메서드
    private PostDTO toDTO(Post post) {
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
                .comments(commentDTOs)  // CommentDTO로 변환된 댓글 리스트
                .createdAt(LocalDateTime.from(post.getCreatedAt()))
                .build();
    }
}
