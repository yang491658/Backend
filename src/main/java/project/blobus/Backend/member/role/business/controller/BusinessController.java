package project.blobus.Backend.member.role.business.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.member.role.business.dto.BusinessDTO;
import project.blobus.Backend.member.role.business.service.BusinessService;

@Log4j2
@RestController
@RequestMapping("/member/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    // 기업계정 회원가입
    @PostMapping("/register")
    public Long register(@RequestBody BusinessDTO dTO) {
        return businessService.register(dTO);
    }

    // 기업계정 회원가입 - 중복 확인
    @GetMapping("/dup/{userId}")
    public boolean duplicate(@PathVariable String userId) {
        return businessService.duplicate(userId);
    }
}
