package project.blobus.Backend.member.role.general.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.member.role.general.dto.GeneralDTO;
import project.blobus.Backend.member.role.general.service.GeneralService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/member/general")
public class GeneralController {
    @Autowired
    private GeneralService generalService;

    // 일반계정 회원가입
    @PostMapping("/")
    public Long register(@RequestBody GeneralDTO dTO) {
        return generalService.register(dTO);
    }

    // 일반계정 회원가입 - 중복 확인
    @GetMapping("/dup/{userId}")
    public boolean duplicate(@PathVariable String userId) {
        return generalService.duplicate(userId);
    }

    // 일반계정 회원가입 - 메일 전송
    @PostMapping("/send/{userId}")
    public Long sendEmail(@PathVariable String userId) {
        return generalService.sendEmail(userId);
    }

    // 일반계정 아이디 찾기
    @GetMapping("/find/{name}&{phoneNum}")
    public String find(@PathVariable String name,
                       @PathVariable String phoneNum) {
        return generalService.find(name, phoneNum);
    }

    // 일반계정 비밀번호 찾기(변경)
    @PutMapping("/find/")
    public Map<String, String> modify(@RequestBody GeneralDTO dTO) {
        generalService.modify(dTO);
        return Map.of("modify", "SUCCESS");
    }
}
