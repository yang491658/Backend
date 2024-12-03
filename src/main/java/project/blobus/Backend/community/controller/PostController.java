package project.blobus.Backend.community.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.community.dto.PostDTO;
import project.blobus.Backend.community.service.PostService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService service;

    // 커뮤니티 게시글 목록 조회
    @GetMapping("/list")
    public PageResponseDTO<PostDTO> list(PageRequestDTO pageRequestDTO,
                                         @RequestParam String boardType,
                                         @RequestParam String category,
                                         @RequestParam String keyward) {
        return service.getList(pageRequestDTO, boardType, category, keyward);
    }

    // 커뮤니티 게시글 상세 정보 조회
    @GetMapping("/{id}")
    public PostDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    // 커뮤니티 게시글 등록
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody PostDTO dto) {
        Long id = service.register(dto);
        return Map.of("register", id);
    }

    // 커뮤니티 게시글 수정
    @PutMapping("/{id}")
    public Map<String, String> modify(@PathVariable Long id,
                                      @RequestBody PostDTO dto) {
        dto.setId(id);
        service.modify(dto);
        return Map.of("modify", "SUCCESS");
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/{id}")
    public Map<String, String> remove(@PathVariable Long id) {
        service.remove(id);
        return Map.of("remove", "SUCCESS");
    }
}
