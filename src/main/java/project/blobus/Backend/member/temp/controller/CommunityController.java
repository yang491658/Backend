package project.blobus.Backend.member.temp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.member.temp.dto.CommunityBoardDTO;
import project.blobus.Backend.member.temp.service.CommunityService;

@Log4j2
@RestController
@RequestMapping("/mypage")
public class CommunityController {
    @Autowired
    private CommunityService service;

    @GetMapping("/list")
    public PageResponseDTO<CommunityBoardDTO> list(PageRequestDTO pageRequestDTO,
                                                   @RequestParam String userId,
                                                   @RequestParam String boardType,
                                                   @RequestParam String boardCategory) {
        return service.getList(pageRequestDTO, userId, boardType, boardCategory);
    }
}
