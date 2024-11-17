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

    // 일반계정 회원가입
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody BusinessDTO businessDTO) {
        Long id = businessService.register(businessDTO);
        return Map.of("id", id);
    }

    // 회원정보 조회
    @GetMapping("/{userId}")
    public boolean duplicate(@PathVariable(name = "userId") String userId) {
        return businessService.duplicate(userId);
    }

//    // 회원정보 수정 + 비밀번호 변경
//    @PutMapping("/{enb}")
//    public Map<String, String> modify(@PathVariable(name = "enb") Long enb,
//                                      @RequestBody EmployeeDTO employeeDTO) {
//        employeeDTO.setEnb(enb);
//        employeeService.modify(employeeDTO);
//        return Map.of("result", "SUCCESS");
//      }

//    // 회사 탈퇴
//    @DeleteMapping("/{enb}")
//    public Map<String, String> remove(@PathVariable(name = "enb") Long enb) {
//        employeeService.remove(enb);
//        return Map.of("result", "SUCCESS");
//    }
}
