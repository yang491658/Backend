package project.blobus.Backend.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.blobus.Backend.main.service.AllSearchService;

import java.util.Map;

@CrossOrigin
@RestController
public class AllSearchController {
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
