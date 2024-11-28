package project.blobus.Backend.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.dto.PostListDTO;
import project.blobus.Backend.community.entity.Post;
import project.blobus.Backend.community.repository.PostRepository;
import project.blobus.Backend.community.service.PostService;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("/add")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long postId) {
        PostDTO post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public Page<PostListDTO> getAllPosts(
            @RequestParam(required = false) Post.BoardType boardType,
            @RequestParam(required = false) Post.UserType userType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findPostListDtos(boardType, userType, pageable);
    }

}


//    // 게시글 생성
//    @PostMapping("/add")
//    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
//        PostDTO createdPost = postService.createPost(postDTO);
//        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
//    }
//
//    // 게시글 단건 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
//        return ResponseEntity.ok(postService.getPostById(id));
//    }
//
//    // 게시글 전체 목록 조회
//    @GetMapping
//    public ResponseEntity<List<PostDTO>> getAllPosts() {
//        return ResponseEntity.ok(postService.getAllPosts());
//    }
//
//    // 특정 게시판의 게시글 조회
//    @GetMapping("/board/{boardType}")
//    public ResponseEntity<List<PostDTO>> getPostsByBoardType(@PathVariable String boardType) {
//        return ResponseEntity.ok(postService.getPostsByBoardType(boardType));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
//        // 게시글 ID를 받아 해당 게시글을 삭제하고 성공 여부를 반환
//        boolean isDeleted = postService.remove(postId);
//        if (isDeleted) {
//            return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);  // 삭제 성공 (HTTP 200)
//        } else {
//            return new ResponseEntity<>("게시글 삭제 실패", HttpStatus.NOT_FOUND);  // 삭제 실패 (HTTP 404)
//        }
//    }
//}
