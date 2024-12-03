package project.blobus.Backend.community.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.community.dto.CommentDTO;
import project.blobus.Backend.community.service.CommentService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService service;

    // 댓글 목록 조회
    @GetMapping("/{id}")
    public PageResponseDTO<CommentDTO> list(PageRequestDTO pageRequestDTO,
                                            @PathVariable Long postId) {
        return service.getList(pageRequestDTO, postId);
    }

    // 댓글 등록
    @PostMapping("/")
    public Map<String, String> register(@RequestBody CommentDTO dto) {
        service.register(dto);
        return Map.of("register", "SUCCESS");
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public Map<String, String> modify(@PathVariable Long id,
                                      @RequestBody CommentDTO dto) {
        dto.setId(id);
        service.modify(dto);
        return Map.of("modify", "SUCCESS");
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public Map<String, String> remove(@PathVariable Long id) {
        service.remove(id);
        return Map.of("remove", "SUCCESS");
    }
}
