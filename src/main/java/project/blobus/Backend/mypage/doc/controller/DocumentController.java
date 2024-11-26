package project.blobus.Backend.mypage.doc.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.mypage.doc.dto.DocumentdDTO;
import project.blobus.Backend.mypage.doc.service.DocumentService;

@Log4j2
@RestController
@RequestMapping("/mypage")
public class DocumentController {
    @Autowired
    private DocumentService service;

    @GetMapping("/list")
    public PageResponseDTO<DocumentdDTO> list(PageRequestDTO pageRequestDTO,
                                              @RequestParam String userId,
                                              @RequestParam String boardType,
                                              @RequestParam String boardCategory) {
        return service.getList(pageRequestDTO, userId, boardType, boardCategory);
    }
}
