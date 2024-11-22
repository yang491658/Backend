package project.blobus.Backend.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // 게시글 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // 특정 게시판의 게시글 조회
    @GetMapping("/board/{boardType}")
    public ResponseEntity<List<PostDTO>> getPostsByBoardType(@PathVariable String boardType) {
        return ResponseEntity.ok(postService.getPostsByBoardType(boardType));
    }
}
