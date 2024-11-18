package project.blobus.Backend.member.member.general.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.blobus.Backend.member.member.general.dto.GeneralDTO;
import project.blobus.Backend.member.member.general.service.GeneralService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/member/general")
public class GeneralController {
    @Autowired
    private GeneralService generalService;

    // 일반계정 회원가입
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody GeneralDTO generalDTO) {
        Long id = generalService.register(generalDTO);
        return Map.of("id", id);
    }

    // 일반계정 회원가입 - 중복 확인
    @GetMapping("/dup/{userId}")
    public boolean duplicate(@PathVariable(name = "userId") String userId) {
        return generalService.duplicate(userId);
    }

    // 일반계정 회원가입 - 메일 전송
    @PostMapping("/send/{userId}")
    public Long sendEmail(@PathVariable(name = "userId") String userId) {
        return generalService.sendEmail(userId);
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
