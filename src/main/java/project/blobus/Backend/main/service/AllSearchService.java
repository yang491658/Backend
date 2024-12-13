package project.blobus.Backend.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.blobus.Backend.main.dto.AllSearchDTO;
import project.blobus.Backend.main.repository.AllSearchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AllSearchService {
    @Autowired
    AllSearchRepository allSearchRepository;

    public Map<String, Object> getSearchResults(String search, int page,String category) {
        Map<String, Object> map = new HashMap<>();

        // end = page 1 * 10 = 10개
        // start = 10 - 9 - 1;
        // 페이지 번호 계산 (0부터 시작하므로 -1)
        int end = page * 10;
        int start = end - 9 - 1;


        if ("policy".equalsIgnoreCase(category)) {
            // 정책 관련 데이터 검색
            List<AllSearchDTO> policyTitles = allSearchRepository.getPolicyTitles(search, start);
            Integer totalItem = allSearchRepository.countPolicyTitles(search);
            Integer totalPage = totalItem / 10; // 계산 공식 작성
            if(totalItem % 10 != 0) {
                totalPage += 1;
            }
            Integer totalItem2 = allSearchRepository.countCommunityPostTitles(search);
            Integer totalPage2 = totalItem2 / 10; // 계산 공식 작성
            if(totalItem2 % 10 != 0) {
                totalPage2 += 1;
            }

            //            List<String> result = policyTitles.stream()
//                    .map(AllSearchDTO::getTitle)
//                    .collect(Collectors.toList());

            List<Map<String, Object>> result = policyTitles.stream()
                    .map(dto -> {
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("id", dto.getId());
                        map2.put("title", dto.getTitle());
                        map2.put("category", dto.getCategory());
                        return map2;
                    })
                    .collect(Collectors.toList());

            map.put("list", result);

            Map<String, Object> policyData = new HashMap<>();
            policyData.put("totalItem", totalItem);  // 예시 값 (실제 값은 실제 총 개수를 기반으로 계산해야 함)
            policyData.put("totalPage", totalPage);   // 예시 값 (실제 총 페이지 수 계산 필요)

            Map<String, Object> communityData = new HashMap<>();
            communityData.put("totalItem", totalItem2);  // 예시 값 (실제 값은 실제 총 개수를 기반으로 계산해야 함)
            communityData.put("totalPage", totalPage2);   // 예시 값 (실제 총 페이지 수 계산 필요)

            map.put("policy", policyData);   // 예시 값 (실제 총 페이지 수 계산 필요)
            map.put("community", communityData);   // 예시 값 (실제 총 페이지 수 계산 필요)



        } else if ("community".equalsIgnoreCase(category)) {

            // 커뮤니티 게시글 검색
            List<AllSearchDTO> communityPosts = allSearchRepository.findAllCommunityPostTitles(search, start);
            Integer totalItem = allSearchRepository.countCommunityPostTitles(search);
            Integer totalPage = totalItem / 10; // 계산 공식 작성
            if(totalItem % 10 != 0) {
                totalPage += 1;
            }

            Integer totalItem2 = allSearchRepository.countPolicyTitles(search);
            Integer totalPage2 = totalItem2 / 10; // 계산 공식 작성
            if(totalItem2 % 10 != 0) {
                totalPage2 += 1;
            }

//            List<String> result = communityPosts.stream()
//                    .map(AllSearchDTO::getTitle)
//                    .collect(Collectors.toList());

            List<Map<String, Object>> result = communityPosts.stream()
                    .map(dto -> {
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("id", dto.getId());
                        map2.put("title", dto.getTitle());
                        map2.put("visibility",dto.getVisibility());
                        map2.put("author_id",dto.getAuthorId());
                        return map2;
                    })
                    .collect(Collectors.toList());

            map.put("list", result);

            Map<String, Object> policyData = new HashMap<>();
            policyData.put("totalItem", totalItem2);  // 예시 값 (실제 값은 실제 총 개수를 기반으로 계산해야 함)
            policyData.put("totalPage", totalPage2);   // 예시 값 (실제 총 페이지 수 계산 필요)

            Map<String, Object> communityData = new HashMap<>();
            communityData.put("totalItem", totalItem);  // 예시 값 (실제 값은 실제 총 개수를 기반으로 계산해야 함)
            communityData.put("totalPage", totalPage);   // 예시 값 (실제 총 페이지 수 계산 필요)

            map.put("policy", policyData);   // 예시 값 (실제 총 페이지 수 계산 필요)
            map.put("community", communityData);   // 예시 값 (실제 총 페이지 수 계산 필요)
        } else {
            map.put("error", "Invalid category");
        }

        return map;

        }
}
