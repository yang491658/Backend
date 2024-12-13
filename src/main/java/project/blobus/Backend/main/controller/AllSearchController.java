package project.blobus.Backend.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.blobus.Backend.main.service.AllSearchService;

import java.util.Map;

// 리액트 경로랑 백엔드 다른 도메인에서 포트에서 동작할때 CORS 정책 오류 해결하기 위한 코드
@CrossOrigin

// Restful웹 서비스의 컨트롤러를 정의하기 위한 코드 HTTP의 데이터를 직접 반환 JSON 형식 또는 XML 형식 데이터 전송
@RestController
public class AllSearchController {

    // AllSearchService에 의존하여 연결하는 것
    @Autowired
    AllSearchService allSearchService;

    @GetMapping(value = "/allSearch", produces = "application/json")
    public Map<String, Object> allSearch(
            @RequestParam("search") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam("category") String category
    ) {

        return allSearchService.getSearchResults(search, page,category);
    }
}
