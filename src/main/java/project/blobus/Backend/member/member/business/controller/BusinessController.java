package project.blobus.Backend.member.member.business.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.member.member.business.dto.BusinessDTO;
import project.blobus.Backend.member.member.business.service.BusinessService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/member/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    // 기업계정 회원가입
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody BusinessDTO businessDTO) {
        Long id = businessService.register(businessDTO);
        return Map.of("id", id);
    }

    // 기업계정 회원가입 - 중복 확인
    @GetMapping("/dup/{userId}")
    public boolean duplicate(@PathVariable(name = "userId") String userId) {
        return businessService.duplicate(userId);
    }
}
