package project.blobus.Backend.community.service.impl;

import lombok.RequiredArgsConstructor;
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
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. ID: " + postId));
        return toDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByBoardType(String boardType) {
        List<Post> posts = postRepository.findByBoardType(boardType);
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
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
