package project.blobus.Backend.youth.job.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {
    private List<E> dtoList;
    private List<Integer> pageNumList;
    private PageRequestDTO pageRequestDTO;
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int)totalCount;

        int pageSize = 5; // 페이지 번호 리스트의 크기 (변경: 5개로 설정)
        int end = (int)(Math.ceil(pageRequestDTO.getPage() / (double)pageSize)) * pageSize;
        int start = end - (pageSize - 1);
        int last = (int)(Math.ceil(totalCount/(double)pageRequestDTO.getSize()));   // 마지막 페이지구하기
        end = end > last ? last : end;  // end가 last보다 크면 last로 설정

        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        if(prev) this.prevPage = start - 1;
        if(next) this.nextPage = end + 1;

        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDTO.getPage();
    }
}
