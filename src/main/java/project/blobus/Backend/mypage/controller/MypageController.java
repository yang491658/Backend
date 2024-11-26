package project.blobus.Backend.mypage.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.mypage.dto.BookmarkDTO;
import project.blobus.Backend.mypage.dto.DocumentdDTO;
import project.blobus.Backend.mypage.service.BookmarkService;
import project.blobus.Backend.mypage.service.DocumentService;

@Log4j2
@RestController
@RequestMapping("/mypage")
public class MypageController {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private DocumentService documentService;

    @GetMapping("/bookmark")
    public PageResponseDTO<BookmarkDTO> getBookmark(PageRequestDTO pageRequestDTO,
                                                    @RequestParam String userId,
                                                    @RequestParam String category) {
        return bookmarkService.getList(pageRequestDTO, userId, category);
    }

    @GetMapping("/doc")
    public PageResponseDTO<DocumentdDTO> list(PageRequestDTO pageRequestDTO,
                                              @RequestParam String userId,
                                              @RequestParam String boardType,
                                              @RequestParam String boardCategory) {
        return documentService.getList(pageRequestDTO, userId, boardType, boardCategory);
    }
}
